package com.netnumeri.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.netnumeri.client.events.PlaceManager;
import com.netnumeri.client.events.PlaceRequestEvent;
import com.netnumeri.client.events.PlaceRequestHandler;
import com.netnumeri.client.events.RestUrl;

public class PresenterController {

    private PlaceManager placeManager;
    private Presenter currentPresenter;
    private PresenterProxy presenterProxy;

    private class MyPlaceRequestHandler implements PlaceRequestHandler {

        public void onPlaceRequest(PlaceRequestEvent event) {
            final RestUrl newPlace = event.getUrl();

            System.out.println("onPlaceRequest " + newPlace);

//            final PresenterProxy<?> pp = presenterProxyMap.get(newPlace.getPresenterUrl());


            Presenter tmpPres = presenterProxy.getPresenter(newPlace.getPresenterUrl());
            if (tmpPres == null) {
                System.out.println("No such presenter " + newPlace.getPresenterUrl());
                return;
            }
            currentPresenter = tmpPres;


            //           if (pp != null) {
//                Application.showGlasspanelLoading();

            GWT.runAsync(new RunAsyncCallback() {
                public void onFailure(Throwable caught) {
//                        Application.hideGlasspanelLoading();
                    Window.alert("Code split download failed!!! Please try to refresh the application.");
                }

                public void onSuccess() {
//                        Application.hideGlasspanelLoading();
                    //BasePresenter p = pp.getPresenter();
                    currentPresenter.activate();
                }
            });
//            }
        }

    }

    public PresenterController(HandlerManager eventManager, PresenterProxy presenterProxy) {
        this.presenterProxy = presenterProxy;
        placeManager = new PlaceManager(eventManager);
        addPlaceRequestHandler(eventManager, new MyPlaceRequestHandler());
    }

    public Presenter getCurrentPresenter() {
        if (currentPresenter == null) {
            currentPresenter = presenterProxy.getDefaultPresenter();
        }
        return currentPresenter;

    }


    private void addPlaceRequestHandler(HandlerManager eventManager, PlaceRequestHandler myPlaceRequestHandler) {
        eventManager.addHandler(PlaceRequestEvent.TYPE, myPlaceRequestHandler);
    }


}
