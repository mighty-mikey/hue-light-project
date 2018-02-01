package com.minds.great.hueLightProject.core.controllers;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.ConnectionView;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.LightSystemInterface;
import com.minds.great.hueLightProject.core.models.ConnectionError;
import com.minds.great.hueLightProject.core.models.LightSystem;

import io.reactivex.disposables.CompositeDisposable;

public class ConnectionController {

    private CompositeDisposable compositeDisposable;
    private LightSystemInterface lightSystemInterface;
    private ConnectionView view;

    public ConnectionController(LightSystemInterface lightSystemInterface) {
        this.lightSystemInterface = lightSystemInterface;
    }

    public void viewLoaded(ConnectionView view) {
        this.view = view;

        if(null == compositeDisposable){
            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add(lightSystemInterface.getLightSystemListObservable()
                .subscribe(lightSystems -> {
                    showWaitForConnection();
                    lightSystemInterface.connectToLightSystem(lightSystems.get(0).getIpAddress());
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

    BehaviorRelay<LightSystem> getConnectionSuccessfulRelay() {
        return lightSystemInterface.getLightSystemObservable();
    }

    void connect(String lightSystemIpAddress) {
        lightSystemInterface.connectToLightSystem(lightSystemIpAddress);
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
}
