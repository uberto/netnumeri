package com.netnumeri.client.events;

import com.google.gwt.core.client.GWT;

public class EventsMonitorGwt implements EventsMonitor {
    @Override
    public void notifyPlaceRequest(PlaceRequestEvent event) {
         GWT.log("notifyPlaceRequest " + event);
    }

    @Override
    public void notifyError(String errorMessage) {
        GWT.log("notifyError " + errorMessage);

    }
}
