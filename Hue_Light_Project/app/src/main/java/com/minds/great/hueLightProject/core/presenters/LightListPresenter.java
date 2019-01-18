package com.minds.great.hueLightProject.core.presenters;


import com.minds.great.hueLightProject.core.domain.ConnectionDomain;
import com.minds.great.hueLightProject.core.domain.LightSystemDomain;
import com.minds.great.hueLightProject.core.models.Mood;
import com.minds.great.hueLightProject.data.MoodDao;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class LightListPresenter {

    private ConnectionDomain connectionDomain;
    private LightSystemDomain lightSystemDomain;
    LightsListInterface view;
    private Disposable heartBeatRelay;

    public LightListPresenter(ConnectionDomain connectionDomain,
                              LightSystemDomain lightSystemDomain) {
        this.connectionDomain = connectionDomain;
        this.lightSystemDomain = lightSystemDomain;
    }

    public void viewLoaded(LightsListInterface lightsListInterface) {
        heartBeatRelay = connectionDomain.getHeartBeatRelay()
                .subscribe(lightSystem -> lightsListInterface.updateLights(lightSystem.getBridge().getBridgeState().getLightPoints()));
        view = lightsListInterface;
        view.updateLights(lightSystemDomain.getLightList());
    }
    public void viewUnloaded(){
        if(heartBeatRelay != null){
            heartBeatRelay.dispose();
            heartBeatRelay = null;
        }
        view = null;
    }

    public void setSelectedLightPosition(int position) {
        lightSystemDomain.setSelectedLightPosition(position);
        view.navigateToSingleLightFragment();
    }

    public List<LightPoint> getLightList() {
        return lightSystemDomain.getLightList();
    }

    public void saveMood() {
        Mood mood = new Mood();
        mood.setName("myFirstLightMood");
        mood.setListOfLights(getLightList());
    }
}
