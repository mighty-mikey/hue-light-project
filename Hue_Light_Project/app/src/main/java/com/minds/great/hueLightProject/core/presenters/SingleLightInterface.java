package com.minds.great.hueLightProject.core.presenters;


import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

public interface SingleLightInterface {
    void showColorPicker();

    void showColorTempSeekBar();

    void updateLight(LightPoint lightSystem);
}
