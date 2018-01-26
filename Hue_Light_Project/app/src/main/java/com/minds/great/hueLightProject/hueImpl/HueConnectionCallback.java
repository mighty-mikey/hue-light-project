package com.minds.great.hueLightProject.hueImpl;

import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.models.ConnectionError;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnection;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.ConnectionEvent;
import com.philips.lighting.hue.sdk.wrapper.domain.HueError;

import java.util.List;


public class HueConnectionCallback extends BridgeConnectionCallback {

    private PublishRelay<LightSystem>  lightSystemRelay;
    private PublishRelay<ConnectionError> errorRelay;

    public HueConnectionCallback(PublishRelay<LightSystem> lightSystemRelay, PublishRelay<ConnectionError> errorRelay) {
        this.lightSystemRelay = lightSystemRelay;
        this.errorRelay = errorRelay;
    }

    @Override
    public void onConnectionEvent(BridgeConnection bridgeConnection, ConnectionEvent connectionEvent) {
        switch (connectionEvent) {
            case LINK_BUTTON_NOT_PRESSED:
                //TODO:  enter correct error code;
//                    errorRelay.accept(new ConnectionError.Builder().build());
                break;

            case COULD_NOT_CONNECT:
                //TODO:  enter correct error code;
                errorRelay.accept(new ConnectionError.Builder().build());
                break;

            case CONNECTION_LOST:
                //TODO:  enter correct error code;
                errorRelay.accept(new ConnectionError.Builder().build());
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
                break;

            default:
                break;
        }
    }

    @Override
    public void onConnectionError(BridgeConnection bridgeConnection, List<HueError> list) {
        for (HueError error : list) {
            errorRelay.accept(new ConnectionError.Builder().build());
        }
    }
}
