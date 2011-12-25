package com.netnumeri.shared.pojoc.field;

public class StringField extends PojoCField<String> {
    private static final long serialVersionUID = 1494817930245132458L;

    private int maxLength;

    public StringField(String name, int maxLength) {
        super(name);
        this.maxLength = maxLength;
    }

    public StringField() {
        super();
    }

    public int getMaxLength() {
        return maxLength;
    }

    @Override
    public void setValueAsString(String value) {
        setValue(value);
    }

}
