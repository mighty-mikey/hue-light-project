package com.minds.great.hueLightProject.userInterface.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import dagger.hilt.android.AndroidEntryPoint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minds.great.hueLightProject.R;

@AndroidEntryPoint
public class MainFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
