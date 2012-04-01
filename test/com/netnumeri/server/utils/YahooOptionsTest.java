package com.netnumeri.server.utils;

import junit.framework.TestCase;
import org.dom4j.Document;
import org.junit.Test;

public class YahooOptionsTest extends TestCase {

    OptionsDocuments screen;

    @Override
    protected  void setUp(){
        try {
            screen =  YahooOptions.getOptionsDocuments("SSRI");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetStockOptionChain() throws Exception {

        Document callsDocument = screen.callsDocument;

        System.out.println("screen = " + callsDocument.asXML());

        Document putsDocument = screen.putsDocument;

        System.out.println("putsDocument.asXML() = " + putsDocument.asXML());



    }
}
