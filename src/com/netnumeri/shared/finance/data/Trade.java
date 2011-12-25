package com.netnumeri.shared.finance.data;

import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.finpojo.NamedObject;

import java.util.Date;

public class Trade extends NamedObject implements FinConstants {
    double price = 0;
    int size = 0;
    private Date date;
//    private long time;


    public Trade() {
    }

    public Trade(String name) {
        super(name);
        //       time = 0;
        price = 0;
        size = 0;
        date = new Date();
    }

    public Trade(String name, Trade trade) {
        super(name);
        date = trade.date;
//        time = trade.time;
        price = trade.price;
        size = trade.size;
    }

    public Trade(String name, Date f4JDateTime, double price, int size) {
        super(name);
        date = f4JDateTime;
        //     time = f4JDateTime.getTime();
        this.price = price;
        this.size = size;
    }

    public Trade(String name, Date d, int t, double price, int size) {
        super(name);
        date = d;
        //    time = t;
        this.price = price;
        this.size = size;
    }

    public void set(Date d, double price, int size) {
        date = d;
        //       time = t;
        this.price = price;
        this.size = size;
    }

    public void set(Trade trade) {
        date = trade.date;
//        time = trade.time;
        price = trade.price;
        size = trade.size;
        //  exchange = Trade->exchange;
    }


    public boolean isValid() {
        if (date == null) {
            return false;
        }
        if (price == 0 && size == 0) {
            return false;
        }
        if (price < 0 || size < 0) {
            return false;
        }
        return true;
    }

    public Date getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public int getSize() {
        return size;
    }

    public void setDate(Date d) {
        date = d;
    }

    public void setPrice(double Price) {
        price = Price;
    }

    public void setSize(int Size) {
        size = Size;
    }

    public int compareTo(java.lang.Object obj) {
        String strDateTime = (String) getKey();
        return strDateTime.compareTo((String) obj);
    }

    public Object getKey() {
        return date;
    }
}
