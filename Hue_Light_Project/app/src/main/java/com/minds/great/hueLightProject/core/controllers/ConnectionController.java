package com.minds.great.hueLightProject.core.controllers;

import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.ConnectionView;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.LightSystemInterface;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MemoryInterface;
import com.minds.great.hueLightProject.core.models.ConnectionError;
import com.minds.great.hueLightProject.core.models.LightSystem;
import io.reactivex.disposables.CompositeDisposable;

public class ConnectionController {

    private CompositeDisposable compositeDisposable;
    private LightSystemInterface lightSystemInterface;
    private MemoryInterface memory;
    private ConnectionView view;
    private PublishRelay<LightSystem> connectionSuccessfulRelay = PublishRelay.create();

    public ConnectionController(LightSystemInterface lightSystemInterface, MemoryInterface memory) {
        this.lightSystemInterface = lightSystemInterface;
        this.memory = memory;
        compositeDisposable = new CompositeDisposable();
    }

    public void viewLoaded(ConnectionView view) {
        this.view = view;

        compositeDisposable.add(lightSystemInterface.getLightSystemListObservable().subscribe(lightSystems -> showWaitForConnection()));

        compositeDisposable.add(lightSystemInterface.getLightSystemObservable().subscribe(lightSystem -> {
            memory.saveLightSystem(lightSystem);
            connectToLightSystem(lightSystem);
        }));

        compositeDisposable.add(lightSystemInterface.getErrorObservable()
                .subscribe(this::showErrorMessage)
        );
    }

    public void viewUnloaded() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
        view = null;
    }

    public void search() {
        view.showProgressBar();
        view.hideConnectButton();
        view.hideErrorMessage();
        lightSystemInterface.searchForLightSystems();
    }

    PublishRelay<LightSystem> getConnectionSuccessfulRelay() {
        return connectionSuccessfulRelay;
    }

    void connect(LightSystem lightSystem) {
        lightSystemInterface.connectToLightSystem(lightSystem);
    }

    private void showErrorMessage(ConnectionError connectionError) {
        view.showErrorMessage(connectionError.getCode());
        view.hideProgressBar();
        view.showConnectButton();
    }

    private void showWaitForConnection() {
        view.hideProgressBar();
        view.showWaitingForConnection();
        view.hideConnectButton();
    }

    private void connectToLightSystem(LightSystem lightSystem) {
        lightSystemInterface.connectToLightSystem(lightSystem);
        connectionSuccessfulRelay.accept(lightSystem);
    }
}
