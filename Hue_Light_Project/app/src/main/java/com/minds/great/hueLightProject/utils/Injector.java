package com.minds.great.hueLightProject.utils;

import com.minds.great.hueLightProject.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={HueModule.class})
public interface Injector {
    void inject(MainActivity activity);
}
