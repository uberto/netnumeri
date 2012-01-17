package com.netnumeri.client.log;

import com.google.gwt.core.client.GWT;
import com.netnumeri.client.events.SupportListener;

public class ListenerGwtLog implements SupportListener {
    @Override
    public void listenThis(String message) {
        GWT.log(message);
    }
}
