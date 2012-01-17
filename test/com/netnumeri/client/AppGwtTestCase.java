package com.netnumeri.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class AppGwtTestCase extends GWTTestCase {


    protected NetNumeriApplication app;

    public AppGwtTestCase() {
    }

    @Override
    protected void gwtSetUp() throws Exception {
        super.gwtSetUp();    //To change body of overridden methods use File | Settings | File Templates.

        Element bodyElem = RootPanel.getBodyElement();

        assertNotNull(bodyElem);

        String html = "\n" +
                "<h1 id=\"title-label\">Sample Application</h1>\n" +
                "\n" +
                "\n" +
                "<table align=\"center\">\n" +
                "    <tr>\n" +
                "        <td id=\"slot1\"></td>\n" +
                "        <td id=\"slot2\"></td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "\n" +
                "<div id=\"wrapper\"></div>";
        bodyElem.setInnerHTML(html);

        assertNotNull(RootPanel.get("slot1"));

        app = new NetNumeriApplication();
        assertNotNull(app);
        app.onModuleLoad();

    }

    public void asyncTestValidation(Timer timer) {
        // Set a delay period significantly longer than the
        // event is expected to take.
        delayTestFinish(500);

        // Schedule the event and return control to the test system.
        timer.schedule(200);
    }

    @Override
    public String getModuleName() {
        return "com.netnumeri.MySampleApplication";
    }
}
