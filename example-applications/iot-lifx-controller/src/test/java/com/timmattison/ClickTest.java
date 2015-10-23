package com.timmattison;

import com.timmattison.button.ClickType;
import com.timmattison.configuration.BasicSceneRotatorPersistence;
import com.timmattison.configuration.Configuration;
import com.timmattison.configuration.ConfigurationFromTypeSafe;
import com.timmattison.lifx.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * Created by timmattison on 10/16/15.
 */
public abstract class ClickTest {
    private Configuration configuration;
    private LifxController lifxController;

    /**
     * Returns the type of button click we are testing
     *
     * @return
     */
    protected abstract ClickType getClickType();

    protected abstract boolean isUnitTest();

    @Before
    public void setup() {
        configuration = new ConfigurationFromTypeSafe();
        lifxController = new BasicLifxController(configuration, new BasicSceneRotatorPersistence());
    }

    @Test
    public void clickActionShouldNotBeNull() {
        Action action = configuration.getAction(getClickType());

        Assert.assertThat(action, is(notNullValue()));
    }

    @Test
    public void clickActionStateSceneOrSceneListShouldNotBeNull() {
        Action action = configuration.getAction(getClickType());
        Scene scene = action.getScene();
        State state = action.getState();
        SceneList sceneList = action.getSceneList();

        Assert.assertTrue("Either state, scene, or scene list must be set",
                ((scene != null) || (state != null) || (sceneList != null)));
    }

    @Test
    public void runAction() throws IOException {
        if (isUnitTest()) {
            return;
        }

        Action action = configuration.getAction(getClickType());
        lifxController.runAction(action);
    }
}
