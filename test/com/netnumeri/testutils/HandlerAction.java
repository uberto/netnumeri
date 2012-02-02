package com.netnumeri.testutils;

import com.google.gwt.event.dom.client.ClickHandler;
import org.hamcrest.Description;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import org.junit.Assert;

public class HandlerAction implements Action {

    private ClickHandler handler;

    public void click() {
        checkHandler();
        handler.onClick(null);
    }


    public void describeTo(Description description) {
        description.appendText("requests an async callback");
    }

    private void checkHandler() {
        if (handler == null) {
            Assert.fail("ClickHandler not assigned");
        }
    }

    @SuppressWarnings("unchecked")
    public Object invoke(Invocation invocation) throws Throwable {
        handler = (ClickHandler) invocation.getParameter(0);
        return null;
    }
}
