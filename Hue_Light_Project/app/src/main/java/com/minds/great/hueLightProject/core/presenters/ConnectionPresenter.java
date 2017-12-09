package com.minds.great.hueLightProject.core.presenters;

import com.minds.great.hueLightProject.core.models.ConnectionError;
import com.minds.great.hueLightProject.core.models.LightSystem;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ConnectionPresenter {

    private CompositeDisposable compositeDisposable;
    private LightSystemInterface lightSystemInterface;
    private MemoryInterface memory;
    private ConnectionView view;

    public ConnectionPresenter(LightSystemInterface lightSystemInterface, MemoryInterface memory) {
        this.lightSystemInterface = lightSystemInterface;
        this.memory = memory;
        compositeDisposable = new CompositeDisposable();
    }

    public void viewLoaded(ConnectionView view) {
        this.view = view;

        LightSystem storedLightSystem = memory.getLightSystem();

        if(storedLightSystem != null){
            connectAndNavigateToLightActivity(storedLightSystem);
        }else{
            subscribeToRelays();
        }
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

    private void subscribeToRelays() {
        compositeDisposable.add(lightSystemInterface.getLightSystemListObservable().subscribe(lightSystems -> {
                    if (lightSystems != null && !lightSystems.isEmpty()) {
                        showWaitForConnection(lightSystems);
                    }
                })
        );

        compositeDisposable.add(lightSystemInterface.getLightSystemObservable().subscribe(lightSystem -> {
            memory.saveLightSystem(lightSystem);
            connectAndNavigateToLightActivity(lightSystem);
        }));

        compositeDisposable.add(lightSystemInterface.getErrorObservable()
                .subscribe(this::showErrorMessage)
        );
    }

    private void showErrorMessage(ConnectionError connectionError) {
        view.showErrorMessage(connectionError.getCode());
        view.hideProgressBar();
        view.showConnectButton();
    }

    private void showWaitForConnection(List<LightSystem> lightSystems) {
        view.hideProgressBar();
        view.showWaitingForConnection();
        view.hideProgressBar();
        view.hideConnectButton();
        lightSystemInterface.connectToLightSystem(lightSystems.get(0));
    }

    private void connectAndNavigateToLightActivity(LightSystem lightSystem) {
        lightSystemInterface.connectToLightSystem(lightSystem);
        view.navigateToLightActivity();
    }
}
