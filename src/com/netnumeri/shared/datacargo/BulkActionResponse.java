package com.netnumeri.shared.datacargo;

import java.util.LinkedList;
import java.util.List;

public class BulkActionResponse implements CommandResponse {

    List<Long> successIdList = new LinkedList<Long>();

    List<Long> failureIdList = new LinkedList<Long>();

    String message = "";

    public List<Long> getSuccessIdList() {
        return successIdList;
    }

    public void setSuccessIdList(List<Long> successIdList) {
        this.successIdList = successIdList;
    }

    public List<Long> getFailureIdList() {
        return failureIdList;
    }

    public void setFailureIdList(List<Long> failureIdList) {
        this.failureIdList = failureIdList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
