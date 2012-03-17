package com.netnumeri.shared.finance.data;

import com.netnumeri.shared.finance.finpojo.Parameters;

public class TransactionCost extends Parameters {
    private int option;

    public TransactionCost() {
        super();
    }


    public TransactionCost(double costPerContract, int option) {
        super(costPerContract);
        this.option = option;
    }


    public TransactionCost(double costPerContract, double costPerTicket, double costPerValue) {
        super(costPerContract, costPerTicket, costPerValue);
        option = kMixed;
    }


    public TransactionCost(double costPerContract, double costPerTicket, double costPerValue, double costPerHoldingDay) {
        super(costPerContract, costPerTicket, costPerValue, costPerHoldingDay);
        option = kComplete;
    }

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

    public double getCost(int amount,
                          double price,
                          double holdingPeriod) {
        return parameter[0] +
                parameter[1] * Math.abs(amount) +
                parameter[2] * Math.abs(amount) * price -
                parameter[3] * holdingPeriod * amount * price;
    }

}


