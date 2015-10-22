package com.timmattison.lifx;

import com.typesafe.config.ConfigList;
import com.typesafe.config.ConfigObject;

/**
 * Created by timmattison on 10/17/15.
 */
public class Action {
    public static final String sceneList = "scenes";
    public static final String stateList = "states";
    public static final String singleState = "state";
    public static final String singleScene = "scene";

    private ConfigObject configObject;

    public Action(ConfigObject configObject) {
        this.configObject = configObject;
    }

    public Scene getScene() {
        // Is there a single scene in the config?
        if (!configObject.containsKey(singleScene)) {
            // No, return NULL
            return null;
        }

        // Extract the scene ID and return it as a scene object
        String sceneId = (String) configObject.get(singleScene).unwrapped();

        return new Scene(sceneId);
    }

    public State getState() {
        // Is there a single state in the config?
        if (!configObject.containsKey(singleState)) {
            // No, return NULL
            return null;
        }

        // Extract the state configuration
        ConfigObject innerConfigObject = (ConfigObject) configObject.get(singleState);

        // Return the configuration as a state object
        return new State(innerConfigObject);
    }

    public SceneList getSceneList() {
        // Is there a scene list in the config?
        if (!configObject.containsKey(sceneList)) {
            // No, return NULL
            return null;
        }

        // Extract the state configuration
        ConfigList innerConfigList = (ConfigList) configObject.get(sceneList);

        // Return the configuration as a state object
        return new SceneList(innerConfigList);
    }
}
