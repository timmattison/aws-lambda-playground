package com.timmattison.guice;

import com.google.inject.AbstractModule;
import com.timmattison.configuration.BasicSceneRotatorPersistence;
import com.timmattison.configuration.Configuration;
import com.timmattison.configuration.ConfigurationFromTypeSafe;
import com.timmattison.configuration.SceneRotatorPersistence;
import com.timmattison.lifx.BasicLifxController;
import com.timmattison.lifx.LifxController;

/**
 * Created by timmattison on 10/16/15.
 */
public class LambdaModule extends AbstractModule {
    @Override
    protected void configure() {
        // Get our configuration data using the TypeSafe Config library
        bind(Configuration.class).to(ConfigurationFromTypeSafe.class);

        // Use the basic implementation of the LIFX controller
        bind(LifxController.class).to(BasicLifxController.class);

        // Use the basic scene rotator
        bind(SceneRotatorPersistence.class).to(BasicSceneRotatorPersistence.class);
    }
}
