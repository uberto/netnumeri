package com.netnumeri.client.jsneeded.view;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.netnumeri.client.jsneeded.AppGwtTestCase;
import com.netnumeri.shared.entity.OptionType;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Option;

import java.util.Date;

public class SingleOptionViewFormTest extends AppGwtTestCase {
    private SingleOptionViewForm listView;

    @Override
    public void gwtSetUp() throws Exception {
        super.gwtSetUp();
        listView = new SingleOptionViewForm();
    }


    public void testShowNew() throws Exception {

        listView.showNew();

        asyncTestValidation(new Timer() {
            public void run() {

                assertEquals(1, RootPanel.get("wrapper").getWidgetCount());
//                assertEquals(3, app.get.getRowCount());
//                assertEquals(4, app.bugListview.getCellCount(0));
//                assertEquals(4, app.bugListview.getCellCount(1));

                assertEquals("Options Portfolio", RootPanel.get("title-label").getElement().getInnerText());
                finishTest();
            }
        });


    }



    public void testShowEdit() throws Exception {
        TDay    day =   new TDay("4/9/2009");

        Option option = new Option("1", "NasdaqNG", "name", "ticket", OptionType.PUT, 1.1, day);


        listView.showEdit(option);

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
