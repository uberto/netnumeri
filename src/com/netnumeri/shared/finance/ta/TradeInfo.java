package com.netnumeri.shared.finance.ta;

import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.Instrument;

public class TradeInfo {
    private double tradeOpen;
    private double tradeHigh;
    private double tradeLow;
    private double tradeClose;
    private double tradeResult;
    private int tradeDuration;
    private int tradeType;
    private TDay date;
    private Instrument instrument;

    public TradeInfo(double tradeOpen,
                     double tradeHigh,
                     double tradeLow,
                     double tradeClose,
                     double tradeResult,
                     int tradeType,
                     int tradeDuration,
                     TDay tradeDate,
                     Instrument instrument) {
        this.tradeOpen = tradeOpen;
        this.tradeHigh = tradeHigh;
        this.tradeLow = tradeLow;
        this.tradeClose = tradeClose;
        this.tradeResult = tradeResult;
        this.tradeDuration = tradeDuration;
        this.tradeType = tradeType;
        this.date = (TDay) tradeDate.clone();
        this.instrument = instrument;
    }

    public double getOpenPrice() {
        return tradeOpen;
    }

    public double getHighPrice() {
        return tradeHigh;
    }

    public double getLowPrice() {
        return tradeLow;
    }

    public double getClosePrice() {
        return tradeClose;
    }

    public double getTradeResult() {
        return tradeResult;
    }

    public int getTradeType() {
        return tradeType;
    }

    public double getTradeOpen() {
        return tradeOpen;
    }

    public void setTradeOpen(double tradeOpen) {
        this.tradeOpen = tradeOpen;
    }

    public double getTradeHigh() {
        return tradeHigh;
    }

    public void setTradeHigh(double tradeHigh) {
        this.tradeHigh = tradeHigh;
    }

    public double getTradeLow() {
        return tradeLow;
    }

    public void setTradeLow(double tradeLow) {
        this.tradeLow = tradeLow;
    }

    public double getTradeClose() {
        return tradeClose;
    }

    public void setTradeClose(double tradeClose) {
        this.tradeClose = tradeClose;
    }

    public int getTradeDuration() {
        return tradeDuration;
    }

    public void setTradeDuration(int tradeDuration) {
        this.tradeDuration = tradeDuration;
    }

    public TDay getDate() {
        return date;
    }

    public void setDate(TDay date) {
        this.date = date;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public int compareTo(java.lang.Object obj) {
        String strDate = (String) getKey();
        return strDate.compareTo((String) obj);
    }

    public Object getKey() {
        return date;
    }


}

