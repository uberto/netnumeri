package com.netnumeri.server.command;


import com.netnumeri.shared.datacargo.CommandRequest;
import com.netnumeri.shared.datacargo.CommandResponse;
import com.netnumeri.shared.finance.finpojo.User;
import com.netnumeri.shared.pojoc.BusinessAccount;

public interface ModelCommand<REQUEST extends CommandRequest, RESPONSE extends CommandResponse> {

    public void setBusinessAccount(BusinessAccount ba);

    public BusinessAccount getBusinessAccount();

    public void setUser(User user);

    public User getUser();

    public RESPONSE execute(REQUEST request) throws Exception;
}
