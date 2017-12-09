package com.minds.great.hueLightProject.core.presenters;

import com.jakewharton.rxrelay2.PublishRelay;
import com.minds.great.hueLightProject.core.models.ConnectionError;
import com.minds.great.hueLightProject.core.models.LightSystem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionPresenterTest {

    @Mock
    private
    ConnectionView viewMock;

    @Mock
    private LightSystemInterface lightSystemMock;

    @Mock
    private MemoryInterface memoryMock;

    private ConnectionPresenter subject;

    private PublishRelay<List<LightSystem>> lightSystemListRelay;
    private PublishRelay<ConnectionError> errorRelay;
    private PublishRelay<LightSystem> lightSystemRelay;


    @Before
    public void setUp() {
        lightSystemListRelay = PublishRelay.create();
        errorRelay = PublishRelay.create();
        lightSystemRelay = PublishRelay.create();

        when(lightSystemMock.getErrorObservable()).thenReturn(errorRelay);
        when(lightSystemMock.getLightSystemListObservable()).thenReturn(lightSystemListRelay);
        when(lightSystemMock.getLightSystemObservable()).thenReturn(lightSystemRelay);

        subject = new ConnectionPresenter(lightSystemMock, memoryMock);
    }

    @Test
    public void search_showsAndHidesViewElements() {
        subject.viewLoaded(viewMock);
        subject.search();
        verify(viewMock).showProgressBar();
        verify(viewMock).hideConnectButton();
        verify(viewMock).hideErrorMessage();
        verify(lightSystemMock).getLightSystemListObservable();
    }

    @After
    public void cleanUp(){
        lightSystemListRelay = null;
        errorRelay = null;
        lightSystemRelay = null;
    }

}