package com.netnumeri.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface PlaceRequestHandler extends EventHandler {
    public void onPlaceRequest(PlaceRequestEvent placeRequestEvent);
}
