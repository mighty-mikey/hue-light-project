package com.minds.great.hueLightProject.utils.dagger;

import android.content.Context;
import android.support.annotation.NonNull;

import com.minds.great.hueLightProject.core.presenters.LightSystemInterface;
import com.minds.great.hueLightProject.core.presenters.MemoryInterface;
import com.minds.great.hueLightProject.data.Memory;
import com.minds.great.hueLightProject.hueImpl.HueLightSystem;
import com.philips.lighting.hue.sdk.PHHueSDK;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class HueModule {

    private Context context;

    public HueModule(@NonNull Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    @NonNull
    public Context providesContext(){
        return context;
    }

    @Provides
    @Singleton
    LightSystemInterface providesBridgeController(PHHueSDK phHueSDK){
        return new HueLightSystem(phHueSDK);
    }

    @Provides
    @Singleton
    MemoryInterface providesMemoryInterface(Context context){
        return new Memory(context);
    }

    @Provides
    @Singleton
    PHHueSDK providesPHHueSDK(){
        return PHHueSDK.create();
    }
}
