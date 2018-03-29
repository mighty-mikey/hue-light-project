package com.minds.great.hueLightProject.data;

import android.util.Log;

import com.minds.great.hueLightProject.core.domain.domainInterfaces.MemoryInterface;
import com.philips.lighting.hue.sdk.wrapper.knownbridges.KnownBridge;
import com.philips.lighting.hue.sdk.wrapper.knownbridges.KnownBridges;

import java.util.Collections;
import java.util.List;


public class HueMemory implements MemoryInterface {

    @Override
    public String getLightSystemIpAddress() {
        List<KnownBridge> bridges = null;
        try {
            bridges = KnownBridges.getAll();
        } catch (UnsatisfiedLinkError ignored){
            Log.e("HueMemory", ignored.getMessage());
        }

        if (null == bridges || bridges.isEmpty()) {
            return null;
        }

        return Collections.max(bridges, (a, b) -> a.getLastConnected().compareTo(b.getLastConnected())).getIpAddress();
    }
}
