package com.timmattison.configuration;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.*;
import com.timmattison.lifx.Scene;
import com.timmattison.lifx.SceneList;

import java.util.List;

/**
 * Created by timmattison on 10/22/15.
 */
public class BasicSceneRotatorPersistence implements SceneRotatorPersistence {
    public static final String IOT_LIFX_CONTROLLER_QUEUE_PREFIX = "iot-lifx-controller";

    public Scene getNextScene(SceneList sceneList) {
        // Get our credentials
        AWSCredentials credentials = null;
        try {
            credentials = new EnvironmentVariableCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }

        // Get an SQS client for the US-East-1 region
        AmazonSQS sqs = new AmazonSQSClient(credentials);
        Region usEast1 = Region.getRegion(Regions.US_EAST_1);
        sqs.setRegion(usEast1);

        // Create a queue for temporary storage if it doesn't exist already
        int sceneListHashCode = Math.abs(sceneList.hashCode());
        CreateQueueRequest createQueueRequest = new CreateQueueRequest(IOT_LIFX_CONTROLLER_QUEUE_PREFIX + "-" + sceneListHashCode);
        String queueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();

        // Get any messages out of the queue.  Hopefully there's only one but it doesn't really matter.
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();

        String lastBody = null;

        // Loop through, grab the state info, and delete the messages
        for (Message message : messages) {
            lastBody = message.getBody();

            String messageReceiptHandle = message.getReceiptHandle();
            sqs.deleteMessage(new DeleteMessageRequest(queueUrl, messageReceiptHandle));
        }

        // Get the list of scenes
        List<String> uuids = sceneList.getScenes();

        // Find the index of the last scene we used.  If we can't find it we'll get -1, which is OK.
        int lastSceneIndex = uuids.indexOf(lastBody);

        // Go to the next scene in the list, or go to the first scene in the list if lastSceneIndex is -1
        int nextSceneIndex = (lastSceneIndex + 1) % uuids.size();

        // Get the string for the next scene and persist it back into the queue as our last scene
        String nextScene = uuids.get(nextSceneIndex);
        sqs.sendMessage(new SendMessageRequest(queueUrl, nextScene));

        // Return the scene
        return new Scene(nextScene);
    }
}
