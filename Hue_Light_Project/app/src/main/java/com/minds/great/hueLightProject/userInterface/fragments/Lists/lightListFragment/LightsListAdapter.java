package com.minds.great.hueLightProject.userInterface.fragments.Lists.lightListFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.presenters.LightListPresenter;
import com.minds.great.hueLightProject.hueImpl.HueUtil;
import com.minds.great.hueLightProject.userInterface.fragments.Lists.EvenOddAdapter;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;
import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor;

import java.util.List;

public class LightsListAdapter extends EvenOddAdapter {
    private List<LightPoint> lightsList;
    private Context context;

    private LightListPresenter lightListPresenter;

    LightsListAdapter(LightListPresenter lightListPresenter) {
        this.lightListPresenter = lightListPresenter;
    }

    @Override
    public int getCount() {
        if (null != lightsList) {
            return lightsList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return lightsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LightPoint light = lightsList.get(position);

        int itemViewType = getItemViewType(position);

        View itemView = inflateView(itemViewType, context);

        if (itemView != null) {
            TextView lightName = itemView.findViewById(R.id.lightName);
            ImageView lightOnIcon = itemView.findViewById(R.id.lightOnIcon);
            ImageView lightOffIcon = itemView.findViewById(R.id.lightOffIcon);
            lightName.setText(light.getName());

            if (light.getLightState().isOn()) {
                ColorMode lightColorMode = light.getLightState().getColormode();
                if (lightColorMode == ColorMode.XY) {
                    HueColor.RGB rgb = light.getLightState().getColor().getRGB();
                    lightOnIcon.setColorFilter(Color.argb(light.getLightState().getBrightness(), rgb.r, rgb.g, rgb.b));
                } else if (lightColorMode == ColorMode.COLOR_TEMPERATURE) {
                    lightOnIcon.setColorFilter(HueUtil.getRGBFromColorTemperature(light.getLightState().getCT(), light.getLightState().getBrightness()));
                }
                lightOnIcon.setVisibility(View.VISIBLE);
                lightOffIcon.setVisibility(View.GONE);
            }
            else{
                lightOnIcon.setVisibility(View.GONE);
                lightOffIcon.setVisibility(View.VISIBLE);
            }

            lightOnIcon.setOnClickListener((icon) -> {
                LightState lightState = light.getLightState();
                lightState.setOn(false);
                light.updateState(lightState);
            });

            lightOffIcon.setOnClickListener((icon) -> {
                LightState lightState = light.getLightState();
                lightState.setOn(true);
                light.updateState(lightState);
            });
            itemView.setOnClickListener(view ->
                    lightListPresenter.setSelectedLightPosition(position));
        }
        return itemView;
    }

    void setLightsList(List<LightPoint> lightPoints, Context context) {
        this.context = context;
        this.lightsList = lightPoints;
        notifyDataSetChanged();
    }

    void updateListData(List<LightPoint> lightPoints) {
        this.lightsList = lightPoints;
        notifyDataSetChanged();
    }

    public int getEvenLayout(){
        return R.layout.lights_list_even;
    }
    public int getOddLayout(){
        return R.layout.lights_list_odd;
    }
}
