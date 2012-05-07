package com.netnumeri.shared.field;

import com.netnumeri.shared.finance.date.TDay;

public class DayEntityField extends ValuesEntityField<TDay>  {

    private DayEntityField() {
        super();
    }

    public DayEntityField(FieldMap fieldMap, FieldName fieldName) {
        super(fieldMap, fieldName);
    }

    @Override
    protected String valueToString() {
//        DateTimeFormat dtf = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.RFC_2822);
//        return dtf.format(get());

        return get().getWeekday() + "/" + get().getMonth() + 1 + "/" + get().getYear();
    }
}
