package com.netnumeri.shared.finance.data;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;

public class SplitArray extends LinkedHashMap implements Serializable {
    public SplitArray() {
        super();
    }

    public void insert(Split c) {
        put(c.getDate(), c);
    }

    public void insert(Date date, double split) {
        put(date, new Split(date, split));
    }

    public Split getSplit(Date date) {
        Split s = new Split();
        s.set((Split) get(date));
        return s;
    }

    public Split getSplit(int i) {
        Split s = new Split();
        s.set((Split) get(i));
        return s;
    }

}