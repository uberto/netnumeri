package com.netnumeri.shared.finance.data;


import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.utils.DateUtils;

public abstract class DateBound  {

    private TDay lowerBoundTDay;
    private TDay upperBoundTDay;

    private TDay lowerRangeTDay;
    private TDay upperRangeTDay;

    private boolean rangeEnabled = false;

    public DateBound() {
        disableRange();
    }

    public void enableRange() {
        rangeEnabled = true;
    }

    public final void disableRange() {
        rangeEnabled = false;
    }

    public void setRangeBounds(TDay lowerDate, TDay upperDate) {
        enableRange();
        lowerRangeTDay = lowerDate;
        upperRangeTDay = upperDate;
    }

    public void setWindow(TDay lowerDate, TDay upperDate) {
        lowerBoundTDay = lowerDate;
        upperBoundTDay = upperDate;
    }

    public TDay getLowerBoundDate() {
        return lowerBoundTDay;
    }

    public void setLowerBoundDate(TDay lowerBoundDate) {
        this.lowerBoundTDay = lowerBoundDate;
    }

    public TDay getUpperBoundDate() {
        return upperBoundTDay;
    }

    public void setUpperBoundDate(TDay upperBoundTDay) {
        this.upperBoundTDay = upperBoundTDay;
    }

    public TDay getLowerRangeDate() {
        return lowerRangeTDay;
    }

    public void setLowerRangeDate(TDay lowerRangeDate) {
        this.lowerRangeTDay = lowerRangeDate;
    }

    public TDay getUpperRangeDate() {
        return upperRangeTDay;
    }

    public void setUpperRangeDate(TDay upperRangeTDay) {
        this.upperRangeTDay = upperRangeTDay;
    }


    public boolean isRangeEnabled() {
        return rangeEnabled;
    }

    public boolean inBounds(TDay date) {

        boolean b1 = date.isGreaterEqual(lowerBoundTDay);
        boolean b2 = date.isLessEqual(upperBoundTDay);

        boolean b3 =  b1 && b2;

        if (!b3)  {
            System.out.println("date            = " + date);
            System.out.println("lowerBoundTDay  = " + lowerBoundTDay);
            System.out.println("upperBoundTDay  = " + upperBoundTDay);

            System.out.println("b1 = " + b1);
            System.out.println("b2 = " + b2);
            System.out.println("b3 = " + b3);
        }
            return b3;
    }

    public TDay getFirstDate() {
        if (lowerBoundTDay == null) return null;
        if (isRangeEnabled()) {
            return DateUtils.max(getLowerRangeDate(), lowerBoundTDay);
        } else {
            return lowerBoundTDay;
        }
    }

    public TDay getLastDate() {
        if (upperBoundTDay == null) return null;
        if (isRangeEnabled()) {
            TDay min = DateUtils.min(getUpperRangeDate(), upperBoundTDay);
            System.out.println("min = " + min);
            return min;
        } else {
            System.out.println("upperBoundTDay = " + upperBoundTDay);
            return upperBoundTDay;
        }
    }
}
