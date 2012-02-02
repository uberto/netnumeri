package com.netnumeri.shared.entity;

import java.io.Serializable;

public class EntityId implements Serializable {

    private String idString;

    public EntityId(String idString) {

        this.idString = idString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityId entityId = (EntityId) o;

        if (idString != null ? !idString.equals(entityId.idString) : entityId.idString != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return idString != null ? idString.hashCode() : 0;
    }
}
