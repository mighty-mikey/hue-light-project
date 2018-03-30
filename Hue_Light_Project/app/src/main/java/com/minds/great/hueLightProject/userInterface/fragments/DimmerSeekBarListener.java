package com.minds.great.hueLightProject.userInterface.fragments;

import android.widget.SeekBar;

import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;

import java.util.Timer;
import java.util.TimerTask;

public class DimmerSeekBarListener implements SeekBar.OnSeekBarChangeListener {
    private Timer timer = null;
    private LightState lightState = new LightState();
    private int brightness = 0;
    private LightPoint light;

    DimmerSeekBarListener(LightPoint light) {
        this.light = light;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        brightness = i;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        lightState.setBrightness(light.getLightState().getBrightness());
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (brightness != lightState.getBrightness()) {
                    lightState.setBrightness(brightness);
                    light.updateState(lightState);
                }
            }
        }, 300, 300);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        timer.cancel();
        lightState.setBrightness(brightness);
        light.updateState(lightState);
    }

}
