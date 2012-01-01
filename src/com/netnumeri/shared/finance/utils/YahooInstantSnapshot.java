package com.netnumeri.shared.finance.utils;

public class YahooInstantSnapshot {
    private String Symbol;
    private String CompanyName;
    private String LastPrice;
    private String LastTradeDate;
    private String LastTradeTime;
    private String Change;
    private String PercentChange;
    private String Volume;
    private String AverageDailyVol;
    private String Bid;
    private String Ask;
    private String PreviousClose;
    private String TodaysOpen;
    private String DaysRange;
    private String YearWeekRange;
    private String EarningsperShare;
    private String PERatio;
    private String DividendPayDate;
    private String DividendperShare;
    private String DividendYield;
    private String MarketCapitalization;
    private String StockExchange;

    public YahooInstantSnapshot() {
    }

    public YahooInstantSnapshot(String symbol, String companyName, String lastPrice, String lastTradeDate, String lastTradeTime, String change, String percentChange,
                                String volume, String averageDailyVol, String bid, String ask, String previousClose, String todaysOpen, String daysRange,
                                String yearWeekRange, String earningsperShare, String PERatio, String dividendPayDate, String dividendperShare, String dividendYield, String marketCapitalization,
                                String stockExchange) {
        Symbol = symbol;
        CompanyName = companyName;
        LastPrice = lastPrice;
        LastTradeDate = lastTradeDate;
        LastTradeTime = lastTradeTime;
        Change = change;
        PercentChange = percentChange;
        Volume = volume;
        AverageDailyVol = averageDailyVol;
        Bid = bid;
        Ask = ask;
        PreviousClose = previousClose;
        TodaysOpen = todaysOpen;
        DaysRange = daysRange;
        YearWeekRange = yearWeekRange;
        EarningsperShare = earningsperShare;
        this.PERatio = PERatio;
        DividendPayDate = dividendPayDate;
        DividendperShare = dividendperShare;
        DividendYield = dividendYield;
        MarketCapitalization = marketCapitalization;
        StockExchange = stockExchange;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getLastPrice() {
        return LastPrice;
    }

    public void setLastPrice(String lastPrice) {
        LastPrice = lastPrice;
    }

    public String getLastTradeDate() {
        return LastTradeDate;
    }

    public void setLastTradeDate(String lastTradeDate) {
        LastTradeDate = lastTradeDate;
    }

    public String getLastTradeTime() {
        return LastTradeTime;
    }

    public void setLastTradeTime(String lastTradeTime) {
        LastTradeTime = lastTradeTime;
    }

    public String getChange() {
        return Change;
    }

    public void setChange(String change) {
        Change = change;
    }

    public String getPercentChange() {
        return PercentChange;
    }

    public void setPercentChange(String percentChange) {
        PercentChange = percentChange;
    }

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }

    public String getAverageDailyVol() {
        return AverageDailyVol;
    }

    public void setAverageDailyVol(String averageDailyVol) {
        AverageDailyVol = averageDailyVol;
    }

    public String getBid() {
        return Bid;
    }

    public void setBid(String bid) {
        Bid = bid;
    }

    public String getAsk() {
        return Ask;
    }

    public void setAsk(String ask) {
        Ask = ask;
    }

    public String getPreviousClose() {
        return PreviousClose;
    }

    public void setPreviousClose(String previousClose) {
        PreviousClose = previousClose;
    }

    public String getTodaysOpen() {
        return TodaysOpen;
    }

    public void setTodaysOpen(String todaysOpen) {
        TodaysOpen = todaysOpen;
    }

    public String getDaysRange() {
        return DaysRange;
    }

    public void setDaysRange(String daysRange) {
        DaysRange = daysRange;
    }

    public String getYearWeekRange() {
        return YearWeekRange;
    }

    public void setYearWeekRange(String yearWeekRange) {
        YearWeekRange = yearWeekRange;
    }

    public String getEarningsperShare() {
        return EarningsperShare;
    }

    public void setEarningsperShare(String earningsperShare) {
        EarningsperShare = earningsperShare;
    }

    public String getPERatio() {
        return PERatio;
    }

    public void setPERatio(String PERatio) {
        this.PERatio = PERatio;
    }

    public String getDividendPayDate() {
        return DividendPayDate;
    }

    public void setDividendPayDate(String dividendPayDate) {
        DividendPayDate = dividendPayDate;
    }

    public String getDividendperShare() {
        return DividendperShare;
    }

    public void setDividendperShare(String dividendperShare) {
        DividendperShare = dividendperShare;
    }

    public String getDividendYield() {
        return DividendYield;
    }

    public void setDividendYield(String dividendYield) {
        DividendYield = dividendYield;
    }

    public String getMarketCapitalization() {
        return MarketCapitalization;
    }

    public void setMarketCapitalization(String marketCapitalization) {
        MarketCapitalization = marketCapitalization;
    }

    public String getStockExchange() {
        return StockExchange;
    }

    public void setStockExchange(String stockExchange) {
        StockExchange = stockExchange;
    }

}