package com.minds.great.hueLightProject.core.presenters;

public interface SingleLightInterface {
    void showColorPicker();

    void setOnOffSwitch(boolean lightIsOn);

    void setLightNameText(String nameOfLight);

    void setDimmerProgress(int brightness);

    void initColorTemp(int colorTemp);
}
