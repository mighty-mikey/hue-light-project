package com.minds.great.hueLightProject.core.presenters;


import com.minds.great.hueLightProject.core.controllers.ConnectionController;
import com.minds.great.hueLightProject.core.controllers.LightSystemController;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;
import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor;

import io.reactivex.disposables.Disposable;

public class SingleLightPresenter {
    private ConnectionController connectionController;
    private LightSystemController lightSystemController;
    private Disposable subscribe;

    public SingleLightPresenter(ConnectionController connectionController, LightSystemController lightSystemController) {
        this.connectionController = connectionController;
        this.lightSystemController = lightSystemController;
    }

    public void viewLoaded(SingleLightInterface singleLightInterface) {
        subscribe = connectionController.getLightsAndGroupsHeartbeatRelay().subscribe(lightSystem -> singleLightInterface.updateLight(lightSystem.getLight(lightSystemController.getSelectedLightPosition())));
        if(lightSystemController.getSelectedLightColorMode().equals(ColorMode.XY)){
            singleLightInterface.showColorPicker();
        }
        else if(lightSystemController.getSelectedLightColorMode().equals(ColorMode.COLOR_TEMPERATURE)){
            singleLightInterface.showColorTempSeekBar();
        }
    }

    public void viewUnloaded(){
        if(subscribe != null){
            subscribe.dispose();
            subscribe = null;
        }
    }

    public void setColor(HueColor hueColor) {
        lightSystemController.setColor(hueColor);
    }

}
