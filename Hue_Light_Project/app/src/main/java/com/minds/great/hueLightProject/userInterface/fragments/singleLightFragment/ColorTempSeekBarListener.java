package com.minds.great.hueLightProject.userInterface.fragments.singleLightFragment;

import android.widget.SeekBar;

import com.minds.great.hueLightProject.core.presenters.SingleLightPresenter;
import com.minds.great.hueLightProject.userInterface.UiConstants;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


public class ColorTempSeekBarListener implements SeekBar.OnSeekBarChangeListener {
    private int colorTemperature = 0;
    private SingleLightPresenter presenter;
    private Disposable subscribe;

    ColorTempSeekBarListener(SingleLightPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        colorTemperature = progress + UiConstants.HUE_COLOR_TEMP_OFFSET;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        subscribe = Observable
                .timer(300, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> presenter.updateColorTemperature(colorTemperature));

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (subscribe != null) {
            subscribe.dispose();
            subscribe = null;
        }
        presenter.updateColorTemperature(colorTemperature);
    }
}
