package com.minds.great.hueLightProject.core.models;

import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

public class LightSystem {

    private String ipAddress;
    private Bridge bridge;

    private LightSystem() {
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Bridge getBridge() {
        return bridge;
    }

    public LightPoint getLight(int i){
        return bridge.getBridgeState().getLights().get(i);
    }

    static public class Builder {
        private String ipAddress;
        private Bridge bridge;

        public Builder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder bridge(Bridge bridge){
            this.bridge = bridge;
            return this;
        }

        public LightSystem build() {
            LightSystem lightSystem = new LightSystem();
            lightSystem.ipAddress = ipAddress;
            lightSystem.bridge = bridge;
            return lightSystem;
        }
    }
}
