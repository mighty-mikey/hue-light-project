package com.minds.great.hueLightProject.hueImpl;

import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnection;

public class HueUtil {

    static LightSystem convertBridgeConnectionToLightSystem(BridgeConnection bridgeConnection) {
        return new LightSystem.Builder()
                .userName(bridgeConnection.getBridge().getName())
                .build();
    }
}
