package com.minds.great.hueLightProject.userInterface.fragments.Lists.moodListFragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.models.Mood;
import com.minds.great.hueLightProject.core.presenters.MoodListPresenter;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;
import com.minds.great.hueLightProject.userInterface.fragments.Lists.EvenOddAdapter;

import java.util.Collections;
import java.util.List;

public class MoodListAdapter extends EvenOddAdapter {
    private List<Mood> moodList = Collections.emptyList();
    private Context context;
    private MoodListPresenter moodListPresenter;

    MoodListAdapter(MoodListPresenter moodListPresenter){
        this.moodListPresenter = moodListPresenter;
    }

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
        TextView editName = viewItem.findViewById(R.id.editName);
        TextView name = viewItem.findViewById(R.id.name);
        name.setText(String.valueOf(mood.getName()));
        editName.setText(String.valueOf(mood.getName()));
        name.setOnClickListener(view12 -> moodListPresenter.selectSavedMood(mood));

        View deleteIcon = viewItem.findViewById(R.id.moodDeleteIcon);
        deleteIcon.setOnClickListener(view1 ->
                ((LightProjectActivity) context).getMoodListViewModel().deleteMood(mood));

        View editIcon = viewItem.findViewById(R.id.moodEditIcon);
        View saveIcon = viewItem.findViewById(R.id.moodSaveIcon);
        editIcon.setOnClickListener(view13 -> {
           name.setVisibility(View.GONE);
           saveIcon.setVisibility(View.VISIBLE);
           editName.setVisibility(View.VISIBLE);
        });
        saveIcon.setOnClickListener(view14 -> {
            mood.setName(editName.getText().toString());
            ((LightProjectActivity) context).getMoodListViewModel().updateMood(mood);
            name.setVisibility(View.VISIBLE);
            saveIcon.setVisibility(View.GONE);
            editName.setVisibility(View.GONE);
            editName.requestFocus();
        });

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
