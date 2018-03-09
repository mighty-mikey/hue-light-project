package com.minds.great.hueLightProject.utils.dagger;

import com.minds.great.hueLightProject.core.controllers.ConnectionController;
import com.minds.great.hueLightProject.core.controllers.LightSystemController;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.LightSystemInterface;
import com.minds.great.hueLightProject.core.controllers.MainController;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MemoryInterface;
import com.minds.great.hueLightProject.core.presenters.LightListPresenter;
import com.minds.great.hueLightProject.core.presenters.SingleLightPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
class LightProjectModule {

    @Singleton
    @Provides
    SingleLightPresenter providesSingleLightPresenter(LightSystemController lightSystemController){
        return new SingleLightPresenter(lightSystemController);
    }

    @Singleton
    @Provides
    LightListPresenter providesLightListPresenter( ConnectionController connectionController, LightSystemController lightSystemController){
        return new LightListPresenter(connectionController, lightSystemController);
    }

    @Singleton
    @Provides
    ConnectionController providesConnectionController(LightSystemInterface lightSystemInterface){
        return new ConnectionController(lightSystemInterface);
    }

    @Singleton
    @Provides
    MainController providesMainController(MemoryInterface memory, ConnectionController connectionController){
        return new MainController(memory, connectionController);
    }

    @Singleton
    @Provides
    LightSystemController providesLightSystemController(ConnectionController connectionController){
        return new LightSystemController(connectionController);
    }
}
