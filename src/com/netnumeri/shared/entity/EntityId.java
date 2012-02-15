package com.netnumeri.shared.entity;

import java.io.Serializable;
import java.util.Arrays;

public class EntityId implements Serializable {

    private byte[] idString;


    public EntityId() {
    }

    public EntityId(String idString) {

        this.idString = idString.getBytes();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityId entityId = (EntityId) o;

        if (!Arrays.equals(idString, entityId.idString)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return idString != null ? Arrays.hashCode(idString) : 0;
    }

    @Override
    public String toString() {
        return new String(idString);
    }
}
