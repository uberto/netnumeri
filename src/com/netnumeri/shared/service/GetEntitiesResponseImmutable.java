package com.netnumeri.shared.service;

import java.util.ArrayList;
import java.util.List;

public class GetEntitiesResponseImmutable<T> implements  GetEntitiesResponse<T> {
    private List<T> entityList = new ArrayList<T>();

    public GetEntitiesResponseImmutable(List<T> entityList) {
        this.entityList = entityList;
    }

    private GetEntitiesResponseImmutable() {
    }

    @Override
    public void add(T option) {
        entityList.add(option);
    }

    @Override
    public List<T> getEntityList() {
        return entityList;
    }

    @Override
    public void addAll(List<T> optionList) {
        entityList.addAll(optionList);
    }

    @Override
    public String toString() {
        String list = "";
        for (T option : entityList) {
            if (list.length() >0){
                list +=", ";
            }
            list += option.toString();

        }

        return "GetEntitiesResponse{" +
                list + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GetEntitiesResponseImmutable that = (GetEntitiesResponseImmutable) o;

        if (entityList != null ? !entityList.equals(that.entityList) : that.entityList != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return entityList != null ? entityList.hashCode() : 0;
    }
}
