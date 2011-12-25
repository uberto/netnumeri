package com.netnumeri.shared.finance.data;

import com.netnumeri.shared.finance.finpojo.Parameters;

public class TransactionCost extends Parameters {
    private int option; // transaction cost option

    public TransactionCost() {
        super();
    }

    /**
     * Transaction cost normal constructor
     */
    public TransactionCost(double costPerContract, int option) {
        super(costPerContract);
        this.option = option;
    }

    /**
     * Transaction cost normal constructor
     */
    public TransactionCost(double costPerContract, double costPerTicket, double costPerValue) {
        super(costPerContract, costPerTicket, costPerValue);
        option = kMixed;
    }

    /**
     * (CostPerContract, CostPerTicket, CostPerValue, CostPerHoldingDay)
     */
    public TransactionCost(double costPerContract, double costPerTicket, double costPerValue, double costPerHoldingDay) {
        super(costPerContract, costPerTicket, costPerValue, costPerHoldingDay);
        option = kComplete;
    }

    /**
     * Return transaction cost which might be a function of transaction amount
     */
    public double getCost(double amount) {
        switch (option) {
            case kPercent:
                return Math.abs(amount * parameter[0]);
            case kFixed:
                return Math.abs(parameter[0]);
            case kMixed:
                if (amount < parameter[1]) {
                    return Math.abs(parameter[0]);
                } else {
                    return Math.abs(amount * parameter[2]);
                }
        }
        return 0;
    }

    /**
     * Return transaction cost for a given quote, buy/sell signal, amount to trade and expected holding period in days
     */
    /*public double getCost(Quote quote,
                          int buySell,
                          int amount,
                          int holdingPeriod)
    {
        double price;
        if (buySell == BUY)
            price = quote.getAsk();
        else
            price = quote.getBid();

        return parameter[0] +
                parameter[1] * Math.abs(amount) +
                parameter[2] * Math.abs(amount) * price -
                parameter[3] * holdingPeriod * amount * price;
    }*/
    public double getCost(int amount,
                          double price,
                          double holdingPeriod) {
        return parameter[0] +
                parameter[1] * Math.abs(amount) +
                parameter[2] * Math.abs(amount) * price -
                parameter[3] * holdingPeriod * amount * price;
    }

}


