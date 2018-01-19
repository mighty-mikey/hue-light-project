package com.minds.great.hueLightProject.core.controllers.controllerInterfaces;

import com.minds.great.hueLightProject.core.models.LightSystem;

public interface MainActivityView {
    void navigateToConnectionActivity();

    void finishConnectionActivity();

    void switchToLightsList();

    void setMainLightSystem(LightSystem lightSystem);
}
