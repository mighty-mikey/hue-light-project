package com.minds.great.hueLightProject.core.domain.domainInterfaces;

import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

public interface MainInterface {
    void navigateToConnectionFragment();

    void navigateToLightListFragment();

    void navigateToSingleLightFragment();
}
