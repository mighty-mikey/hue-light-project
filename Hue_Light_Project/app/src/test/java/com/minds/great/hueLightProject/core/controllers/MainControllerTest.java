package com.minds.great.hueLightProject.core.controllers;

import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MainActivityInterface;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MemoryInterface;
import com.minds.great.hueLightProject.core.models.LightSystem;

import org.junit.After;
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
public class MainControllerTest {

    private MainController subject;

    @Mock
    private MainActivityInterface view;

    @Mock
    private MemoryInterface memory;

    @Mock
    private ConnectionController connectionController;
    private PublishRelay<LightSystem> connectionSuccessfulRelay;

    @Before
    public void setUp() throws Exception {
        connectionSuccessfulRelay = PublishRelay.create();
        when(connectionController.getConnectionSuccessfulRelay()).thenReturn(connectionSuccessfulRelay);

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

    @Test
    public void viewLoaded_whenConnectionSuccessful_finishesActivity() throws Exception {
        subject.viewLoaded(view);
        verify(view, never()).finishConnectionActivity();
        connectionSuccessfulRelay.accept(new LightSystem.Builder().build());
        verify(view).finishConnectionActivity();
    }

    @After
    public void tearDown() throws Exception {
        connectionSuccessfulRelay = null;
    }
}