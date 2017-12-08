package com.minds.great.hueLightProject.core.presenters;

import com.minds.great.hueLightProject.core.controllers.ConnectionController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionPresenterTest {

    @Mock
    private
    ConnectionView viewMock;

    @Mock
    private
    ConnectionController connectionControllerMock;

    @Test
    public void search_showsAndHidesViewElements(){
        ConnectionPresenter subject = new ConnectionPresenter(connectionControllerMock);
        subject.viewLoaded(viewMock);
        subject.search();
        verify(viewMock).showProgressBar();
        verify(viewMock).hideConnectButton();
        verify(viewMock).hideErrorMessage();
        verify(connectionControllerMock).getLightSystemListObservable();
    }

}