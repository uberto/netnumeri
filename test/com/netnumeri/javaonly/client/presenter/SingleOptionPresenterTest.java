package com.netnumeri.javaonly.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netnumeri.client.presenter.SingleOptionPresenter;
import com.netnumeri.client.service.GetOptionServiceAsync;
import com.netnumeri.client.view.SingleOptionView;
import com.netnumeri.shared.StubsForTests;
import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.service.GetEntityResponse;
import com.netnumeri.shared.service.GetEntityResponseImmutable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class SingleOptionPresenterTest {
    private SingleOptionPresenter pres;
    public SingleOptionView view = mock(SingleOptionView.class);
    public GetOptionServiceAsync service = mock(GetOptionServiceAsync.class);


    @Before
    public void setUp() throws Exception {
        GetEntityResponse<Option> resp = new GetEntityResponseImmutable<Option>(StubsForTests.createDummyOption());

//        when(service.getEntity(anyString(), (AsyncCallback<GetEntityResponse<Option>>) anyObject())).thenReturn(resp);
        pres = new SingleOptionPresenter(view, service);
    }


    @After
    public void dropDown() throws Exception {
        Mockito.verifyNoMoreInteractions(view);
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    public void activateTest(){
        pres.activate();

        verify(view).show();
        verify(view).setTitle("Edit bug");

        verify(service).getEntity(anyString(), any(AsyncCallback.class));

    }

//    @Test
//    public void success(){
//        pres.activate();
//        pres.
//
//        verify(view).clearBugGrid();
//        verify(view, times(3)).addBug(option.getId(), option.getDesc(), option.getStatus(), option.getUser());
//
//    }


}
