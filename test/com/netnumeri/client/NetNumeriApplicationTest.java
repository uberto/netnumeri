package com.netnumeri.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.netnumeri.client.presenter.OptionListPresenter;
import com.netnumeri.client.presenter.SingleOptionPresenter;
import com.netnumeri.client.view.OptionListView;


public class NetNumeriApplicationTest extends AppGwtTestCase {

    public void testLoadPresenterOnModuleLoad() throws Exception {
        assertEquals(app.getCurrentPresenter().getClass(), OptionListPresenter.class);
        OptionListPresenter presenter = (OptionListPresenter) app.getCurrentPresenter();


        final OptionListView view = presenter.getView();
        view.clickButton();

        asyncTestValidation(new Timer() {
            public void run() {
                assertEquals(1, RootPanel.get("slot1").getWidgetCount());

                assertEquals("Client said: \"Hello, World!\"<br></br>Server answered: \"Hi!\"", view.getMessageText());

                String div = RootPanel.get("slot2").getElement().getInnerHTML();
//            ???    assertEquals("Client said: \"Hello, World!\"<br></br>Server answered: \"Hi!\"", div);


                finishTest();
            }
        });

    }

    public void testSwitchPresenter() throws Exception {
        app.moveToPlace("option");

        assertEquals(app.getCurrentPresenter().getClass(), SingleOptionPresenter.class);
        SingleOptionPresenter presenter = (SingleOptionPresenter) app.getCurrentPresenter();

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
