package com.netnumeri.javaonly.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netnumeri.client.javaonly.events.RestUrl;
import com.netnumeri.client.jsneeded.view.SingleOptionView;
import com.netnumeri.client.javaonly.presenter.SingleOptionPresenter;
import com.netnumeri.client.jsneeded.service.GetOptionServiceAsync;
import com.netnumeri.shared.StubsForTests;
import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.service.GetEntityResponse;
import com.netnumeri.shared.service.GetEntityResponseImmutable;
import com.netnumeri.testutils.AsyncAction;
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
//            oneOf(view).showEdit(null);
//            oneOf(view).setTitle("Edit bug");
            oneOf(service).getEntity(with(""), with(any(AsyncCallback.class)));
        }});
        
        pres.activate(new RestUrl(""));

        context.assertIsSatisfied();

    }

    @Test
    public void success(){
        final AsyncAction<GetEntityResponse<Option>> makeAsyncRequest = new AsyncAction<GetEntityResponse<Option>>();

        context.checking(new Expectations() {{
            oneOf(service).getEntity(with(""), with(any(AsyncCallback.class)));
            will(makeAsyncRequest);
            oneOf(view).showEdit(StubsForTests.createDummyOption());
        }});

        GetEntityResponseImmutable<Option> resp = new GetEntityResponseImmutable(StubsForTests.createDummyOption());


        pres.activate(new RestUrl(""));
        makeAsyncRequest.succeedGiving(resp);

        context.assertIsSatisfied();


    }


}
