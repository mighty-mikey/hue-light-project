package com.minds.great.hueLightProject.core.presenters;


import com.minds.great.hueLightProject.core.controllers.LightSystemController;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;
import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor;

public class SingleLightPresenter {
    private LightSystemController lightSystemController;

    public SingleLightPresenter(LightSystemController lightSystemController) {
        this.lightSystemController = lightSystemController;
    }

    public void viewLoaded(SingleLightInterface singleLightInterface) {
        if(lightSystemController.getSelectedLightColorMode().equals(ColorMode.XY)){
            singleLightInterface.showColorPicker();
        }
        else if(lightSystemController.getSelectedLightColorMode().equals(ColorMode.COLOR_TEMPERATURE)){
            singleLightInterface.showColorTempSeekBar();
        }
    }

    public void setColor(HueColor hueColor) {
        lightSystemController.setColor(hueColor);
    }

}
