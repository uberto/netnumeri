package com.netnumeri.shared.finance.data;


import java.util.Currency;

public class Limit {
    //  Currency currency = new Currency("EUR",3);
    MoneyAmount total = new MoneyAmount();
    MoneyAmount boughtUsed = new MoneyAmount();
    MoneyAmount soldUsed = new MoneyAmount();
    MoneyAmount netUsed = new MoneyAmount();
    MoneyAmount grossUsed = new MoneyAmount();

    public Limit() {
    }

    public Limit(Currency currency, MoneyAmount total, MoneyAmount boughtUsed, MoneyAmount soldUsed, MoneyAmount netUsed, MoneyAmount grossUsed) {
        //   this.currency = currency;
        this.total = total;
        this.boughtUsed = boughtUsed;
        this.soldUsed = soldUsed;
        this.netUsed = netUsed;
        this.grossUsed = grossUsed;
    }

    public MoneyAmount getTotal() {
        return total;
    }

    public void setTotal(MoneyAmount total) {
        this.total = total;
    }

    public MoneyAmount getBoughtUsed() {
        return boughtUsed;
    }

    public void setBoughtUsed(MoneyAmount boughtUsed) {
        this.boughtUsed = boughtUsed;
    }

    public MoneyAmount getSoldUsed() {
        return soldUsed;
    }

    public void setSoldUsed(MoneyAmount soldUsed) {
        this.soldUsed = soldUsed;
    }

    public MoneyAmount getNetUsed() {
        return netUsed;
    }

    public void setNetUsed(MoneyAmount netUsed) {
        this.netUsed = netUsed;
    }

    public MoneyAmount getGrossUsed() {
        return grossUsed;
    }

    public void setGrossUsed(MoneyAmount grossUsed) {
        this.grossUsed = grossUsed;
    }

/*
    public void doView(HttpServletRequest request, HttpServletResponse response, Writer out) throws Throwable
    {
        HTMLTable table = new HTMLTable();
        table.addLine("Total", this.getTotal());
        table.addLine("Bought", this.getBoughtUsed());
        table.addLine("Sold", this.getSoldUsed());
        table.addLine("Net", this.getNetUsed());
        table.addLine("Gross", this.getGrossUsed());
        table.doView(request, response, out);
    }
*/

}