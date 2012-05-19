package com.netnumeri.shared.finance.finpojo.asset;

import com.netnumeri.shared.field.FieldMap;
import com.netnumeri.shared.field.FieldName;
import com.netnumeri.shared.field.StringEntityField;
import com.netnumeri.shared.finance.data.Dividend;
import com.netnumeri.shared.finance.data.DividendArray;
import com.netnumeri.shared.finance.data.Split;
import com.netnumeri.shared.finance.data.SplitArray;

import java.io.Serializable;
import java.util.Date;

public class Stock extends Asset implements Serializable {

    enum Field implements FieldName {
        ticker
    }
    private FieldMap fieldMap = new FieldMap();

    private StringEntityField ticker = new StringEntityField(fieldMap,Field.ticker, 5);

//
//    DividendArray dividendArray = null;
//    SplitArray splitArray = null;

    public Stock() {
        super();
    }

    public Stock(String name) {
        super(name);
    }

    public double modelPrice(int model) {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

}
