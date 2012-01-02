package com.netnumeri.client.view;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.netnumeri.client.AppGwtTestCase;

public class OptionListViewTest extends AppGwtTestCase {

    private OptionListView listView;

    @Override
    public void gwtSetUp() throws Exception {
        super.gwtSetUp();
        listView = new OptionListViewFlexTable();
    }


    public void testPrepareEmptyListAtStart() throws Exception {

        listView.show();
        listView.setTitle("title");

        asyncTestValidation(new Timer() {
            public void run() {

                assertEquals(1, RootPanel.get("wrapper").getWidgetCount());
//                assertEquals(3, app.get.getRowCount());
//                assertEquals(4, app.bugListview.getCellCount(0));
//                assertEquals(4, app.bugListview.getCellCount(1));

                assertEquals("title", RootPanel.get("title-label").getElement().getInnerText());
                finishTest();
            }
        });


    }



    public void testDisplayMessageFromServer() throws Exception {


        listView.show();

        asyncTestValidation(new Timer() {
            public void run() {


                assertEquals(1, RootPanel.get("wrapper").getWidgetCount());
//                assertEquals(3, app.bugListview.getRowCount());
//                app.bugListview.clearBugGrid();
//                assertEquals(1, app.bugListview.getRowCount());
//
//                app.bugListview.addBug(1, "aa", BugEnum.REJECTED, "u");
//                assertEquals(2, app.bugListview.getRowCount());
                finishTest();
            }
        });

    }


}
