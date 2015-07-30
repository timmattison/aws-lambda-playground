package com.timmattison;

import com.amazonaws.services.lambda.runtime.Context;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by timmattison on 7/30/15.
 */
public class ImageHandler {
    private final String imageKey = "image";

    /**
     * Process the incoming JSON as a HashMap and return the image EXIF data, if any.  This is referenced in AWS Lambda
     * as: com.timmattison.ImageHandler::handleRequest
     *
     * @param request
     * @param context
     * @return
     * @throws ImageProcessingException
     * @throws IOException
     */
    public String handleRequest(HashMap request, Context context) throws ImageProcessingException, IOException {
        // Get the base64 encoded image data
        String imageBase64Encoded = (String) request.get(imageKey);

        // Is there any?
        if (imageBase64Encoded == null) {
            // No, just return NULL
            return null;
        }

        // Decode the base64 data
        byte[] imageBytes = Base64.decodeBase64(imageBase64Encoded);

        // Extract the metadata
        Metadata metadata = ImageMetadataReader.readMetadata(new ByteArrayInputStream(imageBytes));

        StringBuilder stringBuilder = new StringBuilder();
        String separator = "";

        // Loop through each metadata directory
        for (Directory directory : metadata.getDirectories()) {
            // Get each metadata tag and add it to the string builder
            for (Tag tag : directory.getTags()) {
                stringBuilder.append(separator);
                stringBuilder.append(tag);
                separator = ", ";
            }
        }

        // Return the complete string
        return stringBuilder.toString();
    }
}
