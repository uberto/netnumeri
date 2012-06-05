package com.netnumeri.shared.finance.finpojo.asset;

import com.netnumeri.shared.field.FieldMap;
import com.netnumeri.shared.field.FieldName;
import com.netnumeri.shared.field.StringEntityField;

import java.io.Serializable;

public class Stock extends Asset implements Serializable {

    enum Field implements FieldName {
        ticker
    }
    private FieldMap fieldMap = new FieldMap();

    private StringEntityField ticker = new StringEntityField(fieldMap,Field.ticker, 5);

//    public Stock() {
//        super();
//    }

    public Stock(String name) {
        this.ticker.setValue(name);
    }

    public double modelPrice(int model) {
        return 0;
    }

    @Override
    public String getName() {
        return ticker.get();
    }

    @Override
    public void setName(String name) {
        this.ticker.setValue(name);
    }

}
