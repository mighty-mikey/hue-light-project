package com.minds.great.hue_light_project.Utils;

import android.content.Context;

import com.minds.great.hue_light_project.BridgeListAdapter;
import com.minds.great.hue_light_project.Core.BridgeController;
import com.minds.great.hue_light_project.Core.BridgeListener;
import com.philips.lighting.hue.sdk.PHHueSDK;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class HueModule {

    @Provides
    @Singleton
    BridgeController providesBridgeController(PHHueSDK phHueSDK, BridgeListener bridgeListener){
        return new BridgeController(phHueSDK, bridgeListener);
    }

    @Provides
    @Singleton
    BridgeListAdapter providesBridgeListAdapter(PHHueSDK phHueSDK, BridgeListener bridgeListener){
        return new BridgeListAdapter();
    }

    @Provides
    @Singleton
    BridgeListener providesBridgeListener(PHHueSDK phHueSDK){
        return new BridgeListener(phHueSDK);
    }

    @Provides
    @Singleton
    PHHueSDK providesPHHueSDK(){
        return PHHueSDK.create();
    }
}
