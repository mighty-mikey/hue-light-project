package com.minds.great.hueLightProject.hueImpl;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;

public class BridgeController {

    private PHHueSDK phHueSDK;

    public BridgeController(PHHueSDK phHueSDK, BridgeListener bridgeListener){
        this.phHueSDK = phHueSDK;
        phHueSDK.getNotificationManager().registerSDKListener(bridgeListener);
    }

    public void searchForBridges(){
        PHBridgeSearchManager searchManager = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        searchManager.search(true, true);
    }

    public void connectToBridge(PHAccessPoint bridge){
        phHueSDK.connect(bridge);
    }
}


