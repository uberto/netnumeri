package com.netnumeri.shared.entity;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EntityValue<T> implements IsSerializable {


    private T value;

    private EntityValue() {
    }

    public EntityValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
