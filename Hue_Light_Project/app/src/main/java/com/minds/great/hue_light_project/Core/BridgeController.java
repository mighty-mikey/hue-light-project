package com.minds.great.hue_light_project.Core;

import com.philips.lighting.hue.sdk.PHHueSDK;

public class BridgeController {

    private PHHueSDK phHueSDK;
    private BridgeListener bridgeListener;

    public BridgeController(PHHueSDK phHueSDK, BridgeListener bridgeListener) {
        this.phHueSDK = phHueSDK;
        this.bridgeListener = bridgeListener;
    }

    public void searchForBridges() {
        phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
    }
}


