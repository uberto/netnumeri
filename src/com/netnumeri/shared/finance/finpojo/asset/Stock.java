package com.netnumeri.shared.finance.finpojo.asset;

import com.netnumeri.shared.finance.data.Dividend;
import com.netnumeri.shared.finance.data.DividendArray;
import com.netnumeri.shared.finance.data.Split;
import com.netnumeri.shared.finance.data.SplitArray;

import java.io.Serializable;
import java.util.Date;

public class Stock extends Asset implements Serializable {

    DividendArray dividendArray = null;
    SplitArray splitArray = null;

    public Stock() {
        super();
    }

    public Stock(String name) {
        super(name);
    }

    public double modelPrice(int model) {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    public DividendArray getDividendArray() {
        return dividendArray;
    }

    public void setDividendArray(DividendArray Array) {
        dividendArray = Array;
    }

    public void addDividend(Dividend dividend) {
        if (dividendArray == null) {
            dividendArray = new DividendArray();
        }
        dividendArray.insert(dividend);
    }

    public void addDividend(Date date, double dividend) {
        if (dividendArray == null) {
            dividendArray = new DividendArray();
        }
        dividendArray.insert(date, dividend);
    }

    public Dividend getDividend(Date date) {
        if (dividendArray != null) {
            return dividendArray.getDividend(date);
        } else {
            return null;
        }
    }

    public SplitArray splitArray() {
        return splitArray;
    }

    public void setSplitArray(SplitArray array) {
        splitArray = array;
    }

    public void addSplit(Split split) {
        if (splitArray == null) {
            splitArray = new SplitArray();
        }
        splitArray.insert(split);
    }

    public void addSplit(Date date, double split) {
        if (splitArray == null) {
            splitArray = new SplitArray();
        }
        splitArray.insert(date, split);
    }

    public Split getSplit(Date date) {
        if (splitArray != null) {
            return splitArray.getSplit(date);
        } else {
            return null;
        }
    }

    public Split getSplit(int i) {
        if (splitArray != null) {
            return splitArray.getSplit(i);
        } else {
            return null;
        }
    }


}
