package com.minds.great.hueLightProject.userInterface.fragments.singleLightFragment;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
    }

    private void initViews() {
        onOffSwitch = (Switch) getView().findViewById(R.id.onOffSwitch);
        lightName = (TextView) getView().findViewById(R.id.lightName);
        dimmer = (SeekBar) getView().findViewById(R.id.dimmer);
        colorTemp = (SeekBar) getView().findViewById(R.id.colorTemp);
        colorPicker = (ColorPickerView) getView().findViewById(R.id.colorPicker);
    }

    private void initLight() {
        onOffSwitch.setOnCheckedChangeListener((compoundButton, b) -> singleLightPresenter.updateOnState(b));
        dimmer.setOnSeekBarChangeListener(new DimmerSeekBarListener(singleLightPresenter));
    }

    @Override
    public void setColorTempProgress(int colorTemp1) {
        colorTemp.setProgress(colorTemp1 - UiConstants.HUE_COLOR_TEMP_OFFSET);
    }

    @Override
    public void showColorTemp() {
        if (getView() != null) {
            colorTemp = (SeekBar) getView().findViewById(R.id.colorTemp);
            colorTemp.setOnSeekBarChangeListener(new ColorTempSeekBarListener(singleLightPresenter));
            colorTemp.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showColorPicker(HueColor color) {
        int i = calculateIntColorFromHueColor(color);
        colorPicker.setInitialColor(i, false);
        getActivity().runOnUiThread(() -> colorPicker.setVisibility(View.VISIBLE));
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
        singleLightPresenter.updateColor(new HueColor(rgb, null, null));
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

    @Override
    public void setOnOffSwitch(boolean lightIsOn) {
        getActivity().runOnUiThread(() -> onOffSwitch.setChecked(lightIsOn));
    }

    @Override
    public void setLightNameText(String nameOfLight) {
        getActivity().runOnUiThread(() -> lightName.setText(nameOfLight));
    }

    @Override
    public void setDimmerProgress(int brightness) {
        getActivity().runOnUiThread(() -> dimmer.setProgress(brightness));
    }
}
