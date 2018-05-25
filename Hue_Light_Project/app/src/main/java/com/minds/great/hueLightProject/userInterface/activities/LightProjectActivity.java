package com.minds.great.hueLightProject.userInterface.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.crashlytics.android.Crashlytics;
import com.minds.great.hueLightProject.R;
import com.minds.great.hueLightProject.core.domain.MainDomain;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.MainInterface;
import com.minds.great.hueLightProject.userInterface.fragments.ConnectionFragment;
import com.minds.great.hueLightProject.userInterface.fragments.MainFragment;
import com.minds.great.hueLightProject.userInterface.fragments.SingleLightFragment;
import com.minds.great.hueLightProject.userInterface.fragments.lightListFragment.LightsListFragment;
import com.minds.great.hueLightProject.utils.dagger.DaggerInjector;
import com.minds.great.hueLightProject.utils.dagger.HueModule;
import com.minds.great.hueLightProject.utils.dagger.Injector;

import io.fabric.sdk.android.Fabric;
import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@EActivity(R.layout.fragment_container)
public class LightProjectActivity extends FragmentActivity implements MainInterface {

    @Inject
    MainDomain mainDomain;
    private FragmentManager fragmentManager;
    private Injector injector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        getInjector().inject(this);

        setContentView(R.layout.fragment_container);

        fragmentManager = getSupportFragmentManager();
        MainFragment mainFragment = new MainFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mainFragment)
                .commit();

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
    protected void onDestroy() {
        injector = null;
        super.onDestroy();
    }

    @Override
    public void navigateToConnectionFragment() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new ConnectionFragment())
                .commit();
    }

    @Override
    public void navigateToLightListFragment() {
        LightsListFragment fragment = new LightsListFragment();
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