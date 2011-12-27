package com.netnumeri.server.command;

import com.netnumeri.shared.datacargo.CommandRequest;
import com.netnumeri.shared.datacargo.CommandResponse;
import com.netnumeri.shared.finance.finpojo.User;
import com.netnumeri.shared.pojoc.BusinessAccount;
import com.netnumeri.shared.utils.ClientWarningException;

public interface ICommandProcessor {

	public <REQUEST extends CommandRequest,
            RESPONSE extends CommandResponse> CommandResponse process(User user, BusinessAccount ba, REQUEST request) throws ClientWarningException;
}
