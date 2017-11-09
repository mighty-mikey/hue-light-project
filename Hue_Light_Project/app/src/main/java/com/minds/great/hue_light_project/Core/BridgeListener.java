package com.minds.great.hue_light_project.Core;

import com.jakewharton.rxrelay2.PublishRelay;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;

import java.util.ArrayList;
import java.util.List;


public class BridgeListener implements PHSDKListener {

    private PHHueSDK phHueSDK;
    private PublishRelay<List<PHAccessPoint>> accessPointRelay = PublishRelay.create();

    public BridgeListener(PHHueSDK phHueSDK){
        this.phHueSDK = phHueSDK;
    }

    @Override
    public void onAccessPointsFound(List accessPoint) {
        List<PHAccessPoint> list = new ArrayList<>();
        for (Object obj: accessPoint) {
            list.add((PHAccessPoint)obj);
        }
        accessPointRelay.accept(list);
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
    public void onBridgeConnected(PHBridge b, String username) {
        phHueSDK.setSelectedBridge(b);
        phHueSDK.enableHeartbeat(b, PHHueSDK.HB_INTERVAL);
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
    }

    @Override
    public void onParsingErrors(List parsingErrorsList) {
        // Any JSON parsing errors are returned here.  Typically your program should never return these.
    }

    public PublishRelay<List<PHAccessPoint>> getAccessPointRelay() {
        return accessPointRelay;
    }
}