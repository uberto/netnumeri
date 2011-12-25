package com.netnumeri.shared.finance.finpojo;


import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.date.TDay;

import java.io.Serializable;
//import java.util.Date;

public class Daily implements Serializable {
    Long id;

    private Instrument instrument;

    private int arrayindex;
    private TDay dailydate = null;
    private int volume = 0;
    private double high = 0;
    private double low = 0;
    private double openprice = 0;
    private double closeprice = 0;
    private int state = FinConstants.NOTAVAILABLE;

    private double bid = 0;
    private double ask = 0;
    private int bidSize = 0;
    private int askSize = 0;
    private int openInterest;

    public Daily() {
        this.state = FinConstants.VALID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Daily data normal constructor
     */
    public Daily(Daily daily) {
        this.arrayindex = daily.arrayindex;
        this.dailydate = daily.dailydate;
        this.high = daily.high;
        this.low = daily.low;
        this.openprice = daily.openprice;
        this.closeprice = daily.closeprice;
        this.volume = daily.volume;
        this.state = daily.state;
        this.instrument = daily.instrument;
    }

    public Daily(Instrument instrument,
                 int arrayindex,
                 TDay d,
                 double high,
                 double low,
                 double open,
                 double close,
                 int volume, int oint,
                 int state) {
        this.instrument = instrument;
        this.arrayindex = arrayindex;
        this.dailydate = d;
        this.high = high;
        this.low = low;
        this.openprice = open;
        this.closeprice = close;
        this.volume = volume;
        openInterest = oint;
        this.state = state;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getOpenprice() {
        return openprice;
    }

    public void setOpenprice(double openprice) {
        this.openprice = openprice;
    }

    public double getCloseprice() {
        return closeprice;
    }

    public void setCloseprice(double closeprice) {
        this.closeprice = closeprice;
    }

    public int getOpenInterest() {
        return openInterest;
    }

    public void setOpenInterest(int openInterest) {
        this.openInterest = openInterest;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public TDay getDailydate() {
        return dailydate;
    }

    public void setDailydate(TDay date) {
        this.dailydate = date;
    }

    public boolean valid() {
        if (state == FinConstants.VALID)
            return true;
        else
            return false;
    }

    public int getArrayindex() {
        return arrayindex;
    }

    public void setArrayindex(int arrayindex) {
        this.arrayindex = arrayindex;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public int getBidSize() {
        return bidSize;
    }

    public void setBidSize(int bidSize) {
        this.bidSize = bidSize;
    }

    public int getAskSize() {
        return askSize;
    }

    public void setAskSize(int askSize) {
        this.askSize = askSize;
    }

    /*   public void set(Date date, double high, double low, double close, int volume, int option)
    {
        this.dailydate = date;
        this.high = high;
        this.low = low;
        openprice = 0;
        this.closeprice = close;
        this.volume = volume;
        this.state = option;
    }*/
/*
    public void set(Date date, double high, double low, double open, double close, int volume, int option)
    {
        this.dailydate = date;
        this.high = high;
        this.low = low;
        this.openprice = open;
        this.closeprice = close;
        this.volume = volume;
        this.state = option;
    }*/


    public double getPrice() {
        return getPrice(FinConstants.TYPICALPRICE);
    }

    public double getPrice(int Option) {
        switch (Option) {
            case FinConstants.MEDIANPRICE:
                return (high + low) / 2.0;
            case FinConstants.TYPICALPRICE:
                return (high + low + closeprice) / 3.0;
            case FinConstants.WEIGHTEDPRICE:
                return (high + low + 2.0 * closeprice) / 4.0;
            case FinConstants.AVERAGEPRICE:
                return (high + low + closeprice + openprice) / 4.0;
            case FinConstants.LOGAVERAGEPRICE:
                return Math.log((high + low + closeprice + openprice) / 4.0);
        }
        return 0;
    }

    /**
     * Return value of a data field HIGH LOW OPEN CLOSE VOLUME
     */
    public double get(int option) {
        switch (option) {
            case FinConstants.HIGH:
                return high;
            case FinConstants.LOW:
                return low;
            case FinConstants.OPEN:
                return openprice;
            case FinConstants.CLOSE:
                return closeprice;
            case FinConstants.VOLUME:
                return volume;
            case FinConstants.AVERAGEPRICE:
                return (high + low + closeprice + openprice) / 4D;
            case FinConstants.LOGAVERAGEPRICE:
                return Math.log((high + low + closeprice + openprice) / 4D);
            default:
                throw new IllegalStateException("Get: Invalid state: " + option);
        }

    }

    /* Set daily data
     *
     */
    public void set(Daily daily) {
        if (daily == null) {
            throw new IllegalArgumentException("Daily: set() " + "Argument is null");
        }
        arrayindex = daily.getArrayindex();
        dailydate = daily.dailydate;
        volume = daily.getVolume();
        high = daily.getHigh();
        low = daily.getLow();
        openprice = daily.getOpenprice();
        closeprice = daily.getCloseprice();
        state = daily.getState();
    }


    public void setKey(Object key) {
        key = dailydate;
    }

    public Object clone() {
        Daily q = new Daily();
        q.arrayindex = this.arrayindex;
        q.volume = this.volume;
        q.high = this.high;
        q.low = this.low;
        q.openprice = this.openprice;
        q.closeprice = this.closeprice;
        q.openInterest = this.openInterest;
        q.state = this.state;
        q.dailydate = (TDay) dailydate.clone();
        return q;
    }

    public Instrument getInstrument() {
        return this.instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
}
