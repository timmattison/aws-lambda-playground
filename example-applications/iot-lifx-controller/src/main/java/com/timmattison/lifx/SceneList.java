package com.timmattison.lifx;

import com.typesafe.config.ConfigList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by timmattison on 10/16/15.
 */
public class SceneList {
    private final ConfigList data;
    private List<String> scenes;

    public SceneList(ConfigList data) {
        this.data = data;
    }

    public List<String> getScenes() {
        if (scenes == null) {
            scenes = new ArrayList<String>();

            for (Object object : data.unwrapped()) {
                scenes.add((String) object);
            }
        }

        return scenes;
    }

    public ConfigList getData() {
        return data;
    }

    @Override
    public int hashCode() {
        return getScenes().hashCode();
    }
}
