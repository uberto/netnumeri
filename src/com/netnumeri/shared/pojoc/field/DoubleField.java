package com.netnumeri.shared.pojoc.field;


import static com.netnumeri.shared.utils.SharedUtils.strToDouble;

public class DoubleField extends PojoCField<Double> {

    public DoubleField(String name) {
        super(name);
    }

	public DoubleField() {
	}

	@Override
    public void setValueAsString(String value) {
        setValue(strToDouble(value));
    }


}