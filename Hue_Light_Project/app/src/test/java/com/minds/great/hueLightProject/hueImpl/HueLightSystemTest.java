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
public class HueLightSystemTest {

    @Mock
    PHHueSDK phHueSDKMock;
    @Mock
    PHNotificationManager phNotificationManager;
    @Mock
    PHBridgeSearchManager phBridgeSearchManager;
    private HueLightSystem subject;

    @Before
    public void setUp() {
        when(phHueSDKMock.getNotificationManager()).thenReturn(phNotificationManager);
        when(phHueSDKMock.getSDKService(PHHueSDK.SEARCH_BRIDGE)).thenReturn(phBridgeSearchManager);
        subject = new HueLightSystem(phHueSDKMock);
    }

    @Test
    public void bridgeController_getsNotificationManager(){
        verify(phHueSDKMock).getNotificationManager();
    }

    @Test
    public void bridgeController_registersListener(){
        verify(phNotificationManager).registerSDKListener(subject);
    }

    @Test
    public void searchForBridges_callsHueLib() {
        subject.searchForLightSystems();
        verify(phHueSDKMock).getSDKService(PHHueSDK.SEARCH_BRIDGE);
    }

    @Test
    public void searchForBridges_searchesForBridges(){
        subject.searchForLightSystems();
        verify(phBridgeSearchManager).search(true, true);
    }
}