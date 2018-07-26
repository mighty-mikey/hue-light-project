package com.minds.great.hueLightProject.core.presenters;


import com.minds.great.hueLightProject.core.domain.ConnectionDomain;
import com.minds.great.hueLightProject.core.domain.LightSystemDomain;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;
import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor;

import io.reactivex.disposables.Disposable;

public class SingleLightPresenter {
    private ConnectionDomain connectionDomain;
    private LightSystemDomain lightSystemDomain;
    private Disposable subscribe;

    public SingleLightPresenter(ConnectionDomain connectionDomain, LightSystemDomain lightSystemDomain) {
        this.connectionDomain = connectionDomain;
        this.lightSystemDomain = lightSystemDomain;
    }

    public void viewLoaded(SingleLightInterface singleLightInterface) {
        subscribe = connectionDomain.getLightsAndGroupsHeartbeatRelay()
                .subscribe(lightSystem -> {
                    LightPoint light = lightSystem.getLight(lightSystemDomain.getSelectedLightPosition());
                    singleLightInterface.updateSingleLightUi(light);
                });
        LightPoint light = lightSystemDomain.getSelectedLightPoint();
        singleLightInterface.setOnOffSwitch(light.getLightState().isOn());
        singleLightInterface.setLightNameText(light.getName());
        singleLightInterface.setDimmerProgress(light.getLightState().getBrightness());

        ColorMode colormode = light.getLightState().getColormode();
        if (colormode.equals(ColorMode.XY)) {
            singleLightInterface.showColorPicker();
        } else if (colormode.equals(ColorMode.COLOR_TEMPERATURE)) {
            singleLightInterface.initColorTemp(light.getLightState().getCT());
        }
    }

    public void viewUnloaded() {
        if (subscribe != null) {
            subscribe.dispose();
            subscribe = null;
        }
    }

    public void setColor(HueColor hueColor) {
        lightSystemDomain.setColor(hueColor);
    }

    public LightPoint getSelectedListPoint() {
        return lightSystemDomain.getSelectedLightPoint();
    }

    public void updateBrightness(int brightness) {
        LightPoint light = lightSystemDomain.getSelectedLightPoint();
        LightState lightState = light.getLightState();
        if (brightness != lightState.getBrightness()) {
            lightState.setBrightness(brightness);
            light.updateState(lightState);
        }
    }

    public void updateOnState(boolean on) {
        LightPoint light = lightSystemDomain.getSelectedLightPoint();
        LightState lightState = light.getLightState();
        lightState.setOn(on);
        light.updateState(lightState);
    }

    public HueColor getColor() {
        return lightSystemDomain.getSelectedLightPoint().getLightState().getColor();
    }
}
