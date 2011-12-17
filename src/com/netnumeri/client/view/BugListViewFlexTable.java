package com.netnumeri.client.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.netnumeri.client.widget.BugFlexTable;
import com.netnumeri.shared.entity.BugEnum;

public class BugListViewFlexTable extends ViewAbstractRoot implements BugListView {

    BugFlexTable flexTable = new BugFlexTable();
    public Button clickMeButton = new Button("Click me");
    public Label messageLabel = new Label();

    public void setTitle(String title) {
        RootPanel.get("title-label").getElement().setInnerText(title);
        flexTable.setTitle(title);

    }


//        addHeaders(flexTable);
//
//        flexTable.addRow("1", "button click doesn't work", "open", "uberto");
//        flexTable.addRow("2", "total crash", "closed", "uberto");
//
//
//        return flexTable;

    private void addHeaders() {
        flexTable.addHeaders("bug id", "description", "status", "assigned to");
    }


    public void clearBugGrid() {

        flexTable.clear();
        flexTable.removeAllRows();
        addHeaders();
    }

    public void addBug(int id, String desc, BugEnum status, String user) {
        flexTable.addRow("" + id, desc, status.name(), user);
    }

    @Override
    public int getRowCount() {
        return flexTable.getRowCount();
    }

    @Override
    public int getCellCount(int row) {
        return flexTable.getCellCount(row);
    }

    @Override
    public void addClickHandler(ClickHandler clickHandler) {
        clickMeButton.addClickHandler(clickHandler);
    }

    @Override
    protected void placeInPanel(Panel panel) {
        panel.add(flexTable);
                RootPanel.get("slot1").add(clickMeButton);
        RootPanel.get("slot2").add(messageLabel);

    }

    @Override
    public void setMessageText(String text) {
        messageLabel.getElement().setInnerHTML(text);
    }

    @Override
    public String getMessageText() {
        return messageLabel.getElement().getInnerHTML();
    }
}
