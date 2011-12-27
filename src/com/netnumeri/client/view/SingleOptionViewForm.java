package com.netnumeri.client.view;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

public class SingleOptionViewForm extends ViewAbstractRoot implements SingleOptionView{

    @Override
    public void setTitle(String title) {
            RootPanel.get("title-label").getElement().setInnerText(title);
//        flexTable.setTitle(title);

    }

    @Override
    protected void placeInPanel(Panel panel) {

    }
}
