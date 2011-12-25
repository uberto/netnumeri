package com.netnumeri.shared.pojoc.field;


import static com.netnumeri.shared.utils.SharedUtils.strToLong;

public class LongField extends PojoCField<Long> {

    public LongField(String name) {
        super(name);
    }

    public LongField() {
    }

    @Override
    public void setValueAsString(String value) {
        setValue(strToLong(value));
    }


}
