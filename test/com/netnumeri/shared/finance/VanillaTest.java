package com.netnumeri.shared.finance;


import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Vanilla;
import junit.framework.TestCase;
import org.junit.Test;

public class VanillaTest   extends TestCase {

    @Test
    public void testVanilla() {

        Vanilla v = new Vanilla();
        v.setDirection(FinConstants.kCall);
        v.setPremium(100);
        v.setInterestRate(0.5);
        v.setStrike(110);
        v.setExpiration(new TDay("10/10/2010"));

        assertEquals(v.getPremium(), 100.0);
        assertEquals(v.getDirection(), FinConstants.kCall);

        double bsPrice = v.modelPrice(FinConstants.BlackScholes);
        System.out.println("bsPrice = " + bsPrice);

    }
}
