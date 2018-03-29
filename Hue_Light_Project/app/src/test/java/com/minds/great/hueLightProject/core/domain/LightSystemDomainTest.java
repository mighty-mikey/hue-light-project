package com.minds.great.hueLightProject.core.domain;

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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LightSystemDomainTest {

    @Mock
    private
    ConnectionDomain connectionDomainMock;
    @Mock
    private
    LightSystem lightSystem;
    @Mock
    private Bridge bridgeMock;
    @Mock
    private BridgeState bridgeStateMock;

    private LightSystemDomain subject;
    private BehaviorRelay<LightSystem> lightSystemRelay;

    @Before
    public void setUp() throws Exception {
        lightSystemRelay = BehaviorRelay.create();
        when(connectionDomainMock.getConnectionSuccessfulRelay()).thenReturn(lightSystemRelay);
        subject = new LightSystemDomain(connectionDomainMock);
    }

    @Test
    public void LightSystemController_initsLightSystem() throws Exception {
        verify(connectionDomainMock).getConnectionSuccessfulRelay();
    }

    @Test
    public void getLightList_whenNothingOnRelay_isNull() throws Exception {
        List<LightPoint> pointList = subject.getLightList();
        assertThat(pointList).isNull();
    }

    @Test
    public void getLightList_whenSystemOnRelay_isNotNull() throws Exception {
        List<LightPoint> pointArray = new ArrayList<>();
        when(lightSystem.getBridge()).thenReturn(bridgeMock);
        when(bridgeMock.getBridgeState()).thenReturn(bridgeStateMock);
        when(bridgeStateMock.getLightPoints()).thenReturn(pointArray);
        lightSystemRelay.accept(lightSystem);
        List<LightPoint> pointList = subject.getLightList();
        assertThat(pointList).isNotNull();
    }

    @After
    public void tearDown() throws Exception {
        reset(connectionDomainMock);
        reset(lightSystem);
        reset(bridgeMock);
        reset(bridgeStateMock);
    }
}