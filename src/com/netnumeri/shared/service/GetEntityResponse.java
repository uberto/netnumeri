package com.netnumeri.shared.service;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.netnumeri.shared.entity.Bug;

public class GetEntityResponse implements IsSerializable {
    private Bug entity;

    private GetEntityResponse() {
    }

    public GetEntityResponse(Bug entity) {
        this.entity = entity;
    }

    public Bug getEntity() {
        return entity;
    }
}
