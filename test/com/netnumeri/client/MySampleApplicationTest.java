package com.netnumeri.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.netnumeri.client.presenter.OptionListPresenter;
import com.netnumeri.client.presenter.PresenterWithView;
import com.netnumeri.client.view.OptionListView;


public class MySampleApplicationTest extends AppGwtTestCase {

    public void testOnModuleLoad() throws Exception {
        PresenterWithView presenter = app.getCurrentPresenter();
        assertEquals(presenter.getClass(), OptionListPresenter.class);
        OptionListPresenter bugListPresenter = (OptionListPresenter) presenter;

        final OptionListView view = bugListPresenter.getView();
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

    public void ignore_testOnModuleLoadFail() throws Exception {
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



    public void ignore_testOnDoubleClick() throws Exception {
//        app.messageLabel.setText("");
//        app.clickMeButton.click();
//        app.bugList.getView().clickButton();

        asyncTestValidation(new Timer() {
            public void run() {
//                assertEquals("Client said: \"Hello, World!\"Server answered: \"Hi!\"", app.messageLabel.getText());
//                app.clickMeButton.click();

                asyncTestValidation(new Timer() {
                    public void run() {
//                        assertEquals("", app.getCurrentPresenter().getView().getMessageText());
                        finishTest();
                    }
                });
            }
        }

        );


    }



}
