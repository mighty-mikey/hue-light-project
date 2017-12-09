package com.minds.great.hueLightProject.core.presenters;


import com.minds.great.hueLightProject.core.models.LightSystem;

public interface MemoryInterface {

    void saveLightSystem(LightSystem lightSystem);

    LightSystem getLightSystem();

}
