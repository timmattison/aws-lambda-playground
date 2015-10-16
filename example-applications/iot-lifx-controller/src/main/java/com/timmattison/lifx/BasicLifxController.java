package com.timmattison.lifx;

import com.google.gson.Gson;
import com.timmattison.configuration.Configuration;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by timmattison on 10/16/15.
 */
public class BasicLifxController implements LifxController {
    private final Configuration configuration;

    @Inject
    public BasicLifxController(Configuration configuration) {
        this.configuration = configuration;
    }

    public void activateScene(Scene scene) throws IOException {
        // Build the URL to activate a scene
        String url = configuration.getLifxApiPrefix() + "/scenes/scene_id:" + scene.getUuid() + "/activate";

        // Execute an HTTP PUT without a body
        executePut(url);
    }

    public void setState(State state) throws IOException {
        // Build the URL to update some bulb states
        String url = configuration.getLifxApiPrefix() + "/lights/" + state.getSelector() + "/state";

        // Build the body structure
        Map body = new HashMap<String, String>();
        body.putAll(state.getData().unwrapped());

        // Remove the selector from the body structure, it is only supposed to be in the header
        body.remove(State.SELECTOR);

        // Execute an HTTP PUT with the body as JSON
        executePut(url, new Gson().toJson(body));
    }

    private void executePut(String url) throws IOException {
        executePut(url, null);
    }

    private void executePut(String url, String body) throws IOException {
        HttpPut httpPut = new HttpPut(url);

        // Build the authorization header
        httpPut.setHeader("Authorization", "Bearer " + configuration.getLifxApiKey());

        // Create an HTTP client
        HttpClient httpClient = HttpClientBuilder.create().useSystemProperties().build();

        // Is there a body?
        if (body != null) {
            // Yes, convert the body to an HTTP entity
            HttpEntity inputEntity = new ByteArrayEntity(body.getBytes("UTF-8"));

            // Set the content type to indicate the body is JSON
            httpPut.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            // Add the entity to the PUT
            httpPut.setEntity(inputEntity);
        }

        // Execute the PUT
        HttpResponse response = httpClient.execute(httpPut);

        // Get the status code
        int statusCode = response.getStatusLine().getStatusCode();

        // Was there an error?
        if (statusCode > 400) {
            // Yes, get the entity result
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            // Log it and throw an exception
            System.err.println("ERROR from LIFX cloud");
            System.err.println(result);
            throw new UnsupportedOperationException("ERROR from LIFX cloud, check logs");
        }
    }

    public void runAction(Action action) throws IOException {
        Scene scene = action.getScene();

        // Is there a scene?
        if (scene != null) {
            // Yes, activate it
            activateScene(scene);
            return;
        }

        State state = action.getState();

        // Is there a state
        if (state != null) {
            // Yes, set it
            setState(state);
            return;
        }

        // Action not understood, throw an exception
        throw new UnsupportedOperationException("Invalid action");
    }
}
