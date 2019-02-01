package com.minds.great.hueLightProject.userInterface.fragments.moodListFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.models.Mood;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.Collections;
import java.util.List;

public class MoodListAdapter extends BaseAdapter{
    private List<Mood> moodList = Collections.emptyList();
    private Context context;

    @Override
    public int getCount() {
            return moodList.size();
    }

    @Override
    public Mood getItem(int i) {
        return moodList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return moodList.get(i).getPrimaryKey();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Mood mood = moodList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewItem = inflater.inflate(R.layout.mood_list_item, null);
        TextView primaryKey = viewItem.findViewById(R.id.primaryKey);
        primaryKey.setText(String.valueOf(mood.getPrimaryKey()));
        return viewItem;
    }

    void setMoodList(List<Mood> moodList, Context context){
        this.moodList = moodList;
        this.context = context;
        notifyDataSetChanged();
    }
}
