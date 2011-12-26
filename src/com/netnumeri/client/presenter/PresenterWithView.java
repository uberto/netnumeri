package com.netnumeri.client.presenter;

import com.netnumeri.client.view.View;

public abstract class PresenterWithView<T extends View> implements Presenter{
    private T view;

    public PresenterWithView(T view) {
        this.view = view;
    }

    @Override
    public void activate() {
//        url = newUrl;

        view.show();
    }


    public T getView() {
        return view;
    }
}
