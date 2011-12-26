package com.netnumeri.client;

import com.google.gwt.core.client.EntryPoint;
import com.netnumeri.client.presenter.BugListPresenter;
import com.netnumeri.client.presenter.PresenterWithView;
import com.netnumeri.client.service.GetBugService;
import com.netnumeri.client.service.MySampleApplicationService;
import com.netnumeri.client.view.BugListViewFlexTable;


public class MySampleApplication implements EntryPoint {


    public String message = "Hello, World!";
    public BugListPresenter bugList;

    public void onModuleLoad() {

        bugList = new BugListPresenter( new BugListViewFlexTable(), GetBugService.App.getInstance(), MySampleApplicationService.App.getInstance());
        bugList.activate();
    }


    public PresenterWithView getCurrentPresenter() {
         return bugList;
    }
}
