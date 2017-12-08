package com.minds.great.hueLightProject.core.controllers;

import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.models.LightSystem;

import java.util.List;

public class ConnectionController {

    ConnectionPoint connectionPoint;

    public ConnectionController(ConnectionPoint connectionPoint){
        this.connectionPoint = connectionPoint;
    }

    public PublishRelay<List<LightSystem>> search(){
        connectionPoint.search();
        return connectionPoint.getConnectionPointObservable();
    }

    public void connectToController(LightSystem controller) {
        connectionPoint.connectToLightSystem(controller);
    }

}
