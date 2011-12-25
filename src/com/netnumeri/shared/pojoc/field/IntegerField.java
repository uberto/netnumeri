package com.netnumeri.shared.pojoc.field;


import static com.netnumeri.shared.utils.SharedUtils.strToInteger;

public class IntegerField extends PojoCField<Integer> {
	private static final long serialVersionUID = -8710761181458085008L;

	public IntegerField(String name) {
        super(name);
    }

    public IntegerField() {
    }

    @Override
    public void setValueAsString(String value) {
        setValue(strToInteger(value));
    }

    public int intValue() {
        if (getValue() == null)
            return 0;
        else
            return getValue().intValue();
    }


}
