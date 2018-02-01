package com.minds.great.hueLightProject.userInterface.fragments.lightListFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;

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

            bridgeName.setText(light.getName());
            onOffSwitch.setChecked(light.getLightState().isOn());
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
