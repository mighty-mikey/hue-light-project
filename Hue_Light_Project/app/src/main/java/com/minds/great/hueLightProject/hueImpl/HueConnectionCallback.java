package com.minds.great.hueLightProject.hueImpl;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.models.ConnectionError;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnection;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateCacheType;
import com.philips.lighting.hue.sdk.wrapper.connection.ConnectionEvent;
import com.philips.lighting.hue.sdk.wrapper.domain.HueError;

import java.util.List;

import static com.minds.great.hueLightProject.core.models.ConnectionError.NO_BRIDGE_FOUND_CODE;


public class HueConnectionCallback extends BridgeConnectionCallback {

    private BehaviorRelay<LightSystem> lightSystemRelay;
    private PublishRelay<ConnectionError> errorRelay;

    public HueConnectionCallback(BehaviorRelay<LightSystem> lightSystemRelay, PublishRelay<ConnectionError> errorRelay) {
        this.lightSystemRelay = lightSystemRelay;
        this.errorRelay = errorRelay;
    }

    @Override
    public void onConnectionEvent(BridgeConnection bridgeConnection, ConnectionEvent connectionEvent) {
        switch (connectionEvent) {
            case LINK_BUTTON_NOT_PRESSED:
                break;

            case COULD_NOT_CONNECT:
                errorRelay.accept(new ConnectionError.Builder().code(NO_BRIDGE_FOUND_CODE).build());
                break;

            case CONNECTION_LOST:
                errorRelay.accept(new ConnectionError.Builder().code(NO_BRIDGE_FOUND_CODE).build());
                break;

            case CONNECTION_RESTORED:
                break;

            case DISCONNECTED:
                // User-initiated disconnection.
                break;

            case CONNECTED:
                break;

            case AUTHENTICATED:
                lightSystemRelay.accept(HueUtil.convertBridgeConnectionToLightSystem(bridgeConnection));
                bridgeConnection.getHeartbeatManager().startHeartbeat(BridgeStateCacheType.LIGHTS_AND_GROUPS, 10000);
                break;

            default:
                break;
        }
    }

    @Override
    public void onConnectionError(BridgeConnection bridgeConnection, List<HueError> list) {
        for (HueError ignored : list) {
            errorRelay.accept(new ConnectionError.Builder().build());
        }
    }
}
