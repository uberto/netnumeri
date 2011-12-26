package com.netnumeri.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.netnumeri.client.service.GetBugService;
import com.netnumeri.server.persistence.DataFetcher;
import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.entity.OptionType;
import com.netnumeri.shared.service.GetEntitiesResponse;
import com.netnumeri.shared.service.GetEntityResponse;

import java.util.Date;

public class GetBugServiceImpl extends RemoteServiceServlet implements GetBugService {

    DataFetcher<Option> fetcher;

    public GetBugServiceImpl(DataFetcher<Option> fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public GetEntitiesResponse getEntities(String query) {
        //should go to persistence

        GetEntitiesResponse res = new GetEntitiesResponse();
        res.addAll(fetcher.fetch(query));

        return res;
    }

    @Override
    public GetEntityResponse getEntity(String query) {
        return new GetEntityResponse( new Option("option2", "GOOG", OptionType.CALL, 180.0, new Date(0)));
    }
}
