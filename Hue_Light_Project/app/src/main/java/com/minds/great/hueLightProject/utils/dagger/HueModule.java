package com.minds.great.hueLightProject.utils.dagger;

import com.minds.great.hueLightProject.core.presenters.LightSystemInterface;
import com.minds.great.hueLightProject.core.presenters.MemoryInterface;
import com.minds.great.hueLightProject.data.MemoryActivity;
import com.minds.great.hueLightProject.hueImpl.HueLightSystem;
import com.philips.lighting.hue.sdk.PHHueSDK;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class HueModule {

    @Provides
    @Singleton
    LightSystemInterface providesBridgeController(PHHueSDK phHueSDK){
        return new HueLightSystem(phHueSDK);
    }

    @Provides
    @Singleton
    MemoryInterface providesMemoryInterface(){
        return new MemoryActivity();
    }

    @Provides
    @Singleton
    PHHueSDK providesPHHueSDK(){
        return PHHueSDK.create();
    }
}
