package com.minds.great.hueLightProject.core.controllers;

import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.minds.great.hueLightProject.core.models.ConnectionError;

import java.util.List;

public class ConnectionController {

    private LightSystemInterface lightSystemInterface;

    public ConnectionController(LightSystemInterface lightSystemInterface){
        this.lightSystemInterface = lightSystemInterface;
    }

    public void startLightSystemSearch(){
        lightSystemInterface.searchForLightSystems();
    }

    public PublishRelay<List<LightSystem>> getLightSystemListObservable(){
        return lightSystemInterface.getLightSystemListObservable();
    }

    public PublishRelay<LightSystem> getLightSystemObservable(){
        return lightSystemInterface.getLightSystemObservable();
    }

    public void connectToLightSystem(LightSystem controller) {
        lightSystemInterface.connectToLightSystem(controller);
    }

    public PublishRelay<ConnectionError> getErrorObservable() {
        return lightSystemInterface.getErrorObservable();
    }
}
