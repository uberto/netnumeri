package com.netnumeri.client.jsneeded.view;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class ViewAbstractWrapper implements View {

    protected abstract void placeInPanel(Panel panel);

    protected void show() {

        RootPanel w = RootPanel.get("wrapper");
        if (w != null) {
            w.clear();
            placeInPanel(w);
        }
        
    }

    @Override
    public void alert(String message) {
        Window.alert(message);
    }
}
