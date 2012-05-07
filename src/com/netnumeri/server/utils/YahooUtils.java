package com.netnumeri.server.utils;

import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.date.*;
import com.netnumeri.shared.finance.finpojo.Instrument;
import com.netnumeri.shared.finance.finpojo.asset.Asset;
import com.netnumeri.shared.finance.finpojo.asset.Stock;
import com.netnumeri.shared.finance.utils.DateUtils;
import com.netnumeri.shared.finance.utils.NetUtils;
import com.netnumeri.shared.finance.utils.YahooInstantSnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

public class YahooUtils  {

    private static final Logger logger = Logger.getLogger(YahooUtils.class.getName());

    public static synchronized String getLineFromURL(InputStream myInputStream) {
        int ch = -1;
        StringBuffer sb = new StringBuffer(256);
        do {
            try {
                ch = myInputStream.read();
            } catch (IOException e) {
                return null;
            }
            if (ch == -1) {
                if (sb.length() == 0) {
                    return null;
                } else {
                    return sb.toString();
                }
            }
            if (ch == 10 || ch == 13) {
                if (sb.length() > 0) {
                    return sb.toString();
                }
            } else {
                sb.append((char) ch);
            }
        }
        while (true);
    }

    // http://finance.yahoo.com/d/quotes.csv?s=EURUSD=X&f=sl1d1ba&e=.csv
    public static String getExchangeRate(String s) {
        try {
            String completeUrl = "http://download.finance.yahoo.com/d/quotes.csv";
            logger.info("completeUrl = " + completeUrl);
            URL url = new URL(completeUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
            os.flush();
            os.write("f=sl1d1ba&s=" + s);
            os.close();
            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                String line = getLineFromURL(is);
                logger.info("Line = " + line);
                return line;
            }
        } catch (MalformedURLException e) {
            logger.info("exception:" + e);
        } catch (IOException e) {
            logger.info("exception:" + e);
        }
        return null;
    }

    /**
     * @param ticker
     * @param lastmonth  0-11    (6 = July)
     * @param lastday    1-31
     * @param lastyear
     * @param firstmonth
     * @param firstday
     * @param firstyear
     * @param frequency  d = daily  w= weekly y=yearly
     */

    public static Asset downloadYahooData(String ticker,
                                          int lastmonth,
                                          int lastday,
                                          int lastyear,
                                          int firstmonth,
                                          int firstday,
                                          int firstyear,
                                          String frequency) throws IOException, ParseException {
        Stock stock = new Stock(ticker);
        String url = "http://ichart.yahoo.com/table.csv?s=" +
                ticker.trim() + "&d=" + lastmonth + "&e=" + lastday + "&f=" + lastyear + "&g=" + frequency + "&a=" + firstmonth + "&b=" + firstday + "&c=" + firstyear + "&ignore=.csv";
        downloadData(stock, url);
        return stock;
    }

    public static Stock downloadYahooData(String ticker,
                                          TDay from,
                                          TDay to) throws IOException, ParseException {
        return (downloadYahooData(ticker, from, to, "d"));
    }

    public static Stock downloadYahooData(String ticker,
                                          TDay from,
                                          TDay to,
                                          String frequency) throws IOException, ParseException {
        int firstmonth = from.getMonth();
        int firstday = from.getWeekday();
        int firstyear = from.getYear();

        int lastmonth = to.getMonth();
        int lastday = to.getWeekday();
        int lastyear = to.getYear();

        //    http://ichart.finance.yahoo.com/table.csv?s=SSRI&a=07&b=1&c=2007&d=03&e=10&f=2009&g=d&ignore=.csv
        Stock stock = new Stock(ticker);

        String url = "http://ichart.yahoo.com/table.csv?s=" +
                ticker.trim() + "&d=" + lastmonth + "&e=" + lastday + "&f=" + lastyear + "&g=" + frequency + "&a=" + firstmonth + "&b=" + firstday + "&c=" + firstyear + "&ignore=.csv";
        System.out.println("\n\n\n\nurl = " + url);

        downloadData(stock, url);
        return stock;
    }

    /**
     * http://ichart.yahoo.com/table.csv?s=SSRI&d=6&e=2&f=2004&g=d&a=10&b=11&c=2000&ignore=.csv";
     *
     * @param instrument
     * @param url
     */
    public static void downloadData(Instrument instrument, String url) throws IOException, ParseException {
        System.out.println("YahooUtils.downloadData");
        String date = null;
        double open = 0;
        double high = 0;
        double low = 0;
        double close = 0;
        double volume = 0;
        Stack lines = new Stack();
        String s3 = null;
        System.out.println("url = " + url);
        InputStream is = NetUtils.openURL(url);
        if (is == null) {
            throw new RuntimeException("cannot newInstance InputStream");
        }
        NetUtils.getLineFromURL(is);
        s3 = NetUtils.getLineFromURL(is);
        while (s3 != null) {
            if (s3 == null) {
                break;
            }
            if (s3.startsWith("<!--")) {
                break;
            }
            lines.push(s3);
            s3 = NetUtils.getLineFromURL(is);
        }

        int i = 0;

        while (!lines.empty()) {

            s3 = (String) lines.pop();

            System.out.println("s3 = " + s3);

            StringTokenizer stringtokenizer = new StringTokenizer(s3, ",");
            String token = stringtokenizer.nextToken();
            date = token;
            float d = 0;
            float d1 = 0;
            for (int j = 0; j < 4; j++) {
                token = stringtokenizer.nextToken();
                double dumm = Double.parseDouble(token);
                switch (j) {
                    case 0:
                        open = dumm;
                        break;
                    case 1:
                        high = dumm;
                        break;
                    case 2:
                        low = dumm;
                        break;
                    case 3:
                        close = dumm;
                        break;
                }
                d += dumm;
            }
            d1 = d / 4;
            double vol = Double.parseDouble(stringtokenizer.nextToken());
            volume = vol;
            instrument.addDaily(i++, DateUtils.toYahoo(date), high, low, open, close, (int) volume, 0, FinConstants.VALID);
        }
        NetUtils.closeURL(is);
    }

    public static Date toYYmmDD(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(date);
    }

    private static YahooInstantSnapshot toSnapshot(String line, String delimiter) {
        YahooInstantSnapshot snap = new YahooInstantSnapshot();
        StringTokenizer stringtokenizer = new StringTokenizer(line, delimiter, true);
        String token = null;
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
        }

        snap.setSymbol(token);
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setCompanyName(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setLastPrice(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setLastTradeDate(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setLastTradeTime(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setChange(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setPercentChange(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setVolume(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setAverageDailyVol(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setBid(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setAsk(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setPreviousClose(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setTodaysOpen(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setDaysRange(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setYearWeekRange(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setEarningsperShare(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setPERatio(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setDividendPayDate(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setDividendperShare(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setDividendYield(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }
            token = stringtokenizer.nextToken();

            snap.setMarketCapitalization(token);
        }
        if (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(delimiter)) {
                token = stringtokenizer.nextToken();
            }

            snap.setStockExchange(token);
        }
        return snap;
    }

    public static YahooInstantSnapshot getCompanySnapshot(String s) {
        try {
            String completeUrl = "http://download.finance.yahoo.com/d/quotes.csv";
            URL url = new URL(completeUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
            os.flush();
            os.write("f=snl1d1t1c1p2va2bapomwerr1dyj1x&s=" + s);
            os.close();
            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                String line = getLineFromURL(is);
                return toSnapshot(line, ",");
            }
        } catch (IOException e) {
        }
        return null;
    }

    public static double formatYahooDate(String s) {
        StringTokenizer stringtokenizer = new StringTokenizer(s, "-");
        int day = Integer.parseInt(stringtokenizer.nextToken());
        String m = stringtokenizer.nextToken();
        int month = -1;
        if (m.equalsIgnoreCase("jan")) {
            month = 0;
        } else if (m.equalsIgnoreCase("feb")) {
            month = 1;
        } else if (m.equalsIgnoreCase("mar")) {
            month = 2;
        } else if (m.equalsIgnoreCase("apr")) {
            month = 3;
        } else if (m.equalsIgnoreCase("may")) {
            month = 4;
        } else if (m.equalsIgnoreCase("jun")) {
            month = 5;
        } else if (m.equalsIgnoreCase("jul")) {
            month = 6;
        } else if (m.equalsIgnoreCase("aug")) {
            month = 7;
        } else if (m.equalsIgnoreCase("sep")) {
            month = 8;
        } else if (m.equalsIgnoreCase("oct")) {
            month = 9;
        } else if (m.equalsIgnoreCase("nov")) {
            month = 10;
        } else if (m.equalsIgnoreCase("dec")) {
            month = 11;
        }
        String y = stringtokenizer.nextToken();
        int year = Integer.parseInt(y);
        if (y.substring(0, 1).equalsIgnoreCase("0")) {
            year = 2000 + year;
        } else {
            year = 1900 + year;
        }
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, day, 0, 0, 0);
        java.util.Date dat = cal.getTime();
        return (double) dat.getTime();
    }

    public static String formatVDate(String s) {
        StringTokenizer stringtokenizer = new StringTokenizer(s, "-");
        String day = stringtokenizer.nextToken();
        if (day.length() == 1) {
            day = "0" + day;
        }
        String m = stringtokenizer.nextToken();
        String month = null;
        if (m.equalsIgnoreCase("jan")) {
            month = "01";
        } else if (m.equalsIgnoreCase("feb")) {
            month = "02";
        } else if (m.equalsIgnoreCase("mar")) {
            month = "03";
        } else if (m.equalsIgnoreCase("apr")) {
            month = "04";
        } else if (m.equalsIgnoreCase("may")) {
            month = "05";
        } else if (m.equalsIgnoreCase("jun")) {
            month = "06";
        } else if (m.equalsIgnoreCase("jul")) {
            month = "07";
        } else if (m.equalsIgnoreCase("aug")) {
            month = "08";
        } else if (m.equalsIgnoreCase("sep")) {
            month = "09";
        } else if (m.equalsIgnoreCase("oct")) {
            month = "10";
        } else if (m.equalsIgnoreCase("nov")) {
            month = "11";
        } else if (m.equalsIgnoreCase("dec")) {
            month = "12";
        }
        String y = stringtokenizer.nextToken();
        return y + month + day;
    }


    /* toMilliseconds Yahoo date in StockEval chart date
    */

    public static String parseYahooDate(String s) {
        StringTokenizer stringtokenizer = new StringTokenizer(s, "-");
        String day = stringtokenizer.nextToken();
        String m = stringtokenizer.nextToken();

        String month = null;
        if (m.equalsIgnoreCase("jan")) {
            month = "01";
        } else if (m.equalsIgnoreCase("feb")) {
            month = "02";
        } else if (m.equalsIgnoreCase("mar")) {
            month = "03";
        } else if (m.equalsIgnoreCase("apr")) {
            month = "04";
        } else if (m.equalsIgnoreCase("may")) {
            month = "05";
        } else if (m.equalsIgnoreCase("jun")) {
            month = "06";
        } else if (m.equalsIgnoreCase("jul")) {
            month = "07";
        } else if (m.equalsIgnoreCase("aug")) {
            month = "08";
        } else if (m.equalsIgnoreCase("sep")) {
            month = "09";
        } else if (m.equalsIgnoreCase("oct")) {
            month = "10";
        } else if (m.equalsIgnoreCase("nov")) {
            month = "11";
        } else if (m.equalsIgnoreCase("dec")) {
            month = "12";
        }
        String y = stringtokenizer.nextToken();
        return (y + month + day);
    }

    public static String parseYahooDate4StockEval(String s) {
        StringTokenizer stringtokenizer = new StringTokenizer(s, "-");
        String day = stringtokenizer.nextToken();
        String m = stringtokenizer.nextToken();
        String month = null;
        if (m.equalsIgnoreCase("jan")) {
            month = "01";
        } else if (m.equalsIgnoreCase("feb")) {
            month = "02";
        } else if (m.equalsIgnoreCase("mar")) {
            month = "03";
        } else if (m.equalsIgnoreCase("apr")) {
            month = "04";
        } else if (m.equalsIgnoreCase("may")) {
            month = "05";
        } else if (m.equalsIgnoreCase("jun")) {
            month = "06";
        } else if (m.equalsIgnoreCase("jul")) {
            month = "07";
        } else if (m.equalsIgnoreCase("aug")) {
            month = "08";
        } else if (m.equalsIgnoreCase("sep")) {
            month = "09";
        } else if (m.equalsIgnoreCase("oct")) {
            month = "10";
        } else if (m.equalsIgnoreCase("nov")) {
            month = "11";
        } else if (m.equalsIgnoreCase("dec")) {
            month = "12";
        }
        if (day.length() == 1) {
            day = "0" + day;
        }
        String y = stringtokenizer.nextToken();
        return (y + month + day);
    }


    public static String proxyYahooData(String ticker,
                                        Date from,
                                        Date to) throws IOException, ParseException {
        return proxyYahooData(ticker, from, to, "d");
    }

    public static String proxyYahooData(String ticker,
                                        Date from,
                                        Date to,
                                        String frequency) throws IOException, ParseException {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(from);
        int firstmonth = cal.get(GregorianCalendar.MONTH);
        int firstday = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int firstyear = cal.get(GregorianCalendar.YEAR);

        cal.setTime(to);
        int lastmonth = cal.get(GregorianCalendar.MONTH);
        int lastday = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int lastyear = cal.get(GregorianCalendar.YEAR);

        //    http://ichart.finance.yahoo.com/table.csv?s=SSRI&a=07&b=1&c=2007&d=03&e=10&f=2009&g=d&ignore=.csv
        String url = "http://ichart.yahoo.com/table.csv?s=" +
                ticker.trim() + "&d=" + lastmonth + "&e=" + lastday + "&f=" + lastyear + "&g=" + frequency + "&a=" + firstmonth + "&b=" + firstday + "&c=" + firstyear + "&ignore=.csv";
        System.out.println("\n\n\n\nurl = " + url);

        return proxyDownloadData(url);
    }

    /**
     * http://ichart.yahoo.com/table.csv?s=SSRI&d=6&e=2&f=2004&g=d&a=10&b=11&c=2000&ignore=.csv";
     */
    public static String proxyDownloadData(String url) throws IOException, ParseException {
        System.out.println("YahooUtils.proxyDownloadData");
        Stack lines = new Stack();
        String s3 = null;
        System.out.println("url = " + url);
        InputStream is = NetUtils.openURL(url);
        if (is == null) throw new RuntimeException("cannot newInstance InputStream");
        NetUtils.getLineFromURL(is);
        s3 = NetUtils.getLineFromURL(is);
        while (s3 != null) {
            if (s3 == null) break;
            if (s3.startsWith("<!--")) break;
            lines.push(s3);
            s3 = NetUtils.getLineFromURL(is);
        }
        StringBuffer sb = new StringBuffer();
        while (!lines.isEmpty()) {
            String o = (String) lines.pop();
            sb.append(o + "\n");
        }
        System.out.println("quote.toString() = " + sb.toString());
        return sb.toString();
    }

    public static Integer getCurrentMonth(){
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.MONTH);
        return i+1;
    }

    public static Integer getCurrentYear(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public static Date getNextMonth(Date date){
        Calendar originalDate = Calendar.getInstance();
        originalDate.setTime(date);
        Calendar nextMonthDate = (Calendar) originalDate.clone();
        nextMonthDate.add(Calendar.MONTH, 1);
        return nextMonthDate.getTime();
    }

    public static String mapKey (Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH);
    }

}




