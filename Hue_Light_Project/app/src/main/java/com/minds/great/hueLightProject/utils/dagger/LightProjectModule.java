package com.minds.great.hueLightProject.utils.dagger;

import com.minds.great.hueLightProject.core.presenters.LightSystemInterface;
import com.minds.great.hueLightProject.core.presenters.ConnectionPresenter;
import com.minds.great.hueLightProject.core.presenters.MemoryInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LightProjectModule {

    @Provides
    @Singleton
    ConnectionPresenter providesConnectionPresenter(LightSystemInterface lightSystemInterface, MemoryInterface memoryInterface){
        return new ConnectionPresenter(lightSystemInterface, memoryInterface);
    }
}
