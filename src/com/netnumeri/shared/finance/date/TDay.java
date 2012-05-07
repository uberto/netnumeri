package com.netnumeri.shared.finance.date;

import java.io.Serializable;

public class TDay implements Serializable, Comparable {

    static final long MILLISECONDS_DAY = 0x5265c00L;

    static final int MonthOffset[] = {
            1, 32, 60, 91, 121, 152, 182, 213, 244, 274,
            305, 335
    };

    static final int MonthDays[] = {
            31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
            30, 31
    };

    static final String DayNames[] = {
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };

    static final String MonthNames[] = {
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
            "November", "December"
    };

    static final String DayNamesShort[] = {
            "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
    };

    static final String MonthNamesShort[] = {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
            "Nov", "Dec"
    };

    static final int MONTHS = 12;
    static final int MILLISECONDS = 1000;
    static final int SECONDS = 60;
    static final int MINUTES = 60;
    static final int HOURS = 24;

    static final long SECONDS_DAY = 60 * 60 * 24;
    final static int SECOND = 1;
    final static int MINUTE = 60;
    final static int HOUR = 60 * 60;
    final static int DAY = 60 * 60 * 24;
    final static int WEEK = 60 * 60 * 24 * 7;
    final static int MONTH = 60 * 60 * 24 * 7 * 4;
    final static int YEAR = 60 * 60 * 24 * 7 * 4 * 12;

    // different year lengths per different conventions
    int[] YearLengths = {365, 360, 360, 365, 360, 365, 360};

    static final int Frequences[] = {
            1, 7, 14, 1, 2, 3, 4, 6, 9, 12
    };

    private long time;
    private int year;
    private int month;
    private int date;
    private int weekday;
    private int parPos;
    private int parNum;

    public TDay() {
        this(System.currentTimeMillis());
    }

    public TDay(long date) {
        time = tolong(date);
        init();
    }

    public TDay(int year, int month, int date) {
        time = toLong(year, month, date);
        init();
    }

    public TDay(int year, int month, int week, int weekday) {
        if (week == 0 || weekday < 0 || weekday > 6) {
            throw new IllegalArgumentException();
        }
        int dir = 1;
        TDay cd = new TDay(year, month, 1);
        if (week < 0) {
            cd = cd.addMonths(1);
            cd = cd.addDays(-1);
            week *= -1;
            dir = -1;
        }
        for (; cd.getWeekday() != weekday; cd = cd.addDays(dir)) {
        }
        cd = cd.addDays((week - 1) * 7 * dir);
        time = tolong(cd.getTime());
        init();
    }

    public TDay(String s) {
        time = parse(s);
        init();
    }

//    public TDay(Date d) {
//        if (d == null) {
//            throw new IllegalArgumentException("d is null");
//        }
//        SimpleDateFormat sdfit = null;
//        try {
//            sdfit = new SimpleDateFormat("MM/dd/yyyy");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        time = parse(sdfit.format(d));
//        init();
//    }


    public void setDate(int theDate) {
        time = toLong(year, month, theDate);
        init();
    }

    public void fromString(String s) {
        time = parse(s);
        init();
    }

    public void setMonth(int theMonth) {
        time = toLong(year, theMonth, date);
        init();
    }

    public void setTime(long theTime) {
        time = tolong(theTime);
        init();
    }

    public void setYear(int theYear) {
        time = toLong(theYear, month, date);
        init();
    }

    public int getDate() {
        return date;
    }

    public int getWeekday() {
        return weekday;
    }

    public int weekday() {
        return weekday;
    }

    public String getDayName() {
        return DayNames[weekday];
    }

    public int getDayOfYear() {
        int res = MonthOffset[month] + date;
        if (month > 1 && isYearLeap()) {
            res--;
        }
        return res;
    }

    public int getDaysInMonth() {
        int res = MonthDays[month];
        if (month == 1 && isYearLeap()) {
            res++;
        }
        return res;
    }

    public int getMonth() {
        return month;
    }

    public int month() {
        return month;
    }

    public String getMonthName() {
        return MonthNames[month];
    }

    public long getTime() {
        return toJAVAlong(time);
    }

    public int getYear() {
        return year;
    }

    public int year() {
        return year;
    }

    public String toString() {
        return (month + 1) + "/" + date + "/" + year;
    }

    public Object clone() {
        return new TDay(getTime());
    }

    public TDay addDays(int days) {
        return new TDay(toJAVAlong(time + (long) days));
    }

    public TDay subtractDays(int days) {
        return new TDay(toJAVAlong(time - (long) days));
    }

    public TDay nextDay() {
        return new TDay(toJAVAlong(time + (long) 1));
    }

    public TDay prevDay() {
        return new TDay(toJAVAlong(time - (long) 1));
    }

    public boolean isEqual(TDay d) {
        return this.date == d.date && this.month == d.month && this.year == d.year;
    }

    public boolean isLess(TDay d) {

        if (this.year() > d.year()) return false;
        if (this.year() < d.year()) return true;

        if (this.month() > d.month())  return false;
        if (this.month() < d.month())  return true;

        if (this.getDate() > d.getDate())  return false;
        if (this.getDate() < d.getDate())  return true;

        return false;
    }

    public boolean isGreater(TDay d) {
        if (this.year() < d.year()) return false;
        if (this.year() > d.year()) return true;

        if (this.month() < d.month())  return false;
        if (this.month() > d.month())  return true;

        if (this.getDate() < d.getDate())  return false;
        if (this.getDate() > d.getDate())  return true;
        return false;
    }

    public boolean isLessEqual(TDay d) {
        if (this.year() > d.year()) return false;
        if (this.year() < d.year()) return true;

        if (this.month() > d.month())  return false;
        if (this.month() < d.month())  return true;

        if (this.getDate() > d.getDate())  return false;
        if (this.getDate() < d.getDate())  return true;

        return true;
    }

    public boolean isGreaterEqual(TDay d) {
        if (this.year() < d.year()) return false;
        if (this.year() > d.year()) return true;

        if (this.month() < d.month())  return false;
        if (this.month() > d.month())  return true;

        if (this.getDate() < d.getDate())  return false;
        if (this.getDate() > d.getDate())  return true;
        return true;
    }

    public TDay addFreq(int freq) {
        int aFreqValue = getFreqValue(freq);
        TDay res;
        if (isFreqInDays(freq)) {
            res = addDays(aFreqValue);
        } else {
            res = addMonths(aFreqValue);
        }
        return res;
    }

    public TDay nextFreq(int freq) {
        int aFreqValue = getFreqValue(freq);
        TDay res;
        if (isFreqInDays(freq)) {
            res = addDays(aFreqValue);
        } else {
            res = addMonths(aFreqValue);
        }
        return res;
    }

    public TDay addFreq(int freq, int num) {
        int aFreqValue = getFreqValue(freq) * num;
        TDay res;
        if (isFreqInDays(freq)) {
            res = addDays(aFreqValue);
        } else {
            res = addMonths(aFreqValue);
        }
        return res;
    }

    public TDay addMonths(int months) {
        int y = year;
        int m = month;
        int d = date;
        m += months;
        y += m / 12;
        m %= 12;
        if (m < 0) {
            y--;
            m += 12;
        }
        TDay t = new TDay(y, m, 1);
        int md = t.getDaysInMonth();
        if (d > md) {
            d = md;
        }
        return new TDay(y, m, d);
    }

    public TDay addWeeks(int weeks, int weekday) {
        if (weekday < 0 || weekday > 6) {
            throw new IllegalArgumentException();
        }
        int dir = 1;
        if (weeks < 0) {
            weeks *= -1;
            dir = -1;
        }
        TDay cd = new TDay(getTime());
        if (weeks == 0 && cd.getWeekday() > weekday) {
            dir = -1;
        }
        for (; cd.getWeekday() != weekday; cd = cd.addDays(dir)) {
            ;
        }
        cd = cd.addDays(weeks * 7 * dir);
        return cd;
    }

    public TDay addYears(int years) {
        return new TDay(year + years, month, date);
    }

    public int diffMonths(TDay initialDate) {
        long res = getTime() - initialDate.getTime();
        res /= MILLISECONDS_DAY * MONTHS;
        return (int) res;
    }

    public int diffDays(TDay initialDate) {
        long res = getTime() - initialDate.getTime();
        res /= MILLISECONDS_DAY;
        return (int) res;
    }

    public int diffSeconds(TDay d) {
        long res = getTime() - d.getTime();
        res /= SECONDS_DAY;
        return (int) res;
    }

    public boolean equals(TDay d) {
        boolean res = year == d.getYear() && month == d.getMonth() && date == d.getDate();
        return res;
    }

    public boolean equalDates(TDay d) {
        boolean res = month == d.getMonth() && date == d.getDate();
        return res;
    }

    public boolean before(TDay d) {
        return getTime() < d.getTime();
    }

    public boolean beforeEqual(TDay d) {
        return getTime() <= d.getTime();
    }

    public boolean after(TDay d) {
        return getTime() > d.getTime();
    }

    public boolean afterEqual(TDay d) {
        return getTime() >= d.getTime();
    }

    public TDay intlMoneyMarket() {
        TDay d = new TDay(getYear(), 2, 2, 4);
        if (before(d)) {
            return d;
        }
        d = new TDay(getYear(), 5, 2, 4);
        if (before(d)) {
            return d;
        }
        d = new TDay(getYear(), 8, 2, 4);
        if (before(d)) {
            return d;
        }
        d = new TDay(getYear(), 11, 2, 4);
        if (before(d)) {
            return d;
        } else {
            return new TDay(getYear() + 1, 2, 2, 4);
        }
    }

    public static int getFreqValue(int freq) {
        if (freq < 0 && freq >= Frequences.length) {
            throw new IllegalArgumentException();
        } else {
            return Frequences[freq];
        }
    }

    public static boolean isDateCorrect(int theYear, int theMonth, int theDate) {
        if (theYear < 1970 || theMonth < 0 || theMonth > 11 || theDate < 1) {
            return false;
        }
        if (theMonth == 1 && theDate == 29 && isYearLeap(theYear)) {
            return true;
        } else {
            return theDate <= MonthDays[theMonth];
        }
    }

    public static boolean isFreqInDays(int freq) {
        boolean res = freq == 0 || freq == 1 || freq == 2;
        return res;
    }

    public boolean isWeekend() {
        boolean res = weekday == 0 || weekday == 6;
        return res;
    }

    public boolean isYearLeap() {
        boolean res = (year & 3) == 0 && year % 100 != 0 || year % 400 == 0;
        return res;
    }

    public static boolean isYearLeap(int theYear) {
        boolean res = (theYear & 3) == 0 && theYear % 100 != 0 || theYear % 400 == 0;
        return res;
    }

    public int subtractFreq(TDay initialDate, int freq) {
        int i = 0;
        if (isFreqInDays(freq)) {
            i = this.diffDays(initialDate);
        } else {
            i = this.diffMonths(initialDate);
        }
        return i;
    }

    public TDay subFreq(int freq) {
        int aFreqValue = -getFreqValue(freq);
        TDay res;
        if (isFreqInDays(freq)) {
            res = addDays(aFreqValue);
        } else {
            res = addMonths(aFreqValue);
        }
        return res;
    }

    public TDay subFreq(int freq, int num) {
        int aFreqValue = -getFreqValue(freq) * num;
        TDay res;
        if (isFreqInDays(freq)) {
            res = addDays(aFreqValue);
        } else {
            res = addMonths(aFreqValue);
        }
        return res;
    }

    private void init() {
        long ta = time + 0x25e584L + 25569L;
        long tb = (4L * ta) / 0x23ab1L;
        ta -= (0x23ab1L * tb + 3L) / 4L;
        year = (int) (((long) 4000 * (ta + 1L)) / 0x164b09L);
        ta = (ta - (1461L * (long) year) / 4L) + 31L;
        month = (int) (((long) 80 * ta) / 2447L);
        date = (int) (ta - (2447L * (long) month) / 80L);
        ta = (long) month / 11L;
        month = (month + 2) - 12 * (int) ta - 1;
        year = (int) ((long) 100 * (tb - 49L)) + year + (int) ta;
        weekday = (int) ((time + 4L) % 7L);
    }

    private long tolong(long theTime) {
        if (theTime < (long) 0) {
            throw new IllegalArgumentException("passing a negative date:" + theTime);
        } else {
            return theTime / MILLISECONDS_DAY;
        }
    }

    private long toLong(int theYear, int theMonth, int theDate) {
        if (!isDateCorrect(theYear, theMonth, theDate)) {
            throw new IllegalArgumentException("Date syntax incorrect. Expected MM/dd/yyyy");
        } else {
            long ta = (++theMonth - 14) / 12;
            ta = ((((1461L * ((long) (theYear - 1600) + ta)) / 4L + (367L * ((long) (theMonth - 2) - ta * 12L)) / 12L) - (3L * (((long) (theYear - 1500) + ta) / 100L)) / 4L) + (long) theDate) - 0x1abe6L;
            ta -= 25569L;
            return ta;
        }
    }

    public long toJAVAlong(long theTime) {
        return theTime * MILLISECONDS_DAY;
    }

    private long parse(String s) {
        label0:
        {
            parPos = 0;
            if (s == null) {
                break label0;
            }
            int sl = s.length();
            int num = readLex(s, sl);
            if (num == 0) {
                num = readLex(s, sl);
            }
            int m;
            if (num == 1) {
                m = parNum;
            } else {
                if (num != 2) {
                    break label0;
                }
                m = parNum - 1;
            }
            num = readNum(s, sl);
            if (num != -1) {
                int d = num;
                num = readNum(s, sl);
                if (num != -1) {
                    if (num < 1900 && num > 69) {
                        num += 1900;
                    }
                    if (num < 70) {
                        num += 2000;
                    }
                    int y = num;
                    return toLong(y, m, d);
                }
            }
        }
        throw new IllegalArgumentException();
    }

    private int readLex(String s, int sl) {
        int res = -1;
        int pos = parPos;
        parNum = readWeekDay(s, sl);
        if (parNum >= 0) {
            return 0;
        }
        parPos = pos;
        parNum = readMonth(s, sl);
        if (parNum >= 0) {
            return 1;
        }
        parPos = pos;
        parNum = readNum(s, sl);
        if (parNum >= 0) {
            return 2;
        } else {
            return res;
        }
    }

    private int readSep(String s, int sl) {
        if (parPos >= sl) {
            return 0;
        }
        for (char c = s.charAt(parPos); c == ' ' || c == ',' || c == '\\' || c == '/' || c == '-' || c == '.'; c = s.charAt(++parPos)) {
            if (parPos >= sl) {
                return 0;
            }
        }
        return 0;
    }

    private int readNum(String s, int sl) {
        readSep(s, sl);
        int n = 0;
        char c;
        for (; parPos < sl && '0' <= (c = s.charAt(parPos)) && c <= '9'; parPos++) {
            n = (n * 10 + c) - 48;
        }
        return n;
    }

    private int readWeekDay(String s, int sl) {
        int res = -1;
        readSep(s, sl);
        int i = 0;
        do {
            if (i >= 7) {
                break;
            }
            res = s.indexOf(DayNamesShort[i], parPos);
            if (res == parPos) {
                res = i;
                parPos += DayNamesShort[i].length();
                break;
            }
            i++;
        }
        while (true);
        return res;
    }

    private int readMonth(String s, int sl) {
        int res = -1;
        readSep(s, sl);
        int i = 0;
        do {
            if (i >= 12) {
                break;
            }
            res = s.indexOf(MonthNamesShort[i], parPos);
            if (res == parPos) {
                res = i;
                parPos += MonthNamesShort[i].length();
                break;
            }
            i++;
        }
        while (true);
        return res;
    }

//    public Date toDate() {
//        int year = this.year;
//        int month = this.month;
//        int weekday = this.date;
//        Calendar cal = Calendar.getInstance ();
//        cal.set(year, month, weekday);
//        cal.set(Calendar.HOUR_OF_DAY, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MILLISECOND, 0);
//        java.util.Date dat = cal.getTime();
//        return dat;
//    }

    public static void main(String[] args) {

        TDay d = new TDay("3/27/2007");
        System.out.println("---------------- d = " + d);
        TDay ddd = d.addDays(3);
        System.out.println("---------------- ddd = " + ddd);
    }

    @Override
    public int compareTo(Object o) {
        if (this.equals((TDay) o)) return 0;
        if (this.after((TDay) o)) return 1;
        return -1;
    }
}
