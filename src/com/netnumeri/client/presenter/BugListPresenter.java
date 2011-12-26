package com.netnumeri.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netnumeri.client.service.GetBugServiceAsync;
import com.netnumeri.client.service.MySampleApplicationServiceAsync;
import com.netnumeri.client.view.BugListView;
import com.netnumeri.client.view.NotificationView;
import com.netnumeri.shared.entity.Bug;
import com.netnumeri.shared.service.GetEntitiesResponse;

import java.util.List;

public class BugListPresenter extends PresenterWithView<BugListView> {

    private BugListView view;
    private GetBugServiceAsync bugListService;
    private MyAsyncCallback asyncCallback;
    private MySampleApplicationServiceAsync messageService;


    public BugListPresenter(BugListView view, GetBugServiceAsync serv, MySampleApplicationServiceAsync messageService) {
        super(view);
        this.view = view;
        bugListService = serv;
        this.messageService = messageService;

        asyncCallback = new MyAsyncCallback(view);

        view.addClickHandler(createClickHandler());
    }




    @Override
    public void activate() {
        super.activate();
        view.setTitle("Active bugs");

        bugListService.getEntities("", new AsyncCallback<GetEntitiesResponse>() {

            @Override
            public void onFailure(Throwable caught) {
                view.alert("Failure on server!");
            }

            @Override
            public void onSuccess(GetEntitiesResponse response) {
                populateGrid(response);
            }
        });

    }

    public void populateGrid(GetEntitiesResponse response) {
        view.clearBugGrid();


        List<Bug> bugList = response.getEntityList();
        for (Bug bug : bugList) {
            view.addBug(bug.getId(), bug.getDesc(), bug.getStatus(), bug.getUser());
        }
    }

    private ClickHandler createClickHandler() {
        return new ClickHandler() {

            public void onClick(ClickEvent event) {
                if (view.getMessageText().equals("")) {
                    messageService.getMessage("Hello, World!", asyncCallback);
                } else {
                    view.setMessageText("");
                }
            }
        };
    }

    public BugListView getView() {
        return view;
    }

    private static class MyAsyncCallback implements AsyncCallback<String> {

        private NotificationView view;

        public MyAsyncCallback(NotificationView view) {
            this.view = view;
        }

        public void onSuccess(String result) {
            view.setMessageText(result);
        }

        public void onFailure(Throwable throwable) {
            view.setMessageText("Failed to receive answer from server!");
        }
    }
}
