package com.netnumeri.server;

import com.netnumeri.server.service.GetBugServiceImpl;
import com.netnumeri.shared.entity.Bug;
import com.netnumeri.shared.entity.BugEnum;
import com.netnumeri.shared.service.GetEntitiesResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class GetBugServiceImplTest {
    private GetBugServiceImpl serv;

    @Before
    public void setup() {
        serv = new GetBugServiceImpl();

    }


    @Test
    public void testGetEntities() throws Exception {

        final GetEntitiesResponse expectedRes = new GetEntitiesResponse();
        Bug b = new Bug(1, "null pointer", BugEnum.OPEN, "uberto");
        expectedRes.add(b);
        Bug b2 = new Bug(2, "stack trace", BugEnum.WORKING, "uberto");
        expectedRes.add(b2);


        GetEntitiesResponse res = serv.getEntities("");

        assertThat(res.getEntityList(), equalTo(expectedRes.getEntityList()));

    }


    @Test
    public void createBug() {
        Bug b = new Bug(2, "stack trace", BugEnum.WORKING, "uberto");

        assertThat(b.getId(), is(2));
        assertThat(b.getDesc(), is("stack trace"));
        assertThat(b.getStatus(), is(BugEnum.WORKING));
        assertThat(b.getUser(), is("uberto"));
    }

}
