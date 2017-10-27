package com.minds.great.hue_light_project.Core;

import com.philips.lighting.hue.sdk.PHHueSDK;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BridgeControllerTest {

    @Mock
    PHHueSDK phHueSDKMock;
    private BridgeListener bridgeListener;
    private BridgeController subject;

    @Before
    public void setUp() {
        bridgeListener = new BridgeListener(phHueSDKMock);
        subject = new BridgeController(phHueSDKMock, bridgeListener);
    }

    @Test
    public void searchForBridges_callsHueLib() {
        verify(phHueSDKMock, never()).getSDKService(PHHueSDK.SEARCH_BRIDGE);
        subject.searchForBridges();
        verify(phHueSDKMock).getSDKService(PHHueSDK.SEARCH_BRIDGE);
    }

}