package com.minds.great.hueLightProject.core.presenters;

import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.domain.ConnectionDomain;
import com.minds.great.hueLightProject.core.domain.LightSystemDomain;
import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;
import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
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
    @Mock
    private LightPoint lightPointMock;
    @Mock
    private LightState lightStateMock;
    private SingleLightPresenter subject;
    private PublishRelay<LightSystem> heartBeatRelay;
    private final int brightness = 5;

    @Before
    public void setUp() throws Exception {
        heartBeatRelay = PublishRelay.create();
        when(connectionDomain.getLightsAndGroupsHeartbeatRelay()).thenReturn(heartBeatRelay);
        when(lightSystemDomain.getSelectedLightPoint()).thenReturn(lightPointMock);
        when(lightPointMock.getLightState()).thenReturn(lightStateMock);
        when(lightStateMock.getColormode()).thenReturn(ColorMode.XY);
        subject = new SingleLightPresenter(connectionDomain, lightSystemDomain);
        //TODO:  add tests around connectionDomain.
    }

    @Test
    public void viewLoaded_whenSelectedLightIsColorLight_showColorPicker() throws Exception {
        subject.viewLoaded(singleLightInterfaceMock);
        verify(singleLightInterfaceMock).showColorPicker();
    }

    @Test
    public void viewLoaded_whenSelectedLightIsNotColorLight_showColorPickerNotCalled() throws Exception {
        when(lightStateMock.getColormode()).thenReturn(ColorMode.COLOR_TEMPERATURE);
        subject.viewLoaded(singleLightInterfaceMock);
        verify(singleLightInterfaceMock, never()).showColorPicker();
    }

    @Test
    public void viewLoaded_subscribesToHeartBeatRelay() {

        subject.viewLoaded(singleLightInterfaceMock);
        verify(connectionDomain).getLightsAndGroupsHeartbeatRelay();
        heartBeatRelay.accept(lightSystem);
        verify(singleLightInterfaceMock).updateSingleLightUi(any());
    }

    @Test
    public void viewLoaded_andLightIsOn_setUiSwitchOn() {
        when(lightStateMock.isOn()).thenReturn(true);
        subject.viewLoaded(singleLightInterfaceMock);
        verify(singleLightInterfaceMock).setOnOffSwitch(true);
    }

    @Test
    public void viewLoaded_andLightIsOff_setUiSwitchOff() {
        when(lightStateMock.isOn()).thenReturn(false);
        subject.viewLoaded(singleLightInterfaceMock);
        verify(singleLightInterfaceMock).setOnOffSwitch(false);
    }

    @Test
    public void viewLoaded_setLightName() {
        String name = "THE LIGHT";
        when(lightPointMock.getName()).thenReturn(name);
        subject.viewLoaded(singleLightInterfaceMock);
        verify(singleLightInterfaceMock).setLightNameText(name);
    }

    @Test
    public void viewLoaded_setDimmerProgress() {
        when(lightStateMock.getBrightness()).thenReturn(brightness);
        subject.viewLoaded(singleLightInterfaceMock);
        verify(singleLightInterfaceMock).setDimmerProgress(brightness);
    }

    @Test
    public void updateBrightness_updatesLightBrightness() {
        subject.updateBrightness(brightness);
        verify(lightStateMock).setBrightness(brightness);
        verify(lightPointMock).updateState(lightStateMock);
    }

    @Test
    public void updateBrightness_whenBrightnessIsSame_doNotUpdate() {
        when(lightStateMock.getBrightness()).thenReturn(brightness);
        subject.updateBrightness(brightness);
        verify(lightStateMock, never()).setBrightness(any());
        verify(lightPointMock, never()).updateState(lightStateMock);
    }

    @Test
    public void updateOnState_setsLightOff() {
        boolean on = false;
        subject.updateOnState(on);
        verify(lightStateMock).setOn(on);
        verify(lightPointMock).updateState(lightStateMock);
    }

    @Test
    public void viewLoaded_colorModeIsColorTemperature_initColorTemp() {
        when(lightStateMock.getColormode()).thenReturn(ColorMode.COLOR_TEMPERATURE);
        int colorTemp = 4;
        when(lightStateMock.getCT()).thenReturn(colorTemp);
        subject.viewLoaded(singleLightInterfaceMock);
        verify(singleLightInterfaceMock).initColorTemp(colorTemp);
    }

    @Test
    public void getColor_returnsSelectedLightColor() {
        HueColor.RGB rgb = new HueColor.RGB(1, 2, 3);
        HueColor hueColor = new HueColor(rgb, null, null);
        when(lightStateMock.getColor()).thenReturn(hueColor);
        HueColor resultColor = subject.getColor();
        assertThat(resultColor).isEqualTo(hueColor);
    }
}