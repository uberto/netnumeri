package com.netnumeri.javaonly.client.presenter;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netnumeri.client.presenter.BugListPresenter;
import com.netnumeri.client.service.GetBugServiceAsync;
import com.netnumeri.client.service.MySampleApplicationServiceAsync;
import com.netnumeri.client.view.BugListView;
import com.netnumeri.shared.entity.Bug;
import com.netnumeri.shared.entity.BugEnum;
import com.netnumeri.shared.service.GetEntitiesResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class BugListPresenterTest {
    private BugListPresenter pres;
    public BugListView view = mock(BugListView.class);
    public GetBugServiceAsync service = mock(GetBugServiceAsync.class);
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

        pres = new BugListPresenter(view, service, messageService);
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
        verify(view).setTitle("Active bugs");
        verify(view).addClickHandler(any(ClickHandler.class));

        verify(service).getEntities(anyString(), any(AsyncCallback.class));

    }

    @Test
    public void clickButton(){
        pres.activate();
        answer.clickHandler.onClick(null);

        verify(view).show();
        verify(view).setTitle("Active bugs");
        verify(view).getMessageText();
        verify(view).addClickHandler(any(ClickHandler.class));
        verify(service).getEntities(anyString(), any(AsyncCallback.class));
        verify(messageService).getMessage(anyString(), any(AsyncCallback.class));
    }

    @Test
    public void success(){
        GetEntitiesResponse resp = new GetEntitiesResponse();
        Bug dummyBug = createDummyBug();
        resp.add(dummyBug);
        resp.add(dummyBug);
        resp.add(dummyBug);
        pres.populateGrid(resp);

        verify(view).clearBugGrid();
        verify(view, times(3)).addBug(dummyBug.getId(), dummyBug.getDesc(), dummyBug.getStatus(), dummyBug.getUser());
        verify(view).addClickHandler(any(ClickHandler.class));

    }



    private Bug createDummyBug() {
        return new Bug(101, "test desc", BugEnum.WORKING, "uberto");
    }

}
