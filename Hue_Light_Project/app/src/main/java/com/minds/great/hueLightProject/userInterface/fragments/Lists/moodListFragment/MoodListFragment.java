package com.minds.great.hueLightProject.userInterface.fragments.Lists.moodListFragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.presenters.MoodListPresenter;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;

import javax.inject.Inject;

public class MoodListFragment extends Fragment {

    @Inject
    MoodListPresenter moodListPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mood_list, container, false);

        if (getActivity() instanceof LightProjectActivity) {
            ((LightProjectActivity) getActivity()).getInjector().inject(this);
        }

        MoodListViewModel moodListViewModel = ((LightProjectActivity) getActivity()).getMoodListViewModel();

        ListView moodList = view.findViewById(R.id.savedMoodsList);
        MoodListAdapter adapter = new MoodListAdapter(moodListPresenter);
        moodList.setAdapter(adapter);
        moodListViewModel.getAllMoods().observe(this, moods ->
                adapter.setMoodList(moods, getContext()));

        return view;
    }
}
