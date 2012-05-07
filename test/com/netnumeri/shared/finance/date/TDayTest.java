package com.netnumeri.shared.finance.date;

import junit.framework.TestCase;
import org.junit.Test;

public class TDayTest extends TestCase {


    @Test
    public void testIsEqual() throws Exception {

        TDay d1 = new TDay("3/27/2007");
        TDay d2 = new TDay("3/27/2007");
        assertTrue( d1.isEqual(d2));
        assertTrue( d1.isLessEqual(d2));
        assertTrue( d1.isGreaterEqual(d2));

        TDay date            = new TDay("1/7/2008");
        TDay lowerBoundTDay  = new TDay("1/2/2008");
        System.out.println("date = " + date);
        System.out.println("lowerBoundTDay = " + lowerBoundTDay);

        assertTrue(date.isGreaterEqual(lowerBoundTDay));

    }

    @Test
    public void testIsLess() throws Exception {

        TDay d1 = new TDay("3/26/2007");
        TDay d2 = new TDay("3/27/2007");
        assertTrue( d1.isLess(d2));

    }

    @Test
    public void testIsGreater() throws Exception {

        TDay d1 = new TDay("3/28/2007");
        TDay d2 = new TDay("3/27/2007");
        assertTrue( d1.isGreater(d2));

    }



}
