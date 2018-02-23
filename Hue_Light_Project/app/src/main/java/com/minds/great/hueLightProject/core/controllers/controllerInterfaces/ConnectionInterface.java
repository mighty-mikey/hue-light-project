package com.minds.great.hueLightProject.core.controllers.controllerInterfaces;


public interface ConnectionInterface {
    void showProgressBar();

    void hideConnectButton();

    void hideErrorMessage();

    void hideProgressBar();

    void showWaitingForConnection();

    void showErrorMessage(int code);

    void showConnectButton();
}
