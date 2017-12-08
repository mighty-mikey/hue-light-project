package com.minds.great.hueLightProject.core.presenters;

import com.minds.great.hueLightProject.core.controllers.ConnectionController;

import io.reactivex.disposables.CompositeDisposable;

public class ConnectionPresenter {

    private CompositeDisposable compositeDisposable;
    private ConnectionController connectionController;
    private ConnectionView view;

    public ConnectionPresenter(ConnectionController connectionController) {
        this.connectionController = connectionController;
        compositeDisposable = new CompositeDisposable();
    }

    public void viewLoaded(ConnectionView view) {
        this.view = view;

        compositeDisposable.add(connectionController.getLightSystemListObservable().subscribe(lightSystems -> {
                    if (lightSystems != null && !lightSystems.isEmpty()) {
                        view.hideProgressBar();
                        view.showWaitingForConnection();
                        view.hideProgressBar();
                        view.hideConnectButton();
                        connectionController.connectToLightSystem(lightSystems.get(0));
                    }
                })
        );

        compositeDisposable.add(connectionController.getLightSystemObservable().subscribe(lightSystem -> {

        }));

        compositeDisposable.add(connectionController.getErrorObservable()
                .subscribe(connectionError -> {
                    view.showErrorMessage(connectionError.getCode());
                    view.hideProgressBar();
                    view.showConnectButton();
                })
        );

    }

    public void viewUnloaded() {
        view = null;
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
    }

    public void search() {
        view.showProgressBar();
        view.hideConnectButton();
        view.hideErrorMessage();
        connectionController.startLightSystemSearch();
    }
}
