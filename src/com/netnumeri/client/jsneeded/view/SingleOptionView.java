package com.netnumeri.client.jsneeded.view;

import com.google.gwt.event.dom.client.ClickHandler;

public interface SingleOptionView extends View {

    void showNew();

    void showEdit(Object entity);

    void addButtonsHandlers(ClickHandler submitHandler, ClickHandler cancelHandler);
}
