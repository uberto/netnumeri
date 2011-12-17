package com.netnumeri.client.view;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.netnumeri.client.AppGwtTestCase;

public class BugListViewTest extends AppGwtTestCase {

    private BugListView bugList;

    @Override
    public void gwtSetUp() throws Exception {
        super.gwtSetUp();
        bugList = new BugListViewFlexTable();
    }


    public void testPrepareEmptyListAtStart() throws Exception {
        bugList.show();

        asyncTestValidation(new Timer() {
            public void run() {

                assertEquals(1, RootPanel.get("wrapper").getWidgetCount());
                assertEquals(1, RootPanel.get("wrapper").getWidgetCount());
//                assertEquals(3, app.get.getRowCount());
//                assertEquals(4, app.bugListview.getCellCount(0));
//                assertEquals(4, app.bugListview.getCellCount(1));

                assertEquals("Active bugs", RootPanel.get("title-label").getElement().getInnerText());
                finishTest();
            }
        });


    }



    public void testDisplayMessageFromServer() throws Exception {


        bugList.show();

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
