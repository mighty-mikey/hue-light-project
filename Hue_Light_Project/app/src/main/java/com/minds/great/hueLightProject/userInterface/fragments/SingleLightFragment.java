package com.minds.great.hueLightProject.userInterface.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.flask.colorpicker.ColorPickerView;
import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.presenters.SingleLightInterface;
import com.minds.great.hueLightProject.core.presenters.SingleLightPresenter;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;
import com.minds.great.hueLightProject.utils.dagger.UiConstants;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor;

import org.androidannotations.annotations.EFragment;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

@EFragment
public class SingleLightFragment extends Fragment implements SingleLightInterface, SeekBar.OnSeekBarChangeListener {

    @Inject
    SingleLightPresenter singleLightPresenter;

    Switch onOffSwitch;
    TextView lightName;
    SeekBar dimmer;
    SeekBar colorTemp;
    ColorPickerView colorPicker;
    private int dimmerValue = 0;
    private Disposable subscribe;
    private LightPoint light;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getActivity() instanceof LightProjectActivity) {
            ((LightProjectActivity) getActivity()).getInjector().inject(this);
        }
        return inflater.inflate(R.layout.fragment_single_light, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initViews();
        initLight();
        colorPicker.addOnColorSelectedListener(this::changeLightColor);
        singleLightPresenter.viewLoaded(this);
        HueColor color = light.getLightState().getColor();
        color.setBrightness(255.0);
        changeLightColor(color);
    }

    private void initLight() {
        light = singleLightPresenter.getSelectedListPoint();

        onOffSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            singleLightPresenter.updateOnState(b);
        });
        dimmer.setOnSeekBarChangeListener(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        singleLightPresenter.viewUnloaded();
    }

    private void changeLightColor(int i) {
        String hexString = Integer.toHexString(i);
        HueColor.RGB rgb = new HueColor.RGB(
                Integer.valueOf(hexString.substring(2, 4), 16),
                Integer.valueOf(hexString.substring(4, 6), 16),
                Integer.valueOf(hexString.substring(6, 8), 16)
        );
        singleLightPresenter.setColor(new HueColor(rgb, null, null));
    }

    private void changeLightColor(HueColor color) {
        int i = calculateIntColorFromHueColor(color);

        colorPicker.setInitialColor(i, false);
    }

    private int calculateIntColorFromHueColor(HueColor color) {
        color.setBrightness(255.0);
        Integer r = color.getRGB().r;
        Integer g = color.getRGB().g;
        Integer b = color.getRGB().b;

        //Shift red 16-bits and mask out other stuff
        r = (r << 16) & 0x00FF0000;
        //Shift Green 8-bits and mask out other stuff
        g = (g << 8) & 0x0000FF00;
        //Mask out anything not blue.
        b = b & 0x000000FF;

        //0xFF000000 for 100% Alpha. Bitwise OR everything together.
        return 0xFF000000 | r | g | b;
    }

    private void initViews() {
        onOffSwitch = (Switch) getView().findViewById(R.id.onOffSwitch);
        lightName = (TextView) getView().findViewById(R.id.lightName);
        dimmer = (SeekBar) getView().findViewById(R.id.dimmer);
        colorTemp = (SeekBar) getView().findViewById(R.id.colorTemp);
        colorPicker = (ColorPickerView) getView().findViewById(R.id.colorPicker);
    }

    @Override
    public void showColorPicker() {
        colorPicker.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateSingleLightUi(LightPoint updatedLight) {
        getActivity().runOnUiThread(() -> {
            onOffSwitch.setChecked(updatedLight.getLightState().isOn());
            dimmer.setProgress(updatedLight.getLightState().getBrightness());
            if (light.getLightState().getColormode().equals(ColorMode.COLOR_TEMPERATURE)) {
                colorTemp.setProgress(updatedLight.getLightState().getCT() - UiConstants.HUE_COLOR_TEMP_OFFSET);
            }
            if (null != colorPicker) {
                int color = calculateIntColorFromHueColor(updatedLight.getLightState().getColor());
                colorPicker.setColor(color, true);
            }
        });
    }

    @Override
    public void setOnOffSwitch(boolean lightIsOn) {
        onOffSwitch.setChecked(lightIsOn);
    }

    @Override
    public void setLightNameText(String nameOfLight) {
        lightName.setText(nameOfLight);
    }

    @Override
    public void setDimmerProgress(int brightness) {
        dimmer.setProgress(brightness);
    }

    @Override
    public void initColorTemp(int colorTemp1) {
        if (getView() != null) {
            colorTemp = (SeekBar) getView().findViewById(R.id.colorTemp);
            colorTemp.setProgress(colorTemp1 - UiConstants.HUE_COLOR_TEMP_OFFSET);
            colorTemp.setOnSeekBarChangeListener(new ColorTempSeekBarListener(light));
            colorTemp.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        dimmerValue = i;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        subscribe = Observable.timer(300, TimeUnit.MILLISECONDS).subscribe(aLong -> {
            singleLightPresenter.updateBrightness(dimmerValue);
        });
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (subscribe != null) {
            subscribe.dispose();
            subscribe = null;
        }
        singleLightPresenter.updateBrightness(dimmerValue);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        ct = progress + UiConstants.HUE_COLOR_TEMP_OFFSET;
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
