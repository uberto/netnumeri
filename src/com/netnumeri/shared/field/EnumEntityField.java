package com.netnumeri.shared.field;

public class EnumEntityField<T extends Enum<T>> extends ValuesEntityField<Enum<T>> {

    public EnumEntityField() {
    }

    public EnumEntityField(FieldName fieldName) {

        super(fieldName);
    }

}
