package com.netnumeri.shared.entity;

import com.netnumeri.shared.field.EntityField;
import com.netnumeri.shared.field.FieldName;

import java.io.Serializable;
import java.util.Collection;

public interface Entity extends Serializable {

    EntityId getId();

    Collection<? extends EntityField<?>> getFields();

    EntityField<?> getField(FieldName fieldname);
}
