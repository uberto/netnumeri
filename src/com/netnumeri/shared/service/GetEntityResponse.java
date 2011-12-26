package com.netnumeri.shared.service;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.netnumeri.shared.entity.Option;

public class GetEntityResponse implements IsSerializable {
    private Option entity;

    private GetEntityResponse() {
    }

    public GetEntityResponse(Option entity) {
        this.entity = entity;
    }

    public Option getEntity() {
        return entity;
    }
}
