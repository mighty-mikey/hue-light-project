package com.minds.great.hueLightProject.core.controllers;

import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.LightsListView;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MemoryInterface;
import com.minds.great.hueLightProject.core.models.LightSystem;

public class LightsListController {

    private MemoryInterface memory;
    private LightsListView view;

    public LightsListController(MemoryInterface memory) {
        this.memory = memory;
    }

    public void loadLightsList() {
        LightSystem lightSystem = memory.getLightSystem();
        this.view.populateLightsList(lightSystem);
    }

    public void viewLoaded(LightsListView view) {
        this.view = view;
    }
}
