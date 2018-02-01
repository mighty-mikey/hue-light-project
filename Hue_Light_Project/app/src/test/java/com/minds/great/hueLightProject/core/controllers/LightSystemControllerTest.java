package com.minds.great.hueLightProject.core.controllers;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.BridgeState;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LightSystemControllerTest {

    @Mock
    private
    ConnectionController connectionControllerMock;
    @Mock
    private
    LightSystem lightSystem;
    @Mock
    private Bridge bridge;
    @Mock
    private BridgeState bridgeState;

    private LightSystemController subject;
    private BehaviorRelay<LightSystem> lightSystemRelay;

    @Before
    public void setUp() throws Exception {
        lightSystemRelay = BehaviorRelay.create();
        when(connectionControllerMock.getConnectionSuccessfulRelay()).thenReturn(lightSystemRelay);
        subject = new LightSystemController(connectionControllerMock);
    }

    @Test
    public void LightSystemController_initsLightSystem() throws Exception {
        verify(connectionControllerMock).getConnectionSuccessfulRelay();
    }

    @Test
    public void getLightList_whenNothingOnRelay_isNull() throws Exception {
        List<LightPoint> pointList = subject.getLightList();
        assertThat(pointList).isNull();
    }

    @Test
    public void getLightList_whenSystemOnRelay_isNotNull() throws Exception {
        List<LightPoint> pointArray = new ArrayList<>();
        when(lightSystem.getBridge()).thenReturn(bridge);
        when(bridge.getBridgeState()).thenReturn(bridgeState);
        when(bridgeState.getLightPoints()).thenReturn(pointArray);
        lightSystemRelay.accept(lightSystem);
        List<LightPoint> pointList = subject.getLightList();
        assertThat(pointList).isNotNull();
    }

    @After
    public void tearDown() throws Exception {
        reset(connectionControllerMock);
        reset(lightSystem);
        reset(bridge);
        reset(bridgeState);
    }
}