package com.netnumeri.shared.finance.ta;


import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.beans.TimeSeries;
import com.netnumeri.shared.finance.data.DateBound;
import com.netnumeri.shared.finance.data.Transaction;
import com.netnumeri.shared.finance.data.TransactionSeries;
import com.netnumeri.shared.finance.date.*;
import com.netnumeri.shared.finance.finpojo.*;
import com.netnumeri.shared.finance.finpojo.Parameters;
import com.netnumeri.shared.finance.finpojo.Portfolio;

import java.util.Map;


public abstract class Strategy extends DateBound implements FinConstants {

    private StrategyBacktest tester;

    private Portfolio portfolio;

    private Portfolio strategyPortfolio;

    double wealth = 0;

    private TransactionSeries transactionSeries = null;

    private Parameters parm = new Parameters();

    protected Strategy() {
    }

    public Strategy(String name, final Portfolio portfolio, TDay firstDate, TDay lastDate, double wealth) {
        this.transactionSeries = new TransactionSeries(name);
        this.wealth = wealth;
        this.strategyPortfolio = portfolio.clone();
        this.portfolio = portfolio;
        tester = new StrategyBacktest(this, wealth);
        setWindow(firstDate, lastDate);
        setRangeBounds(firstDate, lastDate);
    }

    public Portfolio getStrategyPortfolio() {
        return strategyPortfolio;
    }

    public void setStrategyPortfolio(Portfolio strategyPortfolio) {
        this.strategyPortfolio = strategyPortfolio;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public StrategyBacktest getTester() {
        return tester;
    }

    public void add(Transaction transaction) {
        transactionSeries.add(transaction);
        PortfolioUtils.add(strategyPortfolio, transaction);
    }

    public void add(Instrument instrument, int action, int amount, double price, TDay date) {
        add(new Transaction(instrument, action, amount, price, date));
    }

    public void test(TDay firsDate, TDay lasDate) {
        tester.test(firsDate, lasDate, kInvestOnDate);
    }

    public TimeSeries getProfitLossSeries() {
        return tester.getPnLSeries();
    }

    public TimeSeries getWealthSeries() {
        return tester.getWealthSeries();
    }

    abstract public void evaluatePortfolioOnDate(TDay date, Map signals);

    abstract public void evaluateInstrumentOnDate(TDay date, Instrument asset);

    public double objective() {
        if (tester != null) {
            tester = new StrategyBacktest(this, wealth);
        }
        double objective = -tester.test(null, null, kInvestOnDate);
        return objective;
    }


    public Parameters getParm() {
        return parm;
    }

    public void setParm(Parameters parm) {
        this.parm = parm;
    }

    public double getWealth() {
        return wealth;
    }

    public void setWealth(double wealth) {
        this.wealth = wealth;
    }

    public TransactionSeries getTransactionSeries() {
        return transactionSeries;
    }

    public void setTransactionSeries(TransactionSeries transactionSeries) {
        this.transactionSeries = transactionSeries;
    }

}
