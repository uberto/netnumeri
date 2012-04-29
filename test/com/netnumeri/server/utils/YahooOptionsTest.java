package com.netnumeri.server.utils;

import com.netnumeri.shared.entity.OptionType;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Vanilla;
import junit.framework.TestCase;
import org.dom4j.Document;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class YahooOptionsTest extends TestCase {

    OptionsDocuments screen;

    @Override
    protected  void setUp(){
        try {
            screen =  YahooOptions.getOptionsDocuments("IBM", new Date());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void computeMaxpain () throws Exception {
        Double aa = MaximumPainCalculator.calculate("IBM", new Date());
        System.out.println("aa = " + aa);
    }

    @Test
    public void testGetStockOptionChain() throws Exception {

        Document callsDocument = screen.callsDocument;
        System.out.println("screen = " + callsDocument.asXML());

        Document putsDocument = screen.putsDocument;
        System.out.println("putsDocument.asXML() = " + putsDocument.asXML());

        List<Vanilla> callOptions = YahooOptions.getChain(screen, OptionType.CALL);

        List<Vanilla> putOptions = YahooOptions.getChain(screen, OptionType.PUT);

    }
}
