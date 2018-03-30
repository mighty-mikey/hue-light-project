package com.minds.great.hueLightProject.userInterface.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flask.colorpicker.ColorPickerView;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.TextView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.presenters.SingleLightInterface;
import com.minds.great.hueLightProject.core.presenters.SingleLightPresenter;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;
import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

@EFragment
public class SingleLightFragment extends Fragment implements SingleLightInterface {

    @Inject
    SingleLightPresenter singleLightPresenter;

    @ViewById
    Switch onOffSwitch;
    @ViewById
    TextView lightName;
    @ViewById
    SeekBar dimmer;
    @ViewById
    SeekBar colorTemp;
    @ViewById
    ColorPickerView colorPicker;

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
        colorPicker.addOnColorSelectedListener(this::changeLightColor);
        singleLightPresenter.viewLoaded(this);
        light = singleLightPresenter.getSelectedListPoint();
        HueColor color = light.getLightState().getColor();
        changeLightColor(color);
        initViews();
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
        Log.d("what is this shit ", "r: " + rgb.r);
        Log.d("what is this shit ", "g: " + rgb.g);
        Log.d("what is this shit ", "b: " + rgb.b);
        Log.d("what is this shit ", "original int: " + i);
        singleLightPresenter.setColor(new HueColor(rgb, null, null));
    }

    private void changeLightColor(HueColor color) {
        colorPicker.setColor( color.getRGB().r , false);
    }

    private void initViews() {
        onOffSwitch.setChecked(light.getLightState().isOn());
        lightName.setText(light.getName());
        onOffSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            LightState lightState = new LightState();
            lightState.setOn(b);
            light.updateState(lightState);
        });
        light.getLightState().getCT();
        dimmer.setProgress(light.getLightState().getBrightness());
        dimmer.setOnSeekBarChangeListener(new DimmerSeekBarListener(light));

        if (light.getLightState().getColormode().equals(ColorMode.COLOR_TEMPERATURE)) {
            colorTemp.setProgress(light.getLightState().getCT() - 150);
            colorTemp.setOnSeekBarChangeListener(new ColorTempSeekBarListener(light));
        }
    }

    @Override
    public void showColorPicker() {
        colorPicker.setVisibility(View.VISIBLE);
    }

    @Override
    public void showColorTempSeekBar() {
        getActivity().findViewById(R.id.colorTemp).setVisibility(View.VISIBLE);
    }

    @Override
    public void updateLight(LightPoint updatedLight) {
        getActivity().runOnUiThread(() -> {
            onOffSwitch.setChecked(updatedLight.getLightState().isOn());
            dimmer.setProgress(updatedLight.getLightState().getBrightness());
            if (null != colorTemp) {
                colorTemp.setProgress(updatedLight.getLightState().getCT() - 150);
            }
        });
    }
}
