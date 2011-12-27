package com.netnumeri.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netnumeri.client.service.GetOptionServiceAsync;
import com.netnumeri.client.view.BugFormView;
import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.service.GetEntityResponse;

public class BugFormPresenter  extends PresenterWithView {
    private BugFormView view;
    private GetOptionServiceAsync bugEditService;

    public BugFormPresenter(BugFormView view, GetOptionServiceAsync service) {
        super(view);
        this.view = view;
        this.bugEditService = service;
    }


    public void activate() {
        view.setTitle("Edit bug");

        super.activate();

        bugEditService.getEntity("", new AsyncCallback<GetEntityResponse<Option>>() {

            @Override
            public void onFailure(Throwable caught) {
                view.alert("Failure on server!");
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
