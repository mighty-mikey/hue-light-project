package com.minds.great.hueLightProject.utils.dagger;

import com.minds.great.hueLightProject.core.controllers.ConnectionController;
import com.minds.great.hueLightProject.core.controllers.ConnectionPoint;
import com.minds.great.hueLightProject.core.presenters.ConnectionPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LightProjectModule {

    @Provides
    @Singleton
    ConnectionPresenter providesConnectionPresenter(ConnectionController controller){
        return new ConnectionPresenter(controller);
    }

    @Provides
    @Singleton
    ConnectionController providesConnectionController(ConnectionPoint point){
        return new ConnectionController(point);
    }
}
