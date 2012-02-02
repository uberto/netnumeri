package com.netnumeri.client.javaonly.presenter;

import com.netnumeri.client.javaonly.support.SupportDispatcher;
import com.netnumeri.client.jsneeded.view.View;

public abstract class PresenterWithView<T extends View> implements Presenter{
    private T view;
    protected SupportDispatcher monitor;

    public PresenterWithView(T view) {
        this.view = view;
    }


    public T getView() {
        return view;
    }

    @Override
    public void setMonitor(SupportDispatcher monitor) {

        this.monitor = monitor;
    }


}
