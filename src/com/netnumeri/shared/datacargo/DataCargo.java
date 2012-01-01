package com.netnumeri.shared.datacargo;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DataCargo implements IsSerializable {
    private Integer sessionId;

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionHashCode) {
        this.sessionId = sessionHashCode;
    }
}
