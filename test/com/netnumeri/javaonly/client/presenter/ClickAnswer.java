package com.netnumeri.javaonly.client.presenter;

import com.google.gwt.event.dom.client.ClickHandler;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ClickAnswer implements Answer<Void> {

    public ClickHandler clickHandler;

    @Override
    public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
        clickHandler = (ClickHandler) invocationOnMock.getArguments()[0];
        return null;
    }
}
