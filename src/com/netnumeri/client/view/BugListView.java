package com.netnumeri.client.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.netnumeri.shared.entity.BugEnum;

public interface BugListView extends View, NotificationView {
    void setTitle(String title);

    void clearBugGrid();

    void addBug(int id, String desc, BugEnum status, String user);

    int getRowCount();

    int getCellCount(int row);

    void addClickHandler(ClickHandler clickHandler);


    void clickButton();
}
