package com.netnumeri.shared.finance.ta;


import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.beans.TimeSeries;
import com.netnumeri.shared.finance.data.Transaction;
import com.netnumeri.shared.finance.data.TransactionCost;
import com.netnumeri.shared.finance.data.TransactionSeries;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.Instrument;
import com.netnumeri.shared.finance.finpojo.Portfolio;
import com.netnumeri.shared.finance.math.PortfolioMath;
import com.netnumeri.shared.finance.finpojo.asset.Asset;
import com.netnumeri.shared.finance.utils.LogUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class StrategyBacktest implements FinConstants, Serializable {

    double initialWealth;          // initial wealth
    double accountWealth;          // wealth on bank account
    double investedWealth;         // wealth in instruments
    double totalWealth;            // total wealth
    public TimeSeries PnLSeries = null;   // P&L    time series
    public TimeSeries wealthSeries;       // wealth time series
    public TimeSeries costSeries;         // cost   time series
    public TimeSeries tradeSeriesTotal;   // Trade Total time series
    public TimeSeries tradeSeriesLong;    // Trade Long time series
    public TimeSeries tradeSeriesShort;   // Trade Short time series
    public TimeSeries tradeDistributionTotal;  // Tradedistribution Total time series
    public TimeSeries tradeDistributionLong;   // Tradedistribution Long time series
    public TimeSeries tradeDistributionShort;  // Tradedistribution Short time series
    public TimeSeries wealthUnderwaterLine;     // Drawdown-Line
    Asset asset;
    public Portfolio portfolio;

    TransactionSeries transactionSerie;  // trade transaction series
    TransactionCost transactionCost;    // transaction cost
    Portfolio benchmarkPortfolio;

    double totalCost;
    public int numberTradesTotal;     // number of trades in all positions
    public int numberTradesLong;      // number of trades in long positions
    public int numberTradesShort;     // number of trades in short positions
    int numberWinTradesTotal;  // number of win trades in all positions
    int numberWinTradesLong;   // number of win trades in long positions
    int numberWinTradesShort;  // number of win trades in short positions
    int numberLossTradesTotal; // number of loss trades in all positions
    int numberLossTradesLong;  // number of loss trades in all positions
    int numberLossTradesShort; // number of loss trades in all positions

    int numberOpenPositionsTotal; // number of all open positions
    int numberOpenPositionsLong;  // number of all long positions
    int numberOpenPositionsShort; // number of all short positions
    long numberTradeDays;
    int numberHoldDays;
    int numberMeanBarsInWinTotal;  // number of average bars in winners total
    int numberMeanBarsInWinLong;  // number of average bars in winners long
    int numberMeanBarsInWinShort;  // number of average bars in winners short
    int numberMeanBarsInLossTotal;  // number of average bars in loosers total
    int numberMeanBarsInLossLong;  // number of average bars in loosers long
    int numberMeanBarsInLossShort;  // number of average bars in loosers short
    double grossProfitTotal; // total amount of wins in all positions
    double grossProfitLong;  // total amount of wins in long positions
    double grossProfitShort; // total amount of wins in short positions
    double grossLossTotal;   // total amount of losses in all positions
    double grossLossLong;    // total amount of losses in long positions
    double grossLossShort;   // total amount of losses in short positions
    double maxWinTotal;      // max win in one trade long or short
    double maxWinLong;       // max win in one trade only long position
    double maxWinShort;      // max win in one trade only short position
    double maxLossTotal;     // max loss in one trade long or short
    double maxLossLong;      // max loss in one trade only long position
    double maxLossShort;     // max loss in one trade only short position
    double openPositionTotal;// amount of all open position
    double openPositionLong;// amount of long open position
    double openPositionShort;// amount of short open position
    double maxIntradayDrawDown;     // max intraday drawdown total
    TDay maxIntradayDrawDownDate; // max intraday drawdown date
    double wealth;

    int firstEntry;
    int lastEntry;
    boolean isTested;
    ArrayList tradeList = new ArrayList();

    public StrategyBacktest(Strategy strategy, double InitialWealth) {
        super();
        initialWealth = InitialWealth;
//        signalSeries = null;
        transactionSerie = strategy.getTransactionSeries();
        init();
    }

//    public StrategyBacktest(SignalSeries signalSeries, double InitialWealth) {
//        super();
//        this.signalSeries = signalSeries;
//        initialWealth = InitialWealth;
//        transactionSerie = null;
//        portfolio = null;
//        asset = signalSeries.getAsset();
//        init();
//    }

    public StrategyBacktest(TransactionSeries transactionSeries, Portfolio initPortfolio, double InitialWealth) {
        super();
        this.initialWealth = InitialWealth;
//        this.signalSeries = null;
        this.transactionSerie = transactionSeries;
        this.portfolio = initPortfolio;
        init();
    }

    public void init() {
        benchmarkPortfolio = null;
        transactionCost = null;

//        if (signalSeries != null) {
//            PnLSeries = new TS("Daily PnL", signalSeries.serie.getFrequency());
//            wealthSeries = new TS("Accumulated wealth", signalSeries.serie.getFrequency());
//            costSeries = new TS("Cost", signalSeries.serie.getFrequency());
//            tradeSeriesTotal = new TS("Total Trades", signalSeries.serie.getFrequency());
//            tradeSeriesLong = new TS("Long Trades", signalSeries.serie.getFrequency());
//            tradeSeriesShort = new TS("Short Trades", signalSeries.serie.getFrequency());
//            tradeDistributionTotal = new TS("Distribution", signalSeries.serie.getFrequency());
//            tradeDistributionLong = new TS("Long Distribution", signalSeries.serie.getFrequency());
//            tradeDistributionShort = new TS("Short Distribution", signalSeries.serie.getFrequency());
//            wealthUnderwaterLine = new TS("Underwater", signalSeries.serie.getFrequency());
//        } else
        {
            PnLSeries = new TimeSeries("Daily PnL");
            wealthSeries = new TimeSeries("Accumulated wealth");
            costSeries = new TimeSeries("Cost");
            tradeSeriesTotal = new TimeSeries("Total Trades");
            tradeSeriesLong = new TimeSeries("Long Trades");
            tradeSeriesShort = new TimeSeries("Short Trades");
            tradeDistributionTotal = new TimeSeries("Distribution");
            tradeDistributionLong = new TimeSeries("Long Distribution");
            tradeDistributionShort = new TimeSeries("Short Distribution");
            wealthUnderwaterLine = new TimeSeries("Underwater");
        }

        numberTradeDays = 0;
        numberHoldDays = 0;
        isTested = false;
    }

    public void getInitialWealth(double InitialWealth) {
        initialWealth = InitialWealth;
    }

    public void setFirstEntry(int FirstEntry) {
        firstEntry = FirstEntry;
    }

    public void setLastEntry(int LastEntry) {
        lastEntry = LastEntry;
    }

    public void setBenchmarkPortfolio(Portfolio benchmarkPortfolio) {
        this.benchmarkPortfolio = benchmarkPortfolio;
    }

    public int getNTradeDays() {
        return (int) numberTradeDays;
    }

    public int getNHoldDays() {
        return numberHoldDays;
    }

    // Number of trades
    public int getNTradesTotal() {
        return numberTradesTotal;
    }

    public int getNTradesLong() {
        return numberTradesLong;
    }

    public int getNTradesShort() {
        return numberTradesShort;
    }

    // Number of winning trades
    public int getNWinTradesTotal() {
        return numberWinTradesTotal;
    }

    public int getNWinTradesLong() {
        return numberWinTradesLong;
    }

    public int getNWinTradesShort() {
        return numberWinTradesShort;
    }

    // Number of loosing trades
    public int getNLossTradesTotal() {
        return numberLossTradesTotal;
    }

    public int getNLossTradesLong() {
        return numberLossTradesLong;
    }

    public int getNLossTradesShort() {
        return numberLossTradesShort;
    }

    public int getNMeanBarsInWinTotal() {
        return numberMeanBarsInWinTotal;
    }

    public int getNMeanBarsInWinLong() {
        return numberMeanBarsInWinLong;
    }

    public int getNMeanBarsInWinShort() {
        return numberMeanBarsInWinShort;
    }

    public int getNMeanBarsInLossTotal() {
        return numberMeanBarsInLossTotal;
    }

    public int getNMeanBarsInLossLong() {
        return numberMeanBarsInLossLong;
    }

    public int getNMeanBarsInLossShort() {
        return numberMeanBarsInLossShort;
    }

    // Number of open positions
    public int getNOpenPositionsTotal() {
        return numberOpenPositionsTotal;
    }

    public int getNOpenPositionsLong() {
        return numberOpenPositionsLong;
    }

    public int getNOpenPositionsShort() {
        return numberOpenPositionsShort;
    }

    public double getInitialWealth() {
        return initialWealth;
    }

    public double getWealth() {
        return wealth;
    }

    public double getWealthGain() {
        return wealth - initialWealth;
    }

    // gross profit
    public double getGrossProfitTotal() {
        return grossProfitTotal;
    }

    public double getGrossProfitLong() {
        return grossProfitLong;
    }

    public double getGrossProfitShort() {
        return grossProfitShort;
    }

    // gross loss
    public double getGrossLossTotal() {
        return grossLossTotal;
    }

    public double getGrossLossLong() {
        return grossLossLong;
    }

    public double getGrossLossShort() {
        return grossLossShort;
    }

    // net profit
    public double getNetProfitTotal() {
        return grossProfitTotal + grossLossTotal;
    }

    public double getNetProfitLong() {
        return grossProfitLong + grossLossLong;
    }

    public double getNetProfitShort() {
        return grossProfitShort + grossLossShort;
    }

    // Amount of the open positions
    public double getOpenPositionsTotal() {
        return openPositionTotal;
    }

    public double getOpenPositionsLong() {
        return openPositionLong;
    }

    public double getOpenPositionsShort() {
        return openPositionShort;
    }

    // maximal drawdown
    public double getMaxIntradayDrawDown() {
        return maxIntradayDrawDown;
    }

    public TDay getMaxIntradayDrawDownDate() {
        return maxIntradayDrawDownDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getFirstEntry() {
        return firstEntry;
    }

    public int getLastEntry() {
        return lastEntry;
    }

    public void setTransactionCost(TransactionCost TransactionCost) {
        transactionCost = TransactionCost;
    }

    public TransactionCost getTransactionCost() {
        return transactionCost;
    }

    public boolean isTested() {
        return isTested;
    }

    public void changed() {
        isTested = false;
    }

    // Construct benchmark portfolio containing one asset,
    // usually an index
    public void setBenchmarkPortfolio(Asset benchmarkAsset) {
        benchmarkPortfolio = new Portfolio("Benchmark");
        PortfolioMath.add(benchmarkPortfolio, benchmarkAsset);
    }

    public double test() {
        return test(null, null, kInvestOnDate);
    }

    // Return result of continuous stock according to
    // trade signalSeries or strategy (transaction series)
    // Transaction       Account       Portfolio
    // BUY                -               +
    // SELL               +               -
    // SELLSHORT          +               -
    // BUYSHORT           -               +
    // plus transaction costs decreasing account
    // Wealth(t) = Account(t) + Portfolio.getPice(t);
    // PnL(t) = Wealth(t) - Wealth(t-1);
    // where Wealth(t) is marked to market at close
    // Wealth(0) = 0;
    // We can evaluatePortfolioOnDate performance using two strategies:
    // 1. Invest all the money on a fixed date
    // 2. Wait until first buy signalSeries and then invest
    public double test(TDay firstDate, TDay lastDate, int option) {
        numberTradesTotal = 0;
        numberTradesLong = 0;
        numberTradesShort = 0;
        numberWinTradesTotal = 0;
        numberWinTradesLong = 0;
        numberWinTradesShort = 0;
        numberLossTradesTotal = 0;
        numberLossTradesLong = 0;
        numberLossTradesShort = 0;
        grossProfitTotal = 0;
        grossProfitLong = 0;
        grossProfitShort = 0;
        grossLossTotal = 0;
        grossLossLong = 0;
        grossLossShort = 0;
        maxWinTotal = 0;
        maxWinLong = 0;
        maxWinShort = 0;
        maxLossTotal = 0;
        maxLossLong = 0;
        maxLossShort = 0;
        numberHoldDays = 0;
        totalCost = 0;
        wealth = initialWealth;
        maxIntradayDrawDown = 0;

        PnLSeries.clear();
        wealthSeries.clear();
        costSeries.clear();
        tradeSeriesTotal.clear();
        tradeSeriesLong.clear();
        tradeSeriesShort.clear();
        tradeDistributionTotal.clear();
        tradeDistributionLong.clear();
        tradeDistributionShort.clear();

        // Trade signalSeries
//        if (signalSeries != null) return processSignalSerie(option);

        // Transaction series
        if (transactionSerie != null) if (processTransactionSerie(firstDate, lastDate)) return 0;
        isTested = true;
        return 0;
    }

    private boolean processTransactionSerie(TDay firstDate, TDay lastDate) {

        double PnL = 0.0;
        double cost = 0;
        double currentAccount = initialWealth;
        double CurrentWealth = initialWealth;
        double previousWealth = initialWealth;
        double maxWealth = initialWealth;
        double aktDrawDown = 0;

        Transaction transaction;
        Transaction transactionPair;

        if (firstDate == null) firstDate = portfolio.getFirstDate();
        if (lastDate == null) lastDate = portfolio.getLastDate();

        if (firstDate == null || lastDate == null) return true;

        int firstIndex = 0;
        int NTransactions = transactionSerie.getN();

        firstIndex = 0;

        TDay date = firstDate;
        TDay last = lastDate;

        while (date.isLessEqual(last)) {
            transactionPair = null;
            cost = 0;
            for (int index = firstIndex; index < NTransactions; index++) {
                transaction = transactionSerie.getTransaction(index);
                TDay transactionDate = transaction.getDate();

                if (transactionDate.isEqual(date)) {
                    if (transaction.getAction() == BUY) {
                        currentAccount -= (transaction.getValue() + transaction.getCost());

                        Instrument instrument = transaction.getInstrument();
                        transactionPair = transactionSerie.getPair(index);
                        if (transactionPair == null) {
                            TDay operationDate = instrument.getLastDate();
                            double operationLastPrice = instrument.getClose(operationDate);
                            double operationEntryPrice = transaction.getPrice();
                            int operationAmount = transaction.getAmount();
                            openPositionLong += ((operationLastPrice - operationEntryPrice) * operationAmount) - transaction.getCost();
                            numberOpenPositionsLong++;
                        }
                    } else if (transaction.getAction() == SELL) {
                        currentAccount += (transaction.getValue() - transaction.getCost());

                        transactionPair = transactionSerie.getPair(index);
                        if (transactionPair != null) {
                            double tradeBuyWealth = (transactionPair.getValue() + transactionPair.getCost());
                            double tradeSellWealth = (transaction.getValue() - transaction.getCost());
                            double wealthDiff = tradeSellWealth - tradeBuyWealth;
                            if (wealthDiff > 0) {
                                grossProfitLong += wealthDiff;
                                numberWinTradesLong++;
                            } else {
                                grossLossLong += wealthDiff;
                                numberLossTradesLong++;
                            }
                            tradeSeriesTotal.add(date, wealthDiff);
                            tradeSeriesLong.add(date, wealthDiff);
                            tradeDistributionTotal.add(date, wealthDiff);
                            tradeDistributionLong.add(date, wealthDiff);
                            addToTradeList(transaction, transactionPair, wealthDiff, LONG);
                        }
                    } else if (transaction.getAction() == SELLSHORT) {
                        currentAccount += (transaction.getValue() - transaction.getCost());

                        Instrument instrument = transaction.getInstrument();
                        transactionPair = transactionSerie.getPair(index);
                        if (transactionPair == null) {
                            TDay operationDate = instrument.getLastDate();
                            double operationLastPrice = instrument.getClose(operationDate);
                            double entryPrice = transaction.getPrice();
                            int operationAmount = transaction.getAmount();
                            openPositionShort += ((operationLastPrice - entryPrice) * operationAmount) - transaction.getCost();
                            numberOpenPositionsShort++;
                        }
                    } else if (transaction.getAction() == BUYSHORT) {
                        currentAccount -= (transaction.getValue() + transaction.getCost());

                        transactionPair = transactionSerie.getPair(index);
                        if (transactionPair != null) {
                            double TradeSellShortWealth = (transactionPair.getValue() - transactionPair.getCost());
                            double TradeBuyShortWealth = (transaction.getValue() + transaction.getCost());
                            double WealthDiff = TradeSellShortWealth - TradeBuyShortWealth;
                            if (WealthDiff > 0) {
                                grossProfitShort += WealthDiff;
                                numberWinTradesShort++;
                            } else {
                                grossLossShort += WealthDiff;
                                numberLossTradesShort++;
                            }
                            tradeSeriesTotal.add(date, WealthDiff);
                            tradeSeriesShort.add(date, WealthDiff);
                            tradeDistributionTotal.add(date, WealthDiff);
                            tradeDistributionShort.add(date, WealthDiff);
                            addToTradeList(transaction, transactionPair, WealthDiff, SHORT);
                        }
                        break;
                    }
                    PortfolioMath.add(portfolio, transaction);
                    cost += transaction.getCost();
                    firstIndex = index + 1;

                    double mark2market = 0;
                    mark2market = PortfolioMath.getValue(portfolio, date);

                    CurrentWealth = currentAccount + mark2market;
                    wealthSeries.add(date, CurrentWealth);
                    PnL = CurrentWealth - previousWealth;
                    PnLSeries.add(date, PnL);
                    previousWealth = CurrentWealth;
                    costSeries.add(date, cost);
                    totalCost += cost;
                    maxWealth = Math.max(maxWealth, CurrentWealth);
                    aktDrawDown = CurrentWealth - maxWealth;
                    if (aktDrawDown > 0) aktDrawDown = 0;
                    wealthUnderwaterLine.add(date, aktDrawDown);
                    if (aktDrawDown < maxIntradayDrawDown) {
                        maxIntradayDrawDown = aktDrawDown;
                        maxIntradayDrawDownDate = date;
                    }
                } else
                    break;
            }
            date = date.nextDay();
        }

        numberTradesLong = numberWinTradesLong + numberLossTradesLong;
        numberTradesShort = numberWinTradesShort + numberLossTradesShort;
        numberTradesTotal = numberTradesLong + numberTradesShort;
        numberWinTradesTotal = numberWinTradesLong + numberWinTradesShort;
        numberLossTradesTotal = numberLossTradesLong + numberLossTradesShort;
        grossProfitTotal = grossProfitLong + grossProfitShort;
        grossLossTotal = grossLossLong + grossLossShort;
        wealth = CurrentWealth;
        LogUtils.debug("Wealth      : " + CurrentWealth);
        LogUtils.debug("Account     : " + currentAccount);
        LogUtils.debug("Portfolio   : " + portfolio.getPrice(lastDate));
        if (benchmarkPortfolio != null) {
            LogUtils.debug("print benchmark portfolio");
            LogUtils.debug(benchmarkPortfolio.toString());
        }
        return false;
    }


    public int getTradeListEntryNumber(Instrument instrument) {
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        if (tradeList == null) throw new IllegalArgumentException("tradeList cannot be null");
        int NInstruments = tradeList.size();
        for (int i = 0; i < NInstruments; i++)
            if (((TradeListEntry) tradeList.get(i)).getInstrument().equals(instrument)) return i + 1;
        return 0;
    }

    public void addToTradeList(Transaction transaction,
                               Transaction transactionPair,
                               double WealthDiff,
                               int transactionType) {
        Instrument instrument;
        int tradeDuration;
        double entryprice, close, high, low, TradeResult;
        TDay entryDate, closeDate;
        int entryNumbers;
        instrument = transaction.getInstrument();
        if ((transactionPair != null) && (transaction != null)) {
            entryNumbers = getTradeListEntryNumber(instrument);
            if (entryNumbers == 0) {
                tradeList.add(new TradeListEntry(instrument));
                entryNumbers = 1;
            }
            ((TradeListEntry) tradeList.get(entryNumbers - 1)).numberOfTrades++;
            ((TradeListEntry) tradeList.get(entryNumbers - 1)).tradeResult += WealthDiff;
            entryprice = transactionPair.getPrice();
            entryDate = transactionPair.getDate();
            close = transaction.getPrice();
            closeDate = transaction.getDate();
            if (transactionType == LONG) TradeResult = close - entryprice;
            else TradeResult = entryprice - close;

            TDay TDayBefore = entryDate.prevDay();

            TimeSeries series = instrument.getCloseSeries(TDayBefore, closeDate);
            high = series.getMax();
            low = series.getMin();
            tradeDuration = series.getNNonZero();
            TradeInfo tradeInfo = new
                    TradeInfo(entryprice,
                    high,
                    low,
                    close,
                    TradeResult,
                    transactionType,
                    tradeDuration,
                    entryDate,
                    instrument);
            TradeListEntry entry = (TradeListEntry) tradeList.get(entryNumbers - 1);
            ArrayList list = entry.getTradeInfoList();
            list.add(tradeInfo);
        }
    }

    public double getPnL(TDay date) {
        if (!isTested()) test();
        TDay lastDate = PnLSeries.getLastDate();
        if (date == null || lastDate == null) return 0;
        if (date.isGreater(lastDate)) return PnLSeries.getData(lastDate);
        else return PnLSeries.getData(date);
    }

    public TimeSeries getPnLSeries() {
        if (!isTested()) test();
        return PnLSeries;
    }

    public double getPnLMin() {
        if (!isTested()) test();
        return PnLSeries.getMin();
    }

    public double getPnLMax() {
        if (!isTested()) test();
        return PnLSeries.getMax();
    }

    public double getPnLMean() {
        if (!isTested()) test();
        return PnLSeries.getMean();
    }

    public double getPnLStdDev() {
        if (!isTested()) test();
        return PnLSeries.getStdDev();
    }

    public double getPnLVariance() {
        if (!isTested()) test();
        return PnLSeries.getVariance();
    }

    public double getPnLSharpeRatio() {
        if (!isTested()) test();
        return getPnLSharpeRatio(0);
    }

    public double getPnLSharpeRatio(double InterestRate) {
        if (!isTested()) test();
        double Mean = getPnLMean();
        double StdDev = getPnLStdDev();
        if (StdDev != 0) return (Mean - InterestRate) / StdDev;
        else return 0;
    }

    public TDay getPnLMinDate() {
        if (!isTested()) test();
        TDay firstDate = PnLSeries.getFirstDate();
        TDay lastDate = PnLSeries.getLastDate();
        double Min = PnLSeries.getData(firstDate);
        TDay minDate = firstDate;

        if (firstDate != null) {
            TDay dd = firstDate;
            TDay end = lastDate;
            for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                if (!PnLSeries.isEmpty(dd)) {
                    double data = PnLSeries.getData(dd);
                    if (data < Min) {
                        Min = data;
                        minDate = dd;
                    }
                }
            }
            return minDate;
        }
        return null;
    }

    public TDay getPnLMaxDate() {
        if (!isTested()) test();
        TDay firstDate = PnLSeries.getFirstDate();
        TDay lastDate = PnLSeries.getLastDate();
        double Max = PnLSeries.getData(firstDate);
        TDay maxDate = firstDate;
        if (firstDate != null) {
            TDay dd = firstDate;
            TDay end = lastDate;
            for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                if (!PnLSeries.isEmpty(dd)) {
                    if (PnLSeries.getData(dd) > Max) {
                        Max = PnLSeries.getData(dd);
                        maxDate = dd;
                    }
                }
            }
        }
        return maxDate;
    }

    /*
    TH1* getPnLDistribution(int NBins) {
        // Return distribution histogram of PnL series
        if (!isTested())
            performance();
        return PnLSeries.getDistribution(NBins);
    }
     */

    public double getWealth(TDay date) {
        if (!isTested()) test();
        TDay lastDate;
        lastDate = wealthSeries.getLastDate();
        if (date == null || lastDate == null) return 0;
        if (date.isGreater(lastDate)) return wealthSeries.getData(lastDate);
        else return wealthSeries.getData(date);
    }

    public TimeSeries getWealthSeries() {
        if (!isTested()) test();
        return wealthSeries;
    }

    public TimeSeries getWealthUnderwaterLineSeries() {
        if (!isTested()) test();
        return wealthUnderwaterLine;
    }

//    public FinGraph getWealthGraph() {
//        if (!isTested()) test();
//        return wealthSeries.getGraph(800, 400, 1);
//    }

//    public FinGraph getWealthUnderwaterLineGraph() {
//        if (!isTested()) test();
//        return wealthUnderwaterLine.getGraph(800, 400, 1);
//    }

    public double getWealthMin() {
        if (!isTested()) test();
        return wealthSeries.getMin();
    }

    public double getWealthMax() {
        if (!isTested()) test();
        return wealthSeries.getMax();
    }

    public double getWealthMean() {
        if (!isTested()) test();
        return wealthSeries.getMean();
    }

    public double getWealthStdDev() {
        if (!isTested()) test();
        return wealthSeries.getStdDev();
    }

    public double getWealthVariance() {
        if (!isTested()) test();
        return wealthSeries.getVariance();
    }

    public TDay getWealthMinDate() {
        if (!isTested()) test();
        TDay firstDate = wealthSeries.getFirstDate();
        TDay lastDate = wealthSeries.getLastDate();
        double Min = wealthSeries.getData(firstDate);
        TDay minDate = firstDate;

        if (firstDate != null) {
            TDay dd = firstDate;
            TDay end = lastDate;
            for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                if (!wealthSeries.isEmpty(dd)) {
                    if (wealthSeries.getData(dd) < Min) {
                        Min = wealthSeries.getData(dd);
                        minDate = dd;
                    }
                }
            }
        }
        return minDate;
    }

    public TDay getWealthMaxDate() {
        if (!isTested()) test();
        TDay firstDate = wealthSeries.getFirstDate();
        TDay lastDate = wealthSeries.getLastDate();
        double Max = wealthSeries.getData(firstDate);
        TDay maxDate = firstDate;
        if (firstDate != null) {
            TDay dd = firstDate;
            TDay end = lastDate;
            for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                if (!wealthSeries.isEmpty(dd)) {
                    if (wealthSeries.getData(dd) > Max) {
                        Max = wealthSeries.getData(dd);
                        maxDate = dd;

                    }
                }
            }
        }
        return maxDate;
    }

    public TimeSeries getCostSeries() {
        if (!isTested()) test();
        return costSeries;
    }

//    public FinGraph getCostGraph() {
//        if (!isTested()) test();
//        return costSeries.getGraph(800, 400, 1);
//    }

    public double getCostMin() {
        if (!isTested()) test();
        return costSeries.getMin();
    }

    public double getCostMax() {
        if (!isTested()) test();
        return costSeries.getMax();
    }

    public double getCostMean() {
        if (!isTested()) test();
        return costSeries.getMean();
    }

    public double getCostStdDev() {
        if (!isTested()) test();
        return costSeries.getStdDev();
    }

    public double getCostVariance() {
        if (!isTested()) test();
        return costSeries.getVariance();
    }

    public TDay getCostMinDate() {
        if (!isTested()) test();
        TDay firstDate = costSeries.getFirstDate();
        TDay lastDate = costSeries.getLastDate();
        double Min = costSeries.getData(firstDate);
        TDay minDate = firstDate;
        if (firstDate != null) {
            TDay dd = firstDate;
            TDay end = lastDate;
            for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                if (!costSeries.isEmpty(dd)) {
                    if (costSeries.getData(dd) < Min) {
                        Min = costSeries.getData(dd);
                        minDate = dd;
                    }
                }
            }
        }
        return minDate;
    }

    public TDay getCostMaxDate() {
        if (!isTested()) test();
        TDay firstDate = costSeries.getFirstDate();
        TDay lastDate = costSeries.getLastDate();
        double Max = costSeries.getData(firstDate);
        TDay maxDate = firstDate;
        if (firstDate != null) {
            TDay dd = firstDate;
            TDay end = lastDate;
            for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                if (!costSeries.isEmpty(dd)) {
                    if (costSeries.getData(dd) > Max) {
                        Max = costSeries.getData(dd);
                        maxDate = dd;
                    }
                }
            }
        }
        return maxDate;
    }

/*    public TH1 getCostDistribution(int NBins) {
       // Return distribution histogram of
       // transaction cost series
       if (!isTested())
           performance();
       return costSeries.getDistribution(NBins);
   }
*/

    // TradeResults

    public TimeSeries getTradeSeriesTotal() {
        if (!isTested()) test();
        return tradeSeriesTotal;
    }

    public TimeSeries getTradeSeriesLong() {
        if (!isTested()) test();
        return tradeSeriesLong;
    }

    public TimeSeries getTradeSeriesShort() {
        if (!isTested()) test();
        return tradeSeriesShort;
    }

//    public FinGraph getTradeGraphTotal() {
//        if (!isTested()) test();
//        return tradeSeriesTotal.getGraph(800, 400, 1, false);
//    }
//
//    public FinGraph getTradeGraphLong() {
//        if (!isTested()) test();
//        return tradeSeriesLong.getGraph(800, 400, 1, false);
//    }

//    public FinGraph getTradeGraphShort() {
//        if (!isTested()) test();
//        return tradeSeriesShort.getGraph(800, 400, 1, false);
//    }

    public double getTradeMaxWinTotal() {
        if (!isTested()) test();
        return tradeDistributionTotal.getMax();
    }

    public double getTradeMaxWinLong() {
        if (!isTested()) test();
        return tradeDistributionLong.getMax();
    }

    public double getTradeMaxWinShort() {
        if (!isTested()) test();
        return tradeDistributionShort.getMax();
    }

    public double getTradeMaxLossTotal() {
        if (!isTested()) test();
        return tradeDistributionTotal.getMin();
    }

    public double getTradeMaxLossLong() {
        if (!isTested()) test();
        return tradeDistributionLong.getMin();
    }

    public double getTradeMaxLossShort() {
        if (!isTested()) test();
        return tradeDistributionShort.getMin();
    }

    public double getTradeMeanTotal() {
        if (!isTested()) test();
        return tradeDistributionTotal.getMean();
    }

    public double getTradeMeanLong() {
        if (!isTested()) test();
        return tradeDistributionLong.getMean();
    }

    public double getTradeMeanShort() {
        if (!isTested()) test();
        return tradeDistributionShort.getMean();
    }

    public double getTradeStdDevTotal() {
        if (!isTested()) test();
        return tradeDistributionTotal.getStdDev();
    }

    public double getTradeStdDevLong() {
        if (!isTested()) test();
        return tradeDistributionLong.getStdDev();
    }

    public double getTradeStdDevShort() {
        if (!isTested()) test();
        return tradeDistributionShort.getStdDev();
    }


    public double getTradeVarianceTotal() {
        if (!isTested()) test();
        return tradeDistributionTotal.getVariance();
    }

    public double getTradeVarianceLong() {
        if (!isTested()) test();
        return tradeDistributionLong.getVariance();
    }

    public double getTradeVarianceShort() {
        if (!isTested()) test();
        return tradeDistributionShort.getVariance();
    }

    public TDay getTradeMaxWinDateTotal() {
        if (!isTested()) test();

        if (tradeDistributionTotal.getSize() > 0) {
            TDay firstDate = tradeDistributionTotal.getFirstDate();
            TDay lastDate = tradeDistributionTotal.getLastDate();
            double Max = tradeDistributionTotal.getData(firstDate);
            TDay maxDate = firstDate;
            if (firstDate != null) {
                TDay dd = firstDate;
                TDay end = lastDate;
                for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                    if (!tradeDistributionTotal.isEmpty(dd)) {
                        if (tradeDistributionTotal.getData(dd) > Max) {
                            Max = tradeDistributionTotal.getData(dd);
                            maxDate = dd;
                        }
                    }
                }
            }
            return maxDate;
        } else return null;
    }

    public TDay getTradeMaxWinDateLong() {
        if (!isTested()) test();

        if (tradeDistributionLong.getSize() > 0) {
            TDay firstDate = tradeDistributionLong.getFirstDate();
            TDay lastDate = tradeDistributionLong.getLastDate();

            double Max = tradeDistributionLong.getData(firstDate);
            TDay maxDate = firstDate;
            if (firstDate != null) {
                TDay dd = firstDate;
                TDay end = lastDate;
                for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                    if (!tradeDistributionLong.isEmpty(dd)) {
                        if (tradeDistributionLong.getData(dd) > Max) {
                            Max = tradeDistributionLong.getData(dd);
                            maxDate = dd;
                        }
                    }
                }
            }
            return maxDate;
        } else return null;
    }

    public TDay getTradeMaxWinDateShort() {
        if (!isTested()) test();

        if (tradeDistributionShort.getSize() > 0) {
            TDay firstDate = tradeDistributionShort.getFirstDate();
            TDay lastDate = tradeDistributionShort.getLastDate();

            double Max = tradeDistributionShort.getData(firstDate);
            TDay maxDate = firstDate;
            if (firstDate != null) {
                TDay dd = firstDate;
                TDay end = lastDate;
                for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                    if (!tradeDistributionShort.isEmpty(dd)) {
                        if (tradeDistributionShort.getData(dd) > Max) {
                            Max = tradeDistributionShort.getData(dd);
                            maxDate = dd;
                        }
                    }
                }
            }
            return maxDate;
        } else
            return null;
    }

    public TDay getTradeMaxLossDateTotal() {
        if (!isTested()) test();
        if (tradeDistributionTotal.getSize() > 0) {
            TDay firstDate = tradeDistributionTotal.getFirstDate();
            TDay lastDate = tradeDistributionTotal.getLastDate();
            double Min = tradeDistributionTotal.getData(firstDate);
            TDay minDate = firstDate;
            if (firstDate != null) {
                TDay dd = firstDate;
                TDay end = lastDate;
                for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                    if (!tradeDistributionTotal.isEmpty(dd)) {
                        if (tradeDistributionTotal.getData(dd) < Min) {
                            Min = tradeDistributionTotal.getData(dd);
                            minDate = dd;
                        }
                    }
                }
            }
            return minDate;
        } else return null;
    }

    TDay getTradeMaxLossDateLong() {
        if (!isTested()) test();

        if (tradeDistributionLong.getSize() > 0) {
            TDay firstDate = tradeDistributionLong.getFirstDate();
            TDay lastDate = tradeDistributionLong.getLastDate();

            double Min = tradeDistributionLong.getData(firstDate);
            TDay minimumDate = firstDate;
            if (firstDate != null) {
                TDay dd = firstDate;
                TDay end = lastDate;
                for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                    if (!tradeDistributionLong.isEmpty(dd)) {
                        if (tradeDistributionLong.getData(dd) < Min) {
                            Min = tradeDistributionLong.getData(dd);
                            minimumDate = dd;
                        }
                    }
                }
            }
            return minimumDate;
        } else return null;
    }

    public TDay getTradeMaxLossDateShort() {
        if (!isTested()) test();

        if (tradeDistributionShort.getSize() > 0) {
            TDay firstDate = tradeDistributionShort.getFirstDate();
            TDay lastDate = tradeDistributionShort.getLastDate();
            double Min = tradeDistributionShort.getData(firstDate);
            TDay minimumDate = firstDate;
            if (firstDate != null) {
                TDay dd = firstDate;
                TDay end = lastDate;
                for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                    if (!tradeDistributionShort.isEmpty(dd)) {
                        if (tradeDistributionShort.getData(dd) < Min) {
                            Min = tradeDistributionShort.getData(dd);
                            minimumDate = dd;
                        }
                    }
                }
            }
            return minimumDate;
        } else return null;
    }

    public int getNMaxConsecWinTradesTotal() {
        int NConsecWinTradesTotal = 0;
        int NConsecWinTradesTotalReturn = 0;
        if (!isTested()) test();

        if (tradeDistributionTotal.getSize() > 0) {
            TDay firstDate = tradeDistributionTotal.getFirstDate();
            TDay lastDate = tradeDistributionTotal.getLastDate();
            if (firstDate != null) {
                TDay dd = firstDate;
                TDay end = lastDate;
                for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                    if ((!tradeDistributionTotal.isEmpty(dd))) {
                        if (tradeDistributionTotal.getData(dd) > 0) {
                            NConsecWinTradesTotal++;
                            NConsecWinTradesTotalReturn = Math.max(NConsecWinTradesTotalReturn, NConsecWinTradesTotal);
                        } else NConsecWinTradesTotal = 0;
                    }
                }
            }
            return NConsecWinTradesTotalReturn;
        } else return 0;
    }

    public int getNMaxConsecWinTradesLong() {
        int NConsecWinTradesLong = 0;
        int NConsecWinTradesLongReturn = 0;
        if (!isTested()) test();

        if (tradeDistributionLong.getSize() > 0) {
            TDay firstDate = tradeDistributionLong.getFirstDate();
            TDay lastDate = tradeDistributionLong.getLastDate();
            TDay dd = firstDate;
            TDay end = lastDate;
            for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                if ((!tradeDistributionLong.isEmpty(dd))) {
                    if (tradeDistributionLong.getData(dd) > 0) {
                        NConsecWinTradesLong++;
                        NConsecWinTradesLongReturn = Math.max(NConsecWinTradesLongReturn, NConsecWinTradesLong);
                    } else NConsecWinTradesLong = 0;
                }
            }
            return NConsecWinTradesLongReturn;
        } else return 0;
    }

    public int getNMaxConsecWinTradesShort() {
        int NConsecWinTradesShort = 0;
        int NConsecWinTradesShortReturn = 0;
        if (!isTested()) test();

        if (tradeDistributionShort.getSize() > 0) {
            TDay firstDate = tradeDistributionShort.getFirstDate();
            TDay lastDate = tradeDistributionShort.getLastDate();

            if (firstDate != null) {
                TDay dd = firstDate;
                TDay end = lastDate;
                for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                    if ((!tradeDistributionShort.isEmpty(dd))) {
                        if (tradeDistributionShort.getData(dd) > 0) {
                            NConsecWinTradesShort++;
                            NConsecWinTradesShortReturn = Math.max(NConsecWinTradesShortReturn, NConsecWinTradesShort);
                        } else NConsecWinTradesShort = 0;
                    }
                }
            }
            return NConsecWinTradesShortReturn;
        } else return 0;
    }

    public int getNMaxConsecLossTradesTotal() {
        int NConsecLossTradesTotal = 0;
        int NConsecLossTradesTotalReturn = 0;
        if (!isTested()) test();

        if (tradeDistributionTotal.getSize() > 0) {
            TDay firstDate = tradeDistributionTotal.getFirstDate();
            TDay lastDate = tradeDistributionTotal.getLastDate();

            if (firstDate != null) {
                TDay dd = firstDate;
                TDay end = lastDate;
                for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                    if ((!tradeDistributionTotal.isEmpty(dd))) {
                        if (tradeDistributionTotal.getData(dd) < 0) {
                            NConsecLossTradesTotal++;
                            NConsecLossTradesTotalReturn = Math.max(NConsecLossTradesTotalReturn, NConsecLossTradesTotal);
                        } else NConsecLossTradesTotal = 0;
                    }
                }
            }
            return NConsecLossTradesTotalReturn;
        } else return 0;
    }

    public int getNMaxConsecLossTradesLong() {
        int NConsecLossTradesLong = 0;
        int NConsecLossTradesLongReturn = 0;
        if (!isTested()) test();

        if (tradeDistributionLong.getSize() > 0) {
            TDay firstDate = tradeDistributionLong.getFirstDate();
            TDay lastDate = tradeDistributionLong.getLastDate();
            if (firstDate != null) {
                TDay dd = firstDate;
                TDay end = lastDate;
                for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                    if ((!tradeDistributionLong.isEmpty(dd))) {
                        if (tradeDistributionLong.getData(dd) < 0) {
                            NConsecLossTradesLong++;
                            NConsecLossTradesLongReturn = Math.max(NConsecLossTradesLongReturn, NConsecLossTradesLong);
                        } else NConsecLossTradesLong = 0;
                    }
                }
            }
            return NConsecLossTradesLongReturn;
        } else return 0;
    }

    public int getNMaxConsecLossTradesShort() {
        // Return number of max. consecutive loosers short
        int NConsecLossTradesShort = 0;
        int NConsecLossTradesShortReturn = 0;
        if (!isTested()) test();

        if (tradeDistributionShort.getSize() > 0) {
            TDay firstDate = tradeDistributionShort.getFirstDate();
            TDay lastDate = tradeDistributionShort.getLastDate();

            if (firstDate != null) {
                TDay dd = firstDate;
                TDay end = lastDate;
                for (; dd.isLessEqual(end); dd = dd.nextDay()) {
                    if ((!tradeDistributionShort.isEmpty(dd))) {
                        if (tradeDistributionShort.getData(dd) < 0) {
                            NConsecLossTradesShort++;
                            NConsecLossTradesShortReturn = Math.max(NConsecLossTradesShortReturn, NConsecLossTradesShort);
                        } else NConsecLossTradesShort = 0;
                    }
                }
            }
            return NConsecLossTradesShortReturn;
        } else return 0;
    }

    public double getTradesPercentProfitableTotal() {
        if (numberTradesTotal != 0) return ((double) numberWinTradesTotal / numberTradesTotal * 100);
        else return 0;
    }

    public double getTradesPercentProfitableLong() {
        if (numberTradesLong != 0) return ((double) numberWinTradesLong / numberTradesLong * 100);
        else return 0;
    }

    public double getTradesPercentProfitableShort() {
        if (numberTradesShort != 0) return ((double) numberWinTradesShort / numberTradesShort * 100);
        else return 0;
    }

    public double getTradeMeanWinTotal() {
        if (numberWinTradesTotal != 0) return grossProfitTotal / numberWinTradesTotal;
        else return 0;
    }

    public double getTradeMeanWinLong() {
        if (numberWinTradesLong != 0) return grossProfitLong / numberWinTradesLong;
        else return 0;
    }

    public double getTradeMeanWinShort() {
        if (numberWinTradesShort != 0) return grossProfitShort / numberWinTradesShort;
        else return 0;
    }

    public double getTradeMeanLossTotal() {
        if (numberLossTradesTotal != 0) return grossLossTotal / numberLossTradesTotal;
        else return 0;
    }

    public double getTradeMeanLossLong() {
        if (numberLossTradesLong != 0) return grossLossLong / numberLossTradesLong;
        else return 0;
    }

    public double getTradeMeanLossShort() {
        if (numberLossTradesShort != 0) return grossLossShort / numberLossTradesShort;
        else return 0;
    }

    public double getTradeRatioMeanWinLossTotal() {
        if (getTradeMeanLossTotal() != 0) return (getTradeMeanWinTotal() / getTradeMeanLossTotal());
        else return 0;
    }

    public double getTradeRatioMeanWinLossLong() {
        if (getTradeMeanLossLong() != 0) return (getTradeMeanWinLong() / getTradeMeanLossLong());
        else return 0;
    }

    public double getTradeRatioMeanWinLossShort() {
        if (getTradeMeanLossShort() != 0) return (getTradeMeanWinShort() / getTradeMeanLossShort());
        else return 0;
    }

    // profit factor
    public double getProfitFactorTotal() {
        if (Math.abs(grossLossTotal) != 0) return grossProfitTotal / Math.abs(grossLossTotal);
        else return 0;
    }

    public double getProfitFactorLong() {
        if (Math.abs(grossLossLong) != 0) return grossProfitLong / Math.abs(grossLossLong);
        else return 0;
    }

    public double getProfitFactorShort() {
        if (Math.abs(grossLossShort) != 0) return grossProfitShort / Math.abs(grossLossShort);
        else return 0;
    }

    public double getTradeSharpRatioTotal() {
        if (getTradeStdDevTotal() != 0) return (getTradeMeanTotal() / getTradeStdDevTotal());
        else return 0;
    }

    public double getTradeSharpeRatioLong() {
        if (getTradeStdDevLong() != 0) return (getTradeMeanLong() / getTradeStdDevLong());
        else return 0;
    }

    public double getTradeSharpeRatioShort() {
        if (getTradeStdDevShort() != 0) return (getTradeMeanShort() / getTradeStdDevShort());
        else return 0;
    }

    public String toXMLString() {
        if (!isTested()) test();
        StringBuffer sb = new StringBuffer("<TESTRESULT>");
        sb.append("<PNLMEAN>" + getPnLMean() + "</PNLMEAN>");
        sb.append("<PNLVARIANCE>" + getPnLVariance() + "</PNLVARIANCE>");
        sb.append("<PNLSTDDEV>" + getPnLStdDev() + "</PNLSTDDEV>");
        sb.append("<PNLSHARPERATIO>" + getPnLSharpeRatio() + "</PNLSHARPERATIO>");
        sb.append("<PNLMIN>" + getPnLMin() + "</PNLMIN>");
        sb.append("<PNLMINDATE>" + getPnLMinDate() + "</PNLMINDATE>");
        sb.append("<PNLMAX>" + getPnLMax() + "</PNLMAX>");
        sb.append("<PNLMAXDATE>" + getPnLMaxDate() + "</PNLMAXDATE>");

        sb.append("<WEALTHMEAN>" + getWealthMean() + "</WEALTHMEAN>");
        sb.append("<WEALTHVARIANCE>" + getWealthVariance() + "</WEALTHVARIANCE>");
        sb.append("<WEALTHSTDDEV>" + getWealthStdDev() + "</WEALTHSTDDEV>");
        sb.append("<WEALTHMIN>" + getWealthMin() + "</WEALTHMIN>");
        sb.append("<WEALTHMINDATE>" + getWealthMinDate() + "</WEALTHMINDATE>");
        sb.append("<WEALTHMAX>" + getWealthMax() + "</WEALTHMAX>");
        sb.append("<WEALTHMAXDATE>" + getWealthMaxDate() + "</WEALTHMAXDATE>");

        sb.append("<COSTMEAN>" + getCostMean() + "</COSTMEAN>");
        sb.append("<COSTVARIANCE>" + getCostVariance() + "</COSTVARIANCE>");
        sb.append("<COSTSTDDEV>" + getCostStdDev() + "</COSTSTDDEV>");
        sb.append("<COSTMIN>" + getCostMin() + "</COSTMIN>");
        sb.append("<COSTMINDATE>" + getCostMinDate() + "</COSTMINDATE>");
        sb.append("<COSTMAX>" + getCostMax() + "</COSTMAX>");
        sb.append("<COSTMAXDATE>" + getCostMaxDate() + "</COSTMAXDATE>");

        sb.append("<TOTALWL>" + getTradeMeanTotal() + "</TOTALWL>");
        sb.append("<TOTALMAXWIN>" + getTradeMaxWinTotal() + "</TOTALMAXWIN>");
        sb.append("<TOTALMAXWINDATE>" + getTradeMaxWinDateTotal() + "</TOTALMAXWINDATE>");
        sb.append("<TOTALMAXLOSS>" + getTradeMaxLossTotal() + "</TOTALMAXLOSS>");
        sb.append("<TOTALMAXLOSSDATE>" + getTradeMaxLossDateTotal() + "</TOTALMAXLOSSDATE>");
        sb.append("<TOTALNTRADES>" + getNTradesTotal() + "</TOTALNTRADES>");
        sb.append("<TOTALNWINTRADES>" + getNWinTradesTotal() + "</TOTALNWINTRADES>");
        sb.append("<TOTALNLOSSTRADES>" + getNLossTradesTotal() + "</TOTALNLOSSTRADES>");
        sb.append("<TOTALPERCENTPROFIT>" + getTradesPercentProfitableTotal() + "</TOTALPERCENTPROFIT>");
        sb.append("<TOTALNCONWINTRADES>" + getNMaxConsecWinTradesTotal() + "</TOTALNCONWINTRADES>");
        sb.append("<TOTALNCONLOSSTRADES>" + getNMaxConsecLossTradesTotal() + "</TOTALNCONLOSSTRADES>");
        sb.append("<TOTALAVGWINTRADE>" + getTradeMeanWinTotal() + "</TOTALAVGWINTRADE>");
        sb.append("<TOTALAVGLOSSTRADE>" + getTradeMeanLossTotal() + "</TOTALAVGLOSSTRADE>");

        sb.append("<TOTALMAXDRAWDOWN>" + getMaxIntradayDrawDown() + "</TOTALMAXDRAWDOWN>");
        sb.append("<TOTALMAXDRAWDOWNDATE>" + getMaxIntradayDrawDownDate() + "</TOTALMAXDRAWDOWNDATE>");
        sb.append("<TOTALGROSSPROFIT>" + getGrossProfitTotal() + "</TOTALGROSSPROFIT>");
        sb.append("<TOTALGROSSLOSS>" + getGrossLossTotal() + "</TOTALGROSSLOSS>");
        sb.append("<TOTALNETPROFIT>" + getNetProfitTotal() + "</TOTALNETPROFIT>");
        sb.append("<TOTALPROFITFACTOR>" + getProfitFactorTotal() + "</TOTALPROFITFACTOR>");
        sb.append("<TOTALSHARPERATIO>" + getTradeSharpRatioTotal() + "</TOTALSHARPERATIO>");
        sb.append("<TOTALVARIANCE>" + getTradeVarianceTotal() + "</TOTALVARIANCE>");
        sb.append("<TOTALSSTDDEV>" + getTradeStdDevTotal() + "</TOTALSSTDDEV>");

        sb.append("<LONGAVGWL>" + getTradeMeanLong() + "</LONGAVGWL>");
        sb.append("<LONGMAXWIN>" + getTradeMaxWinLong() + "</LONGMAXWIN>");
        sb.append("<LONGMAXWINDATE>" + getTradeMaxWinDateLong() + "</LONGMAXWINDATE>");
        sb.append("<LONGMAXLOSS>" + getTradeMaxLossLong() + "</LONGMAXLOSS>");
        sb.append("<LONGMAXLOSSDATE>" + getTradeMaxLossDateLong() + "</LONGMAXLOSSDATE>");
        sb.append("<LONGNTRADES>" + getNTradesLong() + "</LONGNTRADES>");
        sb.append("<LONGNWINTRADES>" + getNWinTradesLong() + "</LONGNWINTRADES>");
        sb.append("<LONGNLOSSTRADES>" + getNLossTradesLong() + "</LONGNLOSSTRADES>");
        sb.append("<LONGPERCPROFITABLE>" + getTradesPercentProfitableLong() + "</LONGPERCPROFITABLE>");
        sb.append("<LONGNCONWINTRADES>" + getNMaxConsecWinTradesLong() + "</LONGNCONWINTRADES>");
        sb.append("<LONGNCONLOSSTRADES>" + getNMaxConsecLossTradesLong() + "</LONGNCONLOSSTRADES>");
        sb.append("<LONGMEANWINTRADE>" + getTradeMeanWinLong() + "</LONGMEANWINTRADE>");
        sb.append("<LONGAVGLOSSTRADE>" + getTradeMeanLossLong() + "</LONGAVGLOSSTRADE>");
        sb.append("<LONGGROSSPROFIT>" + getGrossProfitLong() + "</LONGGROSSPROFIT>");
        sb.append("<LONGGROSSLOSS>" + getGrossLossLong() + "</LONGGROSSLOSS>");
        sb.append("<LONGNETPROFIT>" + getNetProfitLong() + "</LONGNETPROFIT>");
        sb.append("<LONGPROFITFACTOR>" + getProfitFactorLong() + "</LONGPROFITFACTOR>");
        sb.append("<LONGSHARPERATIO>" + getTradeSharpeRatioLong() + "</LONGSHARPERATIO>");
        sb.append("<LONGVARIANCE>" + getTradeVarianceLong() + "</LONGVARIANCE>");
        sb.append("<LONGSTDDEV>" + getTradeStdDevLong() + "</LONGSTDDEV>");

        sb.append("<SHORTAVGWL>" + getTradeMeanShort() + "</SHORTAVGWL>");
        sb.append("<SHORTMAXWIN>" + getTradeMaxWinShort() + "</SHORTMAXWIN>");
        sb.append("<SHORTMAXWINDATE>" + getTradeMaxWinDateShort() + "</SHORTMAXWINDATE>");
        sb.append("<SHORTMAXLOSS>" + getTradeMaxLossShort() + "</SHORTMAXLOSS>");
        sb.append("<SHORTMAXLOSSDATE>" + getTradeMaxLossDateShort() + "</SHORTMAXLOSSDATE>");
        sb.append("<SHORTNTRADES>" + getNTradesShort() + "</SHORTNTRADES>");
        sb.append("<SHORTNWINTRADES>" + getNWinTradesShort() + "</SHORTNWINTRADES>");
        sb.append("<SHORTNLOSSTRADES>" + getNLossTradesShort() + "</SHORTNLOSSTRADES>");
        sb.append("<SHORTPERCPROFITABLE>" + getTradesPercentProfitableShort() + "</SHORTPERCPROFITABLE>");
        sb.append("<SHORTNCONWINTRADES>" + getNMaxConsecWinTradesShort() + "</SHORTNCONWINTRADES>");
        sb.append("<SHORTNCONLOSSTRADES>" + getNMaxConsecLossTradesShort() + "</SHORTNCONLOSSTRADES>");
        sb.append("<SHORTAVGWINTRADE>" + getTradeMeanWinShort() + "</SHORTAVGWINTRADE>");
        sb.append("<SHORTAVGLOSSTRADE>" + getTradeMeanLossShort() + "</SHORTAVGLOSSTRADE>");
        sb.append("<SHORTGROSSPROFIT>" + getGrossProfitShort() + "</SHORTGROSSPROFIT>");
        sb.append("<SHORTGROSSLOSS>" + getGrossLossShort() + "</SHORTGROSSLOSS>");
        sb.append("<SHORTNEPROFIT>" + getNetProfitShort() + "</SHORTNEPROFIT>");
        sb.append("<SHORTPROFITFACTOR>" + getProfitFactorShort() + "</SHORTPROFITFACTOR>");
        sb.append("<SHORTSHARPERATION>" + getTradeSharpeRatioShort() + "</SHORTSHARPERATION>");
        sb.append("<SHORTVARIANCE>" + getTradeVarianceShort() + "</SHORTVARIANCE>");
        sb.append("<SHORTSTDDEV>" + getTradeStdDevShort() + "</SHORTSTDDEV>");
        sb.append("</TESTRESULT>");

        LogUtils.debug("sb.toString() = " + sb.toString());
        return sb.toString();
    }

    public double getAccountWealth() {
        return accountWealth;
    }

    public void setAccountWealth(double accountWealth) {
        this.accountWealth = accountWealth;
    }

    public double getInvestedWealth() {
        return investedWealth;
    }

    public void setInvestedWealth(double investedWealth) {
        this.investedWealth = investedWealth;
    }

    public double getTotalWealth() {
        return totalWealth;
    }

    public void setTotalWealth(double totalWealth) {
        this.totalWealth = totalWealth;
    }

    public TimeSeries getTradeDistributionTotal() {
        return tradeDistributionTotal;
    }

    public void setTradeDistributionTotal(TimeSeries tradeDistributionTotal) {
        this.tradeDistributionTotal = tradeDistributionTotal;
    }

    public TimeSeries getTradeDistributionLong() {
        return tradeDistributionLong;
    }

    public void setTradeDistributionLong(TimeSeries tradeDistributionLong) {
        this.tradeDistributionLong = tradeDistributionLong;
    }

    public TimeSeries getTradeDistributionShort() {
        return tradeDistributionShort;
    }

    public void setTradeDistributionShort(TimeSeries tradeDistributionShort) {
        this.tradeDistributionShort = tradeDistributionShort;
    }

    public TimeSeries getWealthUnderwaterLine() {
        return wealthUnderwaterLine;
    }

    public void setWealthUnderwaterLine(TimeSeries wealthUnderwaterLine) {
        this.wealthUnderwaterLine = wealthUnderwaterLine;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

//    public SignalSeries getSignalSeries() {
//        return signalSeries;
//    }
//
//    public void setSignalSeries(SignalSeries signalSeries) {
//        this.signalSeries = signalSeries;
//    }

    public TransactionSeries getTransactionSerie() {
        return transactionSerie;
    }

    public void setTransactionSerie(TransactionSeries transactionSerie) {
        this.transactionSerie = transactionSerie;
    }

    public int getNumberTradesTotal() {
        return numberTradesTotal;
    }

    public void setNumberTradesTotal(int numberTradesTotal) {
        this.numberTradesTotal = numberTradesTotal;
    }

    public int getNumberTradesLong() {
        return numberTradesLong;
    }

    public void setNumberTradesLong(int numberTradesLong) {
        this.numberTradesLong = numberTradesLong;
    }

    public int getNumberTradesShort() {
        return numberTradesShort;
    }

    public void setNumberTradesShort(int numberTradesShort) {
        this.numberTradesShort = numberTradesShort;
    }

    public int getNumberWinTradesTotal() {
        return numberWinTradesTotal;
    }

    public void setNumberWinTradesTotal(int numberWinTradesTotal) {
        this.numberWinTradesTotal = numberWinTradesTotal;
    }

    public int getNumberWinTradesLong() {
        return numberWinTradesLong;
    }

    public void setNumberWinTradesLong(int numberWinTradesLong) {
        this.numberWinTradesLong = numberWinTradesLong;
    }

    public int getNumberWinTradesShort() {
        return numberWinTradesShort;
    }

    public void setNumberWinTradesShort(int numberWinTradesShort) {
        this.numberWinTradesShort = numberWinTradesShort;
    }

    public int getNumberLossTradesTotal() {
        return numberLossTradesTotal;
    }

    public void setNumberLossTradesTotal(int numberLossTradesTotal) {
        this.numberLossTradesTotal = numberLossTradesTotal;
    }

    public int getNumberLossTradesLong() {
        return numberLossTradesLong;
    }

    public void setNumberLossTradesLong(int numberLossTradesLong) {
        this.numberLossTradesLong = numberLossTradesLong;
    }

    public int getNumberLossTradesShort() {
        return numberLossTradesShort;
    }

    public void setNumberLossTradesShort(int numberLossTradesShort) {
        this.numberLossTradesShort = numberLossTradesShort;
    }

    public int getNumberOpenPositionsTotal() {
        return numberOpenPositionsTotal;
    }

    public void setNumberOpenPositionsTotal(int numberOpenPositionsTotal) {
        this.numberOpenPositionsTotal = numberOpenPositionsTotal;
    }

    public int getNumberOpenPositionsLong() {
        return numberOpenPositionsLong;
    }

    public void setNumberOpenPositionsLong(int numberOpenPositionsLong) {
        this.numberOpenPositionsLong = numberOpenPositionsLong;
    }

    public int getNumberOpenPositionsShort() {
        return numberOpenPositionsShort;
    }

    public void setNumberOpenPositionsShort(int numberOpenPositionsShort) {
        this.numberOpenPositionsShort = numberOpenPositionsShort;
    }

    public long getNumberTradeDays() {
        return numberTradeDays;
    }

    public void setNumberTradeDays(long numberTradeDays) {
        this.numberTradeDays = numberTradeDays;
    }

    public int getNumberHoldDays() {
        return numberHoldDays;
    }

    public void setNumberHoldDays(int numberHoldDays) {
        this.numberHoldDays = numberHoldDays;
    }

    public int getNumberMeanBarsInWinTotal() {
        return numberMeanBarsInWinTotal;
    }

    public void setNumberMeanBarsInWinTotal(int numberMeanBarsInWinTotal) {
        this.numberMeanBarsInWinTotal = numberMeanBarsInWinTotal;
    }

    public int getNumberMeanBarsInWinLong() {
        return numberMeanBarsInWinLong;
    }

    public void setNumberMeanBarsInWinLong(int numberMeanBarsInWinLong) {
        this.numberMeanBarsInWinLong = numberMeanBarsInWinLong;
    }

    public int getNumberMeanBarsInWinShort() {
        return numberMeanBarsInWinShort;
    }

    public void setNumberMeanBarsInWinShort(int numberMeanBarsInWinShort) {
        this.numberMeanBarsInWinShort = numberMeanBarsInWinShort;
    }

    public int getNumberMeanBarsInLossTotal() {
        return numberMeanBarsInLossTotal;
    }

    public void setNumberMeanBarsInLossTotal(int numberMeanBarsInLossTotal) {
        this.numberMeanBarsInLossTotal = numberMeanBarsInLossTotal;
    }

    public int getNumberMeanBarsInLossLong() {
        return numberMeanBarsInLossLong;
    }

    public void setNumberMeanBarsInLossLong(int numberMeanBarsInLossLong) {
        this.numberMeanBarsInLossLong = numberMeanBarsInLossLong;
    }

    public int getNumberMeanBarsInLossShort() {
        return numberMeanBarsInLossShort;
    }

    public void setNumberMeanBarsInLossShort(int numberMeanBarsInLossShort) {
        this.numberMeanBarsInLossShort = numberMeanBarsInLossShort;
    }

    public double getMaxWinTotal() {
        return maxWinTotal;
    }

    public void setMaxWinTotal(double maxWinTotal) {
        this.maxWinTotal = maxWinTotal;
    }

    public double getMaxWinLong() {
        return maxWinLong;
    }

    public void setMaxWinLong(double maxWinLong) {
        this.maxWinLong = maxWinLong;
    }

    public double getMaxWinShort() {
        return maxWinShort;
    }

    public void setMaxWinShort(double maxWinShort) {
        this.maxWinShort = maxWinShort;
    }

    public double getMaxLossTotal() {
        return maxLossTotal;
    }

    public void setMaxLossTotal(double maxLossTotal) {
        this.maxLossTotal = maxLossTotal;
    }

    public double getMaxLossLong() {
        return maxLossLong;
    }

    public void setMaxLossLong(double maxLossLong) {
        this.maxLossLong = maxLossLong;
    }

    public double getMaxLossShort() {
        return maxLossShort;
    }

    public void setMaxLossShort(double maxLossShort) {
        this.maxLossShort = maxLossShort;
    }

    public double getOpenPositionTotal() {
        return openPositionTotal;
    }

    public void setOpenPositionTotal(double openPositionTotal) {
        this.openPositionTotal = openPositionTotal;
    }

    public double getOpenPositionLong() {
        return openPositionLong;
    }

    public void setOpenPositionLong(double openPositionLong) {
        this.openPositionLong = openPositionLong;
    }

    public double getOpenPositionShort() {
        return openPositionShort;
    }

    public void setOpenPositionShort(double openPositionShort) {
        this.openPositionShort = openPositionShort;
    }

    public ArrayList getTradeList() {
        return tradeList;
    }

    public void setTradeList(ArrayList tradeList) {
        this.tradeList = tradeList;
    }
}
