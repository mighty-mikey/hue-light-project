package com.minds.great.hueLightProject.core.controllers;

import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.controllers.controllerInterfaces.MainFragmentView;
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
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

    private MainController subject;

    @Mock
    private MainFragmentView view;

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
        verify(view).navigateToConnectionFragment();
    }

    @Test
    public void viewCreated_whenLightSystemIsNotNull_connectsToLightSystem() throws Exception {
        when(memory.getLightSystemIpAddress()).thenReturn("1");
        subject.viewCreated(view);
        verify(connectionController).connect("1");
    }

    @Test
    public void viewCreated_whenConnectionSuccessful_switchToLightList() throws Exception {
        LightSystem lightSystem = new LightSystem.Builder().build();
        when(memory.getLightSystemIpAddress()).thenReturn("1");
        subject.viewCreated(view);

        verify(view, never()).navigateToLightListFragment();
        connectionSuccessfulRelay.accept(lightSystem);
        verify(view).navigateToLightListFragment();
    }

    @Test
    public void viewCreated_whenSystemNotInMemory_callNavigateToConnectionActivity() throws Exception {
        when(memory.getLightSystemIpAddress()).thenReturn(null);
        verify(view, never()).navigateToConnectionFragment();
        subject.viewCreated(view);
        verify(view).navigateToConnectionFragment();
    }


    @Test
    public void viewCreated_whenSystemInMemory_navigateToLights() throws Exception {
        when(memory.getLightSystemIpAddress()).thenReturn("1");
        verify(connectionController, never()).connect(any());
        subject.viewCreated(view);
        verify(connectionController).connect(any());
    }

    @After
    public void tearDown() throws Exception {
        connectionSuccessfulRelay = null;
        reset(view);
        reset(memory);
        reset(connectionController);
    }
}