package com.minds.great.hueLightProject.core.controllers;


import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.LightsListInterface;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class LightSystemController {

    private LightSystem lightSystem;
    private ConnectionController connectionController;
    private Disposable subscribe;

    public LightSystemController(ConnectionController connectionController) {
        this.connectionController = connectionController;
        connectionController.getConnectionSuccessfulRelay()
                .subscribe(lightSystem -> this.lightSystem = lightSystem);
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
    public List<LightPoint> getLightList() {
        List<LightPoint> lights = null;
        if (null != lightSystem && null != lightSystem.getBridge()) {
            lights = lightSystem.getBridge().getBridgeState().getLights();
        }
        return lights;
    }
}
