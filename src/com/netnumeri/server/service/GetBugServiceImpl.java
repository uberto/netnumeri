package com.netnumeri.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.netnumeri.client.service.GetBugService;
import com.netnumeri.shared.entity.Bug;
import com.netnumeri.shared.entity.BugEnum;
import com.netnumeri.shared.service.GetEntitiesResponse;
import com.netnumeri.shared.service.GetEntityResponse;

public class GetBugServiceImpl extends RemoteServiceServlet implements GetBugService {

    @Override
    public GetEntitiesResponse getEntities(String query) {
        //should go to persistence

        GetEntitiesResponse res = new GetEntitiesResponse();
        res.add(new Bug(1, "null pointer", BugEnum.OPEN, "uberto"));
        res.add(new Bug(2, "stack trace", BugEnum.WORKING, "uberto"));

        return res;
    }

    @Override
    public GetEntityResponse getEntity(String query) {
        return new GetEntityResponse( new Bug(1, "null pointer", BugEnum.OPEN, "uberto"));
    }
}
