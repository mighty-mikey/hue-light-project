package com.minds.great.hueLightProject.hueImpl;

import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHNotificationManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BridgeControllerTest {

    @Mock
    PHHueSDK phHueSDKMock;
    @Mock
    PHNotificationManager phNotificationManager;
    @Mock
    PHBridgeSearchManager phBridgeSearchManager;
    private BridgeListener bridgeListener;
    private BridgeController subject;

    @Before
    public void setUp() {
        when(phHueSDKMock.getNotificationManager()).thenReturn(phNotificationManager);
        when(phHueSDKMock.getSDKService(PHHueSDK.SEARCH_BRIDGE)).thenReturn(phBridgeSearchManager);
        bridgeListener = new BridgeListener(phHueSDKMock);
        subject = new BridgeController(phHueSDKMock, bridgeListener);
    }

    @Test
    public void bridgeController_getsNotificationManager(){
        verify(phHueSDKMock).getNotificationManager();
    }

    @Test
    public void bridgeController_registersListener(){
        verify(phNotificationManager).registerSDKListener(bridgeListener);
    }

    @Test
    public void searchForBridges_callsHueLib() {
        subject.searchForBridges();
        verify(phHueSDKMock).getSDKService(PHHueSDK.SEARCH_BRIDGE);
    }

    @Test
    public void searchForBridges_searchesForBridges(){
        subject.searchForBridges();
        verify(phBridgeSearchManager).search(true, true);
    }
}