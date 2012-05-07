package com.netnumeri.shared.finance.utils;

import com.netnumeri.shared.finance.date.TDay;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    private static TDay day;

    public static void setToday(TDay tempToday) {
        day = tempToday;
    }
    public static TDay toYahoo(String date) {

        try {
            SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = yyyyMMddHHmmss.parse(date);
            SimpleDateFormat yyyyMMddHHmmss2 = new SimpleDateFormat("MM/dd/yyyy");
            return new TDay(yyyyMMddHHmmss2.format(parse));
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

     public static long diffDays(TDay lowerDate, TDay upDate) {
        long milliseconds1 = lowerDate.getTime();
        long milliseconds2 = upDate.getTime();
        long diff = milliseconds2 - milliseconds1;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;
    }

     public static TDay max(TDay date1, TDay date2) {
        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }
        if (date1.isGreater(date2)) {
            return date1;
        } else {
            return date2;
        }
    }

    public static TDay min(TDay date1, TDay date2) {
        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }
        if (date1.isLess(date2)) {
            return date1;
        } else {
            return date2;
        }
    }

    public static int getDayOfYear(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_YEAR);
    }

    public static int getYear(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getWeekOfYear(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    public static Date fromYYYYMMDD(String value) {
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date fromYYYYMMDDhhmmss(String value) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
        } catch (Exception e) {
            return null;
        }
    }
    //  2011-03-25T17:42:12
    public static String toYYYYMMDD(Date date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date firstQuarterStartDate(Integer year) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.AM_PM, Calendar.AM);
        return cal.getTime();
    }

    public static Date firstQuarterEndDate(Integer year) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.MONTH, 2);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR, 11);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.AM_PM, Calendar.PM);
        return cal.getTime();
    }

    public static Date secondQuarterStartDate(Integer year) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.MONTH, 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.AM_PM, Calendar.AM);
        return cal.getTime();
    }

    public static Date secondQuarterEndDate(Integer year) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.MONTH, 5);
        cal.set(Calendar.DAY_OF_MONTH, 30);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR, 11);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.AM_PM, Calendar.PM);
        return cal.getTime();
    }

    public static Date thirdQuarterStartDate(Integer year) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.MONTH, 6);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.AM_PM, Calendar.AM);
        return cal.getTime();
    }

    public static Date thirdQuarterEndDate(Integer year) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.MONTH, 8);
        cal.set(Calendar.DAY_OF_MONTH, 30);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR, 11);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.AM_PM, Calendar.PM);
        return cal.getTime();
    }

    public static Date fourthQuarterStartDate(Integer year) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.AM_PM, Calendar.AM);
        return cal.getTime();
    }

    public static Date fourthQuarterEndDate(Integer year) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR, 11);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.AM_PM, Calendar.PM);
        return cal.getTime();
    }

    public static Integer getCurrentYear() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        return cal.get(Calendar.YEAR);
    }

    public static Integer getMonth(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static Date getStartOfDay(Date day) {
        if (day == null)
            return null;
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(day);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.AM_PM, Calendar.AM);
        return cal.getTime();
    }

    public static Date getEndOfDay(Date day) {
        if (day == null)
            return null;
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(day);
        cal.set(Calendar.HOUR, 11);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.AM_PM, Calendar.PM);
        return cal.getTime();
    }

    public static Date getFirstDayMonth(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.AM_PM, Calendar.AM);
        return cal.getTime();
    }

    public static Date getStartPreviousMonth(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.AM_PM, Calendar.AM);
        return cal.getTime();
    }

    public static void main(String[] args) {
        System.out.println("args = " + DateUtils.firstQuarterStartDate(2010));
        System.out.println("args = " + DateUtils.firstQuarterEndDate(2010));

        System.out.println("args = " + DateUtils.secondQuarterStartDate(2010));
        System.out.println("args = " + DateUtils.secondQuarterEndDate(2010));

        System.out.println("args = " + DateUtils.thirdQuarterStartDate(2010));
        System.out.println("args = " + DateUtils.thirdQuarterEndDate(2010));

        System.out.println("args = " + DateUtils.fourthQuarterStartDate(2010));
        System.out.println("args = " + DateUtils.fourthQuarterEndDate(2010));


        Date d = getStartPreviousMonth(new Date());

        d = getStartPreviousMonth(d);
        System.out.println("d = " + d);

        d = getStartPreviousMonth(d);
        System.out.println("d = " + d);

        d = getStartPreviousMonth(d);
        System.out.println("d = " + d);

        d = getStartPreviousMonth(d);
        System.out.println("d = " + d);

        d = getStartPreviousMonth(d);
        System.out.println("d = " + d);

        d = getStartPreviousMonth(d);
        System.out.println("d = " + d);

        d = getStartPreviousMonth(d);
        System.out.println("d = " + d);

        d = getStartPreviousMonth(d);
        System.out.println("d = " + d);

        d = getStartPreviousMonth(d);
        System.out.println("d = " + d);

        d = getStartPreviousMonth(d);
        System.out.println("d = " + d);

        d = getStartPreviousMonth(d);
        System.out.println("d = " + d);

        d = getStartPreviousMonth(d);
        System.out.println("d = " + d);


    }
}
