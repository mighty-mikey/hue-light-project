package com.minds.great.hueLightProject.core.presenters;

import com.minds.great.hueLightProject.core.domain.LightSystemDomain;
import com.minds.great.hueLightProject.core.models.Mood;
import com.minds.great.hueLightProject.userInterface.fragments.Lists.moodListFragment.MoodListFragment;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.List;

public class MoodListPresenter {
    private LightSystemDomain lightSystemDomain;


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
