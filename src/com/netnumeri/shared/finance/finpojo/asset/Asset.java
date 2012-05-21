package com.netnumeri.shared.finance.finpojo.asset;


import com.netnumeri.shared.finance.finpojo.Instrument;

import java.io.Serializable;

public abstract class Asset extends Instrument implements Serializable {

    public Asset() {
    }

    public Asset(String name) {
        setName(name);
    }

    /**
     * Calculate CAPM beta with Benchmark representing market portfolio
     */
    public double getBeta(Asset index) {
        return getCovariance(index) / index.variance();
    }

    /**
     * Calculate CAPM expected return excess with Benchmark representing market portfolio
     */
    public double getExcess(Asset index, double interestRate) {
        return interestRate + getBeta(index) * ((Math.pow(index.expectedReturn(), 365) - 1) * 100 - interestRate);
    }

    /**
     * Calculate Sharpe index
     */
    public double getSharpeIndex(double interestRate) {
        return ((Math.pow(expectedReturn(), 365) - 1) * 100 - interestRate) / standardDeviation();
    }

    /**
     * Calculate Traynor index
     */
    public double getTreynorIndex(Asset index, double interestRate) {
        return ((Math.pow(expectedReturn(), 365) - 1) * 100 - interestRate) / getBeta(index);
    }

}

