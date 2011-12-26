package com.netnumeri.server;

import com.netnumeri.server.persistence.DataFetcher;
import com.netnumeri.server.service.GetBugServiceImpl;
import com.netnumeri.shared.StubsForTests;
import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.service.GetEntitiesResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetBugServiceImplTest {
    private GetBugServiceImpl serv;

    @Before
    public void setup() {
        serv = new GetBugServiceImpl(createOptionFetcher());

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

        final GetEntitiesResponse expectedRes = new GetEntitiesResponse();
        expectedRes.addAll(StubsForTests.createDummyOptionList());

        GetEntitiesResponse res = serv.getEntities("");

        assertThat(res, is(expectedRes));

    }

}
