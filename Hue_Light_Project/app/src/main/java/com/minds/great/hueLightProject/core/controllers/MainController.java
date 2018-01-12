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
        String storedLightSystemIpAddress = memory.getLightSystemIpAddress();
        if (null != storedLightSystemIpAddress) {
            connectionController.connect(storedLightSystemIpAddress);
            view.navigateToLightListActivity();
        } else {
            PublishRelay<LightSystem> connectionSuccessfulRelay = connectionController.getConnectionSuccessfulRelay();
            connectionSuccessDisposable = connectionSuccessfulRelay
                    .subscribe(lightSystem -> {
                        view.finishConnectionActivity();
                        view.navigateToLightListActivity();
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
