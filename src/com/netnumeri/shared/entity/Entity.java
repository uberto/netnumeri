package com.netnumeri.shared.entity;

import java.io.Serializable;
import java.util.Set;

public interface Entity extends Serializable {

    EntityId getId();

    Set<EntityField<?>> getFields();

}
