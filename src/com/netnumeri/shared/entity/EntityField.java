package com.netnumeri.shared.entity;

import java.io.Serializable;

public interface EntityField<T> extends Serializable {
    T get();

    String getName();
}
