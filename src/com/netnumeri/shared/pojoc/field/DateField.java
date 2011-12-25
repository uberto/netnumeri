package com.netnumeri.shared.pojoc.field;


import com.netnumeri.shared.utils.SharedUtils;

import java.util.Date;

import static com.netnumeri.shared.utils.SharedUtils.strToDate;

@SuppressWarnings({"deprecation"})   //they are not deprecated on gwt Date
public class DateField extends PojoCField<Date> {

    public DateField(String name) {
        super(name);
    }

    public DateField() {
    }

    @Override
    public void setValueAsString(String value) {
        setValue(strToDate(value));
    }

    @Override
    public String getValueAsString() {
        if (getValue() == null) {
            return "";
        }
//TODO uberto user better date library
        String date = SharedUtils.format(getValue().getDay() + 1) + "-" + SharedUtils.format(getValue().getMonth() + 1) + "-" + SharedUtils.format(1900 + getValue().getYear());
        return date;
    }
}
