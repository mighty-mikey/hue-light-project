package com.minds.great.hueLightProject.utils.dagger;

import android.content.Context;
import android.support.annotation.NonNull;

import com.minds.great.hueLightProject.core.domain.domainInterfaces.LightSystemInterface;
import com.minds.great.hueLightProject.data.HueDomain;
import com.minds.great.hueLightProject.hueImpl.HueLightSystem;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    HueDomain providesHueDomain(){
        return new HueDomain();
    }
}
