package com.netnumeri.client.jsneeded.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.netnumeri.client.jsneeded.widget.HeadersFlexTable;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Option;

public class OptionListViewFlexTable extends ViewAbstractWrapper implements OptionListView {

    HeadersFlexTable flexTable = new HeadersFlexTable();
    private Button clickMeButton = new Button("Click me");
    private Label messageLabel = new Label();

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
        flexTable.addHeaders("Name", "Stock", "Strike", "Ask", "Bid", "Type", "Due to");
    }


    @Override
    public void addOption(Option option) {

//        if (option != null){
//        System.out.println("option.underlying = " + option.underlying);
//        System.out.println("option.strike = " + option.strike);
//        System.out.println("option.ask = " + option.ask);
//        System.out.println("option.bid = " + option.bid);
//        System.out.println("option.type = " + option.type);
//        System.out.println("option.expiry = " + option.expiry);

        flexTable.addRow(option.name.get()

//                (option.underlying != null)?option.underlying.get():"n/a",
//
//                (option.strike != null)?option.strike.get().toString():"n/a",
//
//                (option.ask != null)?option.ask.get().toString():"n/a",
//
//                (option.bid != null)?option.bid.get().toString():"n/a",
//
//                (option.type != null)?option.type.get().toString():"n/a",
//
//                (option.expiry != null)?option.expiry.get().toString():"n/a"

        );
    }

    @Override
    public void showGrid() {

        setTitle("Options Portfolio");
        show();

    }


    public void clearGrid() {

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
