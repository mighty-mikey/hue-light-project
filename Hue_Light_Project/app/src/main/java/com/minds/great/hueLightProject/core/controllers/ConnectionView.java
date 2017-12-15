package com.minds.great.hueLightProject.core.controllers;


public interface ConnectionView {
    void showProgressBar();

    void hideConnectButton();

    void hideErrorMessage();

    void hideProgressBar();

    void showWaitingForConnection();

    void showErrorMessage(int code);

    void showConnectButton();
}
