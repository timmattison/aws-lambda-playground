package com.timmattison.configuration;

import com.timmattison.lifx.Scene;
import com.timmattison.lifx.SceneList;

/**
 * Created by timmattison on 10/22/15.
 */
public interface SceneRotatorPersistence {
    Scene getNextScene(SceneList sceneList);
}
