package com.netnumeri.server;

import com.netnumeri.server.persistence.DataFetcher;
import com.netnumeri.server.service.GetOptionServiceImpl;
import com.netnumeri.shared.StubsForTests;
import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.service.GetEntitiesResponse;
import com.netnumeri.shared.service.GetOptionsResponse;
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
            public List<Option> fetch(String query) {
                return StubsForTests.createDummyOptionList();
            }
        };
    }


    @Test
    public void testGetEntities() throws Exception {

        final GetEntitiesResponse<Option> expectedRes = new GetOptionsResponse();
        expectedRes.addAll(StubsForTests.createDummyOptionList());

        GetEntitiesResponse<Option> res = serv.getEntities("");

        assertThat(res, is(expectedRes));

    }

}
