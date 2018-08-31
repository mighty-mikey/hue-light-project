package com.minds.great.hueLightProject.userInterface.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;

public class MoodListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mood_list, container, false);

        if(getActivity() instanceof LightProjectActivity) {
            ((LightProjectActivity) getActivity()).getInjector().inject(this);
        }

        return view;
    }

    public static Fragment newInstance() {
        return new MoodListFragment();
    }
}
