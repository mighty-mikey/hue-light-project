package com.minds.great.hueLightProject.userInterface.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.flask.colorpicker.ColorPickerView;
import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.presenters.SingleLightInterface;
import com.minds.great.hueLightProject.core.presenters.SingleLightPresenter;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;
import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor;

import javax.inject.Inject;

public class SingleLightFragment extends Fragment implements SingleLightInterface {

    @Inject
    SingleLightPresenter singleLightPresenter;


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

    @Override
    public void showColorPicker() {
        getActivity().findViewById(R.id.color_picker_view).setVisibility(View.VISIBLE);
    }
}
