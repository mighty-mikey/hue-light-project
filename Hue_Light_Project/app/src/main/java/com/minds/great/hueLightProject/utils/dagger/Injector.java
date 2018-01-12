package com.minds.great.hueLightProject.utils.dagger;

import com.minds.great.hueLightProject.userInterface.ConnectionActivity;
import com.minds.great.hueLightProject.userInterface.LightsListActivity;
import com.minds.great.hueLightProject.userInterface.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={HueModule.class, LightProjectModule.class})
public interface Injector {
    void inject(ConnectionActivity activity);

    void inject(MainActivity mainActivity);

    void inject(LightsListActivity lightsListActivity);
}
