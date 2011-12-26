package com.netnumeri.client;

import com.google.gwt.core.client.EntryPoint;
import com.netnumeri.client.presenter.OptionListPresenter;
import com.netnumeri.client.presenter.PresenterWithView;
import com.netnumeri.client.service.GetBugService;
import com.netnumeri.client.service.MySampleApplicationService;
import com.netnumeri.client.view.OptionListViewFlexTable;


public class DerivativesApplication implements EntryPoint {


    public String message = "Hello, World!";
    public OptionListPresenter bugList;

    public void onModuleLoad() {

        bugList = new OptionListPresenter( new OptionListViewFlexTable(), GetBugService.App.getInstance(), MySampleApplicationService.App.getInstance());
        bugList.activate();
    }


    public PresenterWithView getCurrentPresenter() {
         return bugList;
    }
}
