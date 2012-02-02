package com.netnumeri.shared.service;

public class GetEntityResponseImmutable<T> implements GetEntityResponse<T> {
    private T entity;

    private GetEntityResponseImmutable() {
    }

    public GetEntityResponseImmutable(T entity) {
        this.entity = entity;
    }

    @Override
    public T getEntity() {
        return entity;
    }
}
