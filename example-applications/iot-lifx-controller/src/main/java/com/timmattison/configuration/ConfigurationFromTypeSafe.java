package com.timmattison.configuration;

import com.timmattison.button.ClickType;
import com.timmattison.lifx.Action;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;

/**
 * Created by timmattison on 10/16/15.
 */
public class ConfigurationFromTypeSafe implements Configuration {
    public static final String LIFX_API_KEY = "lifx.apiKey";
    public static final String LIFX_API_PREFIX = "lifx.apiPrefix";
    public static final String BUTTON_PREFIX = "button.";
    public static final String BUTTON_SINGLE_CLICK_ACTION = BUTTON_PREFIX + "singleClickAction";
    public static final String BUTTON_DOUBLE_CLICK_ACTION = BUTTON_PREFIX + "doubleClickAction";
    public static final String BUTTON_LONG_CLICK_ACTION = BUTTON_PREFIX + "longClickAction";

    private Config getConfig() {
        return ConfigFactory.load();
    }

    public String getLifxApiKey() {
        return getConfig().getString(LIFX_API_KEY);
    }

    public String getLifxApiPrefix() {
        return getConfig().getString(LIFX_API_PREFIX);
    }

    public Action getAction(ClickType clickType) {
        switch (clickType) {
            case SINGLE:
                return getSingleClickAction();
            case DOUBLE:
                return getDoubleClickAction();
            case LONG:
                return getLongClickAction();
        }

        throw new UnsupportedOperationException("Unknown click type");
    }

    public Action getSingleClickAction() {
        ConfigObject configObject = getConfig().getObject(BUTTON_SINGLE_CLICK_ACTION);

        return new Action(configObject);
    }

    public Action getDoubleClickAction() {
        return new Action(getConfig().getObject(BUTTON_DOUBLE_CLICK_ACTION));
    }

    public Action getLongClickAction() {
        return new Action(getConfig().getObject(BUTTON_LONG_CLICK_ACTION));
    }
}
