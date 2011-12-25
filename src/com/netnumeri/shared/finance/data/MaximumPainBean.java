package com.netnumeri.shared.finance.data;


import java.util.Date;

public class MaximumPainBean {
    Date expirationDay;
    double cumulative;
    double strike;

    public MaximumPainBean() {
    }

    public Date getExpirationDay() {
        return expirationDay;
    }

    public void setExpirationDay(Date expirationDay) {
        this.expirationDay = expirationDay;
    }

    public double getCumulative() {
        return cumulative;
    }

    public void setCumulative(double cumulative) {
        this.cumulative = cumulative;
    }

    public double getStrike() {
        return strike;
    }

    public void setStrike(double strike) {
        this.strike = strike;
    }
}
