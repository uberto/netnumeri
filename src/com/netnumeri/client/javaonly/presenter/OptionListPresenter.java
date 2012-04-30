package com.netnumeri.client.javaonly.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netnumeri.client.javaonly.events.RestUrl;
import com.netnumeri.client.jsneeded.service.GetOptionServiceAsync;
import com.netnumeri.client.jsneeded.service.MySampleApplicationServiceAsync;
import com.netnumeri.client.jsneeded.view.NotificationView;
import com.netnumeri.client.jsneeded.view.OptionListView;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Option;
import com.netnumeri.shared.service.GetEntitiesResponse;

import java.util.List;

public class OptionListPresenter extends PresenterWithView<OptionListView> {

    private GetOptionServiceAsync getListService;
    private MyAsyncCallback asyncCallback;
    private MySampleApplicationServiceAsync messageService;


    public OptionListPresenter(OptionListView view, GetOptionServiceAsync serv, MySampleApplicationServiceAsync messageService) {
        super(view);
        getListService = serv;
        this.messageService = messageService;

        asyncCallback = new MyAsyncCallback(view);

        this.getView().addClickHandler(createClickHandler());
    }




    @Override
    public void activate(RestUrl url) {

        getListService.getEntities("", new AsyncCallback<GetEntitiesResponse<Option>>() {

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

        getView().showGrid();

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
