package com.netnumeri.client.jsneeded;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.netnumeri.client.javaonly.support.EventsMonitor;
import com.netnumeri.client.javaonly.support.EventsMonitorClient;
import com.netnumeri.client.javaonly.events.PlaceChangedHandler;
import com.netnumeri.client.javaonly.events.RestUrl;
import com.netnumeri.client.jsneeded.support.ListenerGwtLog;
import com.netnumeri.client.javaonly.presenter.Presenter;
import com.netnumeri.client.javaonly.presenter.PresentersActivator;
import com.netnumeri.client.javaonly.presenter.PresentersProxy;


public class NetNumeriApplication implements EntryPoint {

    private PresentersActivator presenterController;

    private PlaceChangedHandler placeManager;
    private EventsMonitor monitor;

    public void onModuleLoad() {

        HandlerManager handlerManager = new HandlerManager(null);

        placeManager = new PlaceChangedHandler(handlerManager);

        monitor = new EventsMonitorClient();
        monitor.addListener(new ListenerGwtLog());

        presenterController = new PresentersActivator(handlerManager, new PresentersProxy(), monitor);

        getCurrentPresenter().activate(new RestUrl(""));
//        placeManager.fireCurrentPlace();
    }


    public Presenter getCurrentPresenter() {
         return presenterController.getCurrentPresenter();
    }
    
    public void moveToPlace(String place){
        placeManager.moveToPlace(place);
    }
}
