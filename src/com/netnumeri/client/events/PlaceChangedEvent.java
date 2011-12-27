package com.netnumeri.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class PlaceChangedEvent extends GwtEvent<PlaceChangedHandler> {
    public static Type<PlaceChangedHandler> TYPE = new Type<PlaceChangedHandler>();

    @Override
    public Type<PlaceChangedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PlaceChangedHandler handler) {

    }
}
