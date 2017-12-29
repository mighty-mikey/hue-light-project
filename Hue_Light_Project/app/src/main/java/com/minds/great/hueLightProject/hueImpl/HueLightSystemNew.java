package com.minds.great.hueLightProject.hueImpl;

import android.util.Log;

import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.LightSystemInterface;
import com.minds.great.hueLightProject.core.models.ConnectionError;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnection;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnectionType;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedCallback;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeStateUpdatedEvent;
import com.philips.lighting.hue.sdk.wrapper.connection.ConnectionEvent;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscovery;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscoveryCallback;
import com.philips.lighting.hue.sdk.wrapper.discovery.BridgeDiscoveryResult;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.BridgeBuilder;
import com.philips.lighting.hue.sdk.wrapper.domain.HueError;
import com.philips.lighting.hue.sdk.wrapper.domain.ReturnCode;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class HueLightSystemNew implements LightSystemInterface {

    private Bridge bridge;
    private BridgeDiscovery bridgeDiscovery;
    private PublishRelay<List<LightSystem>> lightSystemListRelay = PublishRelay.create();
    private PublishRelay<ConnectionError> errorRelay = PublishRelay.create();
    private static PublishRelay<LightSystem> lightSystemRelay = PublishRelay.create();

//    static{

        // Load the huesdk native library before calling any SDK method
//        System.loadLibrary("huesdk");
//    }

    @Override
    public void searchForLightSystems() {
        disconnectFromBridge();
        bridgeDiscovery = new BridgeDiscovery();
        bridgeDiscovery.search(BridgeDiscovery.BridgeDiscoveryOption.UPNP, bridgeDiscoveryCallback);
    }

    @Override
    public PublishRelay<List<LightSystem>> getLightSystemListObservable() {
        return lightSystemListRelay;
    }

    @Override
    public void connectToLightSystem(String lightSystemIpAddress) {
        stopBridgeDiscovery();
        disconnectFromBridge();

        bridge = new BridgeBuilder("app name", "device name")
                .setIpAddress(lightSystemIpAddress)
                .setConnectionType(BridgeConnectionType.LOCAL)
                .setBridgeConnectionCallback(bridgeConnectionCallback)
                .addBridgeStateUpdatedCallback(bridgeStateUpdatedCallback)
                .build();

        bridge.connect();
    }

    /**
     * Stops the bridge discovery if it is still running
     */
    private void stopBridgeDiscovery() {
        if (bridgeDiscovery != null) {
            bridgeDiscovery.stop();
            bridgeDiscovery = null;
        }
    }

    /**
     * Disconnect a bridge
     * The hue SDK supports multiple bridge connections at the same time,
     * but for the purposes of this demo we only connect to one bridge at a time.
     */
    private void disconnectFromBridge() {
        if (bridge != null) {
            bridge.disconnect();
            bridge = null;
        }
    }


    @Override
    public PublishRelay<ConnectionError> getErrorObservable() {
        return errorRelay;
    }

    @Override
    public PublishRelay<LightSystem> getLightSystemObservable() {
        return lightSystemRelay;
    }

    /**
     * The callback that receives the results of the bridge discovery
     */
    private BridgeDiscoveryCallback bridgeDiscoveryCallback = new BridgeDiscoveryCallback() {
        @Override
        public void onFinished(final List<BridgeDiscoveryResult> results, final ReturnCode returnCode) {
            // Set to null to prevent stopBridgeDiscovery from stopping it
            bridgeDiscovery = null;

            if (returnCode == ReturnCode.SUCCESS) {
                lightSystemListRelay.accept(convertResultsToLightSystem(results));
            } else if (returnCode == ReturnCode.STOPPED) {
                Log.i(TAG, "Bridge discovery stopped.");
            } else {
                //TODO:  enter error code here.
                errorRelay.accept(new ConnectionError.Builder().build());
            }
        }

        private List<LightSystem> convertResultsToLightSystem(List<BridgeDiscoveryResult> results) {
            List<LightSystem> returnList = new ArrayList<>();

            for (BridgeDiscoveryResult result : results) {
                returnList.add(new LightSystem.Builder().ipAddress(result.getIP()).build());
            }

            return returnList;
        }
    };

    /**
     * The callback the receives bridge state update events
     */
    private BridgeStateUpdatedCallback bridgeStateUpdatedCallback = new BridgeStateUpdatedCallback() {

        @Override
        public void onBridgeStateUpdated(Bridge bridge, BridgeStateUpdatedEvent bridgeStateUpdatedEvent) {
            switch (bridgeStateUpdatedEvent) {
                case INITIALIZED:
                    // The bridge state was fully initialized for the first time.
                    // It is now safe to perform operations on the bridge state.
                    break;

                case LIGHTS_AND_GROUPS:
                    // At least one light was updated.
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * The callback that receives bridge connection events
     */
    private BridgeConnectionCallback bridgeConnectionCallback = new BridgeConnectionCallback() {
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

                default:
                    lightSystemRelay.accept(convertBridgeConnectionToLightSystem(bridgeConnection));
                    break;
            }
        }

        private LightSystem convertBridgeConnectionToLightSystem(BridgeConnection bridgeConnection) {
            return new LightSystem.Builder()
                    .userName(bridgeConnection.getBridge().getName())
                    .build();
        }

        @Override
        public void onConnectionError(BridgeConnection bridgeConnection, List<HueError> list) {
            for (HueError error : list) {
                //TODO:  figure this out
                errorRelay.accept(new ConnectionError.Builder().build());
            }
        }
    };
}
