package com.minds.great.hueLightProject.core.domain;


import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;
import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor;

import java.util.List;

public class LightSystemDomain {

    private LightSystem lightSystem;
    private int position = 0;

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

    public int getSelectedLightPosition(){
        return this.position;
    }

    public void setColor(HueColor hueColor) {
        LightPoint lightPoint = getLightList().get(position);
        LightState lightState = lightPoint.getLightState();
        lightState.setXYWithColor(hueColor);
        lightPoint.updateState(lightState);
    }

    public ColorMode getSelectedLightColorMode() {
        //TODO: Error handling
        return getLightList().get(position).getLightState().getColormode();
    }
}
