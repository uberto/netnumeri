package com.netnumeri.shared.finance.data;

import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.finpojo.NamedObject;

import java.util.Date;

public class Dividend extends NamedObject {
    private double amount;       // dividend amount
    private int divtype;         // Type (kPercentDividend, kCashDividend, kContinuousDividend, kGrowthRateDividend)
    private Date paymentDate = null;  // dividend payment date
    private Date date = null;

    public Dividend() {
    }

    public Dividend(Date d, double Amount) {
        date = d;
        amount = Amount;
        divtype = FinConstants.kPercentDividend;
    }

    public Dividend(Date d, double amount, int type, Date paymentDate) {
        paymentDate = new Date();
        date = d;
        this.amount = amount;
        this.divtype = type;
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public int getDivtype() {
        return divtype;
    }

    public Date getPaymentDate() {
        Date d = paymentDate;
        return d;
    }

    public void setAmount(double Amount) {
        amount = Amount;
    }

    public void setDivtype(int Type) {
        divtype = Type;
    }

    public void setPaymentDate(Date date) {
        paymentDate = date;
    }

    public void set(Date date, double amount) {
        set(date, amount, FinConstants.kPercentDividend, null);
    }

    public void set(Date date, double amount, int type, Date paymentDate) {
        paymentDate = date;
        this.amount = amount;
        this.divtype = type;
        this.paymentDate = paymentDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int compareTo(java.lang.Object obj) {
        String strDate = (String) getKey();
        return strDate.compareTo((String) obj);
    }

    public Object getKey() {
        return date;
    }
}
