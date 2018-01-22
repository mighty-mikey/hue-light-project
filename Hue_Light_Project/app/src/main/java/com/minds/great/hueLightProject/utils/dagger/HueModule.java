package com.minds.great.hueLightProject.utils.dagger;

import android.content.Context;
import android.support.annotation.NonNull;

import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.LightSystemInterface;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MemoryInterface;
import com.minds.great.hueLightProject.data.HueMemory;
import com.minds.great.hueLightProject.hueImpl.HueLightSystem;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class HueModule {

    private Context context;

    public HueModule(@NonNull Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    LightSystemInterface providesLightSystemInterface(){
        return new HueLightSystem(context);
    }

    @Provides
    @Singleton
    MemoryInterface providesMemoryInterface(){
        return new HueMemory();
    }
}
