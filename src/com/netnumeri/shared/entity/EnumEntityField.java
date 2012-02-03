package com.netnumeri.shared.entity;

public class EnumEntityField<T extends Enum<T>> extends ValuesEntityField<Enum<T>> {
    

    public EnumEntityField(FieldName fieldName) {

        this.fieldName = fieldName;
    }

}
