package com.minds.great.hueLightProject.core.controllers;

import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MainActivityView;
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

    public void viewCreated(@Nonnull MainActivityView view) {
        LightSystem storedLightSystem = memory.getLightSystem();
        connectionSuccessDisposable = connectionController.getConnectionSuccessfulRelay()
                .subscribe(lightSystem -> {
                    if (null == storedLightSystem) {
                        memory.saveLightSystem(lightSystem);
                    }
                    view.setMainLightSystem(lightSystem);
                    view.switchToLightsList();
                    view.finishConnectionActivity();
                });
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
