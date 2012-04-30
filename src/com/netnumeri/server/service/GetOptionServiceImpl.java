package com.netnumeri.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.netnumeri.client.jsneeded.service.GetOptionService;
import com.netnumeri.server.persistence.DataFetcher;
import com.netnumeri.shared.StubsForTests;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Option;
import com.netnumeri.shared.service.GetEntitiesResponse;
import com.netnumeri.shared.service.GetEntitiesResponseImmutable;
import com.netnumeri.shared.service.GetEntityResponse;
import com.netnumeri.shared.service.GetEntityResponseImmutable;

import java.util.List;

public class GetOptionServiceImpl extends RemoteServiceServlet implements GetOptionService {

    DataFetcher<Option> fetcher;

    public GetOptionServiceImpl() {
        //for Gwt DevMode
        fetcher = new DataFetcher<Option>() {
            @Override
            public List<Option> selectByCriteria(String query) {
                return StubsForTests.createDummyOptionList();
            }

            @Override
            public Option getById(String id) {
                return StubsForTests.createDummyOption();
            }
        };
    }

    public GetOptionServiceImpl(DataFetcher<Option> fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public GetEntitiesResponse<Option> getEntities(String query) {

        return new GetEntitiesResponseImmutable<Option>(fetcher.selectByCriteria(query));
    }

    @Override
    public GetEntityResponse<Option> getEntity(String query) {
        return new GetEntityResponseImmutable<Option>(fetcher.getById(query));
    }
}
