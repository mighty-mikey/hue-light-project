package com.minds.great.hueLightProject.core.presenters;


import com.minds.great.hueLightProject.core.domain.ConnectionDomain;
import com.minds.great.hueLightProject.core.domain.LightSystemDomain;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class LightListPresenter {

    private ConnectionDomain connectionDomain;
    private LightSystemDomain lightSystemDomain;
    LightsListInterface view;
    private Disposable subscribe;

    public LightListPresenter(ConnectionDomain connectionDomain,
                              LightSystemDomain lightSystemDomain) {
        this.connectionDomain = connectionDomain;
        this.lightSystemDomain = lightSystemDomain;
    }

    public void viewLoaded(LightsListInterface lightsListInterface) {
        subscribe = connectionDomain.getLightsAndGroupsHeartbeatRelay().subscribe(lightsListInterface::updateLights);
        view = lightsListInterface;
    }
    public void viewUnloaded(){
        view = null;
        if(subscribe != null){
            subscribe.dispose();
            subscribe = null;
        }
    }

    public void setSelectedLightPosition(int position) {
        lightSystemDomain.setSelectedLightPosition(position);
        view.navigateToSingleLightFragment();
    }

    public List<LightPoint> getLightList() {
        return lightSystemDomain.getLightList();
    }
}
