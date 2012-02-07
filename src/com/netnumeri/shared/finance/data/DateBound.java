package com.netnumeri.shared.finance.data;


import com.netnumeri.shared.entity.Entity;
import com.netnumeri.shared.field.FieldAttributes;
import com.netnumeri.shared.field.StringEntityField;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.utils.DateUtils;
import com.netnumeri.shared.field.*;

public abstract class DateBound  {

    StringEntityField name = new StringEntityField(Field.name, new FieldAttributes("", 22));

    private TDay lowerBoundTDay;
    private TDay upperBoundTDay;

    private TDay lowerRangeTDay;
    private TDay upperRangeTDay;

    private boolean rangeEnabled = false;

    public DateBound() {
        disableRange();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TDay getLowerBoundTDay() {
        return lowerBoundTDay;
    }

    public void setLowerBoundTDay(TDay lowerBoundTDay) {
        this.lowerBoundTDay = lowerBoundTDay;
    }

    public TDay getLowerRangeTDay() {
        return lowerRangeTDay;
    }

    public void setLowerRangeTDay(TDay lowerRangeTDay) {
        this.lowerRangeTDay = lowerRangeTDay;
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

    public TDay getUpperBoundTDay() {
        return upperBoundTDay;
    }

    public void setUpperBoundTDay(TDay upperBoundTDay) {
        this.upperBoundTDay = upperBoundTDay;
    }

    public TDay getLowerRangeDate() {
        return lowerRangeTDay;
    }

    public void setLowerRangeDate(TDay lowerRangeDate) {
        this.lowerRangeTDay = lowerRangeDate;
    }

    public TDay getUpperRangeTDay() {
        return upperRangeTDay;
    }

    public void setUpperRangeTDay(TDay upperRangeTDay) {
        this.upperRangeTDay = upperRangeTDay;
    }


    public boolean isRangeEnabled() {
        return rangeEnabled;
    }

    public boolean inBounds(TDay date) {
        return date.isGreaterEqual(lowerBoundTDay) && date.isLessEqual(upperBoundTDay);
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
            TDay min = DateUtils.min(getUpperRangeTDay(), upperBoundTDay);
            System.out.println("min = " + min);
            return min;
        } else {
            System.out.println("upperBoundTDay = " + upperBoundTDay);
            return upperBoundTDay;
        }
    }
}
