package com.netnumeri.client.presenter;

import com.netnumeri.client.view.View;

public abstract class PresenterWithView<T extends View> implements Presenter{
    private T view;

    public PresenterWithView(T view) {
        this.view = view;
    }


    public T getView() {
        return view;
    }
}
