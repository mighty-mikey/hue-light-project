package com.minds.great.hueLightProject.utils;

import com.minds.great.hueLightProject.BridgeListAdapter;
import com.minds.great.hueLightProject.hueImpl.HueBridgeController;
import com.philips.lighting.hue.sdk.PHHueSDK;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class HueModule {

    @Provides
    @Singleton
    HueBridgeController providesBridgeController(PHHueSDK phHueSDK){
        return new HueBridgeController(phHueSDK);
    }

    @Provides
    @Singleton
    BridgeListAdapter providesBridgeListAdapter(PHHueSDK phHueSDK){
        return new BridgeListAdapter();
    }


    @Provides
    @Singleton
    PHHueSDK providesPHHueSDK(){
        return PHHueSDK.create();
    }
}
