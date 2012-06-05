package com.netnumeri.shared.finance.finpojo;

import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.asset.Stock;
import com.netnumeri.shared.finance.math.PortfolioMath;
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

    @Override
    protected  void setUp(){
        try {
            portfolio = new Portfolio();
            stock = TestUtils.buildStock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBuildPortfolio() throws Exception {

        PortfolioMath.invest(portfolio, 1000000);

    }

    @Test
    public void testGetFirstDay() throws Exception {

        PortfolioMath.buy(portfolio, stock, 1000, new TDay("03/11/2009"));

        List<PortfolioItem> items = portfolio.items;
        for (int i = 0; i < items.size(); i++) {
            PortfolioItem portfolioItem = items.get(i);
            TDay firstDay = portfolioItem.getInstrument().getFirstDay();
            System.out.println("firstDay = " + firstDay);

            assertTrue(firstDay.isEqual(new TDay("1/2/2008")));
        }

    }

    @Test
    public void testGetLastDay() throws Exception {

    }

    @Test
    public void testInvest() throws Exception {

        PortfolioMath.add(portfolio, stock);

        PortfolioMath.invest(portfolio, 1000000, new TDay("03/11/2009"));

        double value = PortfolioMath.getWealth(portfolio,new TDay("03/11/2009") );

        System.out.println("value = " + value);


    }

    @Test(expected=InstrumentException.class)
    public void testBuyNoPriceForDate() throws Exception {

        PortfolioMath.buy(portfolio, stock, 1000);

    }

    @Test()
    public void testBuyPriceForDate() throws Exception {

        PortfolioMath.buy(portfolio, stock, 1000, new TDay("03/11/2009"));

        List<PortfolioItem> instruments = PortfolioMath.getInstruments(portfolio);

        assertEquals(1, instruments.size());

        double value = PortfolioMath.getValue(portfolio);
        assertEquals(16110.0, value);

        List<PortfolioItem> items = PortfolioMath.getInstruments(portfolio);
        assertEquals(1, items.size());

        Instrument instrument = PortfolioMath.getInstrument(portfolio, 0);

        int nDaily = instrument.getNDaily();
        assertEquals(nDaily, 321);

    }

    @Test
    public void testSell() throws Exception {

        PortfolioMath.buy(portfolio, stock, 1000, new TDay("03/11/2009"));
        PortfolioMath.sell(portfolio, stock, 1000,new TDay("03/11/2009"));

        List<PortfolioItem> instruments = PortfolioMath.getInstruments(portfolio);

        assertEquals(0, instruments.size());

    }

    @Test
    public void testSellShort() throws Exception {

        portfolio = new Portfolio();

        stock = TestUtils.buildStock();

        PortfolioMath.sellShort(portfolio, stock, 1000, new TDay("03/11/2009"));

        List<PortfolioItem> instruments = PortfolioMath.getInstruments(portfolio);

        assertEquals(1, instruments.size());

    }

    @Test
    public void testBuyShort() throws Exception {

        PortfolioMath.sellShort(portfolio, stock, 1000,new TDay("03/11/2009"));

        List<PortfolioItem> instruments = PortfolioMath.getInstruments(portfolio);

        assertEquals(1, instruments.size());

        PortfolioMath.buyShort(portfolio, stock, 1000, new TDay("03/11/2009"));

        assertEquals(0, instruments.size());

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
