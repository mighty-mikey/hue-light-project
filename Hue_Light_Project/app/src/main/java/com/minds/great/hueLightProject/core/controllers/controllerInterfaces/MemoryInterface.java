package com.minds.great.hueLightProject.core.controllers.controllerInterfaces;


import com.minds.great.hueLightProject.core.models.LightSystem;

public interface MemoryInterface {

    void saveLightSystem(LightSystem lightSystem);

    String getLightSystemIpAddress();

}
