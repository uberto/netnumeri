package com.netnumeri.shared.field;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FieldMap implements Serializable {

    private Map<FieldName, EntityField<?>> map = new HashMap<FieldName, EntityField<?>>();


    public EntityField<?> get(FieldName fieldName) {
        return map.get(fieldName);
    }

    public void put(FieldName fieldName, ValuesEntityField<?> entityField) {
        map.put(fieldName, entityField);
    }

    public Collection<? extends EntityField<?>> values() {
        return map.values();
    }
}
