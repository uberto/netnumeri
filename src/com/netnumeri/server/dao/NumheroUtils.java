package com.netnumeri.server.dao;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.netnumeri.server.GuiceModule;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class NumheroUtils {
    private static final Logger log = Logger.getLogger(NumheroUtils.class.getName());

    public static Injector injector = Guice.createInjector(Stage.PRODUCTION, new GuiceModule());
    private static final int TWO_WEEKS_IN_SECONDS = 3600 * 24 * 15;

    public static String createRandomSessionId() {
        Random r = new Random();
        String httpSessionID = "$$" + r.nextInt();
        return httpSessionID;
    }

    public static Cookie createCookie(String httpSessionID) {
        Cookie sessionCookie = new Cookie(Constants.SESSION_COOKIE_NAME, httpSessionID);
        sessionCookie.setMaxAge(TWO_WEEKS_IN_SECONDS);
        sessionCookie.setPath("/");
        return sessionCookie;

    }

    public static String getSessionFromCookie(HttpServletRequest httpRequest) {
        String sessionIDFromClient = null;
        if (httpRequest.getCookies() != null) {
            for (Cookie cookie : httpRequest.getCookies()) {
                log.fine("cookie " + cookie.getName() + "  " + cookie.getValue());
                if (cookie.getName().equals(Constants.SESSION_COOKIE_NAME)) {
                    sessionIDFromClient = cookie.getValue();
                    break;
                }
            }
        }
        return sessionIDFromClient;
    }

    public static InputStream convertStringInInputStream(String orig) {
        try {
            InputStream is = new ByteArrayInputStream(orig.getBytes("UTF-8"));
            return is;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String listToStr(List<String> list) {
        StringBuffer res = new StringBuffer();
        for (String s : list) {
            res.append(s);
        }
        return res.toString();
    }

    public static byte[] readFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len;
        byte[] buffer = new byte[8192];
        while ((len = is.read(buffer, 0, buffer.length)) != -1) {
            out.write(buffer, 0, len);
        }
        return out.toByteArray();
    }

}
