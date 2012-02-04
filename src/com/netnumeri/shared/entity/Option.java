package com.netnumeri.shared.entity;

import com.netnumeri.shared.field.*;

import java.util.*;

public class Option implements Entity {

    private Map<Field, EntityField<?>> fieldMap = new HashMap<Field, EntityField<?>>();

    enum Field implements FieldName {
        bourse, name, underlying, type, strike, expiry;

//        private FieldAttributes fieldAttributes;
//
//        Field(FieldAttributes fieldAttributes) {
//            this.fieldAttributes = fieldAttributes;
//        }
//
//        Field() {
//            this.fieldAttributes = new FieldAttributes("", 0);
//        }
//
//        @Override
//        public FieldAttributes getFieldAttributes() {
//            return fieldAttributes;
//        }
    }

    StringEntityField name = new StringEntityField(Field.name, new FieldAttributes("", 22));
    StringEntityField underlying = new StringEntityField(Field.underlying);
    EnumEntityField<OptionType> type = new EnumEntityField<OptionType>(Field.type);
    DoubleEntityField strike = new DoubleEntityField(Field.strike);
    DateEntityField expiry = new DateEntityField(Field.expiry);
    StringEntityField bourse = new StringEntityField(Field.bourse);

    public Option(String optionName, String stockTicket, OptionType type, Double strike, Date expiry) {

        this.bourse.setValue("NasdaqGS");
        this.name.setValue(optionName);
        this.underlying.setValue(stockTicket);
        this.type.setValue(type);
        this.strike.setValue(strike);
        this.expiry.setValue(expiry);

        fieldMap.put(Field.bourse, bourse);
        fieldMap.put(Field.name, name);
        fieldMap.put(Field.underlying, underlying);
        fieldMap.put(Field.type, this.type);
        fieldMap.put(Field.strike, this.strike);
        fieldMap.put(Field.expiry, this.expiry);

    }

    private Option() {
    }

    public String getOptionName() {
        return name.get();
    }

    public String getStockTicket() {
        return underlying.get();
    }

    public OptionType getType() {
        return (OptionType) type.get();
    }

    public Double getStrike() {
        return strike.get();
    }

    public Date getDateDue() {
        return expiry.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        if (bourse != null ? !bourse.equals(option.bourse) : option.bourse != null) return false;
        if (expiry != null ? !expiry.equals(option.expiry) : option.expiry != null) return false;
        if (name != null ? !name.equals(option.name) : option.name != null) return false;
        if (strike != null ? !strike.equals(option.strike) : option.strike != null) return false;
        if (type != null ? !type.equals(option.type) : option.type != null) return false;
        if (underlying != null ? !underlying.equals(option.underlying) : option.underlying != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (underlying != null ? underlying.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (strike != null ? strike.hashCode() : 0);
        result = 31 * result + (expiry != null ? expiry.hashCode() : 0);
        result = 31 * result + (bourse != null ? bourse.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Option{" +
                "NAME=" + name +
                ", UNDERLYING=" + underlying +
                ", TYPE=" + type +
                ", STRIKE=" + strike +
                ", EXPIRY=" + expiry +
                ", BOURSE=" + bourse +
                '}';
    }

    @Override
    public EntityId getId() {
        return null;
    }

    @Override
    public Set<EntityField<?>> getFields() {

        return new HashSet(Arrays.asList(name, underlying, bourse, type, strike, expiry));
    }
    @Override
    public EntityField<?> mapField(Field field) {

        return fieldMap.get(field);
    }

}
