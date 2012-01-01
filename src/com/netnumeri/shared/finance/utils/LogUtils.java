package com.netnumeri.shared.finance.utils;

import com.google.gwt.core.client.GWT;

public class LogUtils {
    // logging --------------------------
    public static void debug(String msg) {
        debug(msg, null);
    }

    public static void debug(String msg, Throwable e) {
        GWT.log(msg, e);
        if (GWT.isScript()) {
            log(msg);
        } else {
            System.out.println("GWT LOG: " + msg);
            if (e != null) {
                e.printStackTrace();
            }
        }
    }

    public static native void log(String message) /*-{
        console.debug(message);
    }-*/;
}
