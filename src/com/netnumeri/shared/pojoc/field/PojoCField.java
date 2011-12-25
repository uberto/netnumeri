package com.netnumeri.shared.pojoc.field;


import java.io.Serializable;

public abstract class PojoCField<T> implements Serializable {
	private static final long serialVersionUID = -1422821870343204927L;

	private T value;

    private String fieldName;

    private boolean isSearchableField;

    public PojoCField() {
    }

    public PojoCField(String fieldName) {
        this.fieldName = fieldName;
    }


    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getValueAsString() {
        T v = getValue();
        if (v == null) {
            return null;
        } else {
            return v.toString();
        }
    }

    public FieldDsTypeEnum getDsType() {
        return FieldDsTypeEnum.dsNative;
    }

    public abstract void setValueAsString(String value);


    public PojoCField<T> setSearchable(boolean isSearchableField) {
        this.isSearchableField = isSearchableField;
        return this;
    }

    public boolean isSearchableField() {
        return isSearchableField;
    }

    @Override
    public String toString() {
        return fieldName + ": " + getValueAsString();
    }

}
