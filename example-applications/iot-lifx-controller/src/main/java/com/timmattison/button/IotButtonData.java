package com.timmattison.button;

import com.google.common.collect.ImmutableMap;

/**
 * Created by timmattison on 10/16/15.
 */
public class IotButtonData {
    public static final String SERIAL_NUMBER = "serialNumber";
    public static final String BATTERY_VOLTAGE = "batteryVoltage";
    public static final String CLICK_TYPE = "clickType";
    private final ImmutableMap<String, String> data;

    public IotButtonData(ImmutableMap<String, String> data) {
        this.data = data;
    }

    public String getSerialNumber() {
        return data.get(SERIAL_NUMBER);
    }

    public String geBatteryVoltage() {
        return data.get(BATTERY_VOLTAGE);
    }

    public ClickType getClickType() {
        return ClickType.valueOf(data.get(CLICK_TYPE));
    }
}
