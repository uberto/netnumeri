package com.netnumeri.client.jsneeded.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Option;

public interface OptionListView extends View, NotificationView {   //TODO remove NotificationView

    void clearGrid();

//    int getRowCount();
//
//    int getCellCount(int row);

    void addClickHandler(ClickHandler clickHandler);


    void clickButton();

    void addOption(Option option);

    void showGrid();
}
