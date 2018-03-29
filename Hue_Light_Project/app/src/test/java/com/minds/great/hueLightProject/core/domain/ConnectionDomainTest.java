package com.minds.great.hueLightProject.core.domain;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.ConnectionInterface;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.LightSystemInterface;
import com.minds.great.hueLightProject.core.domain.domainInterfaces.MemoryInterface;
import com.minds.great.hueLightProject.core.models.ConnectionError;
import com.minds.great.hueLightProject.core.models.LightSystem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.minds.great.hueLightProject.core.models.ConnectionError.NO_BRIDGE_FOUND_CODE;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionDomainTest {

    @Mock
    private ConnectionInterface viewMock;

    @Mock
    private LightSystemInterface lightSystemMock;

    @Mock
    private MemoryInterface memoryMock;

    private ConnectionDomain subject;

    private PublishRelay<List<LightSystem>> lightSystemListRelay;
    private PublishRelay<ConnectionError> errorRelay;
    private BehaviorRelay<LightSystem> lightSystemRelay;
    private LightSystem lightSystem;


    @Before
    public void setUp() {
        lightSystemListRelay = PublishRelay.create();
        errorRelay = PublishRelay.create();
        lightSystemRelay = BehaviorRelay.create();

        String testIpAddress = "testIpAddress";

        lightSystem = new LightSystem.Builder()
                .ipAddress(testIpAddress)
                .build();

        when(lightSystemMock.getErrorObservable()).thenReturn(errorRelay);
        when(lightSystemMock.getLightSystemListObservable()).thenReturn(lightSystemListRelay);
        when(lightSystemMock.getLightSystemObservable()).thenReturn(lightSystemRelay);

        subject = new ConnectionDomain(lightSystemMock);
    }

    @Test
    public void search_showsAndHidesViewElements() {
        subject.viewLoaded(viewMock);
        resetMocks();
        subject.search();
        verify(viewMock).showProgressBar();
        verify(viewMock).hideConnectButton();
        verify(viewMock).hideErrorMessage();
        verify(lightSystemMock).searchForLightSystems();
    }

    @Test
    public void viewLoaded_whenStoredLightSystemNotFound_doesNotCallConnect() throws Exception {
        when(memoryMock.getLightSystemIpAddress()).thenReturn(null);
        subject.viewLoaded(viewMock);
        verify(lightSystemMock, never()).connectToLightSystem(any());
        verify(lightSystemMock).getLightSystemListObservable();
        verify(lightSystemMock).getErrorObservable();
    }

    @Test
    public void viewLoaded_whenLightSystemListFound_showsWaitForConnection() throws Exception {
        when(memoryMock.getLightSystemIpAddress()).thenReturn(null);
        subject.viewLoaded(viewMock);
        ArrayList<LightSystem> lightSystemList = new ArrayList<>();
        lightSystemList.add(lightSystem);

        resetMocks();
        lightSystemListRelay.accept(lightSystemList);

        verify(viewMock).hideProgressBar();
        verify(viewMock).showWaitingForConnection();
        verify(viewMock).hideConnectButton();
    }

    private void resetMocks() {
        reset(viewMock);
        reset(lightSystemMock);
        reset(memoryMock);
    }

    @Test
    public void viewLoaded_whenErrorFound_informsViewOfError() throws Exception {
        when(memoryMock.getLightSystemIpAddress()).thenReturn(null);
        subject.viewLoaded(viewMock);

        ConnectionError error = new ConnectionError.Builder()
                .code(NO_BRIDGE_FOUND_CODE)
                .build();

        resetMocks();
        errorRelay.accept(error);
        verify(viewMock).showErrorMessage(NO_BRIDGE_FOUND_CODE);
        verify(viewMock).hideProgressBar();
        verify(viewMock).showConnectButton();
    }

    @Test
    public void getConnectionSuccessfulRelay_whenItemAlreadyOnRelay_getsItem() throws Exception {

        BehaviorRelay<LightSystem> relay = subject.getConnectionSuccessfulRelay();
        relay.accept(lightSystem);
        LightSystem returnedSystem = relay.blockingFirst();

        assertThat(returnedSystem.equals(lightSystem)).isTrue();
    }

    @Test
    public void viewLoaded_whenLightSystemIsEmpty_doesNotThrowException() throws Exception {
        List<LightSystem> emptyList = new ArrayList<>();
        subject.viewLoaded(viewMock);
        lightSystemListRelay.accept(emptyList);
    }

    @After
    public void cleanUp() {
        lightSystemListRelay = null;
        errorRelay = null;
        lightSystemRelay = null;
        resetMocks();
    }

}