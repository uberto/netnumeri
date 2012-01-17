package com.netnumeri.client.jsneeded.support;

import com.google.gwt.core.client.GWT;
import com.netnumeri.client.javaonly.support.SupportListener;

public class ListenerGwtLog implements SupportListener {
    @Override
    public void listenThis(String message) {
        GWT.log(message);
    }
}
