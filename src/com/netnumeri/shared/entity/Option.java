package com.netnumeri.shared.entity;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Date;

public class Option implements IsSerializable {

    String optionName;
    String stockTicket;
    OptionType type;
    Double strike;
    Date dateDue;

    public Option(String optionName, String stockTicket, OptionType type, Double strike, Date dateDue) {
        this.optionName = optionName;
        this.stockTicket = stockTicket;
        this.type = type;
        this.strike = strike;
        this.dateDue = dateDue;
    }

    private Option() {
    }

    public String getOptionName() {
        return optionName;
    }

    public String getStockTicket() {
        return stockTicket;
    }

    public OptionType getType() {
        return type;
    }

    public Double getStrike() {
        return strike;
    }

    public Date getDateDue() {
        return dateDue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        if (dateDue != null ? !dateDue.equals(option.dateDue) : option.dateDue != null) return false;
        if (optionName != null ? !optionName.equals(option.optionName) : option.optionName != null) return false;
        if (stockTicket != null ? !stockTicket.equals(option.stockTicket) : option.stockTicket != null) return false;
        if (strike != null ? !strike.equals(option.strike) : option.strike != null) return false;
        if (type != option.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = optionName != null ? optionName.hashCode() : 0;
        result = 31 * result + (stockTicket != null ? stockTicket.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (strike != null ? strike.hashCode() : 0);
        result = 31 * result + (dateDue != null ? dateDue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Option{" +
                "optionName='" + optionName + '\'' +
                ", stockTicket='" + stockTicket + '\'' +
                ", type=" + type +
                ", strike=" + strike +
                ", dateDue=" + dateDue +
                '}';
    }
}
