package com.minds.great.hueLightProject.userInterface.fragments;

import android.widget.SeekBar;

import com.minds.great.hueLightProject.utils.dagger.UiConstants;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;

import java.util.Timer;
import java.util.TimerTask;


public class ColorTempSeekBarListener implements SeekBar.OnSeekBarChangeListener {
    Timer timer = null;
    LightState lightState = new LightState();
    int ct = 0;
    private LightPoint light;

    public ColorTempSeekBarListener(LightPoint light) {
        this.light = light;
    }


}
