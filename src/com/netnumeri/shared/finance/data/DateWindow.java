package com.netnumeri.shared.finance.data;


import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.utils.DateUtils;
import com.netnumeri.shared.pojoc.SearchablePojo;

import java.io.Serializable;


public abstract class DateWindow extends SearchablePojo implements Serializable {

    private TDay lowerBoundTDay;
    private TDay upperBoundDate;

    private TDay lowerRangeTDay;
    private TDay upperRangeDate;

    private boolean rangeEnabled = false;

    public DateWindow() {
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
        upperRangeDate = upperDate;
    }


    public void setWindow(TDay lowerDate, TDay upperDate) {
        lowerBoundTDay = lowerDate;
        upperBoundDate = upperDate;
    }

    public TDay getLowerBoundDate() {
        return lowerBoundTDay;
    }

    public void setLowerBoundDate(TDay lowerBoundDate) {
        this.lowerBoundTDay = lowerBoundDate;
    }

    public TDay getUpperBoundDate() {
        return upperBoundDate;
    }

    public void setUpperBoundDate(TDay upperBoundDate) {
        this.upperBoundDate = upperBoundDate;
    }

    public TDay getLowerRangeDate() {
        return lowerRangeTDay;
    }

    public void setLowerRangeDate(TDay lowerRangeDate) {
        this.lowerRangeTDay = lowerRangeDate;
    }

    public TDay getUpperRangeDate() {
        return upperRangeDate;
    }

    public void setUpperRangeDate(TDay upperRangeDate) {
        this.upperRangeDate = upperRangeDate;
    }


    public boolean isRangeEnabled() {
        return rangeEnabled;
    }

    public void setRangeEnabled(boolean rangeEnabled) {
        this.rangeEnabled = rangeEnabled;
    }

    public void setNDays(int NDays) {
        enableRange();
    }

    public TDay getFirstDateTime() {
        if (isRangeEnabled())
            return DateUtils.max(lowerRangeTDay, lowerBoundTDay);
        else
            return lowerBoundTDay;
    }


    public TDay getLastDateTime() {
        if (isRangeEnabled())
            return DateUtils.min(upperRangeDate, upperBoundDate);
        else
            return upperBoundDate;
    }

    public boolean inBounds(TDay date) {
        return date.isGreaterEqual(lowerBoundTDay) && date.isLessEqual(upperBoundDate);
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
        if (upperBoundDate == null) return null;
        if (isRangeEnabled()) {
            return DateUtils.min(getUpperRangeDate(), upperBoundDate);
        } else {
            return this.upperBoundDate;
        }
    }
}
