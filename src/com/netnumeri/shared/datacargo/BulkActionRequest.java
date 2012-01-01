package com.netnumeri.shared.datacargo;

import java.util.LinkedList;
import java.util.List;

public class BulkActionRequest extends CommandRequest {

    List<Long> entityIdList = new LinkedList<Long>();

    public List<Long> getEntityIdList() {
        return entityIdList;
    }

    public void setEntityIdList(List<Long> entityIdList) {
        this.entityIdList = entityIdList;
    }
}
