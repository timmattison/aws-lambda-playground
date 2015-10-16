package com.timmattison.configuration;

import com.timmattison.button.ClickType;
import com.timmattison.lifx.Action;
import com.timmattison.lifx.Scene;

/**
 * Created by timmattison on 10/16/15.
 */
public interface Configuration {
    String getLifxApiKey();

    String getLifxApiPrefix();

    Action getAction(ClickType clickType);

    Action getSingleClickAction();

    Action getDoubleClickAction();

    Action getLongClickAction();
}
