package com.netnumeri.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;


public class MySampleApplicationTest extends AppGwtTestCase {

    public void testOnModuleLoad() throws Exception {
//        app.messageLabel.setText("");
        assertEquals(1, RootPanel.get("slot1").getWidgetCount());
//        app.clickMeButton.click();

        asyncTestValidation(new Timer() {
            public void run() {
//                assertEquals("Client said: \"Hello, World!\"Server answered: \"Hi!\"", app.messageLabel.getText());
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
