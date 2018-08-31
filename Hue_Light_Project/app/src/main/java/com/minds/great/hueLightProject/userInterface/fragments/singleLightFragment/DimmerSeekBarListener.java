package com.minds.great.hueLightProject.userInterface.fragments.singleLightFragment;

import android.widget.SeekBar;

import com.minds.great.hueLightProject.core.presenters.SingleLightPresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class DimmerSeekBarListener implements SeekBar.OnSeekBarChangeListener {
    private int brightness = 0;
    private Disposable subscribe;
    private SingleLightPresenter presenter;

    DimmerSeekBarListener(SingleLightPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        brightness = i;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        subscribe = Observable
                .timer(300, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> presenter.updateBrightness(brightness));
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (subscribe != null) {
            subscribe.dispose();
            subscribe = null;
        }
        presenter.updateBrightness(brightness);
    }
}
