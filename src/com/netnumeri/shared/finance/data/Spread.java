package com.netnumeri.shared.finance.data;



import java.util.Date;

public class Spread {
    private double bid = 0;  // bid premium per share
    private double ask = 0;  // ask premium per share
    private int bidSize = 0;  // bid size in number of round lot (100 shares)
    private int askSize = 0;  // ask size in number of round lot (100 shares)
    private Date date = null;

    /**
     * Spread normal constructor
     */
    public Spread(Date f4JDateTime, double bid, int bidSize, double ask, int askSize) {
        date = f4JDateTime;
        this.bid = bid;
        this.ask = ask;
        this.bidSize = bidSize;
        this.askSize = askSize;
    }

    public Date getDate() {
        return date;
    }

    public void set(Date d, int t, double bid, int bidSize, double ask, int askSize) {
        date = d;
        this.bid = bid;
        this.ask = ask;
        this.bidSize = bidSize;
        this.askSize = askSize;
    }


    public void set(Spread spread) {
        date = (spread.date);
        bid = spread.bid;
        ask = spread.ask;
        bidSize = spread.bidSize;
        askSize = spread.askSize;
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
}
