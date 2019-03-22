package com.minds.great.hueLightProject.userInterface.fragments.Lists.moodListFragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.models.Mood;
import com.minds.great.hueLightProject.userInterface.fragments.Lists.EvenOddAdapter;

import java.util.Collections;
import java.util.List;

public class MoodListAdapter extends EvenOddAdapter {
    private List<Mood> moodList = Collections.emptyList();
    private Context context;

    @Override
    public int getCount() {
        if (null != moodList) {
            return moodList.size();
        }
        return 0;
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
        int itemViewType = getItemViewType(position);

        View viewItem = inflateView(itemViewType, context);
        TextView name = viewItem.findViewById(R.id.name);
        name.setText(String.valueOf(mood.getName()));
        return viewItem;
    }

    void setMoodList(List<Mood> moodList, Context context) {
        this.moodList = moodList;
        this.context = context;
        notifyDataSetChanged();
    }

    public int getEvenLayout() {
        return R.layout.mood_list_even;
    }

    public int getOddLayout() {
        return R.layout.mood_list_odd;
    }
}
