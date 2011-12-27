package com.netnumeri.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.netnumeri.client.service.GetOptionService;
import com.netnumeri.server.persistence.DataFetcher;
import com.netnumeri.shared.StubsForTests;
import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.entity.OptionType;
import com.netnumeri.shared.service.GetEntitiesResponse;
import com.netnumeri.shared.service.GetEntityResponse;
import com.netnumeri.shared.service.GetOptionResponse;
import com.netnumeri.shared.service.GetOptionsResponse;

import java.util.Date;
import java.util.List;

public class GetOptionServiceImpl extends RemoteServiceServlet implements GetOptionService {

    DataFetcher<Option> fetcher;

    public GetOptionServiceImpl() {
        //for Gwt DevMode
        fetcher = new DataFetcher<Option>() {
            @Override
            public List<Option> fetch(String query) {
                return StubsForTests.createDummyOptionList();
            }
        };
    }

    public GetOptionServiceImpl(DataFetcher<Option> fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public GetEntitiesResponse<Option> getEntities(String query) {
        GetOptionsResponse res = new GetOptionsResponse();
        res.addAll(fetcher.fetch(query));

        return res;
    }

    @Override
    public GetEntityResponse<Option> getEntity(String query) {
        return new GetOptionResponse( new Option("option2", "GOOG", OptionType.CALL, 180.0, new Date(0)));
    }
}
