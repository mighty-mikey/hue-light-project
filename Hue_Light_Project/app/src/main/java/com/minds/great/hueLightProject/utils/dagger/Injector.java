package com.minds.great.hueLightProject.utils.dagger;

import com.minds.great.hueLightProject.userInterface.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={HueModule.class, LightProjectModule.class})
public interface Injector {
    void inject(MainActivity activity);
}
