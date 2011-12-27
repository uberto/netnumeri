package com.netnumeri.client.events;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;

public class PlaceManager implements ValueChangeHandler<String> {
    private final HandlerManager eventManager;
    public static final String HOME = "options";


    public PlaceManager(HandlerManager eventManager) {
        this.eventManager = eventManager;

        // Register ourselves with the History API.
        History.addValueChangeHandler(this);
//
//            // Listen for manual place change events.
//            addPlaceChangedHandler(this);
    }

    public void onValueChange(ValueChangeEvent<String> event) {
        //get events from History, relaunch as ours
        eventManager.fireEvent(new PlaceRequestEvent(new RestUrl(event.getValue()), true));
    }

//        public void onPlaceChange(EventHandler event) {
//            newPlace(event.getUrl());
//        }


    public void fireCurrentPlace() {
        if (History.getToken().isEmpty()) {
            moveToPlace(HOME);
        } else {
            History.fireCurrentHistoryState();
        }
    }

    public void moveToPlace(String place) {
        RestUrl url = new RestUrl(place);
        History.newItem(url.toString(), true);
        //            eventManager.fireEvent(new PlaceRequestEvent(url, false));

    }

//    public void addPlaceRequestHandler(PlaceRequestHandler myPlaceRequestHandler) {
//        eventManager.addHandler(PlaceRequestEvent.TYPE, myPlaceRequestHandler);
//    }
//
//        public void addPlaceChangedHandler(PlaceChangedHandler myPlaceChangedHandler) {
//            eventManager.addHandler(PlaceChangedEvent.TYPE, myPlaceChangedHandler);
//        }


}


