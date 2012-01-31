package com.netnumeri.shared.entity;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.*;

public class Option implements Entity, IsSerializable {

    String optionName;
    String stockTicket;
    OptionType type;
    Double strike;
    Date dateDue;


    private EntityValue<String> value;// needed only to the serialized whitelist


    private Map<EntityField, EntityValue> values = new HashMap<EntityField, EntityValue>();


    static final StringEntityField NAME = new StringEntityField(22, "name");
    static final StringEntityField STOCK_TICKET = new StringEntityField(5, "stock");

    public Option(String optionName, String stockTicket, OptionType type, Double strike, Date dateDue) {
        this.optionName = optionName;
        this.stockTicket = stockTicket;
        this.type = type;
        this.strike = strike;
        this.dateDue = dateDue;
//
        values.put(NAME, new EntityValue( optionName));
        values.put(STOCK_TICKET, new EntityValue( stockTicket));
//
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

    @Override
    public EntityId getId() {
        return null;
    }

    @Override
    public Set<EntityField<?>> getFields() {
        return new HashSet(Arrays.asList(NAME, STOCK_TICKET));
    }

    @Override
    public EntityValue get(EntityField field) {
        return values.get(field);
    }

}
