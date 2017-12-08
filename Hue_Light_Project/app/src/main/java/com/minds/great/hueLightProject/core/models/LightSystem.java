package com.minds.great.hueLightProject.core.models;

public class LightSystem {
    private String ipAddress;
    private String userName;

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserName() {
        return userName;
    }

    private LightSystem(){
    }

    static public class Builder {
        private LightSystem lightSystem;
        String userName;
        String ipAddress;

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public LightSystem build() {
            lightSystem = new LightSystem();
            lightSystem.userName = userName;
            lightSystem.ipAddress = ipAddress;
            return lightSystem;
        }
    }
}
