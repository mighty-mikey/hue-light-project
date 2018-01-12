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
    public void viewCreated_callsCheckMemory(){
        subject.viewCreated(view);
        verify(memory).getLightSystemIpAddress();
    }

    @Test
    public void viewCreated_whenLightSystemIsNull_navigatesToConnectionActivity() throws Exception {
        when(memory.getLightSystemIpAddress()).thenReturn(null);
        subject.viewCreated(view);
        verify(view).navigateToConnectionActivity();
    }

    @Test
    public void viewCreated_whenLightSystemIsNotNull_connectsToLightSystem() throws Exception {
        when(memory.getLightSystemIpAddress()).thenReturn("1");
        subject.viewCreated(view);
        verify(connectionController).connect("1");
    }

    @Test
    public void viewCreated_whenConnectionSuccessful_savesSystemAndFinishesActivity() throws Exception {
        subject.viewCreated(view);
        verify(view, never()).finishConnectionActivity();
        connectionSuccessfulRelay.accept(new LightSystem.Builder().build());
        verify(view).finishConnectionActivity();
    }

    @Test
    public void viewCreated_whenConnectionSuccessful_navigatesToLightActivity() throws Exception {
        subject.viewCreated(view);
        verify(view, never()).navigateToLightListActivity();
        connectionSuccessfulRelay.accept(new LightSystem.Builder().build());
        verify(view).navigateToLightListActivity();
    }

    @Test
    public void viewCreated_whenSystemInMemory_doNotSubscribe() throws Exception {
        when(memory.getLightSystemIpAddress()).thenReturn("2");
        subject.viewCreated(view);
        verify(connectionController, never()).getConnectionSuccessfulRelay();
    }

    @Test
    public void viewCreated_whenSystemInMemory_navigateToLights() throws Exception {
        when(memory.getLightSystemIpAddress()).thenReturn("3");
        subject.viewCreated(view);
        verify(view).navigateToLightListActivity();
    }

    @After
    public void tearDown() throws Exception {
        connectionSuccessfulRelay = null;
    }
}