package com.netnumeri.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.netnumeri.client.events.EventsMonitor;
import com.netnumeri.client.events.EventsMonitorGwt;
import com.netnumeri.client.events.PlaceChangedHandler;
import com.netnumeri.client.presenter.Presenter;
import com.netnumeri.client.presenter.PresentersProxy;
import com.netnumeri.client.presenter.PresentersActivator;


public class NetNumeriApplication implements EntryPoint {

    private PresentersActivator presenterController;

    private PlaceChangedHandler placeManager;
    private EventsMonitor monitor;

    public void onModuleLoad() {

        HandlerManager handlerManager = new HandlerManager(null);

        placeManager = new PlaceChangedHandler(handlerManager);

        monitor = new EventsMonitorGwt();

        presenterController = new PresentersActivator(handlerManager, new PresentersProxy(), monitor);

//        getCurrentPresenter().activate(new RestUrl(""));
        placeManager.fireCurrentPlace();
    }


    public Presenter getCurrentPresenter() {
         return presenterController.getCurrentPresenter();
    }
    
    public void moveToPlace(String place){
        placeManager.moveToPlace(place);
    }
}
