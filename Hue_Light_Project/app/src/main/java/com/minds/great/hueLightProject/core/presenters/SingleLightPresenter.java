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
    private SingleLightInterface view;
    private LightPoint light;
    private LightState lightState;

    public SingleLightPresenter(ConnectionDomain connectionDomain, LightSystemDomain lightSystemDomain) {
        this.connectionDomain = connectionDomain;
        this.lightSystemDomain = lightSystemDomain;
    }

    public void viewLoaded(SingleLightInterface singleLightInterface) {
        this.view = singleLightInterface;
        subscribe = connectionDomain.getLightsAndGroupsHeartbeatRelay().subscribe(lightSystem -> updateUiFromLight());
        updateUiFromLight();
    }

    private void updateUiFromLight() {
        LightPoint light = lightSystemDomain.getSelectedLightPoint();
        view.setOnOffSwitch(light.getLightState().isOn());
        view.setLightNameText(light.getName());
        view.setDimmerProgress(light.getLightState().getBrightness());

        ColorMode colormode = light.getLightState().getColormode();
        if (colormode.equals(ColorMode.XY)) {
            view.showColorPicker();
        } else if (colormode.equals(ColorMode.COLOR_TEMPERATURE)) {
            view.initColorTemp(light.getLightState().getCT());
        }
    }

    public void viewUnloaded() {
        //no test don't remove:
        view = null;
        if (subscribe != null) {
            subscribe.dispose();
            subscribe = null;
        }
    }

    public LightPoint getSelectedListPoint() {
        return lightSystemDomain.getSelectedLightPoint();
    }

    public void updateColor(HueColor hueColor) {
        refreshLightState();
        lightState.setXYWithColor(hueColor);
        lightState.setOn(true);
        light.updateState(lightState);
    }

    public void updateBrightness(int brightness) {
        refreshLightState();
        if (brightness != lightState.getBrightness()) {
            lightState.setBrightness(brightness);
            lightState.setOn(true);
            light.updateState(lightState);
        }
    }

    private void refreshLightState() {
        light = lightSystemDomain.getSelectedLightPoint();
        lightState = light.getLightState();
    }

    public void updateColorTemperature(int colorTemperature) {
        refreshLightState();
        if (colorTemperature != lightState.getCT()) {
            lightState.setCT(colorTemperature);
            lightState.setOn(true);
            light.updateState(lightState);
        }
    }

    public void updateOnState(boolean on) {
        refreshLightState();
        lightState.setOn(on);
        light.updateState(lightState);
    }

    public HueColor getColor() {
        return lightSystemDomain.getSelectedLightPoint().getLightState().getColor();
    }
}
