package com.netnumeri.server;

import com.netnumeri.server.persistence.DataFetcher;
import com.netnumeri.server.service.GetOptionServiceImpl;
import com.netnumeri.shared.StubsForTests;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Option;
import com.netnumeri.shared.service.GetEntitiesResponse;
import com.netnumeri.shared.service.GetEntitiesResponseImmutable;
import com.netnumeri.shared.service.GetEntityResponse;
import com.netnumeri.shared.service.GetEntityResponseImmutable;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetOptionServiceImplTest {
    private GetOptionServiceImpl serv;

    @Before
    public void setup() {
        serv = new GetOptionServiceImpl(createOptionFetcher());

    }

    private DataFetcher<Option> createOptionFetcher() {
        return new DataFetcher<Option>() {
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


    @Test
    public void returnEntitiesList() throws Exception {

        final GetEntitiesResponse<Option> expectedRes = new GetEntitiesResponseImmutable(StubsForTests.createDummyOptionList());

        GetEntitiesResponse<Option> res = serv.getEntities("");

        assertThat(res.toString(), is(expectedRes.toString()));
        assertThat(res, is(expectedRes));

    }

    @Test
    public void returnSingleEntity() throws Exception {

        final GetEntityResponse<Option> expectedRes = new GetEntityResponseImmutable<Option>(StubsForTests.createDummyOption());

        GetEntityResponse<Option> res = serv.getEntity("");

        assertThat(res.getEntity(), is(expectedRes.getEntity()));

    }
}
