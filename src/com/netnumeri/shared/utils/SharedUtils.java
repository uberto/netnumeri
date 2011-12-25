package com.netnumeri.shared.utils;

import com.google.gwt.i18n.client.CurrencyData;
import com.google.gwt.i18n.client.CurrencyList;
import com.google.gwt.i18n.client.NumberFormat;
import com.netnumeri.shared.enums.CurrencyEnum;

import java.util.Date;

public class SharedUtils {

    public static String[] splitUri(String uri) {
        int ei = uri.indexOf('#');
        String server;
        if (ei > 1) {
            server = uri.substring(0, ei);
        } else {
            server = uri;
        }
        String[] p = server.split("/");
        return p;
    }

    public static String initialUppercase(String s) {
		if (s == null || s.length() == 0) {
			return "";
		} else if (s.length() == 1) {
			return s.toUpperCase();
		} else {
			return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
		}
    }

	public static String initialUppercaseAndCamel(String str) {
		String newStr = "";
		if(str != null && str.indexOf("_") > -1) {
			String[] ls = str.split("_");
			for (String s : ls) {
				newStr += initialUppercase(s);
			}
		} else {
			newStr = initialUppercase(str);
		}
		return newStr;
	}

    @SuppressWarnings("deprecation")
	public static Date strToDate(String value) {
        if (value == null) return null;
        try {
            return new Date(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static Double strToDouble(String value) {
        if (value == null) return null;
        try {
            return new Double(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer strToInteger(String value) {
        if (value == null) return null;
        try {
            return new Integer(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static Long strToLong(String value) {
        if (value == null) return null;
        try {
            return new Long(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static Boolean strToBoolean(String value) {
        if (value == null) return null;
        try {
            return new Boolean(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static String doubleToStr(double d, int precision) {
        NumberFormat formatter;
        String pattern = "#,##0";
        if (precision > 0) {
            pattern += '.';
            for (int i = 0; i < precision; i++) {
                pattern += '0';
            }
        }
        formatter = NumberFormat.getFormat(pattern);
        return formatter.format(d);
    }

    public static String currencyToStr(double d, int precision, CurrencyEnum currency) {
    	String ret = doubleToStr(d, precision);
        if (currency != null) {
            CurrencyData currencyData = CurrencyList.get().lookup(currency.name());
            if (currencyData != null) {
                ret += currencyData.getPortableCurrencySymbol();
            }
        }
        return ret;
    }

    public static String format(int date){
        if ((""+date).length() == 1) return "0"+date;
        else return ""+date;
    }

    @SuppressWarnings({"deprecation"})   //they are not deprecated on gwt Date
	public static String dateTimeToStr(Date d) {
		return format(d.getDate()) + "-" + format(d.getMonth() + 1) + "-" + format(1900 + d.getYear()) + " " +
				format(d.getHours()) + ":" + format(d.getMinutes()) + ":" + format(d.getSeconds());
//        + "." + d.getTime() % 1000;
	}



}
