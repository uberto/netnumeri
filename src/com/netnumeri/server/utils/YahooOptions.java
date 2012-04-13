package com.netnumeri.server.utils;

import com.netnumeri.shared.entity.OptionType;
import com.netnumeri.shared.finance.data.MaximumPainBean;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Vanilla;
import com.netnumeri.shared.finance.utils.NetUtils;
import com.netnumeri.shared.finance.utils.YahooInstantSnapshot;
import com.netnumeri.shared.finance.utils.YahooUtils;
import org.dom4j.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class YahooOptions {

//    public static void main(String[] args) throws Exception {
//        calculate("AA");
//        calculate("AXP");
//        calculate("BA");
//        calculate("BAC");
//        calculate("CAT");
//        calculate("CSCO");
//        calculate("CVX");
//        calculate("DD");
//        calculate("DIS");
//        calculate("GE");
//        calculate("HD");
//        calculate("HPQ");
//        calculate("IBM");
//        calculate("INTC");
//        calculate("JNJ");
//        calculate("JPM");
//        calculate("KFT");
//        calculate("KO");
//        calculate("MCD");
//        calculate("MMM");
//        calculate("MRK");
//        calculate("MSFT");
//        calculate("PFE");
//        calculate("PG");
//        calculate("T");
//        calculate("TRV");
//        calculate("UTX");
//        calculate("VZ");
//        calculate("WMT");
//        calculate("XOM");
//    }

    
    public static Double getLastPrice(String ticker){
        Double lastPrice = Double.NaN;
        try {
            YahooInstantSnapshot snapshot = YahooUtils.getCompanySnapshot(ticker);
            lastPrice = Double.parseDouble(snapshot.getLastPrice());
            System.out.println("lastPrice = " + lastPrice);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return lastPrice;
    }
    

    /*
    ** According to the theory of maximum pain, the underlying stock or index will tend to move towards the price
    ** where the greatest numbers of options contracts (in dollar value) will expire worthless.
    ** It is the point where option owners feel the maximum pain and option sellers reap the most reward.
     */
    public static List<Vanilla> getChain(OptionsDocuments callsNode, OptionType direction) {

        XPath xpathSelector = DocumentHelper.createXPath("/table/tr/td/table/tr");
        List nodes = xpathSelector.selectNodes(callsNode.callsDocument);

        List<Vanilla> list = new ArrayList<Vanilla>();

        for (int i = 1; i < nodes.size(); i++) {

            Element node = (Element) nodes.get(i);

            Vanilla vanilla = new Vanilla(callsNode.ticker);

            if (direction == OptionType.CALL)
                vanilla.type.setValue(OptionType.CALL);
            else
                vanilla.type.setValue(OptionType.PUT);

            List ns = node.elements();

            Element strikeNode = (Element)ns.get(0);
            Element strong = strikeNode.element("a").element("strong");
            String strike = strong.getText();
            vanilla.strike.setValue(Double.parseDouble(strike));

            Element isinNode = (Element)ns.get(1);
            Element a = isinNode.element("a");
            vanilla.name.setValue(a.getText());

            Element lastNode = (Element)ns.get(2);
            a = lastNode.element("b");
            vanilla.premium.setValue(Double.parseDouble(a.getText()));

            Element changeNode = (Element)ns.get(3);
            a = changeNode.element("span").element("b");
            vanilla.change.setValue(Double.parseDouble(a.getText()));

            Element bidNode = (Element)ns.get(4);
            if (!bidNode.getText().equals("N/A"))
                vanilla.bid.setValue(Double.parseDouble(bidNode.getText()));
            else
                vanilla.bid.setValue(Double.NaN);

            Element askNode = (Element)ns.get(5);
            if (!askNode.getText().equals("N/A"))
                vanilla.ask.setValue(Double.parseDouble(askNode.getText()));
            else
                vanilla.ask.setValue(Double.NaN);

            Element volumeNode = (Element)ns.get(6);

            if (!volumeNode.getText().equals("N/A"))
                vanilla.contractSize.setValue(Integer.parseInt(volumeNode.getText().replaceAll(",","")));
            else
                vanilla.contractSize.setValue(-1);

            Element openInterestNode = (Element)ns.get(7);
            vanilla.openInterest.setValue(Integer.parseInt(openInterestNode.getText().replaceAll(",","")));

            list.add(vanilla);
        }
        return list;
    }

    public static OptionsDocuments getOptionsDocuments(String ticker) throws IOException, SAXException, ParserConfigurationException, DocumentException {
        final String LOGON_SITE = "finance.yahoo.com";
        final int LOGON_PORT = 80;

        OptionsDocuments documents = null;
        StringBuffer sb = new StringBuffer();
        String s3;

        String url = "http://" + LOGON_SITE + ":" + LOGON_PORT + "/q/op?s=" + ticker;
        System.out.println("url = " + url);

        InputStream is = NetUtils.openURL(url);
        s3 = NetUtils.getLineFromURL(is);

        System.out.println("is = " + s3);

        while (s3 != null) {
            if (s3 == null) {
                break;
            }
            if (s3.startsWith("<!--")) {
                break;
            }
            if (s3.contains("Call Options")) {
                documents = scrape(ticker, s3);
                break;
            }
            s3 = NetUtils.getLineFromURL(is);
        }
        return documents;
    }

    public static OptionsDocuments scrape(String ticker, String htlmScreen) throws IOException, SAXException, ParserConfigurationException, DocumentException {
        OptionsDocuments documents = new OptionsDocuments(ticker);
        int index = htlmScreen.indexOf("<table class=\"yfnc_datamodoutline1\"", 0);
        String s = htlmScreen.substring(index);
        int end = s.indexOf("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
        String callsTable = s.substring(0,end);
        callsTable = fix(callsTable);
        documents.setCallsDocument(DocumentHelper.parseText(callsTable));
        String puts = s.substring(end);
        index = puts.indexOf("<table class=\"yfnc_datamodoutline1\"", 0);
        s = puts.substring(index);
        end = s.indexOf("<table border=\"0\" cellpadding=\"2\" cellspacing=\"0\">");
        String putsTable = s.substring(0,end);
        putsTable = fix(putsTable);
        documents.setPutsDocument(DocumentHelper.parseText(putsTable));
        return documents;

    }

    private static String fix(String callsTable) {

        callsTable = callsTable.replaceAll("alt=\"Up\">", "/>");
        callsTable = callsTable.replaceAll("alt=\"Down\">", "/>");

        ArrayList<String> strings = new ArrayList<String>();
        strings.add("nowrap");
        strings.add("class=\"yfnc_tabledata1\"");
        strings.add("align=\"right\"");
        strings.add("align=\"left\"");
        strings.add("class=\"yfnc_h\"");
        strings.add("style=\"color:#000000;\"");

        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            callsTable = callsTable.replaceAll(s, "");
        }

        return callsTable;
    }

}

