package com.netnumeri.server.utils;

import junit.framework.TestCase;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.junit.Test;

public class YahooOptionsTest extends TestCase {

    OptionsDocuments screen;

    @Override
    protected  void setUp(){
        try {
            screen =  YahooOptions.yahooScreenScraper("SSRI");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetStockOptionChain() throws Exception {

        Document document = DocumentHelper.createDocument();

        System.out.println("screen = " + screen.callsDocument.toString());

        System.out.println("puts = " + screen.putsDocument.toString());

    }
}
