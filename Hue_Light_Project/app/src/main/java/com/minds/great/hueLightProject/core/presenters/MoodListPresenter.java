package com.minds.great.hueLightProject.core.presenters;

import com.minds.great.hueLightProject.core.domain.LightSystemDomain;
import com.minds.great.hueLightProject.core.models.Mood;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.List;

import javax.inject.Inject;

public class MoodListPresenter {
    final private LightSystemDomain lightSystemDomain;

    @Inject
    public MoodListPresenter(LightSystemDomain lightSystemDomain){
        this.lightSystemDomain = lightSystemDomain;
    }

    public void selectSavedMood(Mood mood) {
        List<LightPoint> bridgeLightList = lightSystemDomain.getLightList();
        List<LightPoint> moodLightList = mood.getListOfLights();
        for (LightPoint bridgeLight : bridgeLightList) {
            for (LightPoint moodLight : moodLightList) {
                if (bridgeLight.getIdentifier().equals(moodLight.getIdentifier())) {
                    bridgeLight.updateState(moodLight.getLightState());
                    break;
                }
            }
        }
    }
}
