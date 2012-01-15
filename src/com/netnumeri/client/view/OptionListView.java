package com.netnumeri.client.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.netnumeri.shared.entity.Option;

public interface OptionListView extends View, NotificationView {   //TODO remove NotificationView

    void clearBugGrid();

//    int getRowCount();
//
//    int getCellCount(int row);

    void addClickHandler(ClickHandler clickHandler);


    void clickButton();

    void addOption(Option option);

    void showGrid();
}
