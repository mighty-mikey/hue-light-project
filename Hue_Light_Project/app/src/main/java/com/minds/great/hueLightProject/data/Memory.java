package com.minds.great.hueLightProject.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.minds.great.hueLightProject.core.presenters.MemoryInterface;

public class Memory implements MemoryInterface {

    private final String USER_NAME = "userName";
    private final String IP_ADDRESS = "ipAddress";
    private Context context;

    public Memory(Context context){
        this.context = context;
    }

    public void saveLightSystem(LightSystem lightSystem) {

        SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefs.putString(USER_NAME, lightSystem.getUserName());
        prefs.putString(IP_ADDRESS, lightSystem.getIpAddress());
        prefs.apply();
    }

    @Override
    public LightSystem getLightSystem() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
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

