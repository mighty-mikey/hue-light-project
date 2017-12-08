package com.minds.great.hueLightProject.core.controllers;

import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.models.LightSystem;

import java.util.List;


public interface ConnectionPoint {
    void search();

    PublishRelay<List<LightSystem>> getConnectionPointObservable();

    void connectToLightSystem(LightSystem lightSystem);
}
