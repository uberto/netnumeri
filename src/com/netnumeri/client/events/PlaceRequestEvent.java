package com.netnumeri.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class PlaceRequestEvent extends GwtEvent<PlaceRequestHandler> {

    public static Type<PlaceRequestHandler> TYPE = new Type<PlaceRequestHandler>();

    private final RestUrl url;

    public PlaceRequestEvent(RestUrl url) {
        this.url = url;
    }


    @Override
    public Type<PlaceRequestHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PlaceRequestHandler handler) {
        handler.onPlaceRequest(this);
    }

    public RestUrl getUrl() {
        return url;
    }

}
