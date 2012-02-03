package com.netnumeri.shared.field;

public class StringEntityField extends ValuesEntityField<String> {
    private int maxLen = -1;

    private StringEntityField() {
    }

    public StringEntityField(int maxLen, FieldName name) {
        super(name);
        this.maxLen = maxLen;
    }

    public int getMaxLen() {
        return maxLen;
    }
}
