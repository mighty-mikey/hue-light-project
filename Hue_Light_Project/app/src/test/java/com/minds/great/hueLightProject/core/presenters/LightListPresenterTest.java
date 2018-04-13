package com.minds.great.hueLightProject.core.presenters;

import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.domain.ConnectionDomain;
import com.minds.great.hueLightProject.core.domain.LightSystemDomain;
import com.minds.great.hueLightProject.core.models.LightSystem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
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
    LightsListInterface lightsListInterfaceMock;
    @Mock
    private
    LightSystem lightSystemMock;

    private PublishRelay<LightSystem> lightsAndGroupsHeartbeatRelay = PublishRelay.create();
    private LightListPresenter subject;


    @Before
    public void setUp() throws Exception {
        when(connectionDomainMock.getLightsAndGroupsHeartbeatRelay()).thenReturn(lightsAndGroupsHeartbeatRelay);
        subject = new LightListPresenter(connectionDomainMock, lightSystemDomain);
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

    @Test
    public void setSelectedLightPosition_callsNavigateToSingleLight() {
        subject.viewLoaded(lightsListInterfaceMock);
        subject.setSelectedLightPosition(0);
        verify(lightsListInterfaceMock).navigateToSingleLightFragment();
    }
}