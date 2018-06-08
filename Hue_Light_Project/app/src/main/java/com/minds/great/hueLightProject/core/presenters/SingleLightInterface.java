package com.minds.great.hueLightProject.core.presenters;


import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

public interface SingleLightInterface {
    void showColorPicker();

    void updateSingleLightUi(LightPoint lightSystem);

    void setOnOffSwitch(boolean lightIsOn);

    void setLightNameText(String nameOfLight);

    void setDimmerProgress(int brightness);

    void initColorTemp(int colorTemp);
}
