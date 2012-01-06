package com.netnumeri.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netnumeri.client.service.GetOptionServiceAsync;
import com.netnumeri.client.service.MySampleApplicationServiceAsync;
import com.netnumeri.client.view.NotificationView;
import com.netnumeri.client.view.OptionListView;
import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.service.GetEntitiesResponse;

import java.util.List;

public class OptionListPresenter extends PresenterWithView<OptionListView> {

    private GetOptionServiceAsync bugListService;
    private MyAsyncCallback asyncCallback;
    private MySampleApplicationServiceAsync messageService;


    public OptionListPresenter(OptionListView view, GetOptionServiceAsync serv, MySampleApplicationServiceAsync messageService) {
        super(view);
        bugListService = serv;
        this.messageService = messageService;

        asyncCallback = new MyAsyncCallback(view);

        this.getView().addClickHandler(createClickHandler());
    }




    @Override
    public void activate() {
        super.activate();
        getView().setTitle("Options Portfolio");

        bugListService.getEntities("", new AsyncCallback<GetEntitiesResponse<Option>>() {

            @Override
            public void onFailure(Throwable caught) {
                getView().alert("Failure on server!");
            }

            @Override
            public void onSuccess(GetEntitiesResponse<Option> response) {
                populateGrid(response);
            }
        });

    }

    public void populateGrid(GetEntitiesResponse<Option> response) {
        getView().clearBugGrid();


        List<Option> optionList = response.getEntityList();
        for (Option option : optionList) {
            getView().addOption(option);
        }
    }

    private ClickHandler createClickHandler() {
        return new ClickHandler() {

            public void onClick(ClickEvent event) {
                if (getView().getMessageText().equals("")) {
                    messageService.getMessage("Hello, World!", asyncCallback);
                } else {
                    getView().setMessageText("");
                }
            }
        };
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
