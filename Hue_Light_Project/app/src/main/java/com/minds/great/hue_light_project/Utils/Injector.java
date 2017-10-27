package com.minds.great.hue_light_project.Utils;

import com.minds.great.hue_light_project.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={HueModule.class})
public interface Injector {
    void inject(MainActivity activity);
}
