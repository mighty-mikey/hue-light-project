package com.minds.great.hueLightProject.core.controllers;

import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MainActivityInterface;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MemoryInterface;
import com.minds.great.hueLightProject.core.models.LightSystem;
import javax.annotation.Nonnull;
import io.reactivex.disposables.Disposable;

public class MainController {
    private MemoryInterface memory;
    private ConnectionController connectionController;
    private Disposable connectionSuccessDisposable;

    public MainController(MemoryInterface memory, ConnectionController connectionController) {
        this.memory = memory;
        this.connectionController = connectionController;
    }

    public void viewLoaded(@Nonnull MainActivityInterface view) {
        connectionSuccessDisposable = connectionController.getConnectionSuccessfulRelay()
                .subscribe(lightSystem -> {
                    view.finishConnectionActivity();
                    //TODO: navigate to Light Activity?
                });

        LightSystem storedLightSystem = memory.getLightSystem();
        if (null != storedLightSystem) {
            connectionController.connect(storedLightSystem);
        } else {
            view.navigateToConnectionActivity();
        }
    }

    public void viewUnloaded() {
        if (connectionSuccessDisposable != null) {
            connectionSuccessDisposable.dispose();
            connectionSuccessDisposable = null;
        }
    }
}
