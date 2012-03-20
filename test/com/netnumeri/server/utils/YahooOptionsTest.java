package com.netnumeri.server.utils;

import junit.framework.TestCase;
import org.junit.Test;

public class YahooOptionsTest extends TestCase {

    OptionsRows screen;

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


        System.out.println("screen = " + screen.callsDocument.toString());

        System.out.println("puts = " + screen.putsDocument.toString());

    }
}
