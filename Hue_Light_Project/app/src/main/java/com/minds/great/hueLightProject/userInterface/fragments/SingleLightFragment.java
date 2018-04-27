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
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;
import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor;

import org.androidannotations.annotations.EFragment;

import javax.inject.Inject;

@EFragment
public class SingleLightFragment extends Fragment implements SingleLightInterface {

    @Inject
    SingleLightPresenter singleLightPresenter;

    Switch onOffSwitch;
    TextView lightName;
    SeekBar dimmer;
    SeekBar colorTemp;
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
            colorTemp = (SeekBar) getView().findViewById(R.id.colorTemp);

            colorTemp.setProgress(light.getLightState().getCT() - 150);
            colorTemp.setOnSeekBarChangeListener(new ColorTempSeekBarListener(light));
        }
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
        int i = 0xFF000000 | r | g | b;

        colorPicker.setInitialColor(i, false);
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
    public void showColorTempSeekBar() {
        getActivity().findViewById(R.id.colorTemp).setVisibility(View.VISIBLE);
    }

    @Override
    public void updateLight(LightPoint updatedLight) {
        getActivity().runOnUiThread(() -> {
            onOffSwitch.setChecked(updatedLight.getLightState().isOn());
            dimmer.setProgress(updatedLight.getLightState().getBrightness());
            if (null != colorTemp && updatedLight.getLightState() != null) {
                colorTemp.setProgress(updatedLight.getLightState().getCT() - 150);
            }
            if(null != colorPicker){
                changeLightColor(updatedLight.getLightState().getColor());
            }
        });
    }
}
