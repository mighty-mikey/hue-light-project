package com.minds.great.hueLightProject.core.presenters;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.domain.ConnectionDomain;
import com.minds.great.hueLightProject.core.domain.LightSystemDomain;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

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
public class SingleLightPresenterTest {
    @Mock
    private SingleLightInterface singleLightInterfaceMock;
    @Mock
    private LightSystemDomain lightSystemDomain;
    @Mock
    private ConnectionDomain connectionDomain;
    @Mock
    private LightSystem lightSystem;

    private SingleLightPresenter subject;
    private PublishRelay<LightSystem> heartBeatRelay;

    @Before
    public void setUp() throws Exception {
        subject = new SingleLightPresenter(connectionDomain, lightSystemDomain);
        heartBeatRelay = PublishRelay.create();
        when(connectionDomain.getLightsAndGroupsHeartbeatRelay()).thenReturn(heartBeatRelay);
        //TODO:  add tests around connectionDomain.
    }

    @Test
    public void viewLoaded_whenSelectedLightIsColorLight_showColorPicker() throws Exception {
        when(lightSystemDomain.getSelectedLightColorMode()).thenReturn(ColorMode.XY);
        subject.viewLoaded(singleLightInterfaceMock);
        verify(singleLightInterfaceMock).showColorPicker();
    }

    @Test
    public void viewLoaded_whenSelectedLightIsNotColorLight_showColorPickerNotCalled() throws Exception {
        when(lightSystemDomain.getSelectedLightColorMode()).thenReturn(ColorMode.COLOR_TEMPERATURE);
        subject.viewLoaded(singleLightInterfaceMock);
        verify(singleLightInterfaceMock, never()).showColorPicker();
    }

    @Test
    public void viewLoaded_subscribesToHeartBeatRelay() {
        when(lightSystemDomain.getSelectedLightColorMode()).thenReturn(ColorMode.XY);
        subject.viewLoaded(singleLightInterfaceMock);
        verify(connectionDomain).getLightsAndGroupsHeartbeatRelay();
        heartBeatRelay.accept(lightSystem);
        verify(singleLightInterfaceMock).updateLight(any());
    }
}