package com.netnumeri.server.persistence;

import java.util.List;

public interface DataFetcher<T> {
    public List<T> selectByCriteria(String criteria);

    T getById(String id);
}
