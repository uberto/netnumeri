package com.netnumeri.shared.finance.finpojo.derivative.equity;

import com.netnumeri.shared.entity.Entity;
import com.netnumeri.shared.entity.EntityId;
import com.netnumeri.shared.entity.OptionType;
import com.netnumeri.shared.field.*;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.Instrument;
import com.netnumeri.shared.finance.finpojo.derivative.Derivative;

import java.util.Collection;

public class Option extends Derivative implements Entity {

    private FieldMap fieldMap = new FieldMap();

    public IdEntityField id= new IdEntityField(fieldMap, Field.id);
    public StringEntityField bourse= new StringEntityField(fieldMap, Field.bourse, 12);
    public StringEntityField name = new StringEntityField(fieldMap,Field.name, 32);
    public StringEntityField underlying = new StringEntityField(fieldMap,Field.underlying, 5);
    public DoubleEntityField interestRate = new DoubleEntityField(fieldMap,Field.interestRate);
    public DayEntityField expiry = new DayEntityField(fieldMap,Field.expiry);
    public DoubleEntityField strike = new DoubleEntityField(fieldMap,Field.strike);
    public DoubleEntityField premium = new DoubleEntityField(fieldMap,Field.premium);
    public DoubleEntityField change = new DoubleEntityField(fieldMap,Field.change);
    public DoubleEntityField ask = new DoubleEntityField(fieldMap,Field.ask);
    public DoubleEntityField bid = new DoubleEntityField(fieldMap,Field.bid);
    public DoubleEntityField dividend = new DoubleEntityField(fieldMap,Field.dividend);
    public EnumEntityField<OptionType> type  = new EnumEntityField<OptionType>(fieldMap, Field.type);
    public IntegerEntityField contractSize = new IntegerEntityField(fieldMap,Field.contractSize);
    public IntegerEntityField openInterest = new IntegerEntityField(fieldMap,Field.openInterest);

    @Override
    public Instrument underlying() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double interestRate() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TDay expiration() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double strike() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double premium() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int contractSize() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int openInterest() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double alpha(int model) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double beta(int model) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double theta(int model) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double rho(int model) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double vega(int model) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Option() {
    }

    public Option(String id, String bourse, String optionName, String stockTicket, OptionType type, Double strike, TDay expiry) {

        this.id.setValue(new EntityId(id));
        this.bourse.setValue(bourse);
        this.name.setValue(optionName);
        this.underlying.setValue(stockTicket);
        this.type.setValue(type);
        this.strike.setValue(strike);
        this.expiry.setValue(expiry);

    }

    public double modelPrice(int model) {
        return 0;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public enum Field implements FieldName {
        id,
        bourse,
        name,
        underlying,
        interestRate,
        expiry,
        strike,
        premium,
        change,
        ask,
        bid,
        contractSize,
        openInterest,
        type,
        dividend
    }


    public Option(String Name) {
        name.setValue(Name);
    }

    @Override
    public EntityId getId() {
        return id.get();
    }

    @Override
    public Collection<? extends EntityField<?>> getFields() {
        return fieldMap.values();
    }

    @Override
    public EntityField<?> getField(FieldName fieldname) {
        return fieldMap.get(fieldname);
    }

    public double getPayoff(double instrumentPrice, boolean withPremium) {
        double payoff = 0;
        if (type.get().equals(OptionType.CALL)) {
            payoff = Math.max(0, instrumentPrice - strike.get());
        } else {
            payoff = Math.max(0, strike.get() - instrumentPrice);
        }
        if (withPremium) {
            payoff -= premium.get();
        }
        return payoff;
    }

    @Override
    public String toString() {
        return "Option{" +
                bourse +
                ", " + name +
                ", " + underlying +
                ", " + type +
                ", " + strike +
                ", " + expiry +
                '}';
    }

}