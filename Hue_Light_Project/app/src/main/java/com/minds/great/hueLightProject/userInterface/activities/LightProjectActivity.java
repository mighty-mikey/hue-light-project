package com.minds.great.hueLightProject.userInterface.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import dagger.hilt.android.AndroidEntryPoint;

import com.minds.great.hueLightProject.R;

import com.minds.great.hueLightProject.core.domain.MainDomain;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.MainInterface;
import com.minds.great.hueLightProject.userInterface.fragments.ConnectionFragment;
import com.minds.great.hueLightProject.userInterface.fragments.MainFragment;
import com.minds.great.hueLightProject.userInterface.fragments.Lists.moodListFragment.MoodListViewModel;
import com.minds.great.hueLightProject.userInterface.fragments.singleLightFragment.SingleLightFragment;
import com.minds.great.hueLightProject.userInterface.fragments.tabFragment.TabFragment;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@AndroidEntryPoint
public class LightProjectActivity extends AppCompatActivity implements MainInterface {

    @Inject
    MainDomain mainDomain;
    private FragmentManager fragmentManager;
    private MoodListViewModel moodListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_container);

        moodListViewModel = new ViewModelProvider(this).get(MoodListViewModel.class);

        fragmentManager = getSupportFragmentManager();
        MainFragment mainFragment = new MainFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mainFragment)
                .commit();

    }

    public MoodListViewModel getMoodListViewModel() {
        return moodListViewModel;
    }

    @Override
    protected void onResume() {
        mainDomain.viewLoaded(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mainDomain.viewUnloaded();
        super.onPause();
    }

    @Override
    public void navigateToConnectionFragment() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new ConnectionFragment())
                .commit();
    }

    @Override
    public void navigateToTabFragment() {
        TabFragment fragment = new TabFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void navigateToSingleLightFragment() {
        SingleLightFragment fragment = new SingleLightFragment();

        fragmentManager.beginTransaction()
                .addToBackStack("lightsListFragment")
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}