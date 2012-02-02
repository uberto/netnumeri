package com.netnumeri.shared.service;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.List;

public interface GetEntitiesResponse<T> extends IsSerializable {
//    public void add(T entity);

    public List<T> getEntityList() ;

//    public void addAll(List<T> entityList);

    }
