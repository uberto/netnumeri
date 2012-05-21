package com.netnumeri.shared.finance.ta;

import com.netnumeri.shared.finance.data.Transaction;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.Instrument;
import com.netnumeri.shared.finance.finpojo.Portfolio;
import com.netnumeri.shared.finance.finpojo.PortfolioHelper;

import java.util.Map;

public class SMACrossover extends Strategy {

    private int sma1 = 0;
    private int sma2 = 0;

    private boolean foundABUY = false;

    public SMACrossover(String name,
                        Portfolio portfolio,
                        TDay firstDate,
                        TDay lastDate,
                        double wealth,
                        int sma1,
                        int sma2) {
        super(name, portfolio, firstDate, lastDate, wealth);
        this.sma1 = sma1;
        this.sma2 = sma2;
    }

    public void evaluatePortfolioOnDate(TDay date, Map signals) {
        for (int i = 0; i < getPortfolio().items.size(); i++) {
            Instrument asset = PortfolioHelper.getInstrument(getPortfolio(),i);
            if (asset.isDataAvailable(date)) {
                evaluateInstrumentOnDate(date, asset);
            }
        }
    }

    @Override
//    public void evaluateInstrumentOnDate(TDay date, Instrument asset) {
//
//        System.out.println("SMACrossover.evaluateInstrumentOnDate");
//        System.out.println("date = " + date);
//
//        int sma = SignalSeries.SMA(asset, date, sma1, sma2, PRICE);
//        int amount = 100;
//        if (sma == SELL) {
//            if (foundABUY) {
//                System.out.println("SELL");
//                Transaction transaction = new Transaction(asset, SELL, amount, asset.getClose(date), date);
//                add(transaction);
//                foundABUY = false;
//            }
//        }
//        else if (sma == BUY) {
//            System.out.println("BUY");
//            Transaction transaction = new Transaction(asset, BUY, amount, asset.getClose(date), date);
//            add(transaction);
//            foundABUY = true;
//        }
//    }

    public void evaluateInstrumentOnDate(TDay date, Instrument asset) {

        System.out.println("SMACrossover.evaluateInstrumentOnDate");

        int amount = 1000;

//        int sma = SignalSeries.SMA(asset, date, sma1, sma2, PRICE);
        int sma = 0;
        if (sma == SELL) {
//            if (foundABUY) {
            System.out.println("SELL");
            Transaction transaction = new Transaction(asset, SELL, amount, asset.getClose(date), date);
            add(transaction);
//                foundABUY = false;
//            }
        } else if (sma == BUY) {
            System.out.println("BUY");
            Transaction transaction = new Transaction(asset, BUY, amount, asset.getClose(date), date);
            add(transaction);
//            foundABUY = true;
        }
    }


}



