package com.netnumeri.shared.finance.data;


import com.netnumeri.shared.finance.finpojo.NamedObject;

import java.util.Date;

public class Split extends NamedObject {
    double factor = 0;
    private Date date = new Date();

    public Split() {
    }

    public Split(Date d, double factor) {
        date = d;
        this.factor = factor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double Factor) {
        factor = Factor;
    }

    /**
     * Set data fields
     */
    public void set(Date d, double factor) {
        date = d;
        this.factor = factor;
    }

    public void set(Split s) {
        date = s.getDate();
        factor = s.getFactor();
    }

    public int compareTo(java.lang.Object obj) {
        String strDate = (String) getKey();
        return strDate.compareTo((String) obj);
    }

    public Object getKey() {
        return date;
    }

    /*   public void doView(HttpServletRequest request, HttpServletResponse response, Writer out) throws Throwable
    {
        HTMLTable table = new HTMLTable();
        table.addLine("Quote Date", date);
        table.addLine("Factor", "" + factor);
        table.doView(request, response, out);
    }*/

}
