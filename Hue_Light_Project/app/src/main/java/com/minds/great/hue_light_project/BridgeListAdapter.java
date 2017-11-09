package com.minds.great.hue_light_project;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import java.util.List;

public class BridgeListAdapter extends BaseAdapter{
    private List<PHAccessPoint> listOfFoundBridges;
    private Context context;

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
        if (inflater != null) {
            itemView = inflater.inflate(R.layout.bridge_list_item, null);
            TextView bridgeName = (TextView) itemView.findViewById(R.id.bridgeName);
            TextView macAddress = (TextView) itemView.findViewById(R.id.bridgeMacAddress);
            bridgeName.setText(listOfFoundBridges.get(position).getBridgeId());
            macAddress.setText(listOfFoundBridges.get(position).getMacAddress());
        }
        return itemView;
    }

    void setBridgeList(List<PHAccessPoint> listOfFoundBridges, Context context) {
        this.listOfFoundBridges = listOfFoundBridges;
        this.context = context;
    }
}
