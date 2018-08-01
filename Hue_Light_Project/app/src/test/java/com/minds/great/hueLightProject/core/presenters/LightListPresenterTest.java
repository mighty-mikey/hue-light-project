package com.minds.great.hueLightProject.core.presenters;

import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.domain.ConnectionDomain;
import com.minds.great.hueLightProject.core.domain.LightSystemDomain;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.wrapper.domain.Bridge;
import com.philips.lighting.hue.sdk.wrapper.domain.BridgeState;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LightListPresenterTest {
    @Mock
    private
    ConnectionDomain connectionDomainMock;
    @Mock
    private
    LightSystemDomain lightSystemDomain;
    @Mock
    private
    LightsListInterface view;
    @Mock
    private
    LightSystem lightSystemMock;
    @Mock
    private
    Bridge bridgeMock;
    @Mock
    private
    BridgeState bridgeStateMock;
    @Mock
    private
    List<LightPoint> lightListMock;

    private PublishRelay<LightSystem> heartBeatRelay = PublishRelay.create();
    private LightListPresenter subject;


    @Before
    public void setUp() {
        when(connectionDomainMock.getHeartBeatRelay()).thenReturn(heartBeatRelay);
        when(lightSystemMock.getBridge()).thenReturn(bridgeMock);
        when(bridgeMock.getBridgeState()).thenReturn(bridgeStateMock);
        when(bridgeStateMock.getLightPoints()).thenReturn(lightListMock);
        subject = new LightListPresenter(connectionDomainMock, lightSystemDomain);
    }

    @Test
    public void viewLoaded_whenLightsAndGroupsHeartbeatRelayTriggers_informUI() {
        subject.viewLoaded(view);
        reset(view);
        heartBeatRelay.accept(lightSystemMock);
        verify(view).updateLights(any());
    }

    @Test
    public void viewLoaded_whenLightsAndGroupsHeartbeatRelayTriggers_andViewNotLoaded_doesNotInformUI() {
        heartBeatRelay.accept(lightSystemMock);
        verify(view, never()).updateLights(any());
        subject.viewLoaded(view);
    }

    @Test
    public void setSelectedLightPosition_callsNavigateToSingleLight() {
        subject.viewLoaded(view);
        subject.setSelectedLightPosition(0);
        verify(view).navigateToSingleLightFragment();
    }

    @Test
    public void viewLoaded_setsLightListOnView() {
        subject.viewLoaded(view);
        verify(view).updateLights(any());
    }
}