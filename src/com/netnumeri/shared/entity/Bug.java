package com.netnumeri.shared.entity;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Bug implements IsSerializable {
    int id;
    String desc;
    BugEnum status;
    String user;

    public Bug(int id, String desc, BugEnum status, String user) {
        this.id = id;
        this.desc = desc;
        this.status = status;
        this.user = user;
    }

    private Bug() {
    }


    public String getDesc() {
        return desc;
    }

    public int getId() {
        return id;
    }

    public BugEnum getStatus() {
        return status;
    }

    public String getUser() {
        return user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bug bug = (Bug) o;

        if (id != bug.id) {
            return false;
        }
        if (desc != null ? !desc.equals(bug.desc) : bug.desc != null) {
            return false;
        }
        if (status != bug.status) {
            return false;
        }
        if (user != null ? !user.equals(bug.user) : bug.user != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
