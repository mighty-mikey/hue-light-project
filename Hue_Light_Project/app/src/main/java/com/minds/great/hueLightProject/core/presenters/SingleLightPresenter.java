package com.minds.great.hueLightProject.core.presenters;


import com.minds.great.hueLightProject.core.domain.ConnectionDomain;
import com.minds.great.hueLightProject.core.domain.LightSystemDomain;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
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
                    singleLightInterface.updateLight(light);
                });
        if (lightSystemDomain.getSelectedLightColorMode().equals(ColorMode.XY)) {
            singleLightInterface.showColorPicker();
        } else if (lightSystemDomain.getSelectedLightColorMode().equals(ColorMode.COLOR_TEMPERATURE)) {
            singleLightInterface.showColorTempSeekBar();
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
}
