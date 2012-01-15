package com.netnumeri.javaonly.client.presenter;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netnumeri.client.events.RestUrl;
import com.netnumeri.client.presenter.OptionListPresenter;
import com.netnumeri.client.service.GetOptionServiceAsync;
import com.netnumeri.client.service.MySampleApplicationServiceAsync;
import com.netnumeri.client.view.OptionListView;
import com.netnumeri.shared.StubsForTests;
import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.service.GetEntitiesResponse;
import com.netnumeri.shared.service.GetEntitiesResponseImmutable;
import com.netnumeri.testutils.AsyncAction;
import com.netnumeri.testutils.HandlerAction;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

public class OptionListPresenterTest {
    private OptionListPresenter pres;

//    private ClickAnswer answer = new ClickAnswer();
//@Override
//public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
//    clickHandler = (ClickHandler) invocationOnMock.getArguments()[0];
//    return null;
//}

    private Mockery context = new Mockery();
    public OptionListView view = context.mock(OptionListView.class);
    public GetOptionServiceAsync service = context.mock(GetOptionServiceAsync.class);
    public MySampleApplicationServiceAsync messageService = context.mock(MySampleApplicationServiceAsync.class);
    private HandlerAction clickHandler;


    @Before
    public void setUp() throws Exception {

//        
//        doAnswer(answer).when(view).addClickHandler(any(ClickHandler.class));
//
//        doAnswer(new Answer<String>() {
//            @Override
//            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
//                return "ciao";
//            }
//        }).when(messageService).getMessage(anyString(), any(AsyncCallback.class));
//        
//        when(view.getMessageText()).thenReturn("");

        clickHandler = new HandlerAction();

        context.checking(new Expectations() {{
            oneOf(view).addClickHandler(with(any(ClickHandler.class)));
            will(clickHandler);
//            oneOf(view).showGrid();
//            oneOf(view).setTitle("Options Portfolio");
        }});


        pres = new OptionListPresenter(view, service, messageService);
    }


    @Test
    public void activateTest() {


        context.checking(new Expectations() {{
            oneOf(service).getEntities(with(""), with(any(AsyncCallback.class)));
        }});

        pres.activate(new RestUrl(""));

        context.assertIsSatisfied();
    }

    @Test
    public void clickButton() {

        context.checking(new Expectations() {{
            oneOf(service).getEntities(with(""), with(any(AsyncCallback.class)));
            oneOf(view).getMessageText();
            oneOf(messageService).getMessage(with("Hello, World!"), with(any(AsyncCallback.class)));
        }});

        pres.activate(new RestUrl(""));
        clickHandler.click();

        context.assertIsSatisfied();

    }

    @Test
    public void success() {

        final AsyncAction<GetEntitiesResponse<Option>> makeAsyncRequest = new AsyncAction<GetEntitiesResponse<Option>>();

        context.checking(new Expectations() {{
            oneOf(service).getEntities(with(""), with(any(AsyncCallback.class)));will(makeAsyncRequest);
            oneOf(view).clearBugGrid();
            exactly(2).of(view).addOption(with(any(Option.class)));
            oneOf(view).showGrid();
        }});

        GetEntitiesResponseImmutable resp = new GetEntitiesResponseImmutable(StubsForTests.createDummyOptionList());


        pres.activate(new RestUrl(""));
        makeAsyncRequest.succeedGiving(resp);

        context.assertIsSatisfied();

    }


}
