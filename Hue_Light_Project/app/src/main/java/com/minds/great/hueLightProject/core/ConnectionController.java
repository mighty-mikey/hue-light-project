package com.minds.great.hueLightProject.core;

import com.jakewharton.rxrelay2.PublishRelay;

public class ConnectionController {

    ConnectionPoint connectionPoint;

    public ConnectionController(ConnectionPoint connectionPoint){
        this.connectionPoint = connectionPoint;
    }

    public PublishRelay<LightSystem> search(){
        connectionPoint.search();
        return connectionPoint.getConnectionPointObservable();
    }

    public void connectToController(LightSystem controller) {
        connectionPoint.connectToLightSystem(controller);
    }
    //disconnect method


}
