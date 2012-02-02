package com.netnumeri.client.javaonly.presenter;

import com.netnumeri.client.javaonly.support.SupportDispatcher;
import com.netnumeri.client.jsneeded.service.GetOptionService;
import com.netnumeri.client.jsneeded.service.MySampleApplicationService;
import com.netnumeri.client.jsneeded.view.OptionListViewFlexTable;
import com.netnumeri.client.jsneeded.view.SingleOptionViewForm;

import java.util.HashMap;
import java.util.Map;

public class PresentersProxy {

    public static final String URL_OPTION_LIST = "optionList";
    public static final String URL_OPTION = "option";
    private Map<String, Presenter> presentersMap = new HashMap<String, Presenter>();
    private SupportDispatcher monitor;

    public PresentersProxy() {
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
