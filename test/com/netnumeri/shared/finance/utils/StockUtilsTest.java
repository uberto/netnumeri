package com.netnumeri.shared.finance.utils;

import com.netnumeri.shared.finance.beans.TimeSeries;
import com.netnumeri.shared.finance.finpojo.asset.Stock;
import junit.framework.TestCase;
import org.junit.Test;

public class StockUtilsTest extends TestCase {

    Stock ssri;

    @Override
    protected  void setUp(){
        try {
            ssri =  TestUtils.buildStock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetClose() throws Exception {
        double close = ssri.getClose();
        assertEquals(16.11, close);
    }

    @Test
    public void testGetCloseSeries() throws Exception {
        TimeSeries closeSeries = ssri.getCloseSeries();
        int numberOfData = closeSeries.getNData();
        assertEquals(427,numberOfData);
    }

}
