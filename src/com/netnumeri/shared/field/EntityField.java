package com.netnumeri.shared.field;

import java.io.Serializable;

public interface EntityField<T> extends Serializable {
    T get();

    String getName();
}
