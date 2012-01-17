package com.netnumeri.client.events;

public interface EventsMonitor {
    void notifyPlaceRequest(PlaceRequestEvent event);

    void notifyError(String errorMessage);

    void addListener(SupportListener listener);
}
