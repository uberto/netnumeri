package com.netnumeri.shared.finance.data;


import com.netnumeri.shared.finance.finpojo.NamedObject;

import java.util.Date;

public class Coupon extends NamedObject {
    double rate;
    private Date date = null;

    public Coupon() {
    }

    public Coupon(Date d, double rate) {
        date = d;
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

    public void set(Coupon c) {
        date = c.getDate();
        rate = c.getRate();
    }

    public void setRate(double Rate) {
        rate = Rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int compareTo(java.lang.Object obj) {
        return date.compareTo((Date) obj);
    }

    public Object getKey() {
        return date;
    }

    /* public void doView(HttpServletRequest request, HttpServletResponse response, Writer out) throws Throwable
        {
            HTMLTable table = new HTMLTable();
            table.addLine("Date", date);
            table.addLine("Rate", rate);
            table.doView(request, response, out);
        }

    */
}

