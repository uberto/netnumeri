package com.netnumeri.shared.field;

import java.util.Date;

public class DateEntityField  extends ValuesEntityField<Date>  {


    private DateEntityField() {
        super();
    }

    public DateEntityField(FieldMap fieldMap, FieldName fieldName) {
        super(fieldMap, fieldName);
    }

    @Override
    protected String valueToString() {
//        DateTimeFormat dtf = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.RFC_2822);
//        return dtf.format(get());

        return get().getDay() + "/" + get().getMonth() + 1 + "/" + get().getYear();
    }
}
