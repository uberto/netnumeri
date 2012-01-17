package com.netnumeri.client.javaonly.presenter;

import com.netnumeri.client.javaonly.events.EventsMonitor;
import com.netnumeri.client.javaonly.events.RestUrl;

public interface Presenter {
    void activate(RestUrl url);

    void setMonitor(EventsMonitor monitor);
}
