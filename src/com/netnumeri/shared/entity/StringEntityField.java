package com.netnumeri.shared.entity;

public class StringEntityField extends ValuesEntityField<String> {
    private int maxLen = -1;

    private StringEntityField() {
    }

    public StringEntityField(int maxLen, FieldName name) {
        this.maxLen = maxLen;
        this.fieldName = name;
    }

    public int getMaxLen() {
        return maxLen;
    }
}
