package com.netnumeri.shared.field;

public class StringEntityField extends ValuesEntityField<String> {
    private int maxLen = -1;

    private StringEntityField() {
    }

    public StringEntityField(FieldName name) {
        super(name);
    }

    public StringEntityField(FieldName name, FieldAttributes fieldAttributes) {
       super(name);
        maxLen = fieldAttributes.getMaxLenght();
    }

    public int getMaxLen() {
        return maxLen;
    }
}
