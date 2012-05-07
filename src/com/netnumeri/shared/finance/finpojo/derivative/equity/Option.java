package com.netnumeri.shared.finance.finpojo.derivative.equity;

import com.netnumeri.shared.entity.Entity;
import com.netnumeri.shared.entity.EntityId;
import com.netnumeri.shared.entity.OptionType;
import com.netnumeri.shared.field.*;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.Instrument;

import java.util.Collection;

public class Option extends Instrument implements Entity {

    public Option(String id, String bourse, String optionName, String stockTicket, OptionType type, Double strike, TDay expiry) {

        this.id.setValue(new EntityId(id));
        this.bourse.setValue(bourse);
        this.name.setValue(optionName);
        this.underlying.setValue(stockTicket);
        this.type.setValue(type);
        this.strike.setValue(strike);
        this.expiry.setValue(expiry);

    }

    @Override
    public double modelPrice(int model) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setName(String name) {
        //To change body of implemented methods use File | Settings | File Templates.
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

//    private MonteCarlo monteCarlo;

    public Option(String Name) {
        name.setValue(Name);
    }

//    public Option(String name,
//                  Instrument stock,
//                  TDay expirationDate,
//                  double strikePrice,
//                  double interest,
//                  OptionType optionType,
//                  int model) {
//        setName(name);
//        underlying.setValue(stock.getName());
//        expiry.setValue(expirationDate);
//        strike.setValue(strikePrice);
//        interestRate.setValue(interest);
//
//        this.type.setValue(optionType);
//        this.pricingModel = model;
//    }
//

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