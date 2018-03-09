package com.minds.great.hueLightProject.core.presenters;


import com.minds.great.hueLightProject.core.controllers.ConnectionController;
import com.minds.great.hueLightProject.core.controllers.LightSystemController;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class LightListPresenter {

    private ConnectionController connectionController;
    private LightSystemController lightSystemController;
    private Disposable subscribe;

    public LightListPresenter(ConnectionController connectionController,
                              LightSystemController lightSystemController) {
        this.connectionController = connectionController;
        this.lightSystemController = lightSystemController;
    }

    public void viewLoaded(LightsListInterface lightsListInterface) {
        subscribe = connectionController.getLightsAndGroupsHeartbeatRelay().subscribe(lightsListInterface::updateLights);
    }
    public void viewUnloaded(){
        if(subscribe != null){
            subscribe.dispose();
            subscribe = null;
        }
    }

    public void setSelectedLightPosition(int position) {
        lightSystemController.setSelectedLightPosition(position);
    }

    public List<LightPoint> getLightList() {
        return lightSystemController.getLightList();
    }
}
