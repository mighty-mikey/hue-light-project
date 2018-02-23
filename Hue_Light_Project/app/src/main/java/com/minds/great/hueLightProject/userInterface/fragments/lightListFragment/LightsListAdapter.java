package com.minds.great.hueLightProject.userInterface.fragments.lightListFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.minds.great.hueLightProject.hueImpl.HueUtil;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.environment.TemperatureSensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.environment.TemperatureSensorConfiguration;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.environment.TemperatureSensorState;
import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor;

import java.util.List;

public class LightsListAdapter extends BaseAdapter {
    private List<LightPoint> lightsList;
    private Context context;
    final private int HEADER_CODE = 1;
    final private int FOOTER_CODE = -1;

    @Override
    public int getCount() {
        if(null != lightsList) {
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
        LayoutInflater inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("ViewHolder") View itemView = null;
        int itemViewType = getItemViewType(position);

        if (inflater != null) {
            if (itemViewType == HEADER_CODE) {
                itemView = inflater.inflate(R.layout.lights_list_header, null);
            } else if (itemViewType == FOOTER_CODE) {
                itemView = inflater.inflate(R.layout.lights_list_footer, null);
            } else {
                itemView = inflater.inflate(R.layout.lights_list_item, null);
            }
            TextView bridgeName = (TextView) itemView.findViewById(R.id.lightName);
            Switch onOffSwitch = (Switch) itemView.findViewById(R.id.onOffSwitch);
            View color = itemView.findViewById(R.id.color);
            bridgeName.setText(light.getName());
            onOffSwitch.setChecked(light.getLightState().isOn());
            if (light.getLightState().isOn()) {
                if (light.getLightState().getColormode() == ColorMode.XY) {
                    HueColor.RGB rgb = light.getLightState().getColor().getRGB();
                    color.setBackgroundColor(Color.argb(light.getLightState().getBrightness(), rgb.r, rgb.g, rgb.b));
                } else if (light.getLightState().getColormode() == ColorMode.COLOR_TEMPERATURE) {
                    color.setBackgroundColor(HueUtil.getRGBFromColorTemperature(light.getLightState().getCT(), light.getLightState().getBrightness()));
                }
            } else {
                color.setBackgroundColor(Color.BLACK);
            }
            onOffSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
                LightState lightState = light.getLightState();
                lightState.setOn(b);
                light.updateState(lightState);
            });
        }
        return itemView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_CODE;
        } else if (position == lightsList.size() - 1) {
            return FOOTER_CODE;
        }
        return 0;
    }

    public void setLightsList(List<LightPoint> lightPoints, Context context) {
        this.context = context;
        this.lightsList = lightPoints;
    }

    public void lightsAndGroupsHeartbeat(List<LightPoint> lightPoints) {
        this.lightsList = lightPoints;
        notifyDataSetChanged();
    }
}
