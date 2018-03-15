package com.minds.great.hueLightProject.userInterface.fragments;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.controllers.LightSystemController;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.LightsListInterface;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;
import com.minds.great.hueLightProject.userInterface.fragments.lightListFragment.LightsListAdapter;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import io.reactivex.internal.operators.observable.ObservableTimer;

public class SingleLightFragment extends Fragment implements LightsListInterface {

    @Inject
    LightSystemController lightSystemController;

    private Switch onOffSwitch;
    private LightPoint light;
    private SeekBar dimmer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(getActivity() instanceof LightProjectActivity) {
            ((LightProjectActivity) getActivity()).getInjector().inject(this);
        }
        return inflater.inflate(R.layout.fragment_single_light, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initViews();
        lightSystemController.viewLoaded(this);
    }

    @Override
    public void onDestroy() {
        lightSystemController.viewUnloaded();
        super.onDestroy();
    }

    private void initViews() {
        View view = getView();
        if (null != view) {
            onOffSwitch = (Switch) view.findViewById(R.id.onOffSwitch);

            onOffSwitch.setChecked(light.getLightState().isOn());
            TextView lightName = (TextView) view.findViewById(R.id.lightName);
            lightName.setText(light.getName());
            onOffSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
                LightState lightState = light.getLightState();
                lightState.setOn(b);
                light.updateState(lightState);
            });
            dimmer = (SeekBar) view.findViewById(R.id.dimmer);
            LinearGradient gradient = new LinearGradient(-100, 0, 1100, 0, Color.BLACK, Color.WHITE, Shader.TileMode.CLAMP);
            ShapeDrawable shape = new ShapeDrawable(new RectShape());
            shape.getPaint().setShader(gradient);


//            GradientDrawable gradientDrawable = new GradientDrawable(
//                    GradientDrawable.Orientation.RIGHT_LEFT, //set a gradient direction
//                    new int[] {0xFFFFFFFF,0x00000000}); //set the color of gradient
//            gradientDrawable.setCornerRadius(20f); //set corner radius
//
//            if(Build.VERSION.SDK_INT>=16)
//                dimmer.setBackground(gradientDrawable);
//            else dimmer.setBackgroundDrawable(gradientDrawable);
//            dimmer.setProgressDrawable( shape );
            dimmer.setProgress(light.getLightState().getBrightness());
            dimmer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                Timer timer = null;
                LightState lightState = light.getLightState();
                int brightness = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    brightness = i;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
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

        }
    }

    public void setLight(LightPoint light) {
        this.light = light;
    }

    @Override
    public void updateLights(LightSystem lightSystem) {
        getActivity().runOnUiThread(() -> {
            LightPoint uiLight = lightSystem.getBridge().getBridgeState().getLightPoint(light.getIdentifier());
            onOffSwitch.setChecked(uiLight.getLightState().isOn());
        });
    }
}
