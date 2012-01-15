package com.netnumeri.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.netnumeri.client.events.PlaceManager;
import com.netnumeri.client.events.RestUrl;
import com.netnumeri.client.presenter.Presenter;
import com.netnumeri.client.presenter.PresenterController;
import com.netnumeri.client.presenter.PresenterProxy;


public class NetNumeriApplication implements EntryPoint {

    private PresenterController presenterController;

    private PlaceManager placeManager;

    public void onModuleLoad() {

        HandlerManager handlerManager = new HandlerManager(null);

        placeManager = new PlaceManager(handlerManager);

        presenterController = new PresenterController(handlerManager, new PresenterProxy());

        getCurrentPresenter().activate(new RestUrl(""));
    }


    public Presenter getCurrentPresenter() {
         return presenterController.getCurrentPresenter();
    }
    
    public void moveToPlace(String place){
        placeManager.moveToPlace(place);
    }
}
