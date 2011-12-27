package com.netnumeri.client;

import com.google.gwt.core.client.EntryPoint;
import com.netnumeri.client.presenter.OptionListPresenter;
import com.netnumeri.client.presenter.PresenterWithView;
import com.netnumeri.client.service.GetOptionService;
import com.netnumeri.client.service.MySampleApplicationService;
import com.netnumeri.client.view.OptionListViewFlexTable;


public class NetNumeriApplication implements EntryPoint {


    private String message = "Hello, World!";
    private OptionListPresenter optionListPresenter;

    public void onModuleLoad() {

        optionListPresenter = new OptionListPresenter( new OptionListViewFlexTable(), GetOptionService.App.getInstance(), MySampleApplicationService.App.getInstance());
        optionListPresenter.activate();
    }


    public PresenterWithView getCurrentPresenter() {
         return optionListPresenter;
    }
}
