package com.netnumeri.server.utils;

import com.netnumeri.shared.entity.OptionType;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Option;
import junit.framework.TestCase;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class YahooOptionsTest extends TestCase {

    OptionsDocuments screen;

    @Override
    protected  void setUp(){
        try {
//            String s  =  YahooOptions.getOptionsDocuments("IBM", new Date());

            screen = YahooOptions.scrape("GOOG", YahooOptions.getOptionsDocuments("GOOG",new Date()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void computeMaxpain () throws Exception {
        Double aa = MaximumPainCalculator.calculate("GOOG", new Date());
        System.out.println("aa = " + aa);
    }

//    @Test
//    public void testGetStockOptionChain() throws Exception {
//
//        Document callsDocument = screen.callsDocument;
//        System.out.println("screen = " + callsDocument.asXML());
//
//        Document putsDocument = screen.putsDocument;
//        System.out.println("putsDocument.asXML() = " + putsDocument.asXML());
//
//        List<Option> callOptions = YahooOptions.getChain(screen, OptionType.CALL);
//
//        List<Option> putOptions = YahooOptions.getChain(screen, OptionType.PUT);
//
//    }

    @Test
    public void testLoadOptionChain() throws Exception{

        OptionsChain chain = YahooOptions.loadOptionChain("GOOG");

        System.out.println(" = "  + chain.calls.size());

//        chain.calls


    }



}

