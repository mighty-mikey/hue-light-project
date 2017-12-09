package com.minds.great.hueLightProject.data;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.minds.great.hueLightProject.core.presenters.MemoryInterface;

public class MemoryActivity extends AppCompatActivity implements MemoryInterface {

    private final String USER_NAME = "userName";
    private final String IP_ADDRESS = "ipAddress";

    public void saveLightSystem(LightSystem lightSystem) {
        SharedPreferences.Editor prefs = this.getPreferences(MODE_PRIVATE).edit();
        prefs.putString(USER_NAME, lightSystem.getUserName());
        prefs.putString(IP_ADDRESS, lightSystem.getIpAddress());
        prefs.apply();
    }

    @Override
    public LightSystem getLightSystem() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String userName = prefs.getString(USER_NAME, null);
        String ipAddress = prefs.getString(IP_ADDRESS, null);

        LightSystem lightSystem = null;

        if (userName != null && ipAddress != null) {
            lightSystem = new LightSystem.Builder()
                    .userName(userName)
                    .ipAddress(ipAddress)
                    .build();
        }

        return lightSystem;
    }
}

