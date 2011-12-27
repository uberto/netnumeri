package com.netnumeri.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class PlaceRequestEvent extends GwtEvent<PlaceRequestHandler> {

    public static Type<PlaceRequestHandler> TYPE = new Type<PlaceRequestHandler>();

    private final RestUrl url;

    private final boolean fromHistory;

//    public PlaceRequestEvent(RestUrl request) {
//        this(request, false);
//    }

    public PlaceRequestEvent(RestUrl url, boolean fromHistory) {
        this.url = url;
        this.fromHistory = fromHistory;
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

    boolean isFromHistory() {
        return fromHistory;
    }

}
