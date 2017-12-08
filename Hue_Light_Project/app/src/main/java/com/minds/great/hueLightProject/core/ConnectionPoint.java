package com.minds.great.hueLightProject.core;

import com.jakewharton.rxrelay2.PublishRelay;

import java.util.List;


public interface ConnectionPoint {
    void search();

    PublishRelay<List<LightSystem>> getConnectionPointObservable();

    void connectToLightSystem(LightSystem lightSystem);
}
