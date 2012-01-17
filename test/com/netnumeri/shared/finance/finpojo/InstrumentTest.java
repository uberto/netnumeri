package com.netnumeri.shared.finance.finpojo;

import com.netnumeri.shared.finance.beans.TimeSeries;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.asset.Stock;
import com.netnumeri.shared.finance.utils.TestUtils;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

public class InstrumentTest extends TestCase {

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
    public void testModelPrice() throws Exception {

        double v = stock.modelPrice(0);

        assertEquals(v, 0.0);
    }

    @Test
    public void testGetDailyarray() throws Exception {

        TreeMap<TDay,Daily> dailyarray = stock.getDailyarray();

        Map.Entry<TDay, Daily> tDayDailyEntry = dailyarray.lastEntry();

        TDay key = tDayDailyEntry.getKey();

        // 2009-04-09
        assertEquals("9/4/2009", key.toString());

        Daily daily = tDayDailyEntry.getValue();
        assertEquals(daily.getOpenprice(),16.35);
        assertEquals(daily.getHigh(),16.40);
        assertEquals(daily.getLow(),16.03);
        assertEquals(daily.getCloseprice(),16.11);
        assertEquals(daily.getVolume(),863300);


        // "2007-08-01,34.09,34.56,33.04,33.87,894200,33.87";
        tDayDailyEntry = dailyarray.firstEntry();
        key = tDayDailyEntry.getKey();
        assertEquals("1/8/2007", key.toString());

        daily = tDayDailyEntry.getValue();
        assertEquals(daily.getOpenprice(),34.09);
        assertEquals(daily.getHigh(),34.56);
        assertEquals(daily.getLow(),33.04);
        assertEquals(daily.getCloseprice(),33.87);
        assertEquals(daily.getVolume(),894200);
    }

    @Test
    public void testTempToday() throws Exception {

        TDay tDay = stock.getFirstDate();
        stock.setTempToday(tDay);
        assertEquals(tDay, stock.tempToday());

    }

    @Test
    public void testGetTempSpot() throws Exception {

        stock.setTempSpot(1.0);
        assertEquals(1.0, stock.getTempSpot());

    }

    @Test
    public void testGetTempVolatility() throws Exception {

        stock.setTempVolatility(1.0);
        assertEquals(1.0, stock.getTempVolatility());

        stock.setTempVolatilityFixed(true);
        assertEquals(true, stock.isTempVolatilityFixed());

    }

    @Test
    public void testIsTempIsSpotFixed() throws Exception {

        stock.setTempSpot(1.0);
        assertEquals(1.0, stock.getTempSpot());

    }

    @Test
    public void testIsVolatilityFixed() throws Exception {

         stock.setVolatility(1.0);
         assertEquals(1.0, stock.getVolatility());

         stock.setVolatilityFixed(true);
         assertEquals(true, stock.isVolatilityFixed());

    }


    @Test
    public void testGetFixedSpot() throws Exception {

        stock.setSpot(1.0);

        assertEquals(1.0, stock.spot());

    }

    @Test
    public void testS() throws Exception {

        stock.setSpot(1.0);
        assertEquals(1.0, stock.S());

        stock.setSpotFixed(true);
        assertEquals(true, stock.isSpotFixed());

    }

    @Test
    public void testHistoricalSpot() throws Exception {

        double historicalSpot = stock.getHistoricalSpot(new TDay("4/9/2009"));

        assertEquals(16.18, historicalSpot);


    }

    @Test
    public void testGetHistoricalSpot() throws Exception {

    }

    @Test
    public void testHistoricalVolatility() throws Exception {

    }

    @Test
    public void testGetHistoricalVolatility() throws Exception {

        double historicalVolatility = stock.getHistoricalVolatility();

        assertEquals (historicalVolatility,1.0567523108414614);
    }

    @Test
    public void testVolatility() throws Exception {

    }

    @Test
    public void testSetSpot() throws Exception {

    }

    @Test
    public void testSetVolatility() throws Exception {

    }

    @Test
    public void testResetSpot() throws Exception {

    }

    @Test
    public void testResetVolatility() throws Exception {

    }

    @Test
    public void testStoreSettings() throws Exception {

    }

    @Test
    public void testRestoreSettings() throws Exception {

    }

    @Test
    public void testAddDaily() throws Exception {

    }

    @Test
    public void testIsDataAvailable() throws Exception {

    }

    @Test
    public void testGetFirstIndex() throws Exception {

    }

    @Test
    public void testGetLastIndex() throws Exception {

    }

    @Test
    public void testGetDaily() throws Exception {

    }

    @Test
    public void testGetNextIndex() throws Exception {

    }

    @Test
    public void testGetIndex() throws Exception {

    }

    @Test
    public void testGetDate() throws Exception {

    }

    @Test
    public void testPremium() throws Exception {

    }

    @Test
    public void testGetPrice() throws Exception {

    }

    @Test
    public void testGetLow() throws Exception {

    }

    @Test
    public void testGetHigh() throws Exception {

    }

    @Test
    public void testGetOpen() throws Exception {

    }

    @Test
    public void testGetClose() throws Exception {

        double close = stock.getClose();
        assertEquals(16.11, close);

    }

    @Test
    public void testGetReturn() throws Exception {

    }

    @Test
    public void testGetLogReturn() throws Exception {

    }

    @Test
    public void testGetLast() throws Exception {

    }

    @Test
    public void testGetPriceSeries() throws Exception {

    }

    @Test
    public void testGetSeries() throws Exception {

    }

    @Test
    public void testGetHighSeries() throws Exception {

    }

    @Test
    public void testGetLowSeries() throws Exception {

    }

    @Test
    public void testGetOpenSeries() throws Exception {

    }

    @Test
    public void testGetCloseSeries() throws Exception {

        TimeSeries closeSeries = stock.getCloseSeries();
        int numberOfData = closeSeries.getNData();
        assertEquals(427,numberOfData);
    }

    @Test
    public void testGetVolumeSeries() throws Exception {

    }

    @Test
    public void testReturnSeries() throws Exception {

    }

    @Test
    public void testLogReturnSeries() throws Exception {

    }

    @Test
    public void testGetVolumeReturnSeries() throws Exception {

    }

    @Test
    public void testGetVolumeLogReturnSeries() throws Exception {

    }

    @Test
    public void testExpectedReturn() throws Exception {

    }

    @Test
    public void testGetExpectedReturn() throws Exception {

    }

    @Test
    public void testVariance() throws Exception {

    }

    @Test
    public void testGetVariance() throws Exception {

    }

    @Test
    public void testStandardDeviation() throws Exception {

    }

    @Test
    public void testGetStandardDeviation() throws Exception {

    }

    @Test
    public void testAnnualExpectedReturn() throws Exception {

    }

    @Test
    public void testAnnualVariance() throws Exception {

    }

    @Test
    public void testAnnualStandardDeviation() throws Exception {

    }

    @Test
    public void testGetCovariance() throws Exception {

    }

    @Test
    public void testGetCorrelation() throws Exception {

    }

    @Test
    public void testGetNCorrelationPairs() throws Exception {

    }

    @Test
    public void testIsDailyData() throws Exception {

    }


    @Test
    public void testGetFirstDay() throws Exception {

    }

    @Test
    public void testGetLastDay() throws Exception {

    }

    @Test
    public void testGetNDaily() throws Exception {

    }

    @Test
    public void testGetPrevIndex() throws Exception {

    }

    @Test
    public void testGetPrevDate() throws Exception {

    }

    @Test
    public void testGetNextDate() throws Exception {

    }

    @Test
    public void testGetPrevDaily() throws Exception {

    }

    @Test
    public void testGetNextDaily() throws Exception {

    }

    @Test
    public void testSetMarketSpotShift() throws Exception {

    }

    @Test
    public void testSetMarketVolatilitySpotShift() throws Exception {

    }

    @Test
    public void testGetMarketSpotShift() throws Exception {

    }

    @Test
    public void testGetMarketVolatilityShift() throws Exception {

    }

    @Test
    public void testSetSpotFixed() throws Exception {

    }

    @Test
    public void testSetVolatilityFixed() throws Exception {

    }

    @Test
    public void testSetLastPrice() throws Exception {

    }

    @Test
    public void testSetLastSize() throws Exception {

    }

    @Test
    public void testSetLastAsk() throws Exception {

    }

    @Test
    public void testSetLastBid() throws Exception {

    }

    @Test
    public void testSetLastAskSize() throws Exception {

    }

    @Test
    public void testSetLastBidSize() throws Exception {

    }

    @Test
    public void testSetPriceSeries() throws Exception {

    }

    @Test
    public void testSetReturnSeries() throws Exception {

    }

    @Test
    public void testSetLogReturnSeries() throws Exception {

    }

    @Test
    public void testSetHighSeries() throws Exception {

    }

    @Test
    public void testSetLowSeries() throws Exception {

    }

    @Test
    public void testSetOpenSeries() throws Exception {

    }

    @Test
    public void testSetCloseSeries() throws Exception {

    }

    @Test
    public void testSetVolumeSeries() throws Exception {

    }

    @Test
    public void testSetVolumeReturnSeries() throws Exception {

    }

    @Test
    public void testSetVolumeLogReturnSeries() throws Exception {

    }

    @Test
    public void testSetMarketVolatilityShift() throws Exception {

    }

    @Test
    public void testFirstDate() throws Exception {

    }

    @Test
    public void testLastDate() throws Exception {

    }

    @Test
    public void testDataAvailable() throws Exception {

    }

    @Test
    public void testPrevIndex() throws Exception {

    }

    @Test
    public void testNextIndex() throws Exception {

    }

    @Test
    public void testPrevDate() throws Exception {

    }

    @Test
    public void testNextDate() throws Exception {

    }

    @Test
    public void testPrevDaily() throws Exception {

    }

    @Test
    public void testNextDaily() throws Exception {

    }

    @Test
    public void testDate() throws Exception {

    }

    @Test
    public void testGetVolume() throws Exception {

    }

    @Test
    public void testFreturn() throws Exception {

    }

    @Test
    public void testLogReturn() throws Exception {

    }

    @Test
    public void testPrice() throws Exception {

    }

    @Test
    public void testLow() throws Exception {

    }

    @Test
    public void testHigh() throws Exception {

    }

    @Test
    public void testOpen() throws Exception {

    }

    @Test
    public void testClose() throws Exception {

    }

    @Test
    public void testFReturn() throws Exception {

    }

    @Test
    public void testVolume() throws Exception {

    }

    @Test
    public void testGet() throws Exception {

    }

    @Test
    public void testTimeSeries() throws Exception {

    }

    @Test
    public void testHighSeries() throws Exception {

    }

    @Test
    public void testLowSeries() throws Exception {

    }

    @Test
    public void testOpenSeries() throws Exception {

    }

    @Test
    public void testCloseSeries() throws Exception {

    }

    @Test
    public void testLogAverageSeries() throws Exception {

    }

    @Test
    public void testVolumeSeries() throws Exception {

    }

    @Test
    public void testDailyData() throws Exception {

    }

    @Test
    public void testFirstIndex() throws Exception {

    }

    @Test
    public void testLastIndex() throws Exception {

    }

    @Test
    public void testLenght() throws Exception {

    }

    @Test
    public void testFirstDailyDate() throws Exception {

    }

    @Test
    public void testLastDailyDate() throws Exception {

    }

    @Test
    public void testIndex() throws Exception {

    }

    @Test
    public void testAdd() throws Exception {

    }

    @Test
    public void testDaily() throws Exception {

    }

    @Test
    public void testGetSpot() throws Exception {

    }

    @Test
    public void testGetVolatility() throws Exception {

    }

    @Test
    public void testGetTempToday() throws Exception {

    }

    @Test
    public void testGetLastPrice() throws Exception {

    }

    @Test
    public void testGetLastSize() throws Exception {

    }

    @Test
    public void testGetLastAsk() throws Exception {

    }

    @Test
    public void testGetLastBid() throws Exception {

    }

    @Test
    public void testGetLastAskSize() throws Exception {

    }

    @Test
    public void testGetLastBidSize() throws Exception {

    }

    @Test
    public void testGetReturnSeries() throws Exception {

    }

    @Test
    public void testGetLogReturnSeries() throws Exception {

    }

    @Test
    public void testSetLastPriceDateTime() throws Exception {

    }

    @Test
    public void testSetLastAskDateTime() throws Exception {

    }

    @Test
    public void testSetLastBidDateTime() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {

    }

    @Test
    public void testHashCode() throws Exception {

    }

    @Test
    public void testGetDelta() throws Exception {

    }

    @Test
    public void testSetDelta() throws Exception {

    }
}
