package com.minds.great.hueLightProject.userInterface.fragments.lightListFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.MainInterface;
import com.minds.great.hueLightProject.core.presenters.LightListPresenter;
import com.minds.great.hueLightProject.hueImpl.HueUtil;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;
import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor;

import java.util.List;

public class LightsListAdapter extends BaseAdapter {
    private List<LightPoint> lightsList;
    private Context context;
    final private int EVEN_ROW_CODE = 1;
    final private int ODD_ROW_CODE = -1;

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

        View itemView = inflateView(itemViewType);

        if (itemView != null) {
            TextView lightName = (TextView) itemView.findViewById(R.id.lightName);
            Switch onOffSwitch = (Switch) itemView.findViewById(R.id.onOffSwitch);
            View color = itemView.findViewById(R.id.color);
            lightName.setText(light.getName());
            onOffSwitch.setChecked(light.getLightState().isOn());

            int lightColor = Color.BLACK;
            if (light.getLightState().isOn()) {
                ColorMode lightColorMode = light.getLightState().getColormode();
                if (lightColorMode == ColorMode.XY) {
                    HueColor.RGB rgb = light.getLightState().getColor().getRGB();
                    lightColor = Color.argb(light.getLightState().getBrightness(), rgb.r, rgb.g, rgb.b);
                } else if (lightColorMode == ColorMode.COLOR_TEMPERATURE) {
                    lightColor = HueUtil.getRGBFromColorTemperature(light.getLightState().getCT(), light.getLightState().getBrightness());
                }
            }
            color.setBackgroundColor(lightColor);

            onOffSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
                LightState lightState = light.getLightState();
                lightState.setOn(b);
                light.updateState(lightState);
            });

            color.setOnClickListener(view -> {
                lightListPresenter.setSelectedLightPosition(position);
                ((MainInterface) view.getContext()).navigateToSingleLightFragment(light);
            });

            lightName.setOnClickListener(view -> {
                lightListPresenter.setSelectedLightPosition(position);
                ((MainInterface) view.getContext()).navigateToSingleLightFragment(light);
            });
        }
        return itemView;
    }

    private View inflateView(int itemViewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = null;
        if (inflater != null) {
            if (itemViewType == EVEN_ROW_CODE) {
                itemView = inflater.inflate(R.layout.lights_list_even, null);
            } else {
                itemView = inflater.inflate(R.layout.lights_list_odd, null);
            }
        }
        return itemView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position %2 == 0) {
            return EVEN_ROW_CODE;
        } else {
            return ODD_ROW_CODE;
        }
    }

    void setLightsList(List<LightPoint> lightPoints, Context context) {
        this.context = context;
        this.lightsList = lightPoints;
        notifyDataSetChanged();
    }

    void lightsAndGroupsHeartbeat(List<LightPoint> lightPoints) {
        this.lightsList = lightPoints;
        notifyDataSetChanged();
    }
}
