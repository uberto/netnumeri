package com.netnumeri.client.presenter;

import com.netnumeri.client.view.View;

public abstract class PresenterWithView implements Presenter{
    private View view;

    public PresenterWithView(View view) {
        this.view = view;
    }

    @Override
    public void activate() {
//        url = newUrl;

        view.show();
    }


}
