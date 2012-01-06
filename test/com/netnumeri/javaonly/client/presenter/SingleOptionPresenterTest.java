package com.netnumeri.javaonly.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netnumeri.client.presenter.SingleOptionPresenter;
import com.netnumeri.client.service.GetOptionServiceAsync;
import com.netnumeri.client.view.SingleOptionView;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

public class SingleOptionPresenterTest {
    private SingleOptionPresenter pres;

    private Mockery context = new Mockery();
    public SingleOptionView view = context.mock(SingleOptionView.class);
    public GetOptionServiceAsync service = context.mock(GetOptionServiceAsync.class);


    @Before
    public void setUp() throws Exception {
//        GetEntityResponse<Option> resp = new GetEntityResponseImmutable<Option>(StubsForTests.createDummyOption());
//
////        when(service.getEntity(anyString(), (AsyncCallback<GetEntityResponse<Option>>) anyObject())).thenReturn(resp);

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

        context.assertIsSatisfied();

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
