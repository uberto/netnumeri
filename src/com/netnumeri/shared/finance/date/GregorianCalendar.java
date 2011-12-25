package com.netnumeri.shared.finance.date;


import java.util.Date;


/**
 * <dl>
 * <dt><b>Title: </b><dd>GregorianCalendar</dd>
 * <p/>
 * <dt><b>Description: </b><dd>java.util.GregorianCalendar replacment</dd>
 * </dl>
 *
 * @author <a href="mailto:andre.freller@gmail.com">Andre Freller</a>
 * @version $Revision: 1.1 $
 */
///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////
public class GregorianCalendar extends Calendar {
    public static final int BC = 0;
    public static final int AD = 1;

    public GregorianCalendar() {
        super();
    }

    public GregorianCalendar(Date date) {
        setTime(date);
    }

    public GregorianCalendar(int year, int month, int date) {
        set(year, month, date);
    }

    public GregorianCalendar(int year, int month, int date, int hour) {
        set(year, month, date, hour);
    }

    public GregorianCalendar(int year, int month, int date, int hour, int minute) {
        set(year, month, date, hour, minute);
    }

    public GregorianCalendar(int year, int month, int date, int hour, int minute, int second) {
        set(year, month, date, hour, minute, second);
    }

    protected static int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static int getMaxDaysInMonth(int year, int month) {
        if (month == FEBRUARY && isLeapYear(year))
            return daysInMonth[month] + 1;
        else
            return daysInMonth[month];
    }

    public int getMaxDaysInMonth() {
        return getMaxDaysInMonth(this.year, this.month);
    }

    public static int getNumOfWeeksInMonth(int year, int month, int firstDayOfWeek) {
        int day1 = getWeekDay(year, month, 1);
        int maxDays = getMaxDaysInMonth(year, month);
        if (month == FEBRUARY && !isLeapYear(year) && day1 == firstDayOfWeek)
            return 4;
        if (maxDays == 30 && (day1 > firstDayOfWeek + 5 || day1 < firstDayOfWeek))
            return 6;
        if (maxDays == 31 && (day1 > firstDayOfWeek + 4 || day1 < firstDayOfWeek))
            return 6;
        return 5;
    }

    public int getNumOfWeeksInMonth() {
        return getNumOfWeeksInMonth(this.year, this.month, this.firstWeekDayOfWeek);
    }

    public static int getWeekDay(int year, int month, int dayOfMonth) {
        int weekDay = year - 1900;
        weekDay += weekDay / 4;
        weekDay %= 7;
        if (month <= FEBRUARY && isLeapYear(weekDay))
            weekDay -= 1;
        weekDay += dayOfMonth;
        weekDay %= 7;
        if (month == MAY)
            weekDay += 1;
        else if (month == AUGUST)
            weekDay += 2;
        else if (month == FEBRUARY || month == MARCH || month == NOVEMBER)
            weekDay += 3;
        else if (month == JUNE)
            weekDay += 4;
        else if (month == SEPTEMBER || month == DECEMBER)
            weekDay += 5;
        else if (month == APRIL || month == JULY)
            weekDay += 6;
        weekDay %= 7;

        return SUNDAY + weekDay;
    }

    public int getWeekDay() {
        return getWeekDay(this.year, this.month, this.dayOfMonth);
    }

    public int getFirstWeekDayOfMonth() {
        return getWeekDay(this.year, this.month, 1);
    }

    // Gets the minimum value for the given time field.

    public int getMinimum(int fieldCode) {
        switch (fieldCode) {
            case MILLISECOND:
                return 0;
            case SECOND:
                return 0;
            case MINUTE:
                return 0;
            case HOUR:
                return 0;
            case HOUR_OF_DAY:
                return 0;
            case DATE:
                return 1;
            case MONTH:
                return JANUARY;
            default:
                return -1;
        }
    }
    // Return the minimum value that this field could have, given the current date.

    public int getActualMinimum(int fieldCode) {
        return getMinimum(fieldCode);
    }
    // Gets the highest minimum value for the given field if varies.

    public int getGreatestMinimum(int fieldCode) {
        return getMinimum(fieldCode);
    }

    // Gets the maximum value for the given time field.

    public int getMaximum(int fieldCode) {
        switch (fieldCode) {
            case MILLISECOND:
                return 999;
            case SECOND:
                return 59;
            case MINUTE:
                return 59;
            case HOUR:
                return 11;
            case HOUR_OF_DAY:
                return 23;
            case DATE:
                return 31;
            case MONTH:
                return DECEMBER;
            default:
                return -1;
        }
    }
    // Return the maximum value that this field could have, given the current date.

    public int getActualMaximum(int fieldCode) {
        switch (fieldCode) {
            case MILLISECOND:
                return 999;
            case SECOND:
                return 59;
            case MINUTE:
                return 59;
            case HOUR:
                return 11;
            case HOUR_OF_DAY:
                return 23;
            case DATE:
                return getMaxDaysInMonth();
            case MONTH:
                return DECEMBER;
            default:
                return -1;
        }
    }
    // Gets the lowest maximum value for the given field if varies.

    public int getLeastMaximum(int fieldCode) {
        switch (fieldCode) {
            case MILLISECOND:
                return 999;
            case SECOND:
                return 59;
            case MINUTE:
                return 59;
            case HOUR:
                return 11;
            case HOUR_OF_DAY:
                return 23;
            case DATE:
                return 28;
            case MONTH:
                return DECEMBER;
            default:
                return -1;
        }
    }


    public void set(int fieldCode, int value) {
        switch (fieldCode) {
            case SECOND:
                this.second = value;
                break;
            case MINUTE:
                this.minute = value;
                break;
            case HOUR:
                this.hour = value;
                break;
            case HOUR_OF_DAY:
                if (value <= 12) {
                    this.hour = value;
                    this.amPm = AM;
                } else {
                    this.hour = value - 12;
                    this.amPm = PM;
                }
                break;
            case DATE:
                this.dayOfMonth = value;
                break;
            case MONTH:
                this.month = value;
                if (this.dayOfMonth > getMaxDaysInMonth())
                    this.dayOfMonth = getMaxDaysInMonth();
                break;
            case YEAR:
                this.year = value;
                break;
            case AM_PM:
                this.amPm = value;
                break;
        }
        computeTime();
    }


    // Date Arithmetic function.

    public void add(int fieldCode, int amount) {
        switch (fieldCode) {
            case DATE:
                amount *= 24;
            case HOUR:
                amount *= 60;
            case MINUTE:
                amount *= 60;
            case SECOND:
                amount *= 1000;
            case MILLISECOND:
                this.date.setTime(this.date.getTime() + amount);
                computeFields();
                break;
            case MONTH:
                this.year += (this.month + amount) / 12;
                this.month = (this.month + amount) % 12;
                if (this.month < 0) {
                    this.year -= 1;
                    this.month += 12;
                }
                if (this.dayOfMonth > getMaxDaysInMonth())
                    this.dayOfMonth = getMaxDaysInMonth();
                computeTime();
                break;
            case YEAR:
                this.year += amount;
                computeTime();
                break;
        }
    }

    // Time Field Rolling function.

    public void roll(int fieldCode, int amount) {
        switch (fieldCode) {
            case SECOND:
                this.second = (this.second + amount) % 60;
                if (this.second < 0)
                    this.second += 60;
                break;
            case MINUTE:
                this.minute = (this.minute + amount) % 60;
                if (this.minute < 0)
                    this.minute += 60;
                break;
            case HOUR:
                this.hour = (this.hour + amount) % 24;
                if (this.hour < 0)
                    this.hour += 24;
                break;
            case DATE: {
                this.dayOfMonth = (this.dayOfMonth + amount) % getActualMaximum(DATE);
                if (this.dayOfMonth < 0)
                    this.dayOfMonth += getActualMaximum(DATE);
                break;
            }
            case MONTH: {
                this.month = (this.month + amount) % 12;
                if (this.month < 0)
                    this.month += 12;
                if (this.dayOfMonth > getActualMaximum(DATE))
                    this.dayOfMonth = getActualMaximum(DATE);
                break;
            }
            case YEAR:
                this.year += amount;
                break;
        }
        computeTime();
    }


    // Compares the time field records.

    public boolean before(GregorianCalendar when) {
        return this.date.before(when.getTime());
    }

    public boolean after(GregorianCalendar when) {
        return this.date.after(when.getTime());
    }

    public boolean equals(GregorianCalendar obj) {
        return this.date.equals(obj.getTime());
    }

    public boolean isLeapYear() {
        return isLeapYear(this.year);
    }

    public static boolean isLeapYear(int year) {
        if ((year % 4) == 0 && ((year % 100) != 0 || (year % 400) == 0))
            return true;
        return false;
    }


    // Converts the current millisecond time value time to field values in fields[].

    protected void computeFields() {
        this.second = this.date.getSeconds();
        this.minute = this.date.getMinutes();
        this.hour = this.date.getHours();
        if (this.hour < 12) {
            this.amPm = AM;
        } else {
            this.hour -= 12;
            this.amPm = PM;
        }
        this.dayOfMonth = this.date.getDate();
        this.month = this.date.getMonth();
        this.year = this.date.getYear() + 1900;

    }
    // Converts the current field values in fields[] to the millisecond time value time.

    protected void computeTime() {
        this.date = new Date(this.year - 1900, this.month, this.dayOfMonth, this.hour, this.minute, this.second);
    }

    // Overrides Cloneable

    public Object clone() {
        GregorianCalendar newCal = new GregorianCalendar();
        newCal.setTime(this.date);
        return newCal;
    }

} // end of class GregorianCalendar
