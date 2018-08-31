package com.minds.great.hueLightProject.core.domain;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.LightSystemInterface;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.MainInterface;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.MemoryInterface;
import com.minds.great.hueLightProject.core.models.ConnectionError;
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
public class MainDomainTest {

    private MainDomain subject;

    @Mock
    private MainInterface view;

    @Mock
    private MemoryInterface memory;

    @Mock
    private LightSystemInterface lightSystemInterface;

    @Mock
    private ConnectionDomain connectionDomain;

    private BehaviorRelay<LightSystem> connectionSuccessfulRelay;

    private PublishRelay<ConnectionError> errorRelay;

    @Before
    public void setUp() throws Exception {
        connectionSuccessfulRelay = BehaviorRelay.create();
        errorRelay = PublishRelay.create();
        when(connectionDomain.getConnectionSuccessfulRelay()).thenReturn(connectionSuccessfulRelay);
        when(lightSystemInterface.getErrorObservable()).thenReturn(errorRelay);

        subject = new MainDomain(memory, connectionDomain, lightSystemInterface);
    }

    @Test
    public void viewCreated_callsCheckMemory(){
        subject.viewLoaded(view);
        verify(memory).getLightSystemIpAddress();
    }

    @Test
    public void viewCreated_whenLightSystemIsNull_navigatesToConnectionActivity() throws Exception {
        when(memory.getLightSystemIpAddress()).thenReturn(null);
        subject.viewLoaded(view);
        verify(view).navigateToConnectionFragment();
    }

    @Test
    public void viewCreated_whenLightSystemIsNotNull_connectsToLightSystem() throws Exception {
        when(memory.getLightSystemIpAddress()).thenReturn("1");
        subject.viewLoaded(view);
        verify(connectionDomain).connect("1");
    }

    @Test
    public void viewCreated_whenConnectionSuccessful_switchToLightList() throws Exception {
        LightSystem lightSystem = new LightSystem.Builder().build();
        when(memory.getLightSystemIpAddress()).thenReturn("1");
        subject.viewLoaded(view);

        verify(view, never()).navigateToTabFragment();
        connectionSuccessfulRelay.accept(lightSystem);
        verify(view).navigateToTabFragment();
    }

    @Test
    public void viewCreated_whenSystemNotInMemory_callNavigateToConnectionActivity() throws Exception {
        when(memory.getLightSystemIpAddress()).thenReturn(null);
        verify(view, never()).navigateToConnectionFragment();
        subject.viewLoaded(view);
        verify(view).navigateToConnectionFragment();
    }


    @Test
    public void viewCreated_whenSystemInMemory_navigateToTabs() throws Exception {
        when(memory.getLightSystemIpAddress()).thenReturn("1");
        verify(connectionDomain, never()).connect(any());
        subject.viewLoaded(view);
        verify(connectionDomain).connect(any());
    }

    @After
    public void tearDown() throws Exception {
        connectionSuccessfulRelay = null;
        reset(view);
        reset(memory);
        reset(connectionDomain);
    }
}