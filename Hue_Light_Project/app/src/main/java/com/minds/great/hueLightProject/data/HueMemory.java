package com.minds.great.hueLightProject.data;

import android.content.Context;

import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MemoryInterface;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.wrapper.HueLog;
import com.philips.lighting.hue.sdk.wrapper.Persistence;
import com.philips.lighting.hue.sdk.wrapper.knownbridges.KnownBridge;
import com.philips.lighting.hue.sdk.wrapper.knownbridges.KnownBridges;

import java.util.Collections;
import java.util.List;


public class HueMemory implements MemoryInterface {

    public HueMemory(Context context) {
        // Configure the storage location and log level for the Hue SDK

        // Load the huesdk native library before calling any SDK method
        System.loadLibrary("huesdk");
        Persistence.setStorageLocation(context.getFilesDir().getAbsolutePath(), "hueLightProject");
        HueLog.setConsoleLogLevel(HueLog.LogLevel.DEBUG);
    }

    @Override
    public void saveLightSystem(LightSystem lightSystem) {

    }

    @Override
    public String getLightSystemIpAddress() {
        List<KnownBridge> bridges = KnownBridges.getAll();

        if (bridges.isEmpty()) {
            return null;
        }

        return Collections.max(bridges, (a, b) -> a.getLastConnected().compareTo(b.getLastConnected())).getIpAddress();
    }
}
