package com.minds.great.hueLightProject.core.domain;


import android.annotation.SuppressLint;

import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.List;

import javax.inject.Inject;

public class LightSystemDomain {

    private LightSystem lightSystem;
    private int position = 0;

    @SuppressLint("CheckResult")
    @Inject
    public LightSystemDomain(ConnectionDomain connectionDomain) {
        connectionDomain.getConnectionSuccessfulRelay()
                .subscribe(lightSystem -> this.lightSystem = lightSystem);
    }

    public List<LightPoint> getLightList() {
        List<LightPoint> lights = null;
        if (null != lightSystem && null != lightSystem.getBridge()) {
            lights = lightSystem.getBridge().getBridgeState().getLights();
        }
        return lights;
    }

    public void setSelectedLightPosition(int position) {
        this.position = position;
    }

    private int getSelectedLightPosition() {
        return this.position;
    }

    public LightPoint getSelectedLightPoint() {
        return getLightList().get(getSelectedLightPosition());
    }
}
