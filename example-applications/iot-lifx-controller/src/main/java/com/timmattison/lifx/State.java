package com.timmattison.lifx;

import com.typesafe.config.ConfigObject;

/**
 * Created by timmattison on 10/17/15.
 */
public class State {
    public static final String SELECTOR = "selector";
    public static final String POWER = "power";
    public static final String COLOR = "color";
    public static final String BRIGHTNESS = "brightness";
    public static final String DURATION = "duration";
    private final ConfigObject data;

    public State(ConfigObject data) {
        this.data = data;
    }

    public String getSelector() {
        return (String) data.get(SELECTOR).unwrapped();
    }

    public String getPower() {
        return (String) data.get(POWER).unwrapped();
    }

    public String getColor() {
        return (String) data.get(COLOR).unwrapped();
    }

    public String getBrightness() {
        return (String) data.get(BRIGHTNESS).unwrapped();
    }

    public String getDuration() {
        return (String) data.get(DURATION).unwrapped();
    }

    public ConfigObject getData() {
        return data;
    }
}
