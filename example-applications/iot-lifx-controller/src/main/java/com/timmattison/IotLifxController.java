package com.timmattison;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.common.collect.ImmutableMap;
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
