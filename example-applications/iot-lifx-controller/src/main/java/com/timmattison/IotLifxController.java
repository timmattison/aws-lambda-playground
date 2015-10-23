package com.timmattison;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.timmattison.button.IotButtonData;
import com.timmattison.configuration.Configuration;
import com.timmattison.guice.SharedInjector;
import com.timmattison.lifx.Action;
import com.timmattison.lifx.LifxController;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by timmattison on 10/16/15.
 */
public class IotLifxController {
    public static final String DETAIL_TYPE = "detail-type";
    public static final String SCHEDULED_EVENT = "Scheduled Event";

    private final Configuration configuration = SharedInjector.getInjector().getInstance(Configuration.class);
    private final LifxController lifxController = SharedInjector.getInjector().getInstance(LifxController.class);

    /**
     * Process the incoming JSON as a HashMap and perform the LIFX actions.  This is referenced in AWS Lambda
     * as: com.timmattison.IotLifxController::handleRequest
     *
     * @param request
     * @param context
     * @return
     * @throws IOException
     */
    public String handleRequest(HashMap request, Context context) throws IOException {
        if ((request.containsKey(DETAIL_TYPE)) && (request.get(DETAIL_TYPE).equals(SCHEDULED_EVENT))) {
            // This is a scheduled job just to keep the system warm.  Return immediately.
            return SCHEDULED_EVENT;
        }

        // Convert the request into the IOT button data structure
        IotButtonData iotButtonData = new IotButtonData(ImmutableMap.copyOf(request));

        // Get the action associated with the type of click detected
        Action action = configuration.getAction(iotButtonData.getClickType());

        // Run the action on the LIFX cloud API with the LIFX controller
        lifxController.runAction(action);

        // Return a string to the caller
        return "SUCCESS!";
    }
}
