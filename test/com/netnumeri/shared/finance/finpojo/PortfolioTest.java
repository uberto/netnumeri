package com.netnumeri.shared.finance.finpojo;

import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.asset.Stock;
import com.netnumeri.shared.finance.utils.TestUtils;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
public class PortfolioTest extends TestCase {

    Stock stock;
    Portfolio portfolio;

    @Test(expected=PortfolioException.class)
    public void testBuildPortfolio() throws Exception {
        portfolio = new Portfolio();
        PortfolioHelper.invest(portfolio, 1000000);
    }

    @Test
    public void testGetFirstDay() throws Exception {

    }

    @Test
    public void testGetLastDay() throws Exception {

    }

    @Test
    public void testInvest() throws Exception {

    }

    @Test(expected=InstrumentException.class)
    public void testBuyNoPriceForDate() throws Exception {

        portfolio = new Portfolio();
        stock = TestUtils.buildStock();
        PortfolioHelper.buy( portfolio, stock, 1000);

    }


    @Test()
    public void testBuyPriceForDate() throws Exception {

        portfolio = new Portfolio();

        stock = TestUtils.buildStock();

        PortfolioHelper.buy(portfolio, stock, 1000, new TDay("03/11/2009"));

        double value = PortfolioHelper.getValue(portfolio);
        assertEquals(16110.0, value);

        List<PortfolioItem> items = PortfolioHelper.getInstruments(portfolio);
        assertEquals(1, items.size());

        for (int i = 0; i < items.size(); i++) {
            PortfolioItem portfolioItem = items.get(i);
        }

        Instrument instrument = PortfolioHelper.getInstrument(portfolio,0);

        int nDaily = instrument.getNDaily();
        assertEquals(nDaily, 321);

    }

    @Test
    public void testSell() throws Exception {

    }

    @Test
    public void testSellShort() throws Exception {

    }

    @Test
    public void testBuyShort() throws Exception {

    }

    @Test
    public void testGetWeight() throws Exception {

    }

    @Test
    public void testGetPosition() throws Exception {

    }

    @Test
    public void testGetAmount() throws Exception {

    }

    @Test
    public void testGetWealth() throws Exception {

    }

    @Test
    public void testGetValue() throws Exception {

    }

    @Test
    public void testGetReturn() throws Exception {

    }

    @Test
    public void testGetLogReturn() throws Exception {

    }

    @Test
    public void testLogReturnSeries() throws Exception {

    }

    @Test
    public void testExpectedReturn() throws Exception {

    }

    @Test
    public void testLowerBound() throws Exception {

    }

    @Test
    public void testUpperBound() throws Exception {

    }

    @Test
    public void testCovarianceMatrix() throws Exception {

    }

    @Test
    public void testCorrelationMatrix() throws Exception {

    }
}
