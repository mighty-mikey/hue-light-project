package com.minds.great.hueLightProject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.philips.lighting.hue.sdk.PHAccessPoint;

import java.util.List;

public class BridgeListAdapter extends BaseAdapter {
    private List<PHAccessPoint> listOfFoundBridges;
    private Context context;
    final private int HEADER_CODE = 1;
    final private int FOOTER_CODE = -1;

    @Override
    public int getCount() {
        return listOfFoundBridges.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfFoundBridges.get(position);
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
                itemView = inflater.inflate(R.layout.bridge_list_header, null);
            } else if (itemViewType == FOOTER_CODE) {
                itemView = inflater.inflate(R.layout.bridge_list_footer, null);
            } else {
                itemView = inflater.inflate(R.layout.bridge_list_item, null);
            }
            TextView bridgeName = (TextView) itemView.findViewById(R.id.bridgeName);
            TextView macAddress = (TextView) itemView.findViewById(R.id.bridgeMacAddress);

            bridgeName.setText(listOfFoundBridges.get(position).getBridgeId());
            macAddress.setText(listOfFoundBridges.get(position).getMacAddress());
        }
        return itemView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_CODE;
        } else if (position == listOfFoundBridges.size() - 1) {
            return FOOTER_CODE;
        }
        return 0;
    }

    void setBridgeList(List<PHAccessPoint> listOfFoundBridges, Context context) {
        this.listOfFoundBridges = listOfFoundBridges;
        this.context = context;
    }
}
