package com.minds.great.hueLightProject.core.models;

import com.philips.lighting.model.PHBridge;

public class LightSystem {
    private String ipAddress;
    private String userName;
    private String bridgeId;
    private String macAddress;

    private PHBridge phBridge;

    private LightSystem() {
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserName() {
        return userName;
    }

    public String getBridgeId() {
        return bridgeId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public PHBridge getPhBridge() {
        return phBridge;
    }

    static public class Builder {
        private String userName;
        private String ipAddress;
        private String bridgeId;
        private String macAddress;
        private PHBridge phBridge;

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder bridgeId(String ipAddress) {
            this.bridgeId = ipAddress;
            return this;
        }

        public Builder macAddress(String ipAddress) {
            this.macAddress = ipAddress;
            return this;
        }

        public Builder phBridge(PHBridge phBridge){
            this.phBridge = phBridge;
            return this;
        }

        public LightSystem build() {
            LightSystem lightSystem = new LightSystem();
            lightSystem.userName = userName;
            lightSystem.ipAddress = ipAddress;
            lightSystem.bridgeId = bridgeId;
            lightSystem.macAddress = macAddress;
            lightSystem.phBridge = phBridge;
            return lightSystem;
        }
    }
}
