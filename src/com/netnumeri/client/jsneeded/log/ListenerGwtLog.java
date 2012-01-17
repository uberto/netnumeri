package com.netnumeri.client.jsneeded.log;

import com.google.gwt.core.client.GWT;
import com.netnumeri.client.javaonly.events.SupportListener;

public class ListenerGwtLog implements SupportListener {
    @Override
    public void listenThis(String message) {
        GWT.log(message);
    }
}
