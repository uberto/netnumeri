package com.netnumeri.shared.finance.data;


import com.netnumeri.shared.finance.finpojo.NamedObject;

public class MoneyAmount extends NamedObject {
    private double amount;

    public MoneyAmount() {
        super("");
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

//  Sets a amount amount to zero.

    public void zeroise() {
        amount = 0;
    }

//  Negates a amount amount.

    public void negate() {
        amount = -amount;
    }

    //        Returns true if amount is zero.
    public boolean zero() {
        if (amount == 0) {
            return true;
        } else {
            return false;
        }
    }

//  Returns true if amount is positive (>=0).

    public boolean positive() {
        if (amount >= 0) {
            return true;
        } else {
            return false;
        }
    }

//  Returns true if amount is negative (<=0).

    public boolean negative() {
        if (amount <= 0) {
            return true;
        } else {
            return false;
        }
    }

//  Returns true if amount is equal to the supplied amount.

    public boolean equals(MoneyAmount input) {
        if (input.getAmount() == getAmount()) {
            return true;
        } else {
            return false;
        }
    }

    //     Returns true if MoneyAmount is greater than supplied amount. (Equal returns false)
    public boolean greater(MoneyAmount input) {
        if (getAmount() > input.getAmount()) {
            return true;
        } else {
            return false;
        }
    }

    //  Returns true if MoneyAmount is less than supplied amount.
    public boolean less(MoneyAmount input) {
        if (getAmount() < input.getAmount()) {
            return true;
        } else {
            return false;
        }

    }

    /*   public void doView(HttpServletRequest request, HttpServletResponse response, Writer out) throws Throwable
    {
        HTMLTable table = new HTMLTable();
        table.addLine("Amount", this.getAmount());
        table.addLine("Currency", this.getFx());
        table.doView(request, response, out);
    }*/
}
