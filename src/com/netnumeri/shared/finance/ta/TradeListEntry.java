package com.netnumeri.shared.finance.ta;


import com.netnumeri.shared.finance.finpojo.Instrument;

import java.util.ArrayList;

public class TradeListEntry {
    public Instrument instrument;
    private ArrayList tradeInfoList;
    public double tradeResult;
    public int numberOfTrades;

    public TradeListEntry(Instrument instrument) {
        this.instrument = instrument;
        tradeResult = 0;
        numberOfTrades = 0;
        tradeInfoList = new ArrayList();
    }

    public int compareTo(Object o) {
        TradeListEntry obj = (TradeListEntry) o;
        if (this == obj) {
            return 0;
        }
        if (tradeResult == obj.getTradeResult()) {
            return 0;
        }
        if (tradeResult < obj.getTradeResult()) {
            return 1;
        }
        if (tradeResult > obj.getTradeResult()) {
            return -1;
        }
        return 0;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    Instrument getInstrument() {
        return instrument;
    }

    ArrayList getTradeInfoList() {
        return tradeInfoList;
    }

    public double getTradeResult() {
        return tradeResult;
    }

    public void setTradeResult(double tradeResult) {
        this.tradeResult = tradeResult;
    }

    public int getNumberOfTrades() {
        return numberOfTrades;
    }

    public void setNumberOfTrades(int numberOfTrades) {
        this.numberOfTrades = numberOfTrades;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("");
        sb.append("<TRADEENTRY>");
        sb.append("<TRADEINFOLIST>" + tradeInfoList.toString() + "</TRADEINFOLIST>");
        sb.append("<NTRADES>" + numberOfTrades + "</NTRADES>");
        sb.append("<RESULT>" + tradeResult + "</RESULT>");
        sb.append("</TRADEENTRY>");
        return sb.toString();
    }

    /*   public void doView(HttpServletRequest request, HttpServletResponse response, Writer out) throws Throwable
    {
        HTMLTable table = new HTMLTable();
        table.addLine("Trade Info Link", tradeInfoList);
        table.addLine("Number of trades", "" + numberOfTrades);
        table.addLine("Result of all trades", "" + tradeResult);
        table.doView(request, response, out);
    }*/


}


