package com.netnumeri.shared.service;


import com.google.gwt.user.client.rpc.IsSerializable;

public interface GetEntityResponse<T> extends IsSerializable {

    public T getEntity();
}
