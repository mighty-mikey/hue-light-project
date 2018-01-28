package com.minds.great.hueLightProject.utils.dagger;

import com.minds.great.hueLightProject.userInterface.fragments.ConnectionFragment;
import com.minds.great.hueLightProject.userInterface.activities.LightProjectActivity;
import com.minds.great.hueLightProject.userInterface.fragments.lightListFragment.LightsListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={HueModule.class, LightProjectModule.class})
public interface Injector {
    void inject(ConnectionFragment activity);

    void inject(LightProjectActivity lightProjectActivity);

    void inject(LightsListFragment lightsListFragment);
}
