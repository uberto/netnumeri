package com.netnumeri.client.javaonly.events;

import com.google.gwt.event.shared.EventHandler;

public interface PlaceRequestHandler extends EventHandler {
    public void onPlaceRequest(PlaceRequestEvent placeRequestEvent);
}
