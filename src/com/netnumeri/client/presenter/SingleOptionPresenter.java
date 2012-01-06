package com.netnumeri.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netnumeri.client.service.GetOptionServiceAsync;
import com.netnumeri.client.view.SingleOptionView;
import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.service.GetEntityResponse;

public class SingleOptionPresenter extends PresenterWithView<SingleOptionView> {
    private GetOptionServiceAsync optionServiceAsync;

    public SingleOptionPresenter(SingleOptionView view, GetOptionServiceAsync service) {
        super(view);
        this.optionServiceAsync = service;
    }


    public void activate() {
        getView().setTitle("Edit bug");

        super.activate();

        optionServiceAsync.getEntity("", new AsyncCallback<GetEntityResponse<Option>>() {

            @Override
            public void onFailure(Throwable caught) {
                getView().alert("Failure on server!");
            }

            @Override
            public void onSuccess(GetEntityResponse response) {
                populateForm(response);
            }
        });

    }

    private void populateForm(GetEntityResponse response) {
        //To change body of created methods use File | Settings | File Templates.
    }
}
