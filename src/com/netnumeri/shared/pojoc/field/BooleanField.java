package com.netnumeri.shared.pojoc.field;

import static com.netnumeri.shared.utils.SharedUtils.strToBoolean;

public class BooleanField extends PojoCField<Boolean> {
	private static final long serialVersionUID = -1660332621699474844L;

	public BooleanField(String name) {
        super(name);
    }

    public BooleanField() {
    }

	@Override
	public void setValueAsString(String value) {
	   setValue(strToBoolean(value));
	}

	@Override
    public Boolean getValue() {
        return super.getValue();
    }
}
