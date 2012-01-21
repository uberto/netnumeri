package com.netnumeri.shared.finance.beans;

import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.asset.Stock;
import com.netnumeri.shared.finance.utils.TestUtils;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class TimeSeriesTest extends TestCase {

    Stock stock;
    TimeSeries timeSeries;

    @Before
    public void setUp() throws Exception {
        try {

            stock =  TestUtils.buildStock();
            timeSeries = stock.getCloseSeries();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetFrequency() throws Exception {

        int frequency = timeSeries.getFrequency();

        assertEquals(FinConstants.FR_DAILY, frequency);

    }

    @Test
    public void testGetNRows() throws Exception {

        assertEquals(timeSeries.getNRows(),1);

    }

    @Test
    public void testGetLowerBoundIndex() throws Exception {

        assertEquals(timeSeries.getLowerBoundIndex(),0);

    }

    @Test
    public void testGetUpperBoundIndex() throws Exception {

        assertEquals(timeSeries.getLowerBoundIndex(),0);

    }

    @Test
    public void testGetStdDev() throws Exception {

        assertEquals(timeSeries.getStandardDeviation(),9.90288889291483);

    }

    @Test
    public void testGetStdErr() throws Exception {

        assertEquals(timeSeries.getStdErr(),0.4792343826377079);

    }


    @Test
    public void testIsValidRow() throws Exception {


        assertTrue(timeSeries.isValidRow(0));

        assertFalse(timeSeries.isValidRow(1));


    }

    @Test
    public void testIsEmpty() throws Exception {

    }

    @Test
    public void testGetFirstIndex() throws Exception {

        assertEquals(timeSeries.getFirstIndex(),0);

    }

    @Test
    public void testGetLastIndex() throws Exception {

        assertEquals(timeSeries.getLastIndex(),999);

    }

    @Test
    public void testGetNData() throws Exception {

        assertEquals(timeSeries.getNData(),427);

    }

    @Test
    public void testGetNumberOfNotNullData() throws Exception {

        assertEquals(timeSeries.getNumberOfNotNullData(0),427);

    }

    @Test
    public void testGetPrevIndex() throws Exception {

        assertEquals(timeSeries.getPrevIndex(499),498);
        assertEquals(timeSeries.getPrevIndex(498),497);
        assertEquals(timeSeries.getPrevIndex(497),496);
        assertEquals(timeSeries.getPrevIndex(496),495);
        assertEquals(timeSeries.getPrevIndex(495),492);
        assertEquals(timeSeries.getPrevIndex(494),492);
        assertEquals(timeSeries.getPrevIndex(493),492);
        assertEquals(timeSeries.getPrevIndex(492),491);

    }

    @Test
    public void testGetPrevDate() throws Exception {

        assertEquals(timeSeries.getLastDate(),new TDay("26/4/2010"));

    }

    @Test
    public void testGetPrevData() throws Exception {

    }

    @Test
    public void testGetNextIndex() throws Exception {

    }

    @Test
    public void testGetNextDate() throws Exception {

    }

    @Test
    public void testGetNextData() throws Exception {

    }

    @Test
    public void testGetIndex() throws Exception {

    }

    @Test
    public void testSubtractFreq() throws Exception {

    }

    @Test
    public void testDiffDays() throws Exception {

    }

    @Test
    public void testGetDate() throws Exception {

    }

    @Test
    public void testAddFreq() throws Exception {

    }

    @Test
    public void testSet() throws Exception {

    }

    @Test
    public void testAdd() throws Exception {

    }

    @Test
    public void testGetTimeSeries() throws Exception {

    }

    @Test
    public void testGetAccumulatedSeries() throws Exception {

    }

    @Test
    public void testGetDiffSeries() throws Exception {

    }

    @Test
    public void testGetShiftedSeries() throws Exception {

    }

    @Test
    public void testGetFirstData() throws Exception {

    }

    @Test
    public void testGetLastValidData() throws Exception {

    }

    @Test
    public void testGetLastData() throws Exception {

    }

    @Test
    public void testGetNZero() throws Exception {

    }

    @Test
    public void testGetNNonZero() throws Exception {

    }

    @Test
    public void testGetNPositive() throws Exception {

    }

    @Test
    public void testGetNNegative() throws Exception {

    }

    @Test
    public void testGetMin() throws Exception {

    }

    @Test
    public void testGetMax() throws Exception {

    }

    @Test
    public void testGetMean() throws Exception {

    }

    @Test
    public void testGetVariance() throws Exception {

    }

    @Test
    public void testGetStandardDeviation() throws Exception {

    }

    @Test
    public void testGetError() throws Exception {

    }

    @Test
    public void testGetMomentum() throws Exception {

    }

    @Test
    public void testGetAsimmetry() throws Exception {

    }

    @Test
    public void testGetExcess() throws Exception {

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
    public void testGetAutoCovariance() throws Exception {

    }

    @Test
    public void testGetAutoCorrelation() throws Exception {

    }

    @Test
    public void testGetReturnSeries() throws Exception {

    }

    @Test
    public void testGetLogReturnSeries() throws Exception {

    }

    @Test
    public void testGetNCrossings() throws Exception {

    }

    @Test
    public void testLinearMapping() throws Exception {

    }

    @Test
    public void testSMA() throws Exception {

    }

    @Test
    public void testLag() throws Exception {

    }

    @Test
    public void testLead() throws Exception {

    }

    @Test
    public void testPlus() throws Exception {

    }

    @Test
    public void testMinus() throws Exception {

    }

    @Test
    public void testTimes() throws Exception {

    }

    @Test
    public void testDivided() throws Exception {

    }

    @Test
    public void testGetData() throws Exception {

    }

    @Test
    public void testGetStandardError() throws Exception {

    }

    @Test
    public void testGetAsymmetry() throws Exception {

    }

    @Test
    public void testAsRecipes() throws Exception {

    }

    @Test
    public void testToArray() throws Exception {

    }

    @Test
    public void testToDateValue() throws Exception {

    }

    @Test
    public void testToTimeSeries() throws Exception {

    }

    @Test
    public void testGetLastNData() throws Exception {

    }

    @Test
    public void testCopriLastNData() throws Exception {

    }

    @Test
    public void testGetSize() throws Exception {

    }

    @Test
    public void testSetSize() throws Exception {

    }

    @Test
    public void testGetInitialSize() throws Exception {

    }

    @Test
    public void testSetInitialSize() throws Exception {

    }

    @Test
    public void testGetExpansion() throws Exception {

    }

    @Test
    public void testSetExpansion() throws Exception {

    }

    @Test
    public void testGetDimension() throws Exception {

    }

    @Test
    public void testSetDimension() throws Exception {

    }

    @Test
    public void testGetLengthsArray() throws Exception {

    }

    @Test
    public void testSetLengthsArray() throws Exception {

    }

    @Test
    public void testGetMinimum() throws Exception {

    }

    @Test
    public void testSetMinimum() throws Exception {

    }

    @Test
    public void testGetMaximum() throws Exception {

    }

    @Test
    public void testSetMaximum() throws Exception {

    }

    @Test
    public void testGetCorrelationPairs() throws Exception {

    }

    @Test
    public void testSetCorrelationPairs() throws Exception {

    }
}
