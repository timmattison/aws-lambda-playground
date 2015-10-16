package com.timmattison.lifx;

import java.io.IOException;

/**
 * Created by timmattison on 10/16/15.
 */
public interface LifxController {
    /**
     * Run an action on the LIFX cloud
     *
     * @param action
     * @throws IOException
     */
    void runAction(Action action) throws IOException;

    /**
     * Activate a scene on the LIFX cloud
     *
     * @param scene
     * @throws IOException
     */
    void activateScene(Scene scene) throws IOException;
}
