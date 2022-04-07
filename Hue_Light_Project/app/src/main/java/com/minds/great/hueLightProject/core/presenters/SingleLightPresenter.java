package com.minds.great.hueLightProject.core.presenters;


import com.minds.great.hueLightProject.core.domain.ConnectionDomain;
import com.minds.great.hueLightProject.core.domain.LightSystemDomain;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;
import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class SingleLightPresenter {
    private ConnectionDomain connectionDomain;
    private LightSystemDomain lightSystemDomain;
    private Disposable subscribe;
    private SingleLightInterface view;
    private LightPoint light;
    private LightState lightState;

    @Inject
    public SingleLightPresenter(ConnectionDomain connectionDomain, LightSystemDomain lightSystemDomain) {
        this.connectionDomain = connectionDomain;
        this.lightSystemDomain = lightSystemDomain;
    }

    public void viewLoaded(SingleLightInterface singleLightInterface) {
        this.view = singleLightInterface;
        subscribe = connectionDomain.getHeartBeatRelay().subscribe(lightSystem -> updateUiFromLight());
        initLightStateView();
    }

    private void updateUiFromLight() {
        ColorMode colormode = light.getLightState().getColormode();
        view.setOnOffSwitch(light.getLightState().isOn());
        view.setLightNameText(light.getName());
        view.setDimmerProgress(light.getLightState().getBrightness());
        if (colormode.equals(ColorMode.COLOR_TEMPERATURE)) {
            view.setColorTempProgress(light.getLightState().getCT());
        }
    }

    private void initLightStateView(){
        light = lightSystemDomain.getSelectedLightPoint();
        Boolean wasLightOn = light.getLightState().isOn();
        ColorMode colormode = light.getLightState().getColormode();
        if (colormode.equals(ColorMode.XY)) {
            if (!wasLightOn) {
                light.getLightState().setOn(true);
            }
            HueColor color = light.getLightState().getColor();
            color.setBrightness(255.0);
            view.showColorPicker(color);
            light.getLightState().setOn(wasLightOn);
        } else if (colormode.equals(ColorMode.COLOR_TEMPERATURE)) {
            view.showColorTemp();
        }
        updateUiFromLight();
    }

    public void viewUnloaded() {
        //no test don't remove:
        view = null;
        if (subscribe != null) {
            subscribe.dispose();
            subscribe = null;
        }
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
