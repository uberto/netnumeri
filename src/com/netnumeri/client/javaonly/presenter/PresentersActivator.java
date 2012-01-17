package com.netnumeri.client.javaonly.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.netnumeri.client.javaonly.events.EventsMonitor;
import com.netnumeri.client.javaonly.events.PlaceRequestEvent;
import com.netnumeri.client.javaonly.events.PlaceRequestHandler;
import com.netnumeri.client.javaonly.events.RestUrl;

public class PresentersActivator {

    private Presenter currentPresenter;
    private PresentersProxy presenterProxy;
    private EventsMonitor monitor;

    private class MyPlaceRequestHandler implements PlaceRequestHandler {

        public void onPlaceRequest(PlaceRequestEvent event) {
            final RestUrl newPlace = event.getUrl();

//            System.out.println("onPlaceRequest " + newPlace);
            monitor.notifyPlaceRequest(event);
            
            
//            final PresenterProxy<?> pp = presenterProxyMap.get(newPlace.getPresenterUrl());


            Presenter tmpPres = presenterProxy.getPresenter(newPlace.getPresenterUrl());
            if (tmpPres == null) {
                monitor.notifyError("No such presenter " + newPlace.getPresenterUrl());
                return;
            }
            tmpPres.setMonitor(monitor);
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
                    currentPresenter.activate(newPlace);
                }
            });
//            }
        }

    }

    public PresentersActivator(HandlerManager eventManager, PresentersProxy presenterProxy, EventsMonitor monitor) {
        this.presenterProxy = presenterProxy;
        this.monitor = monitor;
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
