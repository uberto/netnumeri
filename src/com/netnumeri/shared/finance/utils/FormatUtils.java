package com.netnumeri.shared.finance.utils;


import com.netnumeri.shared.finance.beans.FinConstants;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class FormatUtils implements FinConstants {

    public static String getString(HttpServletRequest request, String key) {
        try {
            ResourceBundle labels = (ResourceBundle) request.getSession().getAttribute("labels");
            return labels.getString(key);
        } catch (Exception e) {
            return key;
        }
    }

    public static String format(double d, Locale locale) {
        DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        format.applyPattern("###,###.###");
        format.setMinimumIntegerDigits(1);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        format.setGroupingUsed(false);
        return format.format(d);
    }

    public static void setTable(Hashtable table, String stringa) {
        StringTokenizer stringtokenizer =
                new StringTokenizer(stringa, ",", false);
        String token = null;
        while (stringtokenizer.hasMoreTokens()) {
            token = stringtokenizer.nextToken();
            if (token.equalsIgnoreCase(",") || token.equalsIgnoreCase(" ")) {
                continue;
            }
        }
        table.put(token, token);
    }

    public static String optionToString(int option) {
        switch (option) {
            case VALID:
                return "VALID";
            case NOTAVAILABLE:
                return "N/A";
            case INTERPOLATED:
                return "INTERPOLATED";
        }
        return "";
    }

    public static String actionToSting(int action) {
        switch (action) {
            case BUY:
                return "Buy";
            case SELL:
                return "Sell";
            case BUYSHORT:
                return "Buy short";
            case SELLSHORT:
                return "Sell short";
            case HOLD:
                return "Hold";
            default:
                System.out.println("actionToString. Unknown transaction type");
                return "";
        }
    }

    public static String escape(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!Character.isLetterOrDigit(ch)) {
                sb.append("");
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    public static String replaceSpace(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isSpaceChar(ch) ||
                    ch == '-') {
                sb.append("&nbsp;");
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }


    public static String formatDouble(double d, int i, int j) {
        StringBuffer stringbuffer;
        for (stringbuffer = new StringBuffer(formatDouble(d, j)); stringbuffer.length() < i; stringbuffer.insert(0, ' ')) {
            ;
        }
        return stringbuffer.toString();
    }


    public static String formatDouble(double d, int i) {
        Long long1 = new Long(Math.round(d * Math.pow(10D, i)));
        StringBuffer stringbuffer = new StringBuffer(long1.toString());
        if (i == 0) {
            return stringbuffer.toString();
        }
        int j = 0;
        if (d < 0) {
            j = 1;
        }
        for (; stringbuffer.length() <= i + j; stringbuffer.insert(j, '0')) {
            ;
        }
        stringbuffer.insert(stringbuffer.length() - i, '.');
        return stringbuffer.toString();
    }

    public static String formatString(String s, int i) {
        int k = s.length();
        StringBuffer stringbuffer = new StringBuffer();
        for (int j = 0; j < i; j++) {
            if (j < k) {
                stringbuffer.append(s.charAt(j));
            } else {
                stringbuffer.append(' ');
            }
        }
        return stringbuffer.toString();
    }


    public static String dToStr(double d) {
        Double double1 = new Double(d);
        return double1.toString();
    }

    public static String setMoneyValue(double d) {
        double d1 = d;
        String s = clearExponent(d1);
        int i = s.length();
        int j = s.indexOf(".");
        if (j < i - 2) {
            float f = Float.valueOf(s.substring(j + 3, j + 4)).floatValue();
            int k = (int) f;
            if (k > 4) {
                if (d1 > 0) {
                    d1 += 0.005D;
                } else {
                    d1 -= 0.005D;
                }
                s = clearExponent(d1);
                i = s.length();
                j = s.indexOf(".");
            }
        }
        if (j == -1) {
            s = s + ".00";
        } else if (j == i - 2) {
            s = s + "0";
        } else if (j < i - 2) {
            s = s.substring(0, j + 3);
        }
        return s;
    }

    public static String clearExponent(double d) {
        double d1 = d;
        String s = fmt(d, 0, 16);
        int i = s.length();
        int j = s.indexOf("e");
        if (j > 0) {
            float f = Float.valueOf(s.substring(j + 1)).floatValue();
            int k = (int) f;
            String s1 = s;
            if (d1 > 0) {
                s = s1.substring(0, 1) + s1.substring(2, 2 + k) + "." + s1.substring(2 + k, j);
            } else {
                s = s1.substring(0, 2) + s1.substring(3, 3 + k) + "." + s1.substring(3 + k, j);
            }
        }
        return s;
    }

    public static String doubleToString(double d) {
        String s4 = "0123456789";
        boolean flag;
        double d1;
        if (d < 0) {
            flag = true;
            d1 = -d;
        } else {
            flag = false;
            d1 = d;
        }
        String s = Double.toString(d1);
        int j = s.indexOf('e');
        String s1;
        String s2;
        int i;
        if (j == -1) {
            s1 = s;
            s2 = "";
            i = 0;
        } else {
            s1 = s.substring(0, j);
            s2 = s.substring(j + 1);
            if (s2.startsWith("+")) {
                i = Integer.parseInt(s2.substring(1));
            } else {
                i = Integer.parseInt(s2);
            }
        }
        int k = s1.indexOf('.');
        String s3;
        if (k == -1) {
            s3 = s1;
        } else {
            s3 = s1.substring(0, k);
        }
        long l1;
        if (s3.length() == 0) {
            l1 = 0L;
        } else {
            l1 = Long.parseLong(s3);
        }
        StringBuffer stringbuffer = new StringBuffer(s3 + ".");
        double d2 = Math.pow(10D, i);
        double d3 = d1 - (double) l1 * d2;
        if (d3 < 0) {
            d3 += d2;
        }
        int l = 16 - s3.length();
        if (l > 3) {
            l = 3;
        }
        for (int k1 = 0; k1 < l; k1++) {
            d2 /= 10D;
            int i1 = (int) (d3 / d2);
            stringbuffer.append(s4.charAt(i1));
            d3 -= (double) i1 * d2;
        }

        int j1 = stringbuffer.length();
        if (stringbuffer.charAt(j1 - 1) == '.') {
            stringbuffer.setLength(--j1);
        }
        return (flag ? "-" : "") + stringbuffer + (s2.length() == 0 ? "" : "e" + s2);
    }

    public static String fmt(double d, int i, int j) {
        return fmt(d, i, j, 0);
    }

    public static String fmt(double d, int i, int j, int k) {
        return fmt(doubleToString(d), i, k);
    }

    public static String fmt(String s, int i, int j) {
        int k = s.length();
        if (k >= i) {
            return s;
        }
        int l = i - k;
        StringBuffer stringbuffer = new StringBuffer(l);
        for (int i1 = 0; i1 < l; i1++) {
            stringbuffer.append(' ');
        }
        return stringbuffer + s;
    }

    public static final String MonthNamesShort[] = {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
            "Nov", "Dec"
    };

    public static String format(String string, char tosubstitute, String replacement) {
        if (string != null) {
            byte[] ss = string.getBytes();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < ss.length; i++) {
                byte s = ss[i];
                if (s == tosubstitute) {
                    sb.append(replacement);
                } else {
                    sb.append((char) s);
                }
            }
            return sb.toString();
        }
        return null;
    }

    public static String format(double d) {
        Locale loc = new Locale("en", "EN");
        // NumberFormat nf = NumberFormat.getNumberInstance(loc);
        DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance(loc);
        format.applyPattern("###,###.###");
        format.setMinimumIntegerDigits(1);
        format.setMaximumFractionDigits(3);
        format.setMinimumFractionDigits(1);
        format.setGroupingUsed(false);
        return format.format(d);
    }

    public static String format(Integer d) {
        Locale loc = new Locale("en", "EN");
        // NumberFormat nf = NumberFormat.getNumberInstance(loc);
        DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance(loc);
        format.applyPattern("###,###");
        format.setMinimumIntegerDigits(1);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(1);
        format.setGroupingUsed(false);
        return format.format(d);
    }

    public static String formatStockEval(double d) {
        Locale loc = new java.util.Locale("en", "EN");
        // NumberFormat nf = NumberFormat.getNumberInstance(loc);
        DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance(loc);
        format.applyPattern("###,###.###");
        format.setMinimumIntegerDigits(1);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(1);
        format.setGroupingUsed(false);
        return format.format(d);
    }

    public static String toLowerCase(String str) {
        char[] caratteri = str.toCharArray();
        for (int i = 0; i < caratteri.length; i++) {
            char c = caratteri[i];
            if (Character.isUpperCase(c)) {
                caratteri[i] = Character.toLowerCase(c);
            }
        }
        return new String(caratteri);
    }

    public static String toString(int date) {
        String asString = "" + date;
        return asString.substring(0, 4) + "/" + asString.substring(4, 6) + "/" + asString.substring(6, 8);
    }

    public static String getGetter(String field) {
        if (field == null) {
            throw new IllegalArgumentException("field cannot be null");
        }
        if (field.length() == 0) {
            throw new IllegalArgumentException("field cannot be null");
        }
        try {
            char[] caratteri = field.toCharArray();
            caratteri[0] = Character.toUpperCase(caratteri[0]);
            String.valueOf(caratteri);
            return "get" + String.valueOf(caratteri);
        } catch (Exception e) {
            return null;
        }
    }

    public static String format(Double cost) {
        if (cost != null) {
            return format(cost.doubleValue());
        } else {
            return "0";
        }
    }

    public static String toSQL(Date date) {
        SimpleDateFormat sqlformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:yyyyMMddHHmmss.SSS");
        return sqlformat.format(date);
    }

    public static String padright(String str, char c, int totallen) {
        if (str == null) {
            str = "";
        }
        if (str.length() > totallen) {
            str = str.substring(0, totallen);
        }
        StringBuffer sb = new StringBuffer();
        if (str == null) {
            for (int i = 0; i < totallen; i++) {
                sb.append(c);
            }
            return sb.toString();
        }
        sb.append(str);
        for (int i = str.length(); i < totallen; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static String padleft(String str, char c, int totallen) {
        if (str.length() > totallen) {
            str = str.substring(0, totallen);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < totallen - str.length(); i++) {
            sb.append(c);
        }
        sb.append(str);
        return sb.toString();
    }

    public static String padleft(Integer str, char c, int totallen) {
        return padleft("" + str, c, totallen);
    }
}
