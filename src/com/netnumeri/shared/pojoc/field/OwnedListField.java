package com.netnumeri.shared.pojoc.field;


import com.netnumeri.shared.pojoc.DetailBasePojo;

import java.util.ArrayList;

public class OwnedListField<T extends DetailBasePojo> extends ArrayList<T> {
    private static final long serialVersionUID = -1772533850561423077L;

    private String fieldName;

    public OwnedListField() {
    }

    public OwnedListField(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    @SuppressWarnings("unchecked")
    public boolean addDetail(DetailBasePojo detail) {
        return add((T) detail);
    }
}
