package com.netnumeri.shared.pojoc.field;



public class EnumField<T extends Enum<T>> extends PojoCField<T> {
    private T defaultValue;

    public EnumField() {
        defaultValue = null;
    }

    @SuppressWarnings("unchecked")
    public Class<T> getEnumClass() {
        return (Class<T>) defaultValue.getClass();
    }

    @Override
    public void setValueAsString(String value) {
        if (value == null) {
            setValue(null);
        } else {
            T a = Enum.valueOf(getEnumClass(), value);
            setValue(a);
        }
    }

    @Override
    public String getValueAsString() {
        if (getValue() == null) {
            return null;
        } else {
            return getValue().name();
        }
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public FieldDsTypeEnum getDsType() {
        return FieldDsTypeEnum.string;
    }
}
