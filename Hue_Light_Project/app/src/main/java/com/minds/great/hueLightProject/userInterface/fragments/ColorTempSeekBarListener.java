package com.minds.great.hueLightProject.userInterface.fragments;

import android.widget.SeekBar;

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

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        ct = i + 150;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        lightState.setCT(light.getLightState().getCT());
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (ct != lightState.getCT()) {
                    lightState.setCT(ct);
                    light.updateState(lightState);
                }
            }
        }, 300, 300);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        timer.cancel();
        lightState.setCT(ct);
        light.updateState(lightState);
    }
}
