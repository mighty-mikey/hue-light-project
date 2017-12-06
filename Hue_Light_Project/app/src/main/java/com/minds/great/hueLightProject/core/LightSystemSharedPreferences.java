package com.minds.great.hueLightProject.core;

public class LightSystemSharedPreferences {
    private String ipAddress;
    private String userName;

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserName() {
        return userName;
    }

    private LightSystemSharedPreferences(){
    }

    static public class Builder {
        private LightSystemSharedPreferences lightSystemSharedPreferences;
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

        public LightSystemSharedPreferences build() {
            lightSystemSharedPreferences = new LightSystemSharedPreferences();
            lightSystemSharedPreferences.userName = userName;
            lightSystemSharedPreferences.ipAddress = ipAddress;
            return lightSystemSharedPreferences;
        }
    }
}
