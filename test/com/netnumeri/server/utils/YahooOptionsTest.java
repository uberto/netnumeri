package com.netnumeri.server.utils;

import junit.framework.TestCase;
import org.junit.Test;
import org.w3c.dom.Document;

public class YahooOptionsTest extends TestCase {

    String screen;

    @Override
    protected  void setUp(){
        try {
            screen =  YahooOptions.getScreen("SSRI");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetStockOptionChain() throws Exception {


        System.out.println("screen = " + screen);
        Document doc = XML.stringToDocument(screen);
    }
}
