package com.netnumeri.client.javaonly.support;

import com.netnumeri.client.javaonly.events.PlaceRequestEvent;

public interface EventsMonitor {
    void notifyPlaceRequest(PlaceRequestEvent event);

    void notifyError(String errorMessage);

    void addListener(SupportListener listener);
}
