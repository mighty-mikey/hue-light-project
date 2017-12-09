package com.minds.great.hueLightProject.core.presenters;

import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.models.ConnectionError;
import com.minds.great.hueLightProject.core.models.LightSystem;

import java.util.List;


public interface LightSystemInterface {
    void searchForLightSystems();

    PublishRelay<List<LightSystem>> getLightSystemListObservable();

    void connectToLightSystem(LightSystem lightSystem);

    PublishRelay<ConnectionError> getErrorObservable();

    PublishRelay<LightSystem> getLightSystemObservable();
}
