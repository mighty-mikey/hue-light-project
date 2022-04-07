package com.minds.great.hueLightProject.userInterface.fragments.Lists.moodListFragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import dagger.hilt.android.AndroidEntryPoint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.presenters.MoodListPresenter;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;

import javax.inject.Inject;

@AndroidEntryPoint
public class MoodListFragment extends Fragment {

    @Inject
    MoodListPresenter moodListPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mood_list, container, false);
        MoodListViewModel moodListViewModel = ((LightProjectActivity) getActivity()).getMoodListViewModel();

        ListView moodList = view.findViewById(R.id.savedMoodsList);
        MoodListAdapter adapter = new MoodListAdapter(moodListPresenter);
        moodList.setAdapter(adapter);
        moodListViewModel.getAllMoods().observe(this, moods ->
                adapter.setMoodList(moods, getContext()));

        return view;
    }
}
