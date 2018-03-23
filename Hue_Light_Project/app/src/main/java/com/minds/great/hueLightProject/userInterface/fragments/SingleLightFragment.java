package com.minds.great.hueLightProject.userInterface.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flask.colorpicker.ColorPickerView;

import android.widget.SeekBar;
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

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

public class SingleLightFragment extends Fragment implements SingleLightInterface {

    @Inject
    SingleLightPresenter singleLightPresenter;

    private Switch onOffSwitch;
    private LightPoint light;
    private SeekBar dimmer;
    private SeekBar colorTemp;

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
        ColorPickerView colorPicker = (ColorPickerView) getActivity().findViewById(R.id.color_picker_view);
        colorPicker.addOnColorSelectedListener(this::changeLightColor);
        singleLightPresenter.viewLoaded(this);
        initViews();
    }


    @Override
    public void onDestroy() {
        singleLightPresenter.viewUnloaded();
        super.onDestroy();
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

    private void initViews() {
        View view = getView();
        if (null != view) {
            onOffSwitch = (Switch) view.findViewById(R.id.onOffSwitch);

            onOffSwitch.setChecked(light.getLightState().isOn());
            TextView lightName = (TextView) view.findViewById(R.id.lightName);
            lightName.setText(light.getName());
            onOffSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
                LightState lightState = new LightState();
                lightState.setOn(b);
                light.updateState(lightState);
            });
            light.getLightState().getCT();
            dimmer = (SeekBar) view.findViewById(R.id.dimmer);
            dimmer.setProgress(light.getLightState().getBrightness());
            dimmer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                Timer timer = null;
                LightState lightState = new LightState();
                int brightness = 0;

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
            });

            if(light.getLightState().getColormode().equals(ColorMode.COLOR_TEMPERATURE)){
                colorTemp = (SeekBar) view.findViewById(R.id.colorTemp);
                colorTemp.setProgress(light.getLightState().getCT() - 150);
                colorTemp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    Timer timer = null;
                    LightState lightState = new LightState();
                    int ct = 0;

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
                });
            }
        }
    }

    public void setLight(LightPoint light) {
        this.light = light;
    }

    @Override
    public void showColorPicker() {
        getActivity().findViewById(R.id.color_picker_view).setVisibility(View.VISIBLE);
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
            colorTemp.setProgress(updatedLight.getLightState().getCT() - 150);
        });
    }
}
