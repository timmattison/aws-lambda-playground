package com.timmattison.lifx;

/**
 * Created by timmattison on 10/16/15.
 */
public class Scene {
    private final String uuid;

    public Scene(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
