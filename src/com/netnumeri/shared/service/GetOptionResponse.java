package com.netnumeri.shared.service;

import com.netnumeri.shared.entity.Option;

public class GetOptionResponse implements GetEntityResponse<Option> {
    private Option entity;

    private GetOptionResponse() {
    }

    public GetOptionResponse(Option entity) {
        this.entity = entity;
    }

    @Override
    public Option getEntity() {
        return entity;
    }
}
