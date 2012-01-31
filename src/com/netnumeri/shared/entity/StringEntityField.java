package com.netnumeri.shared.entity;

import java.util.Map;

public class StringEntityField implements EntityField<String> {
    private Map<EntityField<?>, EntityValue<?>> values;
    private int maxLen = -1;
    private String name;

    private StringEntityField() {
    }

    public StringEntityField(int maxLen, String name) {
        this.maxLen = maxLen;
        this.name = name;
    }

    public void setValues(Map <EntityField<?>, EntityValue<?>> values) {
        this.values = values;
    }

    @Override
    public String get() {
        return values.get(this ).toString();
    }

    @Override
    public String getName() {
        return name;
    }
}
