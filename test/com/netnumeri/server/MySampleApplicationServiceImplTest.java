package com.netnumeri.server;

import com.netnumeri.server.service.MySampleApplicationServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class MySampleApplicationServiceImplTest {
    private MySampleApplicationServiceImpl serv;

    @Before
    public void setup(){
        serv = new MySampleApplicationServiceImpl();
    }

    @Test
    public void testGetMessage() throws Exception {

        String m = serv.getMessage("pippo");

        assertThat(m, is("Client said: \"pippo\"<br>Server answered: \"Hi!\""));

    }
}
