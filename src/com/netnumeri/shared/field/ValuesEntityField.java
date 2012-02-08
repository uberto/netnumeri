package com.netnumeri.shared.field;

public class ValuesEntityField<T> implements EntityField<T> {

    private T value;
    protected FieldName fieldName;
    private FieldAttributes attributes;

    public ValuesEntityField(FieldMap fieldMap, FieldName fieldName) {
        this.fieldName = fieldName;
        fieldMap.put(fieldName, this);
    }

    public ValuesEntityField() {
    }

    public void setValue(T value) {
        if (value == null){
            throw new RuntimeException("Illegal null assignation to field:" + fieldName);
        }
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public String toString() {
        return "{" + fieldName + ":" + valueToString() + '}';
    }

    protected String valueToString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValuesEntityField that = (ValuesEntityField) o;

        if (fieldName != null ? !fieldName.equals(that.fieldName) : that.fieldName != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (fieldName != null ? fieldName.hashCode() : 0);
        return result;

    }

    public FieldAttributes getAttributes() {
        return attributes;
    }

    protected void setAttributes(FieldAttributes fieldAttributes) {
        this.attributes = fieldAttributes;
    }
}
