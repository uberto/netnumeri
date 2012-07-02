package com.netnumeri.server.utils;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.*;
import java.util.Date;

public class YahooOptionsTest extends TestCase {

    OptionsDocuments screen;

    @Override
    protected void setUp() {
        try {
//            String s  =  YahooOptions.getOptionsDocuments("IBM", new Date());

            screen = YahooOptions.scrape("GOOG", YahooOptions.getOptionsDocuments("GOOG", new Date()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void computeMaxpain() throws Exception {
        Double aa = MaximumPainCalculator.calculate("GOOG", new Date());
        System.out.println("aa = " + aa);
    }

    @Test
    public void testLoadOptionChain() throws Exception {

        OptionsChain chain = YahooOptions.loadOptionChain("GOOG");

        System.out.println(" = " + chain.calls.size());

        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream("./test/resources/GOOG.obj");
            out = new ObjectOutputStream(fos);
            out.writeObject(chain);
            out.close();


        } catch (IOException ex) {
            ex.printStackTrace();
        }

        OptionsChain e = null;
        try {
            FileInputStream fileIn =
                    new FileInputStream("./test/resources/GOOG.obj");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (OptionsChain) in.readObject();
            in.close();
            fileIn.close();

            System.out.println(" = " + e.calls.size());


        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }

    }

}

