package com.minds.great.hueLightProject.core.controllers;


import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.List;

public class LightSystemController {

    private LightSystem lightSystem;

    public LightSystemController(ConnectionController connectionController) {
        connectionController.getConnectionSuccessfulRelay()
                .subscribe(lightSystem -> this.lightSystem = lightSystem);
    }

    public List<LightPoint> getLightList() {
        List<LightPoint> lights = null;
        if (null != lightSystem && null != lightSystem.getBridge()) {
            lights = lightSystem.getBridge().getBridgeState().getLights();
        }
        return lights;
    }
}
