package com.minds.great.hueLightProject.userInterface.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.controllers.MainController;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MainFragmentView;
import com.minds.great.hueLightProject.userInterface.fragments.ConnectionFragment;
import com.minds.great.hueLightProject.userInterface.fragments.MainFragment;
import com.minds.great.hueLightProject.userInterface.fragments.lightListFragment.LightsListFragment;
import com.minds.great.hueLightProject.utils.dagger.DaggerInjector;
import com.minds.great.hueLightProject.utils.dagger.HueModule;
import com.minds.great.hueLightProject.utils.dagger.Injector;

import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@EActivity(R.layout.fragment_container)
public class LightProjectActivity extends FragmentActivity implements MainFragmentView {

    @Inject
    MainController mainController;
    private FragmentManager fragmentManager;
    private Injector injector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getInjector().inject(this);

        setContentView(R.layout.fragment_container);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new MainFragment())
                .commit();

        mainController.viewLoaded(this);
    }

    @Override
    protected void onDestroy() {
        mainController.viewUnloaded();
        injector = null;
        super.onDestroy();
    }

    @Override
    public void navigateToConnectionFragment() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new ConnectionFragment()).commit();
    }

    @Override
    public void navigateToLightListFragment() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new LightsListFragment()).commit();
    }

    public Injector getInjector() {
        if (null == injector) {
            injector = DaggerInjector
                    .builder()
                    .hueModule(new HueModule(this))
                    .build();
        }
        return injector;
    }
}