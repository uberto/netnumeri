package com.netnumeri.shared.finance.ta;

import com.netnumeri.shared.finance.date.TDay;

public class DateValue {

    TDay date;
    double value;

    public DateValue() {
    }

    public TDay getDate() {
        return date;
    }

    public void setDate(TDay date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
