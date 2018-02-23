package com.minds.great.hueLightProject.core.controllers;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.LightsListInterface;
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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
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
    @Mock
    LightSystem lightSystemMock;

    @Mock
    LightsListInterface lightsListInterfaceMock;

    private LightSystemController subject;
    private BehaviorRelay<LightSystem> lightSystemRelay;
    private PublishRelay<LightSystem> lightsAndGroupsHeartbeatRelay = PublishRelay.create();

    @Before
    public void setUp() throws Exception {
        lightSystemRelay = BehaviorRelay.create();
        when(connectionControllerMock.getConnectionSuccessfulRelay()).thenReturn(lightSystemRelay);
        when(connectionControllerMock.getLightsAndGroupsHeartbeatRelay()).thenReturn(lightsAndGroupsHeartbeatRelay);
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

    @Test
    public void viewLoaded_whenLightsAndGroupsHeartbeatRelayTriggers_informUI() throws Exception {
        subject.viewLoaded(lightsListInterfaceMock);
        lightsAndGroupsHeartbeatRelay.accept(lightSystemMock);
        verify(lightsListInterfaceMock).updateLights(any());
    }

    @Test
    public void viewLoaded_whenLightsAndGroupsHeartbeatRelayTriggers_andViewNotLoaded_doesNotInformUI() throws Exception {
        lightsAndGroupsHeartbeatRelay.accept(lightSystemMock);
        subject.viewLoaded(lightsListInterfaceMock);
        verify(lightsListInterfaceMock, never()).updateLights(any());
    }

    @After
    public void tearDown() throws Exception {
        reset(connectionControllerMock);
        reset(lightSystem);
        reset(bridge);
        reset(bridgeState);
    }
}