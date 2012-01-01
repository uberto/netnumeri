package com.netnumeri.shared.finance.utils;

import com.netnumeri.shared.finance.finpojo.asset.Stock;
import junit.framework.TestCase;
import org.junit.Test;

public class StockUtilsTest extends TestCase {

    Stock stock;

    @Override
    protected  void setUp(){
        try {
            stock =  TestUtils.buildStock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetCloseSeries() throws Exception {

    }

}
