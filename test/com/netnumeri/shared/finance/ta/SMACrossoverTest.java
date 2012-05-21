package com.netnumeri.shared.finance.ta;

import com.netnumeri.shared.finance.beans.TimeSeries;
import com.netnumeri.shared.finance.data.Transaction;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.Instrument;
import com.netnumeri.shared.finance.finpojo.Portfolio;
import com.netnumeri.shared.finance.finpojo.PortfolioUtils;
import com.netnumeri.shared.finance.utils.YahooUtils;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;

public class SMACrossoverTest {

    @Test
    public void testStrategy() throws IOException, ParseException {

            Portfolio portfolio = new Portfolio("SMA crossing", 10000);
            TDay da = new TDay("1/1/2007");
            TDay a = new TDay();

            Instrument stock = YahooUtils.downloadYahooData("AAPL", da, a);
            PortfolioUtils.add(portfolio, stock);
    //        FinGraph candle = stock.getGraph(2000, 600, 1), "/home/antonio/graphs/candle-" + stock.getName() + ".gif";

    //        GraphicsUtils.toGIF(stock.getGraph(2000, 600, 1), "/home/antonio/graphs/candle-" + stock.getName() + ".gif");

    //        FinGraph candle = stock.getGraph(2000, 600, 1);

            int sma1 = 5;
            int sma2 = 14;

            Strategy strategy = new SMACrossover("test", portfolio, da, a, 10000, sma1, sma2);

            TDay index = strategy.getFirstDate();
            TDay lastDate = strategy.getLastDate();
            while (index.isLess(lastDate)) {
                strategy.evaluatePortfolioOnDate(index, null);
                index = index.nextDay();
            }

            for (int j = 0; j < strategy.getTransactionSeries().getNTransactions(); j++) {
                Transaction t = strategy.getTransactionSeries().getTransaction(j);
                System.out.println("transaction date = " + t.getDate());
            }

            TimeSeries closes = stock.getCloseSeries();
//            GraphicsUtils.toGIF(TimeSeriesUtils.getGraph(closes, 800, 600, 1), "/home/antonio/graphs/" + stock.getName() + "close.gif");

            Indicator sma1g = new Indicator(closes, "SMA-" + sma1, sma1, -1, -1, -1, -1);
//            GraphicsUtils.toGIF(TimeSeriesUtils.getGraph(sma1g, 800, 600, 1), "/home/antonio/graphs/" + stock.getName() + "SMA-" + sma1 + ".gif");

            Indicator sma2g = new Indicator(closes, "SMA-" + sma2, sma2, -1, -1, -1, -1);
//            GraphicsUtils.toGIF(TimeSeriesUtils.getGraph(sma2g, 800, 600, 1), "/home/antonio/graphs/" + stock.getName() + "SMA-" + sma2 + ".gif");

//            TimeSerieGraph sma1graph = (TimeSerieGraph) TimeSeriesUtils.getGraph(sma1g, 2000, 600, 1);
//            TimeSerieGraph sma2graph = (TimeSerieGraph) TimeSeriesUtils.getGraph(sma2g, 2000, 600, 1);
//            FinGraph timeserie = TimeSeriesUtils.getGraph(closes, 0, 2000, 600, 1, false, strategy.getTransactionSerie());
//            Vector<FinGraph> v = new Vector<FinGraph>();
//            v.add(timeserie);
//            v.add(sma1graph);
//            v.add(sma2graph);
//            TimeSerieGraph maGraph = GraphicsUtils.mergeGraphs("SMA Crossover", v, 2000, 600, 4);
//            GraphicsUtils.toGIF(maGraph, "/home/antonio/graphs/" + stock.getName() + ".gif");

//            TimeSerieGraph maGraphWithSignals = GraphicsUtils.mergeGraphsWithSignals("SSA", v, 2000, 600, 4, strategy.getTransactionSerie());
//            GraphicsUtils.toGIF(maGraphWithSignals, "/home/antonio/graphs/" + stock.getName() + "Withsignals.gif");

            PortfolioUtils.clear(portfolio);
            PortfolioUtils.add(portfolio, stock);

            StrategyBacktest trader = new StrategyBacktest(strategy.getTransactionSeries(), portfolio, 100000);
            double value = trader.test();
//            StrategyBacktestUtils.generateCharts(trader);
            try {
                System.out.println(trader.accountWealth);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }


}
