package com.minds.great.hueLightProject.utils.dagger;

import com.minds.great.hueLightProject.core.controllers.ConnectionController;
import com.minds.great.hueLightProject.core.controllers.LightSystemInterface;
import com.minds.great.hueLightProject.core.controllers.MemoryInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LightProjectModule {

    @Provides
    @Singleton
    ConnectionController providesConnectionPresenter(LightSystemInterface lightSystemInterface, MemoryInterface memoryInterface){
        return new ConnectionController(lightSystemInterface, memoryInterface);
    }
}
