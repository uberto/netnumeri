package com.netnumeri.shared.service;

import com.netnumeri.shared.entity.Option;

import java.util.ArrayList;
import java.util.List;

public class GetOptionsResponse implements  GetEntitiesResponse<Option> {
    private List<Option> entityList = new ArrayList<Option>();


    @Override
    public void add(Option option) {
        entityList.add(option);
    }

    @Override
    public List<Option> getEntityList() {
        return entityList;
    }

    @Override
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

        GetOptionsResponse that = (GetOptionsResponse) o;

        if (entityList != null ? !entityList.equals(that.entityList) : that.entityList != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return entityList != null ? entityList.hashCode() : 0;
    }
}
