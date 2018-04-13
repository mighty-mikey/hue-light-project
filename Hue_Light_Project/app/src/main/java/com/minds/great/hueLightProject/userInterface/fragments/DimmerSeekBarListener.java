package com.minds.great.hueLightProject.userInterface.fragments;

import android.widget.SeekBar;

import com.flask.colorpicker.ColorPickerView;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class DimmerSeekBarListener implements SeekBar.OnSeekBarChangeListener {
    private LightState lightState = new LightState();
    private int brightness = 0;
    private LightPoint light;
    private Disposable subscribe;

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
        subscribe = Observable.timer(300, TimeUnit.MILLISECONDS).subscribe(aLong -> {
            if (brightness != lightState.getBrightness()) {
                lightState.setBrightness(brightness);
                light.updateState(lightState);
            }
        });
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (subscribe != null) {
            subscribe.dispose();
            subscribe = null;
        }
        lightState.setBrightness(brightness);
        light.updateState(lightState);
    }
}
