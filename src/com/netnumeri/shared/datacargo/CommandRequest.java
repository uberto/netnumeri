package com.netnumeri.shared.datacargo;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.netnumeri.shared.enumer.ApplicationCommandEnum;

public abstract class CommandRequest implements IsSerializable {
    public static final int DEFAULT_EXPIRE_SEC = 60 * 5;
    public static final int NO_EXPIRE = 0;

    private String businessAccountId;
    private ApplicationCommandEnum command;

    public ApplicationCommandEnum getCommand() {
        return command;
    }

    public void setCommand(ApplicationCommandEnum command) {
        this.command = command;
    }

    public String getBusinessAccountId() {
        return businessAccountId;
    }

    public void setBusinessAccountId(String businessAccountId) {
        this.businessAccountId = businessAccountId;
    }

    public int getCacheExpireInSec() {
        return DEFAULT_EXPIRE_SEC;
    }

    public CommandRequest[] getRemoveFromCacheOnSuccess() {
        return null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((command == null) ? 0 : command.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CommandRequest other = (CommandRequest) obj;
        if (command == null) {
            if (other.command != null)
                return false;
        } else if (!command.equals(other.command))
            return false;
        return true;
    }

}
