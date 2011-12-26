package com.netnumeri.shared.service;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.netnumeri.shared.entity.Option;

import java.util.ArrayList;
import java.util.List;

public class GetEntitiesResponse implements IsSerializable {
    private List<Option> entityList = new ArrayList<Option>();


    public void add(Option option) {
        entityList.add(option);
    }

    public List<Option> getEntityList() {
        return entityList;
    }

    public void addAll(List<Option> optionList) {
        entityList.addAll(optionList);
    }

    @Override
    public String toString() {
        String list = "";
        for (Option option : entityList) {
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

        GetEntitiesResponse that = (GetEntitiesResponse) o;

        if (entityList != null ? !entityList.equals(that.entityList) : that.entityList != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return entityList != null ? entityList.hashCode() : 0;
    }
}
