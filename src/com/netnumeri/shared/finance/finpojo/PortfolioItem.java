package com.netnumeri.shared.finance.finpojo;


import com.netnumeri.shared.field.FieldMap;
import com.netnumeri.shared.field.FieldName;
import com.netnumeri.shared.field.StringEntityField;
import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.asset.Asset;
import com.netnumeri.shared.finance.finpojo.derivative.Derivative;

import java.io.Serializable;

public class PortfolioItem implements Serializable, FinConstants {

    enum Field implements FieldName {
        name
    }
    private FieldMap fieldMap = new FieldMap();
    public StringEntityField name = new StringEntityField(fieldMap,Field.name, 5);

    Portfolio portfolio;

    private Instrument instrument;

    private int amount = 0;
    private double weight;
    private double tempweight;
    private double assetWeightLowerBound = -1;
    private double assetWeightUpperBound = -1;
    private TDay modelDate = new TDay();
    private double modelPrice;

    public PortfolioItem() {
        init();
    }

    private void init() {
        this.amount = 0;
        this.weight = 1.0;
        this.assetWeightLowerBound = 0.0;
        this.assetWeightUpperBound = 1.0;
        modelPrice = 0;
    }

    public PortfolioItem(Instrument instrument) {
        init();
        this.instrument = instrument;
    }

    // amount < 0 means taking short position in instrument
    public PortfolioItem(Instrument instrument, int amount) {
        init();
        this.instrument = instrument;
        this.amount = amount;
    }

    public double getModelPrice() {
        return modelPrice;
    }

    public void setModelPrice(double modelPrice) {
        this.modelPrice = modelPrice;
    }

    public TDay getModelDate() {
        return modelDate;
    }

    public void setModelDate(TDay modelDate) {
        this.modelDate = modelDate;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public int getAmount() {
        return amount;
    }

    public double getWeight() {
        return weight;
    }

    public double getTempweight() {
        return tempweight;
    }

    public void setTempweight(double tempweight) {
        this.tempweight = tempweight;
    }

    public double getAssetWeightLowerBound() {
        return assetWeightLowerBound;
    }

    public double getAssetWeightUpperBound() {
        return assetWeightUpperBound;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setWeight(double Weight) {
        weight = Weight;
    }

    public void setAssetWeightLowerBound(double LowerBound) {
        assetWeightLowerBound = LowerBound;
    }

    public void setAssetWeightUpperBound(double UpperBound) {
        assetWeightUpperBound = UpperBound;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public int getPosition() {
        if (amount >= 0) {
            return LONG;
        } else {
            return SHORT;
        }
    }

    public double getPrice() {
        if (instrument instanceof Asset) {
            return instrument.getLast();
        } else if (instrument instanceof Derivative) {
            return instrument.premium();
        }
        return 0;
    }

    public double getValue() {
        return getPrice() * amount;
    }

}
