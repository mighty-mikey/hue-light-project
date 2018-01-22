package com.minds.great.hueLightProject.core.models;

public class LightSystem {
    private String ipAddress;
    private String userName;
    private String bridgeId;
    private String macAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserName() {
        return userName;
    }

    private LightSystem() {
    }

    public String getBridgeId() {
        return bridgeId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    static public class Builder {
        private String userName;
        private String ipAddress;
        private String bridgeId;
        private String macAddress;

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

        public LightSystem build() {
            LightSystem lightSystem = new LightSystem();
            lightSystem.userName = userName;
            lightSystem.ipAddress = ipAddress;
            lightSystem.bridgeId = bridgeId;
            lightSystem.macAddress = macAddress;
            return lightSystem;
        }
    }
}
