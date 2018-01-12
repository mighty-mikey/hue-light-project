package com.minds.great.hueLightProject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.model.PHLight;

import java.util.List;
import java.util.Map;

//TODO:  convert to light list adapter
public class LightsListAdapter extends BaseAdapter {
    private List<PHLight> lightsList;
    private Context context;
    final private int HEADER_CODE = 1;
    final private int FOOTER_CODE = -1;

    @Override
    public int getCount() {
        return lightsList.size();
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

            bridgeName.setText(lightsList.get(position).getName());
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

    public void setLightsList(List<PHLight> lightsList, Context context) {
        this.context = context;
        this.lightsList = lightsList;
    }
}
