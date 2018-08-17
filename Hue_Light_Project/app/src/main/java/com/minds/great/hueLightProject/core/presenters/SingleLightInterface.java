package com.minds.great.hueLightProject.core.presenters;

import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor;

public interface SingleLightInterface {
    void showColorPicker(HueColor color);

    void setOnOffSwitch(boolean lightIsOn);

    void setLightNameText(String nameOfLight);

    void setDimmerProgress(int brightness);

    void setColorTempProgress(int colorTemp);

    void showColorTemp();
}
