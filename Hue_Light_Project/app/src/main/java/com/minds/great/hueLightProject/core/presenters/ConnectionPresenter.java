package com.minds.great.hueLightProject.core.presenters;

import com.minds.great.hueLightProject.core.controllers.ConnectionController;

public class ConnectionPresenter {

    ConnectionController controller;
    private ConnectionView view;

    public ConnectionPresenter(ConnectionController controller){
        this.controller = controller;
    }

    public void viewLoaded(ConnectionView view){
        this.view = view;
    }

    public void viewUnloaded(){
        view = null;
    }
    public void search() {
        view.showProgressBar();
        view.hideConnectButton();
        view.hideErrorMessage();
        controller.search();
    }
}
