package com.minds.great.hueLightProject.hueImpl;

import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.controllers.ConnectionPoint;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.minds.great.hueLightProject.utils.HueViewError;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;

import java.util.ArrayList;
import java.util.List;


public class HueBridgeController implements ConnectionPoint, PHSDKListener {

    private PHHueSDK phHueSDK;
    private PublishRelay<List<LightSystem>> publishRelay = PublishRelay.create();

    public HueBridgeController(PHHueSDK phHueSDK) {
        this.phHueSDK = phHueSDK;
        phHueSDK.getNotificationManager().registerSDKListener(this);
    }

    public void search() {
        PHBridgeSearchManager searchManager = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        searchManager.search(true, true);
    }

    @Override
    public PublishRelay<List<LightSystem>> getConnectionPointObservable() {
        return publishRelay;
    }

    @Override
    public void connectToLightSystem(LightSystem lightSystem) {

    }

    public void connectToBridge(PHAccessPoint bridge) {
        phHueSDK.connect(bridge);
    }

    @Override
    public void onAccessPointsFound(List accessPointList) {

        List<LightSystem> list = new ArrayList<>();
        for (Object obj : accessPointList) {
            PHAccessPoint accessPoint = (PHAccessPoint) obj;
            list.add(new LightSystem.Builder()
                    .ipAddress(accessPoint.getIpAddress())
                    .userName(accessPoint.getUsername())
                    .build());
        }
        publishRelay.accept(list);
    }

    @Override
    public void onCacheUpdated(List cacheNotificationsList, PHBridge bridge) {
        // Here you receive notifications that the BridgeResource Cache was updated. Use the PHMessageType to
        // check which cache was updated, e.g.
        if (cacheNotificationsList.contains(PHMessageType.LIGHTS_CACHE_UPDATED)) {
            System.out.println("Lights Cache Updated ");
        }
    }

    @Override
    public void onBridgeConnected(PHBridge foundBridge, String username) {
        phHueSDK.setSelectedBridge(foundBridge);
        phHueSDK.enableHeartbeat(foundBridge, PHHueSDK.HB_INTERVAL);

        LightSystem lightSystem = new LightSystem.Builder()
                .userName(username)
                .ipAddress(foundBridge
                        .getResourceCache()
                        .getBridgeConfiguration()
                        .getIpAddress())
                .build();

        // Here it is recommended to set your connected bridge in your sdk object (as above) and start the heartbeat.
        // At this point you are connected to a bridge so you should pass control to your main program/activity.
        // The username is generated randomly by the bridge.
        // Also it is recommended you store the connected IP Address/ Username in your app here.  This will allow easy automatic connection on subsequent use.
    }

    @Override
    public void onAuthenticationRequired(PHAccessPoint accessPoint) {
        phHueSDK.startPushlinkAuthentication(accessPoint);
        // Arriving here indicates that Pushlinking is required (to prove the User has physical access to the bridge).  Typically here
        // you will display a pushlink image (with a timer) indicating to to the user they need to push the button on their bridge within 30 seconds.
    }

    @Override
    public void onConnectionResumed(PHBridge bridge) {

    }

    @Override
    public void onConnectionLost(PHAccessPoint accessPoint) {
        // Here you would handle the loss of connection to your bridge.
    }

    @Override
    public void onError(int code, final String message) {
        // Here you can handle events such as Bridge Not Responding, Authentication Failed and Bridge Not Found
        HueViewError error = new HueViewError(code, message);

    }

    @Override
    public void onParsingErrors(List parsingErrorsList) {
        // Any JSON parsing errors are returned here.  Typically your program should never return these.
    }
}


