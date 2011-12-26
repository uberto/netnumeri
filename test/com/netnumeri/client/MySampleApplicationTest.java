package com.netnumeri.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.netnumeri.client.presenter.BugListPresenter;
import com.netnumeri.client.presenter.PresenterWithView;
import com.netnumeri.client.view.BugListView;


public class MySampleApplicationTest extends AppGwtTestCase {

    public void testOnModuleLoad() throws Exception {
        PresenterWithView presenter = app.getCurrentPresenter();
        assertEquals(presenter.getClass(), BugListPresenter.class);
        BugListPresenter bugListPresenter = (BugListPresenter) presenter;

        final BugListView view = bugListPresenter.getView();
        view.setMessageText("");
        assertEquals(1, RootPanel.get("slot1").getWidgetCount());
        view.clickButton();

        asyncTestValidation(new Timer() {
            public void run() {
                assertEquals("Client said: \"Hello, World!\"<br></br>Server answered: \"Hi!\"", view.getMessageText());
                finishTest();
            }
        });


    }

    public void testOnModuleLoadFail() throws Exception {
//        app.messageLabel.setText("");
//        app.message = null;
//        app.clickMeButton.click();


        asyncTestValidation(new Timer() {
            public void run() {
//                assertEquals("Failed to receive answer from server!", app.messageLabel.getText());
                finishTest();
            }
        });


    }



    public void testOnDoubleClick() throws Exception {
//        app.messageLabel.setText("");
//        app.clickMeButton.click();
//        app.bugList.getView().clickButton();

        asyncTestValidation(new Timer() {
            public void run() {
//                assertEquals("Client said: \"Hello, World!\"Server answered: \"Hi!\"", app.messageLabel.getText());
//                app.clickMeButton.click();

                asyncTestValidation(new Timer() {
                    public void run() {
                        assertEquals("", app.bugList.getView().getMessageText());
                        finishTest();
                    }
                });
            }
        }

        );


    }



}
