package com.netnumeri.shared.entity;

import com.netnumeri.shared.field.EntityField;

import java.io.Serializable;
import java.util.Set;

public interface Entity extends Serializable {

    EntityId getId();

    Set<EntityField<?>> getFields();

}
