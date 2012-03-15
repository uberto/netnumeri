package com.netnumeri.server.utils;

import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.data.MaximumPainBean;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Vanilla;
import com.netnumeri.shared.finance.utils.NetUtils;
import com.netnumeri.shared.finance.utils.YahooInstantSnapshot;
import com.netnumeri.shared.finance.utils.YahooUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class YahooOptions {

    public static Document getStockOptionChain(String ticker) throws Exception {
        final String LOGON_SITE = "finance.yahoo.com";
        final int LOGON_PORT = 80;

        StringBuffer sb = new StringBuffer();
        String s3;

        InputStream is = NetUtils.openURL("http://" + LOGON_SITE + ":" + LOGON_PORT + "/q/op?s=" + ticker);
        s3 = NetUtils.getLineFromURL(is);
        while (s3 != null) {
            if (s3 == null) {
                break;
            }
            if (s3.startsWith("<!--")) {
                break;
            }
            sb.append(s3);
            s3 = NetUtils.getLineFromURL(is);
        }

        
//        HttpClient client = new HttpClient();
//        client.getHostConfiguration().setHost(LOGON_SITE, LOGON_PORT, "http");
//        client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
//        GetMethod authpost = new GetMethod("/q/op?s=" + ticker);
//        client.executeMethod(authpost);

        String s = sb.toString();
        int i = s.indexOf("Strike");

        String ss = s.substring(s.indexOf("Strike"));
        ss = ss.substring(ss.indexOf("/q/op?s="));
        String sss = "Highlighted options are in-the-money.";
        int j = ss.indexOf(sss);
        String calls = ss.substring(0, j);

        calls = "    <calls><table><tr><td><b>" +
                "           <a name=\"STRIKE" + calls;
        calls = calls.replaceAll("nowrap", "");
        calls = calls.replaceAll("class=\"yfnc_tabledata1\"", "");
        calls = calls.replaceAll("align=\"right\"", "");
        calls = calls.replaceAll("class=\"yfnc_h\"", "");

        String c = calls.replaceAll("alt=\"Up\">", "alt=\"Up\"/>");
        c = c.replaceAll("alt=\"Down\">", "alt=\"Down\"/>");

        String x = "</td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
        c = c.replaceAll(x, "</calls>");
        String puts = c.substring(c.indexOf("</calls>"));
        c = c.substring(0, c.indexOf("</calls>")) + "</calls>";
        puts = puts.substring(puts.indexOf("/q/op?s="));
        puts = "<puts><table><tr><td><b><a name=\"STRIKE" + puts;
        String cputs = puts.replaceAll("alt=\"Up\">", "alt=\"Up\"/>");
        cputs = cputs.replaceAll("alt=\"Down\">", "alt=\"Down\"/>");
        cputs = cputs.replaceAll("</td></tr></table><table border=\"0\" cellpadding=\"2\" cellspacing=\"0\">", "</puts>");
        cputs = cputs.substring(0, cputs.indexOf("</puts>")) + "</puts>";
        Document doc = XML.stringToDocument("<root>" + c + cputs + "</root>");
//        authpost.releaseConnection();
        return doc;
    }

    public static void main(String[] args) throws Exception {
        computeMaxPain("AA");
        computeMaxPain("AXP");
        computeMaxPain("BA");
        computeMaxPain("BAC");
        computeMaxPain("CAT");
        computeMaxPain("CSCO");
        computeMaxPain("CVX");
        computeMaxPain("DD");
        computeMaxPain("DIS");
        computeMaxPain("GE");
        computeMaxPain("HD");
        computeMaxPain("HPQ");
        computeMaxPain("IBM");
        computeMaxPain("INTC");
        computeMaxPain("JNJ");
        computeMaxPain("JPM");
        computeMaxPain("KFT");
        computeMaxPain("KO");
        computeMaxPain("MCD");
        computeMaxPain("MMM");
        computeMaxPain("MRK");
        computeMaxPain("MSFT");
        computeMaxPain("PFE");
        computeMaxPain("PG");
        computeMaxPain("T");
        computeMaxPain("TRV");
        computeMaxPain("UTX");
        computeMaxPain("VZ");
        computeMaxPain("WMT");
        computeMaxPain("XOM");
    }

    private static void computeMaxPain(String str) throws Exception {
        Document doc = YahooOptions.getStockOptionChain(str);
//        System.out.println("doc = " + XML.toString(doc, true, true));

        double lastPrice = 0;
        try {
            YahooInstantSnapshot snapshot = YahooUtils.getCompanySnapshot(str);
            lastPrice = Double.parseDouble(snapshot.getLastPrice());
            System.out.println("lastPrice = " + lastPrice);
        } catch (NumberFormatException e) {
            //    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Node callsNode = XML.findNode(doc, "calls");
        Node putsNode = XML.findNode(doc, "puts");

        List<Vanilla> callsOptions = getChain(callsNode, FinConstants.kCall);
        List<Vanilla> putsOptions = getChain(putsNode, FinConstants.kPut);

//        double cumulativeValue = computeCumulativeValue(lastPrice, callsOptions);

        List<MaximumPainBean> callBeans = new ArrayList<MaximumPainBean>();
        for (int i = 0; i < callsOptions.size(); i++) {
            Vanilla vanilla = callsOptions.get(i);
            MaximumPainBean bean = new MaximumPainBean();
            double cumul = computeCumulativeValue(vanilla.strike(), callsOptions);
            bean.setStrike(vanilla.strike());
            bean.setCumulative(cumul);
            callBeans.add(bean);
        }

        Map map = new HashMap();
        for (int i = 0; i < callBeans.size(); i++) {
            MaximumPainBean maximumPainBean = callBeans.get(i);
            //         System.out.print("maximumPainBean.getStrike() = " + maximumPainBean.getStrike() + " ");
            //         System.out.println("maximumPainBean.getCumulative() = " + maximumPainBean.getCumulative());
            map.put(maximumPainBean.getStrike(), maximumPainBean.getCumulative());
        }

        List<MaximumPainBean> putsBeans = new ArrayList<MaximumPainBean>();
        for (int i = 0; i < putsOptions.size(); i++) {
            Vanilla vanilla = putsOptions.get(i);
            MaximumPainBean bean = new MaximumPainBean();
            double cumul = computeCumulativeValue(vanilla.strike(), putsOptions);
            bean.setStrike(vanilla.strike());
            bean.setCumulative(cumul);
            putsBeans.add(bean);
        }

        Double maxPainCumulative = Double.MAX_VALUE;
        Double maxPainStrike = Double.MAX_VALUE;
        for (int i = 0; i < putsBeans.size(); i++) {
            MaximumPainBean maximumPainBean = putsBeans.get(i);
            //       System.out.print("maximumPainBean.getStrike() = " + maximumPainBean.getStrike() + " ");
            //       System.out.println("maximumPainBean.getCumulative() = " + maximumPainBean.getCumulative());

            if (map.get(maximumPainBean.getStrike()) != null) {
                Double d = (Double) map.get(maximumPainBean.getStrike());
                Double d2 = d + maximumPainBean.getCumulative();

                if (d2 < maxPainCumulative) {
                    maxPainCumulative = d2;
                    maxPainStrike = maximumPainBean.getStrike();
                }
            }
        }
        System.out.println("last price for " + str + " : " + lastPrice);
        System.out.println("maxPainStrike for " + str + " : " + maxPainStrike);
    }

    private static double computeCumulativeValue(double underSpotPrice, List<Vanilla> options) {
        double cumulativeValue = 0;
//        if (options != null)
//            for (int i = 0; i < options.size(); i++) {
//                Vanilla vanilla = options.get(i);
//                int openInt = vanilla.getLastQuote().getOpenInterest();
//                if (vanilla.getDirection() == FinConstants.kCall) {
//                    if (underSpotPrice > vanilla.getStrike()) {
//                        double value = underSpotPrice - vanilla.getStrike();
//                        cumulativeValue = cumulativeValue + (value * openInt);
//                    }
//                } else if (vanilla.getDirection() == FinConstants.kPut) {
//                    if (underSpotPrice < vanilla.getStrike()) {
//                        double value = vanilla.getStrike() - underSpotPrice;
//                        cumulativeValue = cumulativeValue + (value * openInt);
//                    }
//                }
//            }
        return cumulativeValue;
    }

    /*
    ** According to the theory of maximum pain, the underlying stock or index will tend to move towards the price
    ** where the greatest numbers of options contracts (in dollar value) will expire worthless.
    ** It is the point where option owners feel the maximum pain and option sellers reap the most reward.
     */
    private static List<Vanilla> getChain(Node callsNode, int direction) {
        Node tableNode = XML.findNode(callsNode, "table");
        List<Node> children = XML.getImmediateChildren(tableNode);
        List<Vanilla> list = new ArrayList<Vanilla>();

        for (int i = 0; i < children.size(); i++) {
            Vanilla vanilla = new Vanilla();
            vanilla.setDirection(direction);
            Node row = children.get(i);
            List<Node> columns = XML.getImmediateChildren(row);
            for (int j = 0; j < columns.size(); j++) {
                Node node = columns.get(j);

//                if (j == 0) {
//                    vanilla.setStrike(processStrike(node));
//                } else if (j == 1) {
//                    vanilla.setName(getOptionName(node));
//                } else if (j == 2) {
//                    vanilla.setPremium(getValue(node));
//                } else if (j == 4) {
//                    vanilla.getLastQuote().setBid(getBid(node));
//                } else if (j == 5) {
//                    vanilla.getLastQuote().setAsk(getAsk(node));
//                } else if (j == 7) {
//                    vanilla.getLastQuote().setOpenInterest(getOpenInterest(node));
//                }
            }
            list.add(vanilla);
        }
        return list;
    }

    private static double getAsk(Node node) {
//        System.out.println("XML.toString(node,true,true) = " + XML.toString(node, true, true));
        Node sNode = XML.findNode(node, "span");
        if (sNode != null) {
            String s = XML.getData(sNode);
            return Double.parseDouble(s);
        }
        return Double.NaN;
    }

    private static double getBid(Node node) {
//        System.out.println("XML.toString(node,true,true) = " + XML.toString(node, true, true));
        Node sNode = XML.findNode(node, "span");
        if (sNode != null) {
            String s = XML.getData(sNode);
            return Double.parseDouble(s);
        }
        return Double.NaN;
    }

    private static int getOpenInterest(Node node) {
        String s = XML.getData(node);
        s = s.replaceAll(",", "");
        return Integer.parseInt(s);
    }

    private static double getValue(Node node) {
//        System.out.println("XML.toString(node,true,true) = " + XML.toString(node, true, true));
        Node sNode = XML.findNode(node, "span");
        if (sNode != null) {
            return Double.parseDouble(XML.getData(sNode));
        }
        return Double.NaN;
    }

    private static String getOptionName(Node node) {
        Node sNode = XML.findNode(node, "a");
        return XML.getData(sNode);
    }

    private static double processStrike(Node row) {
        Node strikeNode = XML.findNode(row, "a");
        String strikeString = XML.getData(strikeNode);
        return Double.parseDouble(strikeString);
    }
}
