package com.minds.great.hueLightProject.core.presenters;

import com.minds.great.hueLightProject.core.controllers.LightSystemController;
import com.philips.lighting.hue.sdk.wrapper.domain.clip.ColorMode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SingleLightPresenterTest {
    @Mock
    private SingleLightInterface singleLightInterfaceMock;
    @Mock
    private LightSystemController lightSystemController;

    private SingleLightPresenter subject;

    @Before
    public void setUp() throws Exception {
        subject = new SingleLightPresenter(lightSystemController);
    }

    @Test
    public void viewLoaded_whenSelectedLightIsColorLight_showColorPicker() throws Exception {
        when(lightSystemController.getSelectedLightColorMode()).thenReturn(ColorMode.XY);
        subject.viewLoaded(singleLightInterfaceMock);
        verify(singleLightInterfaceMock).showColorPicker();
    }

    @Test
    public void viewLoaded_whenSelectedLightIsNotColorLight_showColorPickerNotCalled() throws Exception {
        when(lightSystemController.getSelectedLightColorMode()).thenReturn(ColorMode.COLOR_TEMPERATURE);
        subject.viewLoaded(singleLightInterfaceMock);
        verify(singleLightInterfaceMock, never()).showColorPicker();
    }
}