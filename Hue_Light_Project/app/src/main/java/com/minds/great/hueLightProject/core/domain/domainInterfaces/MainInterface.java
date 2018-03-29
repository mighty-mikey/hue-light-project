package com.minds.great.hueLightProject.core.controllers.controllerInterfaces;

import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

public interface MainInterface {
    void navigateToConnectionFragment();

    void navigateToLightListFragment();

    void navigateToSingleLightFragment(LightPoint light);
}
