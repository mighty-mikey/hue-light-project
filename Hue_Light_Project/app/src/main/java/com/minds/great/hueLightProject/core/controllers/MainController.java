package com.minds.great.hueLightProject.core.controllers;

import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MainFragmentView;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MemoryInterface;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.List;

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

    public void viewLoaded(@Nonnull MainFragmentView view) {
        String lightSystemIpAddress = memory.getLightSystemIpAddress();
        connectionSuccessDisposable = connectionController.getConnectionSuccessfulRelay()
                .subscribe(lightSystem -> view.navigateToLightListFragment());
        if (null != lightSystemIpAddress) {
            connectionController.connect(lightSystemIpAddress);
        } else {
            view.navigateToConnectionFragment();
        }
    }

    public void viewUnloaded() {
        if (connectionSuccessDisposable != null) {
            connectionSuccessDisposable.dispose();
            connectionSuccessDisposable = null;
        }
    }
}
