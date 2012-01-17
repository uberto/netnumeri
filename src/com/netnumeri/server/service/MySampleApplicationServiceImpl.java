package com.netnumeri.server.service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.netnumeri.client.jsneeded.service.MySampleApplicationService;

public class MySampleApplicationServiceImpl extends RemoteServiceServlet implements MySampleApplicationService {


    static int times = 0;

    public String getMessage(String msg) {

        times++;
        //System.err.println("--" + times);
        if (msg == null)
            throw new RuntimeException("Not a valid message!");

        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}
