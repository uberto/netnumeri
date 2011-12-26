package com.netnumeri.server.persistence;

import java.util.List;

public interface DataFetcher<T> {
    public List<T> fetch(String query);
}
