package com.minds.great.hueLightProject.utils.dagger;

import android.content.Context;
import android.support.annotation.NonNull;

import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.LightSystemInterface;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MemoryInterface;
import com.minds.great.hueLightProject.data.HueMemory;
import com.minds.great.hueLightProject.hueImpl.HueLightSystemNew;

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

    @Singleton
    @Provides
    @NonNull
    Context providesContext(){
        return context;
    }

    @Provides
    @Singleton
    LightSystemInterface providesLightSystemInterface(){
        return new HueLightSystemNew(context);
    }

    @Provides
    @Singleton
    MemoryInterface providesMemoryInterface(){
        return new HueMemory();
    }
}
