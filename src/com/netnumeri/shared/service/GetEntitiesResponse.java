package com.netnumeri.shared.service;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.netnumeri.shared.entity.Bug;

import java.util.ArrayList;
import java.util.List;

public class GetEntitiesResponse implements IsSerializable {
    private List<Bug> entityList = new ArrayList<Bug>();


    public void add(Bug bug) {
        entityList.add(bug);
    }

    public List<Bug> getEntityList() {
        return entityList;
    }
}
