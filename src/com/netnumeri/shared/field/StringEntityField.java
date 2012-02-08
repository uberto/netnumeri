package com.netnumeri.shared.field;

public class StringEntityField extends ValuesEntityField<String> {

    private StringEntityField() {
        super();
    }

    public StringEntityField(FieldMap fieldMap,FieldName name, int maxLen) {
        super(fieldMap, name);
        setAttributes(new FieldAttributes("", maxLen));
    }

}
