package com.netnumeri.client.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.netnumeri.client.widget.HeadersFlexTable;
import com.netnumeri.shared.entity.Option;

public class OptionListViewFlexTable extends ViewAbstractRoot implements OptionListView {

    HeadersFlexTable flexTable = new HeadersFlexTable();
    public Button clickMeButton = new Button("Click me");
    public Label messageLabel = new Label();

    private void setTitle(String title) {
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
        flexTable.addHeaders("Name", "Stock", "Type", "Due to", "Strike");
    }


    @Override
    public void addOption(Option option) {
        flexTable.addRow(option.getOptionName(), option.getStockTicket(), option.getType().toString(), option.getDateDue().toString(), option.getStrike().toString());

    }

    @Override
    public void showGrid() {

        setTitle("Options Portfolio");
        show();

    }


    public void clearBugGrid() {

        flexTable.clear();
        flexTable.removeAllRows();
        addHeaders();
    }
//
//    @Override
//    public int getRowCount() {
//        return flexTable.getRowCount();
//    }
//
//    @Override
//    public int getCellCount(int row) {
//        return flexTable.getCellCount(row);
//    }

    @Override
    public void addClickHandler(ClickHandler clickHandler) {
        RootPanel.get("slot1").add(clickMeButton);
        RootPanel.get("slot2").add(messageLabel);

        clickMeButton.addClickHandler(clickHandler);
    }

    @Override
    public void clickButton() {
        clickMeButton.click();


    }

    @Override
    protected void placeInPanel(Panel panel) {
        panel.add(flexTable);

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
