package com.netnumeri.client.presenter;

import com.netnumeri.client.service.GetOptionService;
import com.netnumeri.client.service.MySampleApplicationService;
import com.netnumeri.client.view.OptionListViewFlexTable;
import com.netnumeri.client.view.SingleOptionViewForm;

import java.util.HashMap;
import java.util.Map;

public class PresenterProxy {

    public static final String URL_OPTION_LIST = "optionList";
    public static final String URL_OPTION = "option";
    private Map<String, Presenter> presentersMap = new HashMap<String, Presenter>();

    public PresenterProxy() {
        prepareProxyMap();
    }

    private void prepareProxyMap() {
        presentersMap.put(URL_OPTION_LIST, createOptionListPresenter());
        presentersMap.put(URL_OPTION, createSingleOptionPresenter());
    }

    private Presenter createSingleOptionPresenter() {
        return new SingleOptionPresenter(new SingleOptionViewForm(), GetOptionService.App.getInstance());
    }

    private Presenter createOptionListPresenter() {
        return new OptionListPresenter(new OptionListViewFlexTable(), GetOptionService.App.getInstance(), MySampleApplicationService.App.getInstance());
    }

    public Presenter getPresenter(String presenterUrl) {
        return presentersMap.get(presenterUrl);
    }

    public Presenter getDefaultPresenter() {
        return getPresenter(URL_OPTION_LIST);
    }
}
