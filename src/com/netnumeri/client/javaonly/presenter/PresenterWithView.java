package com.netnumeri.client.javaonly.presenter;

import com.netnumeri.client.javaonly.support.EventsMonitor;
import com.netnumeri.client.jsneeded.view.View;

public abstract class PresenterWithView<T extends View> implements Presenter{
    private T view;
    private EventsMonitor monitor;

    public PresenterWithView(T view) {
        this.view = view;
    }


    public T getView() {
        return view;
    }

    @Override
    public void setMonitor(EventsMonitor monitor) {

        this.monitor = monitor;
    }

    protected EventsMonitor getMonitor() {
        return monitor;
    }
}
