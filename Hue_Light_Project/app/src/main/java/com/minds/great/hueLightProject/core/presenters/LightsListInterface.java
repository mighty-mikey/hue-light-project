package com.minds.great.hueLightProject.core.presenters;

import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.List;

public interface LightsListInterface {

    void updateLights(List<LightPoint> lightSystem);

    void navigateToSingleLightFragment();
}
