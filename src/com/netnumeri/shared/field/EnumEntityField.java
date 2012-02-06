package com.netnumeri.shared.field;

public class EnumEntityField<T extends Enum<T>> extends ValuesEntityField<Enum<T>> {

    private EnumEntityField() {
        super();
    }

    public EnumEntityField(FieldMap fieldMap, FieldName fieldName) {

        super(fieldMap, fieldName);
    }

    @Override
    public T get() {
        return (T) super.get();
    }
}
