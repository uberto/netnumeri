package com.netnumeri.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.netnumeri.client.presenter.Presenter;
import com.netnumeri.client.presenter.PresenterController;
import com.netnumeri.client.presenter.PresenterProxy;


public class NetNumeriApplication implements EntryPoint {

    private PresenterController controller;

    public void onModuleLoad() {

        HandlerManager handlerManager = new HandlerManager(null);
        controller = new PresenterController(handlerManager, new PresenterProxy());

        getCurrentPresenter().activate();
    }


    public Presenter getCurrentPresenter() {
         return controller.getCurrentPresenter();
    }
}
