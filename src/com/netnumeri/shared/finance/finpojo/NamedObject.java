package com.netnumeri.shared.finance.finpojo;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

public abstract class NamedObject implements Serializable, IsSerializable {
    private String name;

    public NamedObject() {
    }

    public NamedObject(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final NamedObject namedObject = (NamedObject) o;

        if (name != null ? !name.equals(namedObject.name) : namedObject.name != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = 2;
        result = 29 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
