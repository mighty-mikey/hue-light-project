package com.minds.great.hueLightProject.utils.dagger;

import com.minds.great.hueLightProject.core.controllers.ConnectionController;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.LightSystemInterface;
import com.minds.great.hueLightProject.core.controllers.MainController;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MemoryInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class LightProjectModule {

    @Provides
    @Singleton
    ConnectionController providesConnectionController(LightSystemInterface lightSystemInterface, MemoryInterface memoryInterface){
        return new ConnectionController(lightSystemInterface);
    }

    @Provides
    @Singleton
    MainController providesMainController(MemoryInterface memory, ConnectionController connectionController){
        return new MainController(memory, connectionController);
    }
}
