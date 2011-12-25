package com.netnumeri.shared.finance.data;



import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;

public class DividendArray extends LinkedHashMap implements Serializable {
    public DividendArray() {
        super();
    }

    public void insert(Dividend c) {
        put(c.getDate(), c);
    }

    public void insert(Date date, double amount) {
        put(date, new Dividend(date, amount));
    }

    /* public Dividend getDividend(int i)
    {
        Dividend d = (Dividend) get(i);
        return d;
    }*/

    public Dividend getDividend(Date date) {
        Dividend d = (Dividend) get(date);
        return d;
    }

}

