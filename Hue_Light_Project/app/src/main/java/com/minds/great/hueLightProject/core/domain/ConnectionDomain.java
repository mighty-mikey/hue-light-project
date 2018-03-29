package com.minds.great.hueLightProject.core.domain;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.ConnectionInterface;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.LightSystemInterface;
import com.minds.great.hueLightProject.core.models.ConnectionError;
import com.minds.great.hueLightProject.core.models.LightSystem;

import io.reactivex.disposables.CompositeDisposable;

public class ConnectionDomain {

    private CompositeDisposable compositeDisposable;
    private LightSystemInterface lightSystemInterface;
    private ConnectionInterface connectionView;

    public ConnectionDomain(LightSystemInterface lightSystemInterface) {
        this.lightSystemInterface = lightSystemInterface;
    }

    public void viewLoaded(ConnectionInterface view) {
        this.connectionView = view;

        if(null == compositeDisposable){
            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add(lightSystemInterface.getLightSystemListObservable()
                .subscribe(lightSystems -> {
                    showWaitForConnection();
                    if(!lightSystems.isEmpty()) {
                        lightSystemInterface.connectToLightSystem(lightSystems.get(0).getIpAddress());
                    }
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
        connectionView = null;
    }

    public void search() {
        connectionView.showProgressBar();
        connectionView.hideConnectButton();
        connectionView.hideErrorMessage();
        lightSystemInterface.searchForLightSystems();
    }

    public BehaviorRelay<LightSystem> getConnectionSuccessfulRelay() {
        return lightSystemInterface.getLightSystemObservable();
    }

    public PublishRelay<LightSystem> getLightsAndGroupsHeartbeatRelay() {
        return lightSystemInterface.getLightsAndGroupsHeartbeatRelayObservable();
    }

    void connect(String lightSystemIpAddress) {
        lightSystemInterface.connectToLightSystem(lightSystemIpAddress);
    }

    private void showErrorMessage(ConnectionError connectionError) {
        connectionView.showErrorMessage(connectionError.getCode());
        connectionView.hideProgressBar();
        connectionView.showConnectButton();
    }

    private void showWaitForConnection() {
        connectionView.hideProgressBar();
        connectionView.showWaitingForConnection();
        connectionView.hideConnectButton();
    }
}
