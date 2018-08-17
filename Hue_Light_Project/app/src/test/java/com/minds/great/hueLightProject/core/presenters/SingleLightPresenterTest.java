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
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SingleLightPresenterTest {
    @Mock
    private SingleLightInterface view;
    @Mock
    private LightSystemDomain lightSystemDomain;
    @Mock
    private ConnectionDomain connectionDomain;
    @Mock
    private LightSystem lightSystemMock;
    @Mock
    private LightPoint lightPointMock;
    @Mock
    private LightState lightStateMock;
    @Mock
    private HueColor hueColorMock;
    private SingleLightPresenter subject;
    private PublishRelay<LightSystem> heartBeatRelay;
    private final int brightness = 5;
    private final int colorTemperature = 7;
    private String lightName = "Light Name";

    @Before
    public void setUp() {
        heartBeatRelay = PublishRelay.create();
        when(connectionDomain.getHeartBeatRelay()).thenReturn(heartBeatRelay);
        when(lightSystemDomain.getSelectedLightPoint()).thenReturn(lightPointMock);
        when(lightPointMock.getLightState()).thenReturn(lightStateMock);
        when(lightStateMock.getColormode()).thenReturn(ColorMode.XY);
        when(lightStateMock.getColor()).thenReturn(hueColorMock);
        subject = new SingleLightPresenter(connectionDomain, lightSystemDomain);
    }

    @Test
    public void viewLoaded_whenSelectedLightIsColorLight_showColorPicker() {
        subject.viewLoaded(view);
        verify(view).showColorPicker(hueColorMock);
    }

    @Test
    public void viewLoaded_whenSelectedLightIsNotColorLight_showColorPickerNotCalled() {
        int colorTemperature = 10;
        when(lightStateMock.getColormode()).thenReturn(ColorMode.COLOR_TEMPERATURE);
        when(lightStateMock.getCT()).thenReturn(colorTemperature);
        subject.viewLoaded(view);
        verify(view, never()).showColorPicker(any());
        verify(view).setColorTempProgress(colorTemperature);
    }

    @Test
    public void viewLoaded_andLightIsOn_setUiSwitchOn() {
        when(lightStateMock.isOn()).thenReturn(true);
        subject.viewLoaded(view);
        verify(view).setOnOffSwitch(true);
    }

    @Test
    public void viewLoaded_andLightIsOff_setUiSwitchOff() {
        when(lightStateMock.isOn()).thenReturn(false);
        subject.viewLoaded(view);
        verify(view).setOnOffSwitch(false);
    }

    @Test
    public void viewLoaded_setLightName() {
        when(lightPointMock.getName()).thenReturn(lightName);
        subject.viewLoaded(view);
        verify(view).setLightNameText(lightName);
    }

    @Test
    public void viewLoaded_setDimmerProgress() {
        when(lightStateMock.getBrightness()).thenReturn(brightness);
        subject.viewLoaded(view);
        verify(view).setDimmerProgress(brightness);
    }

    @Test
    public void viewLoaded_whenLightIsOff_setOnThenOff() {
        //set color pick color based on light color after turning on. then turn light off again. move everythingd
        when(lightStateMock.isOn()).thenReturn(false);
        subject.viewLoaded(view);
        verify(lightStateMock).setOn(true);
        verify(lightStateMock).setOn(false);
    }

    @Test
    public void viewLoaded_whenLightIsOn_doNotSetOn() {
        when(lightStateMock.isOn()).thenReturn(true);
        subject.viewLoaded(view);
        verify(lightStateMock, never()).setOn(false);
    }

    @Test
    public void updateBrightness_updatesLightBrightness() {
        subject.updateBrightness(brightness);
        verify(lightStateMock).setBrightness(brightness);
        verify(lightPointMock).updateState(lightStateMock);
        verify(lightStateMock).setOn(true);
    }

    @Test
    public void updateBrightness_whenBrightnessIsSame_doNotUpdate() {
        when(lightStateMock.getBrightness()).thenReturn(brightness);
        subject.updateBrightness(brightness);
        verify(lightStateMock, never()).setBrightness(any());
        verify(lightPointMock, never()).updateState(lightStateMock);
        verify(lightStateMock, never()).setOn(any());
    }

    @Test
    public void updateColorTemperature_updatesLightColorTemperature() {
        subject.updateColorTemperature(colorTemperature);
        verify(lightStateMock).setCT(colorTemperature);
        verify(lightPointMock).updateState(lightStateMock);
        verify(lightStateMock).setOn(true);
    }

    @Test
    public void updateColorTemperature_whenColorTemperatureIsSame_doNotUpdate() {
        when(lightStateMock.getCT()).thenReturn(colorTemperature);
        subject.updateColorTemperature(colorTemperature);
        verify(lightStateMock, never()).setCT(any());
        verify(lightStateMock, never()).setOn(any());
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
        subject.viewLoaded(view);
        verify(view).setColorTempProgress(colorTemp);
    }

    @Test
    public void viewLoaded_heartBeatUpDates_turnOnSwitchOnView() {
        when(lightStateMock.isOn()).thenReturn(true);
        subject.viewLoaded(view);
        reset(view);
        heartBeatRelay.accept(lightSystemMock);
        verify(view).setOnOffSwitch(true);
    }

    @Test
    public void viewLoaded_heartBeatUpDates_setsDimmerOnView() {
        when(lightStateMock.getBrightness()).thenReturn(brightness);
        subject.viewLoaded(view);
        reset(view);
        heartBeatRelay.accept(lightSystemMock);
        verify(view).setDimmerProgress(brightness);
    }

    @Test
    public void viewLoaded_heartBeatUpDates_setsLightNameOnView() {
        when(lightPointMock.getName()).thenReturn(lightName);
        subject.viewLoaded(view);
        reset(view);
        heartBeatRelay.accept(lightSystemMock);
        verify(view).setLightNameText(lightName);
    }

    @Test
    public void viewLoaded_whenSelectedLightIsNotColorLight_andHeartBeatUpDates_showColorPickerNotCalled() {
        int colorTemperature = 10;
        when(lightStateMock.getColormode()).thenReturn(ColorMode.COLOR_TEMPERATURE);
        when(lightStateMock.getCT()).thenReturn(colorTemperature);
        subject.viewLoaded(view);
        reset(view);
        heartBeatRelay.accept(lightSystemMock);
        verify(view, never()).showColorPicker(any());
        verify(view).setColorTempProgress(colorTemperature);
    }

    @Test
    public void getColor_returnsSelectedLightColor() {
        when(lightStateMock.getColor()).thenReturn(hueColorMock);
        HueColor resultColor = subject.getColor();
        assertThat(resultColor).isEqualTo(hueColorMock);
    }

    @Test
    public void updateColor_setsColorToLightState() {
        subject.updateColor(hueColorMock);
        verify(lightStateMock).setOn(true);
        verify(lightStateMock).setXYWithColor(hueColorMock);
        verify(lightPointMock).updateState(lightStateMock);
    }

    @Test
    public void viewLoaded_whenLightIsCt_informsViewToShowCtBar() {
        when(lightStateMock.getColormode()).thenReturn(ColorMode.COLOR_TEMPERATURE);
        subject.viewLoaded(view);
        verify(view).showColorTemp();
    }
}