package com.minds.great.hueLightProject.userInterface.fragments.moodListFragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.models.Mood;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;

import java.util.List;

public class MoodListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mood_list, container, false);

        if (getActivity() instanceof LightProjectActivity) {
            ((LightProjectActivity) getActivity()).getInjector().inject(this);
        }


        MoodListViewModel moodListViewModel = ((LightProjectActivity) getActivity()).getMoodListViewModel();

        ListView moodList = view.findViewById(R.id.savedMoodsList);
        MoodListAdapter adapter = new MoodListAdapter();
        moodList.setAdapter(adapter);
        moodListViewModel.getAllMoods().observe(this, moods ->
                adapter.setMoodList(moods, getContext()));

        return view;
    }

    public static Fragment newInstance() {
        return new MoodListFragment();
    }
}
