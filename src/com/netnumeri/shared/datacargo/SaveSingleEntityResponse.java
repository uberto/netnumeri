package com.netnumeri.shared.datacargo;

public class SaveSingleEntityResponse implements CommandResponse {
     private Long entityId;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
}
