package com.netnumeri.javaonly.client.presenter;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netnumeri.client.presenter.OptionListPresenter;
import com.netnumeri.client.service.GetOptionServiceAsync;
import com.netnumeri.client.service.MySampleApplicationServiceAsync;
import com.netnumeri.client.view.OptionListView;
import com.netnumeri.shared.StubsForTests;
import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.service.GetEntitiesResponseImmutable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class OptionListPresenterTest {
    private OptionListPresenter pres;
    public OptionListView view = mock(OptionListView.class);
    public GetOptionServiceAsync service = mock(GetOptionServiceAsync.class);
    public MySampleApplicationServiceAsync messageService = mock(MySampleApplicationServiceAsync.class);
    private ClickAnswer answer;


    @Before
    public void setUp() throws Exception {

        answer = new ClickAnswer();
        doAnswer(answer).when(view).addClickHandler(any(ClickHandler.class));

        doAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "ciao";
            }
        }).when(messageService).getMessage(anyString(), any(AsyncCallback.class));
        
        when(view.getMessageText()).thenReturn("");

        pres = new OptionListPresenter(view, service, messageService);
    }


    @After
    public void dropDown() throws Exception {
        Mockito.verifyNoMoreInteractions(view);
        Mockito.verifyNoMoreInteractions(service);
        Mockito.verifyNoMoreInteractions(messageService);
    }

    @Test
    public void activateTest(){
        pres.activate();

        verify(view).show();
        verify(view).setTitle("Options Portfolio");
        verify(view).addClickHandler(any(ClickHandler.class));

        verify(service).getEntities(anyString(), any(AsyncCallback.class));

    }

    @Test
    public void clickButton(){
        pres.activate();
        answer.clickHandler.onClick(null);

        verify(view).show();
        verify(view).setTitle("Options Portfolio");
        verify(view).getMessageText();
        verify(view).addClickHandler(any(ClickHandler.class));
        verify(service).getEntities(anyString(), any(AsyncCallback.class));
        verify(messageService).getMessage(anyString(), any(AsyncCallback.class));
    }

    @Test
    public void success(){
        GetEntitiesResponseImmutable resp = new GetEntitiesResponseImmutable(StubsForTests.createDummyOptionList());

        pres.populateGrid(resp);

        verify(view).clearBugGrid();
        verify(view, times(2)).addOption(any(Option.class));
        verify(view).addClickHandler(any(ClickHandler.class));

    }



}
