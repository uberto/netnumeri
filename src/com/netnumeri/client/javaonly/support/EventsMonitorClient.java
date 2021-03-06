package com.netnumeri.client.javaonly.support;

import com.netnumeri.client.javaonly.events.PlaceRequestEvent;

import java.util.ArrayList;
import java.util.List;

public class EventsMonitorClient implements SupportDispatcher {

    List<SupportListener> listeners = new ArrayList<SupportListener>();

    @Override
    public void notifyPlaceRequest(PlaceRequestEvent event) {

        notifyToListeners("notifyPlaceRequest " + event);
    }

    private void notifyToListeners(String message) {
        for (SupportListener listener : listeners) {
            listener.listenThis(message);
        }
    }

    @Override
    public void addListener(SupportListener listener) {
        listeners.add(listener);
    }

    @Override
    public void notifyError(String errorMessage) {
        notifyToListeners("notifyError " + errorMessage);
   }
}
