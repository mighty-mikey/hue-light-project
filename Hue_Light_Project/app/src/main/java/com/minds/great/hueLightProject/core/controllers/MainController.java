package com.minds.great.hueLightProject.core.controllers;

import com.jakewharton.rxrelay2.PublishRelay;
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

    public void viewCreated(@Nonnull MainActivityInterface view) {
        LightSystem storedLightSystem = memory.getLightSystem();
        if (null != storedLightSystem) {
            connectionController.connect(storedLightSystem);
            view.navigateToLightActivity();
        } else {
            connectionSuccessDisposable = connectionController.getConnectionSuccessfulRelay()
                    .subscribe(lightSystem -> {
                        memory.saveLightSystem(lightSystem);
                        view.finishConnectionActivity();
                        view.navigateToLightActivity();
                    });
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
