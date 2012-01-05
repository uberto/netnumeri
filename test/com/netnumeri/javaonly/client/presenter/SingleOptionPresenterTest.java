package com.netnumeri.javaonly.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netnumeri.client.presenter.SingleOptionPresenter;
import com.netnumeri.client.service.GetOptionServiceAsync;
import com.netnumeri.client.view.SingleOptionView;
import com.netnumeri.shared.StubsForTests;
import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.service.GetEntityResponse;
import com.netnumeri.shared.service.GetEntityResponseImmutable;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

public class SingleOptionPresenterTest {
    private SingleOptionPresenter pres;
    public SingleOptionView view;
    public GetOptionServiceAsync service;
    private Mockery context;


    @Before
    public void setUp() throws Exception {
        GetEntityResponse<Option> resp = new GetEntityResponseImmutable<Option>(StubsForTests.createDummyOption());

//        when(service.getEntity(anyString(), (AsyncCallback<GetEntityResponse<Option>>) anyObject())).thenReturn(resp);

        context = new Mockery();
        view = context.mock(SingleOptionView.class);
        service = context.mock(GetOptionServiceAsync.class);


        pres = new SingleOptionPresenter(view, service);
    }


    @Test
    public void activateTest() {
        context.checking(new Expectations() {{
            oneOf(view).show();
            oneOf(view).setTitle("Edit bug");
            oneOf(service).getEntity(with(""), with(any(AsyncCallback.class)));
        }});
        
        pres.activate();

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
