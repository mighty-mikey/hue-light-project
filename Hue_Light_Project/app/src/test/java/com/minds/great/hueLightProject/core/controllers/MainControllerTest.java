package com.minds.great.hueLightProject.core.controllers;

import com.minds.great.hueLightProject.core.models.LightSystem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

    MainController subject;

    @Mock
    MainActivityInterface view;

    @Mock
    private MemoryInterface memory;

    @Mock
    private ConnectionController connectionController;

    @Before
    public void setUp() throws Exception {
        subject = new MainController(memory, connectionController);
    }

    @Test
    public void viewLoaded_callsCheckMemory(){
        subject.viewLoaded(view);
        verify(memory).getLightSystem();
    }

    @Test
    public void viewLoaded_whenLightSystemIsNull_navigatesToConnectionActivity() throws Exception {
        when(memory.getLightSystem()).thenReturn(null);
        subject.viewLoaded(view);
        verify(view).navigateToConnectionActivity();
    }

    @Test
    public void viewLoaded_whenLightSystemIsNotNull_connectsToLightSystem() throws Exception {
        LightSystem lightSystem = new LightSystem.Builder().build();
        when(memory.getLightSystem()).thenReturn(lightSystem);
        subject.viewLoaded(view);
        verify(connectionController).connect(lightSystem);
    }
}