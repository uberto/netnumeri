package com.netnumeri.shared.entity;

import com.netnumeri.shared.field.*;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Option implements Entity {

     enum Field implements FieldName {
        bourse, name, underlying, type, strike, expiry;

        @Override
        public String getFieldName() {
            return name();
        }
    }

    //TODO put the field class inside the enum


    StringEntityField NAME = new StringEntityField(22, Field.name);
      StringEntityField UNDERLYING = new StringEntityField(5, Field.underlying);
     EnumEntityField<OptionType> TYPE = new EnumEntityField<OptionType>(Field.type);
     DoubleEntityField STRIKE = new DoubleEntityField(Field.strike);
     DateEntityField EXPIRY = new DateEntityField(Field.expiry);
     StringEntityField BOURSE = new StringEntityField(20, Field.bourse);

    public Option(String optionName, String stockTicket, OptionType type, Double strike, Date expiry) {

        BOURSE.setValue("NasdaqGS");
        NAME.setValue(optionName);
        UNDERLYING.setValue(stockTicket);
        TYPE.setValue(type);
        STRIKE.setValue(strike);
        EXPIRY.setValue(expiry);

    }

    private Option() {
    }

    public String getOptionName() {
        return NAME.get();
    }

    public String getStockTicket() {
        return UNDERLYING.get();
    }

    public OptionType getType() {
        return (OptionType) TYPE.get();
    }

    public Double getStrike() {
        return STRIKE.get();
    }

    public Date getDateDue() {
        return EXPIRY.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        if (BOURSE != null ? !BOURSE.equals(option.BOURSE) : option.BOURSE != null) return false;
        if (EXPIRY != null ? !EXPIRY.equals(option.EXPIRY) : option.EXPIRY != null) return false;
        if (NAME != null ? !NAME.equals(option.NAME) : option.NAME != null) return false;
        if (STRIKE != null ? !STRIKE.equals(option.STRIKE) : option.STRIKE != null) return false;
        if (TYPE != null ? !TYPE.equals(option.TYPE) : option.TYPE != null) return false;
        if (UNDERLYING != null ? !UNDERLYING.equals(option.UNDERLYING) : option.UNDERLYING != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = NAME != null ? NAME.hashCode() : 0;
        result = 31 * result + (UNDERLYING != null ? UNDERLYING.hashCode() : 0);
        result = 31 * result + (TYPE != null ? TYPE.hashCode() : 0);
        result = 31 * result + (STRIKE != null ? STRIKE.hashCode() : 0);
        result = 31 * result + (EXPIRY != null ? EXPIRY.hashCode() : 0);
        result = 31 * result + (BOURSE != null ? BOURSE.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Option{" +
                "NAME=" + NAME +
                ", UNDERLYING=" + UNDERLYING +
                ", TYPE=" + TYPE +
                ", STRIKE=" + STRIKE +
                ", EXPIRY=" + EXPIRY +
                ", BOURSE=" + BOURSE +
                '}';
    }

    @Override
    public EntityId getId() {
        return null;
    }

    @Override
    public Set<EntityField<?>> getFields() {
        return new HashSet(Arrays.asList(NAME, UNDERLYING, BOURSE, TYPE, STRIKE, EXPIRY));
    }


}
