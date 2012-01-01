package com.netnumeri.shared.finance.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetUtils {

    public static boolean closeURL(InputStream myInputStream) {
        try {
            myInputStream.close();
        }
        catch (java.io.IOException e) {
            return false;
        }
        return true;
    }

     public static InputStream openFile(String s) {
        try {
            FileInputStream fileinputstream = new FileInputStream(s);
            return fileinputstream;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static InputStream openURL(String s) {
        URL url;
        try {
            url = new URL(s);
        }
        catch (MalformedURLException malformedurlexception) {
            return openFile(s);
        }
        InputStream inputstream;
        try {
            URLConnection urlconnection = url.openConnection();
            urlconnection.setUseCaches(false);
            inputstream = urlconnection.getInputStream();
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace(System.out);
            return null;
        }
        return inputstream;
    }

    //slightly shorter StringBuffer version
    public static synchronized String getLineFromURL(InputStream myInputStream) {
        int ch = -1;
        StringBuffer sb = new StringBuffer(256);
        while (true) {
            try {
                ch = myInputStream.read();
            }
            catch (java.io.IOException e) {
                return null;
            }
            if (ch == -1) {
                if (sb.length() == 0) {
                    return null;
                } else {
                    return (sb.toString());
                }
            } else if ((ch == '\n') || (ch == '\r')) {
                if (sb.length() > 0) {
                    return (sb.toString());
                }
            } else {
                sb.append((char) ch);
            }
        }
    }

}
