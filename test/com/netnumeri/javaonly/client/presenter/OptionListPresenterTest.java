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


        context.checking(new Expectations() {{
            oneOf (view).addClickHandler(with(any(ClickHandler.class)));
            oneOf(view).show();
            oneOf(view).setTitle("Options Portfolio");
         }});


        pres = new OptionListPresenter(view, service, messageService);
    }


    @Test
    public void activateTest(){

        context.checking(new Expectations() {{
            oneOf (service).getEntities(with(""), with(any(AsyncCallback.class)));
        }});

        pres.activate();

        context.assertIsSatisfied();
    }

    @Test
    public void clickButton(){

        context.checking(new Expectations() {{
            oneOf (service).getEntities(with(""), with(any(AsyncCallback.class)));
        }});

        pres.activate();
//        answer.clickHandler.onClick(null);

        context.assertIsSatisfied();
//
//        verify(view).show();
//        verify(view).setTitle("Options Portfolio");
//        verify(view).getMessageText();
//        verify(view).addClickHandler(any(ClickHandler.class));
//        verify(service).getEntities(anyString(), any(AsyncCallback.class));
//        verify(messageService).getMessage(anyString(), any(AsyncCallback.class));
    }

    @Test
    public void success(){

        context.checking(new Expectations() {{
            oneOf (service).getEntities(with(""), with(any(AsyncCallback.class)));
            oneOf (view).clearBugGrid();
            exactly(2).of(view).addOption(with(any(Option.class)));
        }});

        GetEntitiesResponseImmutable resp = new GetEntitiesResponseImmutable(StubsForTests.createDummyOptionList());


        pres.activate();
        pres.populateGrid(resp);

        context.assertIsSatisfied();

//        verify(view).clearBugGrid();
//        verify(view, times(2)).addOption(any(Option.class));
//        verify(view).addClickHandler(any(ClickHandler.class));

    }



}
