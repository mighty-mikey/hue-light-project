package com.minds.great.hueLightProject.core.controllers;

import com.minds.great.hueLightProject.core.models.LightSystem;

public class MainController {
    private MainActivityInterface view;
    private MemoryInterface memory;
    private ConnectionController connectionController;

    public MainController(MemoryInterface memory, ConnectionController connectionController) {

        this.memory = memory;
        this.connectionController = connectionController;
        connectionController.getConnectionSuccessfulRelay().subscribe(lightSystem -> view.finishConnectionActivity());
    }

    public void viewLoaded(MainActivityInterface view) {
        this.view = view;
        LightSystem lightSystem = memory.getLightSystem();
        if (null != lightSystem) {
            connectionController.connect(lightSystem);
        } else {
            view.navigateToConnectionActivity();
        }
    }


}
