package com.minds.great.hueLightProject.data;

import android.util.Log;

import com.philips.lighting.hue.sdk.wrapper.knownbridges.KnownBridge;
import com.philips.lighting.hue.sdk.wrapper.knownbridges.KnownBridges;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;


public class HueDomain{

    @Inject
    public HueDomain() {}

    public String getLastConnectedBridgeIpAddress() {
        List<KnownBridge> bridges = null;
        try {
            bridges = KnownBridges.getAll();
        } catch (UnsatisfiedLinkError ignored){
            Log.e("HueDomain", ignored.getMessage());
        }

        if (null == bridges || bridges.isEmpty()) {
            return null;
        }

        return Collections.max(bridges, (a, b) -> a.getLastConnected().compareTo(b.getLastConnected())).getIpAddress();
    }
}
