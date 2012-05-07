package com.netnumeri.shared.finance.beans;


import com.netnumeri.shared.finance.data.DateBound;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.matrix.Matrix;
import com.netnumeri.shared.finance.ta.DateValue;
import com.netnumeri.shared.finance.utils.DateUtils;
import com.netnumeri.shared.finance.utils.LogUtils;
import com.netnumeri.shared.finance.utils.NumericalRecipesUtils;

import java.io.Serializable;

public class TimeSeries extends DateBound implements Serializable {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int frequency = FinConstants.FR_DAILY;
    private int option = 0;
    private int size = 0;
    private int initialSize = 0;
    private int expansion = 0;
    private int dimension = 0;
    protected int[] lengthsArray = null;
    public Matrix data;

    private double[] minimum = null;
    private double[] maximum = null;
    private double[] mean = null;
    private double[] variance = null;
    private double[] asymmetry = null;
    private double[] excess = null;
    private int correlationPairs = 0;

    public TimeSeries() {
        super();
        init();
    }

    public TimeSeries(String name) {
        setName(name);
        init();
    }

    public void init() {
        option = -1;
        initialSize = 500;
        expansion = 500;
        size = 0;
        dimension = 0;
        lengthsArray = null;
        data = null;
        minimum = null;
        maximum = null;
        mean = null;
        variance = null;
        excess = null;
        asymmetry = null;
        correlationPairs = 0;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int Option) {
        option = Option;
    }

    public int getNRows() {
        return dimension;
    }

    public TDay getPrevDate(TDay date1) {
        return getPrevDate(getIndex(date1));
    }

    public TDay getNextDate(TDay date1) {
        return getNextDate(getIndex(date1));
    }

    public double getPrevData(TDay date1, int Row) {
        return getPrevData(getIndex(date1), Row);
    }

    public double getPrevData(TDay date1) {
        return getPrevData(getIndex(date1), 0);
    }

    public double getNextData(TDay date1) {
        return getNextData(date1, 0);
    }

    public double getNextData(TDay date, int Row) {
        return getNextData(getIndex(date), Row);
    }

    public int getIndex(String date, String pattern) {
        return getIndex(date, pattern);
    }

    public double getData(String date, String pattern, int Row) {
        return getData(date, pattern, Row);
    }

    public double getData(String date, String pattern) {
        return getData(date, pattern, 0);
    }

    double getStandardError(int row) {
        if (!isValidRow(row)) throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        return getStandardDeviation(row) / Math.sqrt(lengthsArray[row]);
    }

    public int getLowerBoundIndex() {
        return getIndex(getLowerBoundDate());
    }

    public int getUpperBoundIndex() {
        return getIndex(getUpperBoundDate());
    }

    public double getStdDev(int FirstIndex, int LastIndex, int Row) {
        return getStandardDeviation(FirstIndex, LastIndex, Row);
    }

    public double getStdDev(int FirstIndex, int LastIndex) {
        return getStandardDeviation(FirstIndex, LastIndex, 0);
    }

    public double getStdErr(int FirstIndex, int LastIndex, int Row) {
        return getStandardError(FirstIndex, LastIndex, Row);
    }

    public double getStdErr(int FirstIndex, int LastIndex) {
        return getStandardError(FirstIndex, LastIndex, 0);
    }

    public double getStdDev(TDay firstDate, TDay lastDate, int Row) {
        return getStandardDeviation(firstDate, lastDate, Row);
    }

    public double getStdDev(TDay firstDate, TDay lastDate) {
        return getStandardDeviation(firstDate, lastDate, 0);
    }

    public double getStdErr(TDay firstDate, TDay lastDate, int Row) {
        return getStandardError(firstDate, lastDate, Row);
    }

    public double getStdErr(TDay firstDate, TDay lastDate) {
        return getStandardError(firstDate, lastDate, 0);
    }

    public double getStdDev(int Row) {
        return getStandardDeviation(Row);
    }

    public double getStdDev() {
        return getStandardDeviation(0);
    }

    public double getStdErr(int Row) {
        return getStandardError(Row);
    }

    public double getStdErr() {
        return getStandardError(0);
    }

    // Verify if Row number is valid

    public boolean isValidRow(int row) {
        if (row < 0 || row >= dimension) return false;
        else return true;
    }

    public boolean isEmpty(int index) {
        return isEmpty(index, 0);
    }

    /**
     * Check if time series data present for index
     * If row == -1 return false if non-empty data entry exists for at
     * least one row associated with index
     */
    public boolean isEmpty(int index, int row) {
        if (index >= size) return true;
        if (row == -1) {
            for (int RowCount = 0; RowCount < dimension; RowCount++) {
                if (!Double.isNaN(data.get(RowCount, index))) return false;
            }
            return true;
        }
        if (isValidRow(row)) {
            if (Double.isNaN(data.get(row, index)))
                return true;
            else
                return false;
        }
        return false;
    }

    public boolean isEmpty(TDay date) {
        return isEmpty(date, 0);
    }

    // Check if time xyseries data present for datetime

    public boolean isEmpty(TDay date, int Row) {
        int Index = getIndex(date);
        if (Index == -1) return true;
        else return isEmpty(Index, Row);
    }

    public int getFirstIndex() {
        TDay firstDate = getFirstDate();
        if (firstDate == null) throw new IllegalArgumentException("firstDate cannot be null");
        return getIndex(firstDate);
    }

    public int getLastIndex() {
        return getIndex(getLastDate());
    }

    public int getNData() {
        return getNumberOfNotNullData(0);
    }

    // Return number of non-empty data entries for Row
    public int getNumberOfNotNullData(int Row) {
        if (isValidRow(Row))
            return lengthsArray[Row];
        else
            return 0;
    }

    // Return index of previous non empty entry
    public int getPrevIndex(int index) {
        if (index == -1) return -1;
        int FirstIndex = getFirstIndex();
        int PrevIndex = index - 1;
        while (PrevIndex >= FirstIndex) {
            if (!isEmpty(PrevIndex))
                return PrevIndex;
            else
                PrevIndex--;
        }
        return -1;
    }

    // Return date of previous non empty entry
    // Return zero date if no such entry
    public TDay getPrevDate(int Index) {
        return getDate(getPrevIndex(Index));
    }

    public double getPrevData(int index) {
        return getPrevData(index, 0);
    }

    // Return data of previous non empty entry
    public double getPrevData(int index, int row) {
        int PrevIndex = getPrevIndex(index);
        if (PrevIndex == -1) {
            LogUtils.debug(" GetPrevData. No previous data for index " + index);
            return 0.0;
        } else
            return data.get(row, PrevIndex);
    }

    // Return index of next non empty entry
    public int getNextIndex(int index) {
        if (index == -1) return -1;
        int LastIndex = getLastIndex();
        int NextIndex = index + 1;
        while (NextIndex <= LastIndex) {
            if (!isEmpty(NextIndex))
                return NextIndex;
            else
                NextIndex++;
        }
        return -1;
    }

    // Return date of next non empty entry
    // Return zero date if no such entry
    public TDay getNextDate(int index) {
        return getDate(getNextIndex(index));
    }

    public double getNextData(int index) {
        return getNextData(index, 0);
    }

    // Return data of next non empty entry
    public double getNextData(int index, int row) {
        int nextIndex = getNextIndex(index);
        if (nextIndex == -1) {
            LogUtils.debug(" GetNextData. No next data for index " + index);
            return 0;
        } else
            return data.get(row, nextIndex);
    }

    public int getIndex(TDay date) {
        if (!inBounds(date))
            throw new IllegalStateException("index not in bound");
        TDay dt = getLowerBoundDate();
        return (int) DateUtils.diffDays(dt, date);
    }

    final long MILLISECONDS_DAY = 0x5265c00L;

    public int subtractFreq(TDay finalDate, TDay initialDate, int freq) {
        return this.diffDays(finalDate, initialDate);
    }

    public int diffDays(TDay finalDate, TDay initialDate) {
        long res = finalDate.getTime() - initialDate.getTime();
        res /= MILLISECONDS_DAY;
        return (int) res;
    }

    public TDay getDate(int index) {
        if (index == -1) throw new IllegalStateException("index not in bound");
        TDay lb = getLowerBoundDate();
        return addFreq(lb, index);
    }

    public TDay addFreq(TDay lb, int index) {
        TDay res = lb.addDays(index);
        return res;
    }

    public double getData(TDay date) {
        return getData(date, 0);
    }

    public double getData(TDay date, int row) {
        if (date == null) return FinConstants.ts_empty;
        int index = getIndex(date);
        if (index == -1)
            return FinConstants.ts_empty;
        else
            return data.get(row, index);
    }

    public double getData(int index) {
        return getData(index, 0);
    }

    public double getData(int index, int row) {
        if (index < getLowerBoundIndex() || index > getUpperBoundIndex())
            throw new IllegalStateException("index out of bound");
        else
            return data.get(row, index);
    }

    public void set(int index, double data) {
        set(index, data, 0);
    }

    public void set(int index, double data, int row) {
        if (index < getLowerBoundIndex() || index > getUpperBoundIndex()) {
            LogUtils.debug("TS: Set. index out of bounds: " + index);
            return;
        }
        if (isEmpty(index, row)) {
            LogUtils.debug("TS: Set. Empty entry with index :" + index);
            return;
        }
        this.data.set(row, index, data);
    }

    public void set(TDay date, double Data) {
        set(date, Data, 0);
    }

    public void set(TDay date, double Data, int Row) {
        int Index = getIndex(date);
        if (Index != -1)
            set(Index, Data, Row);
        else
            LogUtils.debug("TS: Set. CalendarDate out of bounds");
    }

    public void set(TimeSeries Series) {
        set(Series, 0, 0);
    }

    public void set(TimeSeries serie, int FromRow, int ToRow) {
        int FirstIndex = serie.getFirstIndex();
        int LastIndex = serie.getLastIndex();
        for (int Index = FirstIndex; Index <= LastIndex; Index++) {
            if (!serie.isEmpty(Index, FromRow))
                add(serie.getDate(Index), serie.getData(Index, FromRow), ToRow);
        }
    }

    public void set(TimeSeries Series, TDay firstDate, TDay lastDate) {
        set(Series, firstDate, lastDate, 0, 0);
    }

    public void set(TimeSeries series, TDay firstDate, TDay lastDate, int fromRow, int toRow) {
        firstDate = DateUtils.max(firstDate, series.getFirstDate());
        lastDate = DateUtils.min(lastDate, series.getLastDate());
        int FirstIndex = series.getIndex(firstDate);
        int LastIndex = series.getIndex(lastDate);
        for (int Index = FirstIndex; Index <= LastIndex; Index++) {
            if (!series.isEmpty(Index, fromRow))
                add(series.getDate(Index), series.getData(Index, fromRow), toRow);
        }
    }

//    public void add(String date, String pattern, double Data, int Row) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//        add(sdf.parse(date), Data, Row);
//    }

//    public void add(String date, String pattern, double Data) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//        add(sdf.parse(date), Data, 0);
//    }

    public void add(TDay date, double data) {
        add(date, data, 0);
    }

    public void add(TDay date, double value, int row) {
        int i = 0;
        int dimensionIndex = 0;
        if (row >= dimension) {
            if (lengthsArray == null) {
                lengthsArray = new int[row + 1];
                for (dimensionIndex = 0; dimensionIndex <= row; dimensionIndex++)
                    lengthsArray[dimensionIndex] = 0;
            } else {
                int[] NewNData = new int[row + 1];
                for (dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++)
                    NewNData[dimensionIndex] = lengthsArray[dimensionIndex];
                for (dimensionIndex = dimension; dimensionIndex <= row; dimensionIndex++)
                    NewNData[dimensionIndex] = 0;
                lengthsArray = NewNData;
            }

            // minimum
            if (minimum == null) {
                minimum = new double[row + 1];
                for (dimensionIndex = 0; dimensionIndex <= row; dimensionIndex++) {
                    minimum[dimensionIndex] = 1e99;
                }
            } else {
                double[] NewMin = new double[row + 1];
                for (dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++)
                    NewMin[dimensionIndex] = minimum[dimensionIndex];
                for (dimensionIndex = dimension; dimensionIndex <= row; dimensionIndex++)
                    NewMin[dimensionIndex] = 1e99;
                minimum = NewMin;
            }

            // maximum
            if (maximum == null) {
                maximum = new double[row + 1];
                for (dimensionIndex = 0; dimensionIndex <= row; dimensionIndex++)
                    maximum[dimensionIndex] = -1e99;
            } else {
                double[] NewMax = new double[row + 1];
                for (dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++)
                    NewMax[dimensionIndex] = maximum[dimensionIndex];
                for (dimensionIndex = dimension; dimensionIndex <= row; dimensionIndex++)
                    NewMax[dimensionIndex] = -1e99;
                maximum = NewMax;
            }

            // mean
            if (mean == null) {
                mean = new double[row + 1];
                for (dimensionIndex = 0; dimensionIndex <= row; dimensionIndex++)
                    mean[dimensionIndex] = 0;
            } else {
                double[] NewMean = new double[row + 1];
                for (dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++)
                    NewMean[dimensionIndex] = mean[dimensionIndex];
                for (dimensionIndex = dimension; dimensionIndex <= row; dimensionIndex++)
                    NewMean[dimensionIndex] = 0;
                mean = NewMean;
            }

            // variance
            if (variance == null) {
                variance = new double[row + 1];
                for (dimensionIndex = 0; dimensionIndex <= row; dimensionIndex++)
                    variance[dimensionIndex] = 0;
            } else {
                double[] newVariance = new double[row + 1];
                for (dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++)
                    newVariance[dimensionIndex] = variance[dimensionIndex];
                for (dimensionIndex = dimension; dimensionIndex <= row; dimensionIndex++)
                    newVariance[dimensionIndex] = 0;
                variance = newVariance;
            }

            // asymmetry
            if (asymmetry == null) {
                asymmetry = new double[row + 1];
                for (dimensionIndex = 0; dimensionIndex <= row; dimensionIndex++)
                    asymmetry[dimensionIndex] = 0;
            } else {
                double[] newAsymmetry = new double[row + 1];
                for (dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++)
                    newAsymmetry[dimensionIndex] = asymmetry[dimensionIndex];
                for (dimensionIndex = dimension; dimensionIndex <= row; dimensionIndex++)
                    newAsymmetry[dimensionIndex] = 0;
                asymmetry = newAsymmetry;
            }

            // excess
            if (excess == null) {
                excess = new double[row + 1];
                for (dimensionIndex = 0; dimensionIndex <= row; dimensionIndex++)
                    excess[dimensionIndex] = 0;
            } else {
                double[] NewExcess = new double[row + 1];
                for (dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++)
                    NewExcess[dimensionIndex] = excess[dimensionIndex];
                for (dimensionIndex = dimension; dimensionIndex <= row; dimensionIndex++)
                    NewExcess[dimensionIndex] = 0;
                excess = NewExcess;
            }

            // isMinchanged
/*
            if (isMinchanged == null)
            {
                isMinchanged = new boolean[row + 1];
                for (dimensionIndex = 0; dimensionIndex <= row; dimensionIndex++)
                    isMinchanged[dimensionIndex] = true;
            }
            else
            {
                boolean[] newIsMinchanged = new boolean[row + 1];
                for (dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++)
                    newIsMinchanged[dimensionIndex] = isMinchanged[dimensionIndex];
                for (dimensionIndex = dimension; dimensionIndex <= row; dimensionIndex++)
                    newIsMinchanged[dimensionIndex] = true;
                isMinchanged = newIsMinchanged;
            }

            // isMaxchanged
            if (isMaxchanged == null)
            {
                isMaxchanged = new boolean[row + 1];
                for (dimensionIndex = 0; dimensionIndex <= row; dimensionIndex++)
                    isMaxchanged[dimensionIndex] = true;
            }
            else
            {
                boolean[] newIsMaxchanged = new boolean[row + 1];
                for (dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++)
                    newIsMaxchanged[dimensionIndex] = isMaxchanged[dimensionIndex];
                for (dimensionIndex = dimension; dimensionIndex <= row; dimensionIndex++)
                    newIsMaxchanged[dimensionIndex] = true;
                isMaxchanged = newIsMaxchanged;
            }

            // isMeanchanged
            if (isMeanchanged == null)
            {
                isMeanchanged = new boolean[row + 1];
                for (dimensionIndex = 0; dimensionIndex <= row; dimensionIndex++)
                    isMeanchanged[dimensionIndex] = true;
            }
            else
            {
                boolean[] newIsMeanchanged = new boolean[row + 1];
                for (dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++)
                    newIsMeanchanged[dimensionIndex] = isMeanchanged[dimensionIndex];
                for (dimensionIndex = dimension; dimensionIndex <= row; dimensionIndex++)
                    newIsMeanchanged[dimensionIndex] = true;
                isMeanchanged = newIsMeanchanged;
            }

            // isVariancechanged
            if (isVariancechanged == null)
            {
                isVariancechanged = new boolean[row + 1];
                for (dimensionIndex = 0; dimensionIndex <= row; dimensionIndex++)
                    isVariancechanged[dimensionIndex] = true;
            }
            else
            {
                boolean[] newIsVariancechanged = new boolean[row + 1];
                for (dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++)
                    newIsVariancechanged[dimensionIndex] = isVariancechanged[dimensionIndex];
                for (dimensionIndex = dimension; dimensionIndex <= row; dimensionIndex++)
                    newIsVariancechanged[dimensionIndex] = true;
                isVariancechanged = newIsVariancechanged;
            }

            // isAsymmetrychanged
            if (isAsymmetrychanged == null)
            {
                isAsymmetrychanged = new boolean[row + 1];
                for (dimensionIndex = 0; dimensionIndex <= row; dimensionIndex++)
                    isAsymmetrychanged[dimensionIndex] = true;
            }
            else
            {
                boolean[] NewIsAsymmetrychanged = new boolean[row + 1];
                for (dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++)
                    NewIsAsymmetrychanged[dimensionIndex] = isAsymmetrychanged[dimensionIndex];
                for (dimensionIndex = dimension; dimensionIndex <= row; dimensionIndex++)
                    NewIsAsymmetrychanged[dimensionIndex] = true;
                isAsymmetrychanged = NewIsAsymmetrychanged;
            }

            // isExcesschanged
            if (isExcesschanged == null)
            {
                isExcesschanged = new boolean[row + 1];
                for (dimensionIndex = 0; dimensionIndex <= row; dimensionIndex++)
                    isExcesschanged[dimensionIndex] = true;
            }
            else
            {
                boolean[] NewIsExcesschanged = new boolean[row + 1];
                for (dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++)
                    NewIsExcesschanged[dimensionIndex] = isExcesschanged[dimensionIndex];
                for (dimensionIndex = dimension; dimensionIndex <= row; dimensionIndex++)
                    NewIsExcesschanged[dimensionIndex] = true;
                isExcesschanged = NewIsExcesschanged;
            }
*/
        }

        // First add
        if (data == null) {
            dimension = row + 1;
            size = initialSize;
            data = new Matrix(dimension, size);
            for (dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++) {
                for (i = 0; i < size; i++) {
                    data.set(dimensionIndex, i, FinConstants.ts_empty);
                }
            }
            setLowerBoundDate(date);
            setLowerRangeDate(date);
            setUpperRangeDate(date);
            TDay upperDate = getDate(size - 1);
            setUpperBoundDate(upperDate);
        }

        // Expand left

        TDay lowerBoundDate = getLowerBoundDate();

        if (date.isLess(lowerBoundDate)) {

            TDay lbdt = getLowerBoundDate();
            long addSize = Math.max(expansion, DateUtils.diffDays(lbdt, date));

            //lbdt.subtractFreq(new TDay(date), frequency));
            long newSize = size + addSize;
            long newNRows = dimension;
            if (row >= dimension)
                newNRows = row + 1;
            Matrix data = new Matrix((int) newNRows, (int) newSize);

            TDay newlbdt = lbdt.subtractDays((int) addSize);

            setLowerBoundDate(newlbdt);
            for (dimensionIndex = 0; dimensionIndex < newNRows; dimensionIndex++) {
                for (i = 0; i < newSize; i++)
                    data.set(dimensionIndex, i, FinConstants.ts_empty);
            }
            for (dimensionIndex = 0; dimensionIndex < newNRows; dimensionIndex++) {
                if (!Double.isNaN(this.data.get(dimensionIndex, 0))) {
                    for (i = 0; i < size; i++)
                        data.set(dimensionIndex, (int) (i + addSize), this.data.get(dimensionIndex, i));
                }
            }
            this.data = data;
            size = (int) newSize;
            dimension = (int) newNRows;
            setLowerRangeDate(date);
        }

        if (date.isGreater(getUpperBoundDate())) {

            System.out.println("date " + date + " is after upper bound = " + getUpperBoundDate());

            printWindow();

            TDay dt = getUpperBoundDate();
            long addSize = Math.max(expansion, DateUtils.diffDays(date, dt));

            long newSize = size + addSize;
            int newNRows = dimension;
            if (row >= dimension) {
                newNRows = row + 1;
            }
            Matrix data = new Matrix((int) newNRows, (int) newSize);
            for (dimensionIndex = 0; dimensionIndex < newNRows; dimensionIndex++) {
                for (i = 0; i < newSize; i++) {
                    data.set(dimensionIndex, i, FinConstants.ts_empty);
                }
            }
            for (dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++) {
                if (!Double.isNaN(this.data.get(dimensionIndex, 0))) {
                    for (i = 0; i < size; i++)
                        data.set(dimensionIndex, i, this.data.get(dimensionIndex, i));
                }
            }
            this.data = data;
            size = (int) newSize;
            dimension = newNRows;
            TDay upperDate = getDate((int) (newSize - 1));

            setUpperBoundDate(upperDate);
            setUpperRangeDate(date);
        }

        // New Rowension
        if (row >= dimension) {

            int NewNRows = row + 1;
            Matrix NewData = new Matrix(NewNRows, size);
            for (dimensionIndex = 0; dimensionIndex < dimension; dimensionIndex++) {
                for (i = 0; i < size; i++) {
                    NewData.set(dimensionIndex, i, data.get(dimensionIndex, i));
                }
            }
            for (dimensionIndex = dimension; dimensionIndex < NewNRows; dimensionIndex++) {
                for (i = 0; i < size; i++)
                    NewData.set(dimensionIndex, i, FinConstants.ts_empty);
            }
            data = NewData;
            dimension = NewNRows;
        }

        if (date.isGreater(getUpperRangeDate())) setUpperRangeDate(date);
        if (date.isLess(getLowerRangeDate())) setLowerRangeDate(date);

        int Index = getIndex(date);
        if (isEmpty(Index, row)) lengthsArray[row]++;

        data.set(row, Index, value);
    }


    private void printWindow() {

        System.out.println("------------ window ------------------------------------------");
        System.out.println("getLowerBoundDate() = " + getLowerBoundDate().toString());
        System.out.println("getLowerBoundIndex() = " + getLowerBoundIndex());
        System.out.println("getUpperBoundTDay() = " + getUpperBoundDate().toString());
        System.out.println("getUpperBoundIndex() = " + getUpperBoundIndex());
        System.out.println("--------------------------------------------------------------");

    }

    public TimeSeries getTimeSeries(TDay first, TDay last) {
        return getTimeSeries(first, last, 0);
    }

    // Return subseries from first to last
    public TimeSeries getTimeSeries(TDay first, TDay last, int row) {
        if (!isValidRow(row)) throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        TimeSeries timeseries = new TimeSeries();
        if (last == null) last = getLastDate();
        int FirstIndex = getIndex(first);
        int LastIndex = getIndex(last);
        for (int Index = FirstIndex; Index <= LastIndex; Index++) {
            if (!isEmpty(Index, row)) timeseries.add(getDate(Index), getData(Index, row));
        }
        return timeseries;
    }

    // Return accumulated xyseries
    // This function is usefult when you e.g.
    // have a xyseries of Util.debug returns and need
    // to calculate accumulated Util.debug return for
    // each day

    public TimeSeries getAccumulatedSeries() {
        TimeSeries AccumulatedSeries = new TimeSeries();
        TDay firstCalendarDate = getFirstDate();
        TDay lastCalendarDate = getLastDate();
        double AccumulatedData = 0;

        int FirstIndex = getIndex(firstCalendarDate);
        int LastIndex = getIndex(lastCalendarDate);
        for (int Index = FirstIndex; Index <= LastIndex; Index++) {
            if (!isEmpty(Index)) {
                AccumulatedData += getData(Index);
                AccumulatedSeries.add(getDate(Index), AccumulatedData);
            }
        }
        return AccumulatedSeries;
    }

    public TimeSeries getDiffSeries(TimeSeries Series) {
        TimeSeries diffSeries = new TimeSeries();
        if (frequency != Series.frequency) {
            LogUtils.debug("TS: GetDiffSeries. Time xyseries with different tick sizes");
            return diffSeries;
        }
        TDay firstCalendarDate = DateUtils.max(getFirstDate(), Series.getFirstDate());
        TDay lastCalendarDate = DateUtils.min(getLastDate(), Series.getLastDate());

        int FirstIndex = getIndex(firstCalendarDate);
        int LastIndex = getIndex(lastCalendarDate);
        for (int index = FirstIndex; index <= LastIndex; index++) {
            if (!isEmpty(index) && !Series.isEmpty(index)) {
                diffSeries.add(getDate(index), getData(index) - Series.getData(index));
            }
        }
        return diffSeries;
    }

    public TimeSeries getShiftedSeries() {
        return getShiftedSeries(1, null, null);
    }

    public TimeSeries getShiftedSeries(TDay from, TDay to) {
        return getShiftedSeries(1, from, to);
    }

    public TimeSeries getShiftedSeries(int Lag, TDay from, TDay to) {
        TimeSeries shiftedSeries = new TimeSeries();

        TDay firstCalendarDate = getFirstDate();
        TDay lastCalendarDate = getLastDate();
        if (from != null) firstCalendarDate = from;
        if (to != null) lastCalendarDate = to;

        for (TDay date = firstCalendarDate;
             date.isLessEqual(lastCalendarDate);
             date = date.addDays(1)) {
            if (!isEmpty(date)) {
                date = date.addDays(Lag);
                shiftedSeries.add(date, getData(date));
            }
        }
        return shiftedSeries;
    }

    public double getFirstData() {
        return getFirstData(0);
    }

    public double getFirstData(int row) {
        if (!isValidRow(row)) {
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        }
        return data.get(row, getFirstIndex());
    }

    public double getLastValidData() {
        return getLastValidData(0);
    }

    public int getLastValidIndex() {
        return getLastValidIndex(0);
    }

    public double getLastValidData(int row) {
        if (!isValidRow(row)) throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        int i = getLastIndex();
        Double d = data.get(row, i);
        while (d.isNaN() && i >= 0)
            d = data.get(row, i--);
        return d;
    }

    public int getLastValidIndex(int row) {
        if (!isValidRow(row)) throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        int i = getLastIndex();
        Double d = data.get(row, i);
        while (d.isNaN() && i >= 0)
            d = data.get(row, i--);
        return i;
    }

    public double getLastData() {
        return getLastData(0);
    }

    public double getLastData(int row) {
        if (!isValidRow(row)) throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        return data.get(row, getLastIndex());
    }


    public double getMin(int FirstIndex, int LastIndex) {
        return getMin(FirstIndex, LastIndex, 0);
    }

    // Calculate min data value between first and last index in row

    public double getMin(int fIndex, int lIndex, int row) {
        if (!isValidRow(row)) {
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        }
        fIndex = Math.max(fIndex, getFirstIndex());
        lIndex = Math.min(lIndex, getLastIndex());
        double Min = 1e99;
        for (int i = fIndex; i <= lIndex; i++) {
            if (!isEmpty(i, row)) {
                Min = Math.min(Min, data.get(row, i));
            }
        }
        return Min;
    }

    public double getMax(int FirstIndex, int LastIndex) {
        return getMax(FirstIndex, LastIndex, 0);
    }

    // Calculate max data value between first and last index in row
    public double getMax(int fIndex, int lIndex, int row) {
        if (!isValidRow(row)) {
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        }
        fIndex = Math.max(fIndex, getFirstIndex());
        lIndex = Math.min(lIndex, getLastIndex());
        double Max = -1e99;
        for (int i = fIndex; i <= lIndex; i++) {
            if (!isEmpty(i, row)) {
                Max = Math.max(Max, data.get(row, i));
            }
        }
        return Max;
    }

    public int getNZero() {
        return getNZero(0);
    }

    // Return number of zero data entries in row

    public int getNZero(int row) {
        if (!isValidRow(row)) {
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        }
        int FirstIndex = getFirstIndex();
        int LastIndex = getLastIndex();
        int N = 0;
        for (int i = FirstIndex; i <= LastIndex; i++) {
            if (!isEmpty(i)) {
                if (data.get(row, i) == 0) {
                    N++;
                }
            }
        }
        return N;
    }

    /**
     * Return number of non zero data entries
     */
    public int getNNonZero() {
        return getNNonZero(0);
    }

    /**
     * Return number of non zero data entries in row
     *
     * @param row
     */
    public int getNNonZero(int row) {
        if (!isValidRow(row)) {
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        }
        int FirstIndex = getFirstIndex();
        int LastIndex = getLastIndex();
        int N = 0;
        for (int i = FirstIndex; i <= LastIndex; i++) {
            if (!isEmpty(i)) {
                if (data.get(row, i) != 0) {
                    N++;
                }
            }
        }
        return N;
    }

    public int getNPositive() {
        return getNPositive(0);
    }

    // Return number of positive (sign) data entries in row

    public int getNPositive(int row) {
        if (!isValidRow(row)) {
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        }
        int FirstIndex = getFirstIndex();
        int LastIndex = getLastIndex();
        int N = 0;
        for (int i = FirstIndex; i <= LastIndex; i++) {
            if (!isEmpty(i)) {
                if (data.get(row, i) > 0) {
                    N++;
                }
            }
        }
        return N;
    }

    public int getNNegative() {
        return getNNegative(0);
    }

    // Return number of negative (sign) data entries in row

    public int getNNegative(int row) {
        if (!isValidRow(row)) {
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        }
        int FirstIndex = getFirstIndex();
        int LastIndex = getLastIndex();
        int N = 0;
        for (int i = FirstIndex; i <= LastIndex; i++) {
            if (!isEmpty(i)) {
                if (data.get(row, i) < 0) {
                    N++;
                }
            }
        }
        return N;
    }

    public double getMean(int FirstIndex, int LastIndex) {
        return getMean(FirstIndex, LastIndex, 0);
    }

    // Calculate mean value between first and last index

    public double getMean(int firstIndex, int lastIndex, int row) {
        if (!isValidRow(row)) {
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        }
        firstIndex = Math.max(firstIndex, getFirstIndex());
        lastIndex = Math.min(lastIndex, getLastIndex());
        int N = 0;
        double Mean = 0;
        for (int i = firstIndex; i <= lastIndex; i++) {
            if (!isEmpty(i)) {
                double val = data.get(row, i);
                Mean += val;
                N++;
            }
        }
        return Mean / N;
    }

    public double getVariance(int FirstIndex, int LastIndex) {
        return getVariance(FirstIndex, LastIndex, 0);
    }

    public double getVariance(int firstIndex, int lastIndex, int row) {
        if (!isValidRow(row)) {
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        }
        firstIndex = Math.max(firstIndex, getFirstIndex());
        lastIndex = Math.min(lastIndex, getLastIndex());
        double Mean = getMean(firstIndex, lastIndex, row);
        int N = 0;
        double Variance = 0;
        for (int i = firstIndex; i <= lastIndex; i++) {
            if (!isEmpty(i)) {
                Variance += (data.get(row, i) - Mean) * (data.get(row, i) - Mean);
                N++;
            }
        }
        return Variance / (N - 1);
    }

    public double getStandardDeviation(int firstIndex, int lastIndex) {
        return getStandardDeviation(firstIndex, lastIndex, 0);
    }

    public double getStandardDeviation(int firstIndex, int lastIndex, int row) {
        if (!isValidRow(row)) {
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        }
        return Math.sqrt(getVariance(firstIndex, lastIndex, row));
    }

    public double getStandardError(int FirstIndex, int LastIndex) {
        return getStandardError(FirstIndex, LastIndex, 0);
    }

    public double getStandardError(int firstIndex, int lastIndex, int row) {
        if (!isValidRow(row)) {
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        }
        firstIndex = Math.max(firstIndex, getFirstIndex());
        lastIndex = Math.min(lastIndex, getLastIndex());
        double Mean = getMean(firstIndex, lastIndex, row);
        int N = 0;
        double SE = 0;
        for (int i = firstIndex; i <= lastIndex; i++) {
            if (!isEmpty(i)) {
                // SE += (valori[row][i] - Mean)*(valori[row][i] - Mean);
                SE += (data.get(row, i) - Mean) * (data.get(row, i) - Mean);
                N++;
            }
        }
        return Math.sqrt(SE / (N * (N - 1)));
    }

    public double getMomentum(int k, int FirstIndex, int LastIndex) {
        return getMomentum(k, FirstIndex, LastIndex, 0);
    }

    // Return cental momentum of k-th order between first and last index
    // k = 1 - mean
    // k = 2 - variance
    // k = 3 - asymmetry
    // k = 4 - excess

    public double getMomentum(int k, int firstIndex, int lastIndex, int row) {
        if (!isValidRow(row)) {
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        }
        firstIndex = Math.max(firstIndex, getFirstIndex());
        lastIndex = Math.min(lastIndex, getLastIndex());
        double X;
        if (k == 1) {
            X = 0;
        } else {
            X = getMean(firstIndex, lastIndex, row);
        }
        int N = 0;
        double Momentum = 0;
        for (int i = firstIndex; i <= lastIndex; i++) {
            if (!isEmpty(i)) {
                // Momentum += Math.pow(valori[row][i] - X, k);
                Momentum += Math.pow(data.get(row, i) - X, k);
                N++;
            }
        }
        if (N == 0) {
            LogUtils.debug("getMomentum . N = 0");
            return 0;
        } else {
            return Momentum / N;
        }
    }

    public double getAsimmetry(int firstIndex, int lastIndex) {
        return getAsimmetry(firstIndex, lastIndex, 0);
    }

    // Return asymmetry of time xyseries distribution between first and last index

    public double getAsimmetry(int firstIndex, int lastIndex, int row) {
        if (!isValidRow(row)) {
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        }
        firstIndex = Math.max(firstIndex, getFirstIndex());
        lastIndex = Math.min(lastIndex, getLastIndex());
        double StdDev = getStandardDeviation(firstIndex, lastIndex, row);
        if (StdDev == 0) {
            LogUtils.debug("getAsimmetry. StdDev = 0");
            return 0;
        } else {
            return getMomentum(3, firstIndex, lastIndex, row) / Math.pow(StdDev, 3);
        }
    }

    public double getExcess(int firstIndex, int lastIndex) {
        return getExcess(firstIndex, lastIndex, 0);
    }

    public double getExcess(int FirstIndex, int LastIndex, int row) {
        // Return excess of time xyseries distribution between first and last index
        if (!isValidRow(row)) {
            throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        }
        FirstIndex = Math.max(FirstIndex, getFirstIndex());
        LastIndex = Math.min(LastIndex, getLastIndex());
        double StdDev = getStandardDeviation(FirstIndex, LastIndex, row);
        if (StdDev == 0) {
            LogUtils.debug("getAsimmetry. StdDev = 0");
            return 0;
        } else {
            return getMomentum(4, FirstIndex, LastIndex, row) / Math.pow(StdDev, 4);
        }
    }

/*
    public double getRandom(int FirstIndex, int LastIndex) {
        return getRandom(FirstIndex, LastIndex, 0);
    }

    public double getRandom(int FirstIndex, int LastIndex, int Row) {
        // Return random data between first and last index
        if (!isValidRow(Row)) {
            Util.debug("TimeSeriesFractal: getRandom. Illegal row number:" + Row + " " + getName());
            return 0;
        }
        FirstIndex = Math.max(FirstIndex, firstIndex());
        LastIndex = Math.min(LastIndex, lastIndex());
        int Index = 0;
        do {
            Index = FirstIndex +  * (LastIndex - FirstIndex + 1);
        } while (isEmpty(Index, Row));
        //            return valori[Row][Index];
        return valori.get(Row, Index);
    }
*/

    public double getMin(TDay firstDate, TDay lastDate) {
        return getMin(firstDate, lastDate, 0);
    }

    // Return min value between first and last date

    public double getMin(TDay firstDate, TDay lastDate, int Row) {
        return getMin(getIndex(firstDate), getIndex(lastDate), Row);
    }

    public double getMax(TDay firstDate, TDay lastDate) {
        return getMax(firstDate, lastDate, 0);
    }

    // Return max value between first and last date

    public double getMax(TDay firstDate, TDay lastDate, int Row) {
        return getMax(getIndex(firstDate), getIndex(lastDate), Row);
    }

    public double getMean(TDay firstDate, TDay lastDate) {
        return getMean(firstDate, lastDate, 0);
    }

    // Return mean value between first and last date

    public double getMean(TDay firstDate, TDay lastDate, int Row) {
        return getMean(getIndex(firstDate), getIndex(lastDate), Row);
    }

    public double getVariance(TDay firstDate, TDay lastDate) {
        return getVariance(firstDate, lastDate, 0);
    }

    // Calculate variance between first and last date

    public double getVariance(TDay firstDate, TDay lastDate, int Row) {
        return getVariance(getIndex(firstDate), getIndex(lastDate), Row);
    }

    public double getStandardDeviation(TDay firstDate, TDay lastDate) {
        return getStandardDeviation(firstDate, lastDate, 0);
    }

    // Calculate standard deviation between first and last date

    public double getStandardDeviation(TDay firstDate, TDay lastDate, int Row) {
        return getStandardDeviation(getIndex(firstDate), getIndex(lastDate), Row);
    }

    public double getStandardError(TDay firstDate, TDay lastDate) {
        return getStandardError(firstDate, lastDate, 0);
    }

    // Calculate standard Util.debug between first and last date

    public double getStandardError(TDay firstDate, TDay lastDate, int Row) {
        return getStandardError(getIndex(firstDate), getIndex(lastDate), Row);
    }

    public double getMomentum(int k, TDay firstDate, TDay lastDate) {
        return getMomentum(k, firstDate, lastDate, 0);
    }

    // Return cental momentum of k-th order between first and last date
    // k = 1 - mean
    // k = 2 - variance
    // k = 3 - asymmetry
    // k = 4 - excess

    public double getMomentum(int k, TDay firstDate, TDay lastDate, int Row) {
        return getMomentum(k, getIndex(firstDate), getIndex(lastDate), Row);
    }

    public double getAsimmetry(TDay firstDate, TDay lastDate) {
        return getAsimmetry(firstDate, lastDate, 0);
    }

    // Return asymmetry of time xyseries distribution between first and last date

    public double getAsimmetry(TDay firstDate, TDay lastDate, int Row) {
        return getAsimmetry(getIndex(firstDate), getIndex(lastDate), Row);
    }

    public double getExcess(TDay firstDate, TDay lastDate) {
        return getExcess(firstDate, lastDate, 0);
    }

    // Return excess of time xyseries distribution between first and last date

    public double getExcess(TDay firstDate, TDay lastDate, int Row) {
        return getExcess(getIndex(firstDate), getIndex(lastDate), Row);
    }

    public double getMin(String FirstDate, String LastDate, String pattern) {
        return getMin(FirstDate, LastDate, pattern, 0);
    }

    // Return min value between first and last date

    public double getMin(String FirstDate, String LastDate, String pattern, int Row) {
        return getMin(getIndex(FirstDate, pattern), getIndex(LastDate, pattern), Row);
    }

    public double getMax(String FirstDate, String LastDate, String pattern) {
        return getMax(FirstDate, LastDate, pattern, 0);
    }

    // Return max value between first and last date

    public double getMax(String FirstDate, String LastDate, String pattern, int Row) {
        return getMax(getIndex(FirstDate, pattern), getIndex(LastDate, pattern), Row);
    }

    public double getMean(String FirstDate, String LastDate, String pattern) {
        return getMean(FirstDate, LastDate, pattern, 0);
    }

    // Return mean value between first and last date

    public double getMean(String FirstDate, String LastDate, String pattern, int Row) {
        return getMean(getIndex(FirstDate, pattern), getIndex(LastDate, pattern), Row);
    }

    public double getVariance(String FirstDate, String LastDate, String pattern) {
        return getVariance(FirstDate, LastDate, pattern, 0);
    }

    // Calculate variance between first and last date

    public double getVariance(String FirstDate, String LastDate, String pattern, int Row) {
        return getVariance(getIndex(FirstDate, pattern), getIndex(LastDate, pattern), Row);
    }

    public double getStandardDeviation(String FirstDate, String LastDate, String pattern) {
        return getStandardDeviation(FirstDate, LastDate, pattern, 0);
    }

    // Calculate standard deviation between first and last date

    public double getStandardDeviation(String FirstDate, String LastDate, String pattern, int Row) {
        return getStandardDeviation(getIndex(FirstDate, pattern), getIndex(LastDate, pattern), Row);
    }

    public double getStandardError(String FirstDate, String LastDate, String pattern) {
        return getStandardError(FirstDate, LastDate, pattern, 0);
    }

    // Calculate standard Util.debug between first and last date

    public double getStandardError(String FirstDate, String LastDate, String pattern, int Row) {
        return getStandardError(getIndex(FirstDate, pattern), getIndex(LastDate, pattern), Row);
    }

    public double getMomentum(int k, String FirstDate, String LastDate, String pattern) {
        return getMomentum(k, FirstDate, LastDate, pattern, 0);
    }

    // Return cental momentum of k-th order between first and last date
    // k = 1 - mean
    // k = 2 - variance
    // k = 3 - asymmetry
    // k = 4 - excess

    public double getMomentum(int k, String FirstDate, String LastDate, String pattern, int Row) {
        return getMomentum(k, getIndex(FirstDate, pattern), getIndex(LastDate, pattern), Row);
    }

    public double getAsimmetry(String FirstDate, String LastDate, String pattern) {
        return getAsimmetry(FirstDate, LastDate, pattern, 0);
    }

    // Return asymmetry of time xyseries distribution between first and last date

    public double getAsimmetry(String FirstDate, String LastDate, String pattern, int Row) {
        return getAsimmetry(getIndex(FirstDate, pattern), getIndex(LastDate, pattern), Row);
    }

    public double getExcess(String FirstDate, String LastDate, String pattern) {
        return getExcess(FirstDate, LastDate, pattern, 0);
    }

    // Return excess of time xyseries distribution between first and last date

    public double getExcess(String FirstDate, String LastDate, String pattern, int Row) {
        return getExcess(getIndex(FirstDate, pattern), getIndex(LastDate, pattern), Row);
    }

    public double getMin() {
        return getMin(0);
    }

    // Return min

    public double getMin(int row) {
        if (isEmpty(0)) return 0;
        if (!isValidRow(row)) throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        minimum[row] = getMin(getFirstIndex(), getLastIndex(), row);
        return minimum[row];
    }

    public double getMax() {
        return getMax(0);
    }

    // Return max

    public double getMax(int row) {
        if (isEmpty(0)) return 0;
        if (!isValidRow(row)) throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        maximum[row] = getMax(getFirstIndex(), getLastIndex(), row);
        return maximum[row];
    }

    public double getMean() {
        return getMean(0);
    }

    // Return mean

    public double getMean(int row) {
        if (isEmpty(0)) return 0;
        if (!isValidRow(row)) throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        mean[row] = getMean(getFirstIndex(), getLastIndex(), row);
        return mean[row];
    }

    public double getVariance() {
        return getVariance(0);
    }

    // Return variance

    public double getVariance(int row) {
        if (isEmpty(0)) return 0;
        if (!isValidRow(row)) throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        variance[row] = getVariance(getFirstIndex(), getLastIndex(), row);
        return variance[row];
    }

    public double getStandardDeviation() {
        return getStandardDeviation(0);
    }

    // Return standard deviation

    public double getStandardDeviation(int row) {
        if (isEmpty(0)) return 0;
        if (!isValidRow(row)) throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        return Math.sqrt(getVariance(row));
    }

    public double getError() {
        return getError(0);
    }

    public double getError(int row) {
        if (isEmpty(0)) return 0;
        if (!isValidRow(row)) throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        return getStandardDeviation(row) / Math.sqrt(lengthsArray[row]);
    }

    public double getMomentum(int k) {
        return getMomentum(k, 0);
    }

    // Return cental momentum of k-th order
    // k = 1 - mean
    // k = 2 - variance
    // k = 3 - asymmetry
    // k = 4 - excess

    public double getMomentum(int k, int row) {
        if (isEmpty(0)) return 0;
        if (!isValidRow(row)) throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        return getMomentum(k, getFirstIndex(), getLastIndex(), row);
    }

    // Return asymmetry

    public double getAsimmetry(int row) {
        if (isEmpty(0)) return 0;
        if (!isValidRow(row)) throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        asymmetry[row] = getAsimmetry(getFirstIndex(), getLastIndex(), row);
        return asymmetry[row];
    }

    public double getExcess() {
        return getExcess(0);
    }

    // Return excess

    public double getExcess(int row) {
        if (isEmpty(0)) return 0;
        if (!isValidRow(row)) throw new IllegalArgumentException("Illegal row number: " + row + " " + getName());
        excess[row] = getExcess(getFirstIndex(), getLastIndex(), row);
        return excess[row];
    }

    // Calculate covariance between two rows

    public double getCovariance(int row1, int row2) {
        if (!isValidRow(row1))
            throw new IllegalArgumentException("Illegal row number: " + row1 + " " + getName());
        if (!isValidRow(row2))
            throw new IllegalArgumentException("Illegal row number: " + row2 + " " + getName());
        double Mean1 = getMean(row1);
        double Mean2 = getMean(row2);
        int FirstIndex = getFirstIndex();
        int LastIndex = getLastIndex();
        correlationPairs = 0;
        double Covariance = 0;
        for (int Index = FirstIndex; Index <= LastIndex; Index++) {
            if (!isEmpty(Index, row1) && !isEmpty(Index, row2)) {
                correlationPairs++;
                Covariance += (data.get(row1, Index) - Mean1) * (data.get(row2, Index) - Mean2);
            }
        }
        if (correlationPairs <= 1) {
            return 0;
        }
        return Covariance / (correlationPairs - 1);
    }

    /**
     * Calculate correlation between two rows
     */
    public double getCorrelation(int row1, int row2) {
        return getCovariance(row1, row2) / (getStandardDeviation(row1) * getStandardDeviation(row2));
    }

    public double getCovariance(TimeSeries series) {
        if (frequency != series.frequency)
            throw new IllegalArgumentException("Different tick sizes");
        double Mean1 = getMean();
        double Mean2 = series.getMean();

        TDay firstCalendarDate = DateUtils.max(getLowerBoundDate(), series.getLowerBoundDate());
        TDay lastCalendarDate = DateUtils.min(getUpperBoundDate(), series.getUpperBoundDate());

        correlationPairs = 0;
        double Covariance = 0;

        for (TDay dt = firstCalendarDate;
             dt.isLessEqual(lastCalendarDate);
             dt = dt.addDays(1)) {
            int Index1 = getIndex(dt);
            int Index2 = series.getIndex(dt);
            if (!isEmpty(Index1) && !series.isEmpty(Index2)) {
                correlationPairs++;
                double val1 = data.get(0, Index1);
                double val2 = series.data.get(0, Index2);
                Covariance += (val1 - Mean1) * (val2 - Mean2);
            }
        }
        if (correlationPairs <= 1) {
            return 0;
        }
        return Covariance / (correlationPairs - 1);
    }

    // Calculate correlation

    public double getCorrelation(TimeSeries series) {
        return getCovariance(series) / (getStandardDeviation() * series.getStandardDeviation());
    }

    // Return number of data pairs used in correlation calculations

    public int getNCorrelationPairs(TimeSeries series) {
        getCovariance(series);
        return correlationPairs;
    }

    public double getAutoCovariance() {
        return getAutoCovariance(1);
    }

    // Calculate autocovariance for lag

    public double getAutoCovariance(int lag) {
        double Mean = getMean();
        double AutoCovariance = 0;
        int CurrentIndex = getFirstIndex();
        int LaggedIndex = getFirstIndex();
        for (int i = 0; i < lag; i++) {
            LaggedIndex = getNextIndex(LaggedIndex);
            if (LaggedIndex == -1) {
                return 0;
            }
        }
        int N = 0;
        while (LaggedIndex != -1) {
            AutoCovariance += (data.get(0, CurrentIndex) - Mean) * (data.get(0, LaggedIndex) - Mean);
            N++;
            CurrentIndex = getNextIndex(CurrentIndex);
            LaggedIndex = getNextIndex(LaggedIndex);
        }
        if (N <= lag) {
            return 0;
        }
        AutoCovariance /= (N - lag);
        return AutoCovariance;
    }

    public double getAutoCorrelation() {
        return getAutoCorrelation(1);
    }

    public double getAutoCorrelation(int lag) {
        return getAutoCovariance(lag) / getVariance();
    }

    public TimeSeries getReturnSeries() {
        return getReturnSeries(1);
    }

    public TimeSeries getReturnSeries(int lag) {
        TimeSeries ReturnSeries = new TimeSeries();
        ReturnSeries.setOption(FinConstants.RETURN);
        TDay date;
        double Data;
        double LastData = getData(getFirstIndex());
        for (int i = getFirstIndex(); i <= getLastIndex(); i++) {
            if (!isEmpty(i)) {
                date = getDate(i);
                Data = getData(i);
                if (LastData != 0)
                    ReturnSeries.add(date, Data / LastData);
                LastData = Data;
            }
        }
        return ReturnSeries;
    }

    public TimeSeries getLogReturnSeries() {
        return getLogReturnSeries(1);
    }

    public TimeSeries getLogReturnSeries(int Lag) {
        TimeSeries ReturnSeries = new TimeSeries();
        ReturnSeries.setOption(FinConstants.LOGRETURN);
        TDay date;
        double Data;
        double LastData = getData(getLowerBoundIndex());
        for (int i = getLowerBoundIndex(); i <= getUpperBoundIndex(); i++) {
            if (!isEmpty(i)) {
                date = getDate(i);
                Data = getData(i);
                if (LastData != 0)
                    ReturnSeries.add(date, Math.log(Data / LastData));
                LastData = Data;
            }
        }
        return ReturnSeries;
    }

    public int getNCrossings() {
        return getNCrossings(0);
    }

    public int getNCrossings(double Level) {
        boolean IsBelow;
        double Data = getFirstData();
        if (Data < Level)
            IsBelow = true;
        else
            IsBelow = false;
        int FirstIndex = getFirstIndex() + 1;
        int LastIndex = getLastIndex();
        int NCrossings = 0;
        for (int Index = FirstIndex; Index <= LastIndex; Index++) {
            if (!isEmpty(Index)) {
                Data = getData(Index);
                if (IsBelow && Data > Level || (!IsBelow && Data < Level)) {
                    NCrossings++;
                    IsBelow = !IsBelow;
                }
            }
        }
        return NCrossings;
    }

    // clear time xyseries

    public void clear() {
        init();
    }

    public void normalize() {
        normalize(0);
    }

    // Normalize data inside a window.
    // Note that data outside the window remain untouched!

    public void normalize(int Row) {
        int FirstIndex = getFirstIndex();
        int LastIndex = getLastIndex();
        double Mean = getMean(Row);
        double StandardDeviation = getStandardDeviation(Row);
        for (int i = FirstIndex; i <= LastIndex; i++) {
            if (!isEmpty(i, Row)) data.set(Row, i, (data.get(Row, i) - Mean) / StandardDeviation);
        }
//        changed(Row);
    }

    public void linearMapping(double Lower, double Upper) {
        linearMapping(Lower, Upper, 0);
    }

    // Perform linear mapping for Row data inside a window.

    public void linearMapping(double Lower, double Upper, int Row) {
        double Alpha = (Upper - Lower) / (getMax(Row) - getMin(Row));
        double Beta = -1 * Alpha * getMin(Row) + Lower;
        int FirstIndex = getFirstIndex();
        int LastIndex = getLastIndex();
        for (int i = FirstIndex; i <= LastIndex; i++)
            if (!isEmpty(i)) data.set(Row, i, data.get(Row, i) * Alpha + Beta);
    }

    /* smooth time xyseries with simple moving average
     */

    public void SMA(int Order) {
        int FirstIndex = getLowerBoundIndex();
        int LastIndex = getUpperBoundIndex();
        double Sum = 0;
        double LastData = 0;
        double PointCount = 0;
        int Index;
        for (Index = FirstIndex; Index <= LastIndex; Index++) {
            if (!isEmpty(Index)) {
                LastData = data.get(0, Index);
                Sum += LastData;
                PointCount++;
                data.set(0, Index, Sum / PointCount);
                if (PointCount == Order) {
                    break;
                }
            }
        }
        FirstIndex = Index + 1;
        for (Index = FirstIndex; Index <= LastIndex; Index++) {
            if (!isEmpty(Index)) {
                Sum -= LastData;
                LastData = data.get(0, Index);
                Sum += LastData;
                data.set(0, Index, Sum / Order);
            }
        }
        //    changed();
    }

    // Return Lag time xyseries

    public TimeSeries lag(int Periods) {
        TimeSeries lag = new TimeSeries();
        int i;
        int k;
        int index = 0;
        int prev;
        for (i = 0; i < Periods; i++) {
            index = getNextIndex(index);
        }
        while (index != -1) {
            prev = index;
            for (i = 0; i < Periods; i++)
                prev = getPrevIndex(prev);
            for (k = 0; k < dimension; k++)
                lag.add(getDate(index), getData(prev, k), k);
            index = getNextIndex(index);
        }
        return lag;
    }

    public TimeSeries lead(int Periods) {
        TimeSeries lead = new TimeSeries();
        int i;
        int k;
        int index = getLastIndex();
        int next;
        int stopat;
        for (i = 0; i < Periods; i++)
            index = getPrevIndex(index);
        stopat = index;
        index = 0;
        next = 0;
        while (index <= stopat) {
            next = index;
            for (i = 0; i < Periods; i++)
                next = getNextIndex(next);
            for (k = 0; k < dimension; k++)
                lead.add(getDate(index), getData(next, k), k);
            index = getNextIndex(index);
        }
        return lead;
    }

    public TimeSeries clone() {
        TimeSeries timeSeries = new TimeSeries();
        timeSeries.clear();
        timeSeries.size = this.size;
        timeSeries.data = new Matrix(this.dimension, size);
        for (int rowCount = 0; rowCount < this.dimension; rowCount++)
            for (int i = 0; i < size; i++) timeSeries.data.set(rowCount, i, data.get(rowCount, i));
        timeSeries.frequency = this.frequency;
        timeSeries.setLowerBoundDate(this.getLowerBoundDate());
        timeSeries.setUpperBoundDate(this.getUpperBoundDate());
        timeSeries.setLowerRangeDate(this.getLowerRangeDate());
        timeSeries.setUpperRangeDate(this.getUpperRangeDate());
        timeSeries.initialSize = this.initialSize;
        timeSeries.expansion = this.expansion;
        timeSeries.dimension = this.dimension;
        timeSeries.lengthsArray = this.lengthsArray;
        timeSeries.minimum = this.minimum;
        timeSeries.maximum = this.maximum;
        timeSeries.mean = this.mean;
        timeSeries.variance = this.variance;
        timeSeries.option = option;
        timeSeries.correlationPairs = correlationPairs;
        if (minimum != null) {
            timeSeries.minimum = new double[minimum.length];
            for (int i = 0; i < minimum.length; i++)
                timeSeries.minimum[i] = minimum[i];
        }
        if (maximum != null) {
            timeSeries.maximum = new double[maximum.length];
            for (int i = 0; i < maximum.length; i++)
                timeSeries.maximum[i] = maximum[i];
        }
        if (mean != null) {
            timeSeries.mean = new double[mean.length];
            for (int i = 0; i < mean.length; i++)
                timeSeries.mean[i] = mean[i];
        }
        if (variance != null) {
            timeSeries.variance = new double[variance.length];
            for (int i = 0; i < variance.length; i++)
                timeSeries.variance[i] = variance[i];
        }
        if (asymmetry != null) {
            timeSeries.asymmetry = new double[asymmetry.length];
            for (int i = 0; i < asymmetry.length; i++)
                timeSeries.asymmetry[i] = asymmetry[i];
        }
        if (excess != null) {
            timeSeries.excess = new double[excess.length];
            for (int i = 0; i < excess.length; i++)
                timeSeries.excess[i] = excess[i];
        }
        return timeSeries;
    }

    public TimeSeries plus(double data) {
        for (int i = getLowerBoundIndex(); i <= getUpperBoundIndex(); i++)
            if (!isEmpty(i)) this.data.set(0, i, this.data.get(0, i) + data);
        minimum[0] += data;
        maximum[0] += data;
        mean[0] += data;
        return this;
    }

    public TimeSeries minus(double data) {
        for (int i = getLowerBoundIndex(); i <= getUpperBoundIndex(); i++)
            if (!isEmpty(i)) this.data.set(0, i, this.data.get(0, i) - data);
        minimum[0] -= data;
        maximum[0] -= data;
        mean[0] -= data;
        return this;
    }

    public TimeSeries times(double data) {
        for (int i = getLowerBoundIndex(); i <= getUpperBoundIndex(); i++)
            if (!isEmpty(i)) this.data.set(0, i, this.data.get(0, i) * data);
        minimum[0] *= data;
        maximum[0] *= data;
        mean[0] *= data;
        variance[0] *= (data * data);
        return this;
    }

    public TimeSeries divided(double data) {
        for (int i = getLowerBoundIndex(); i <= getUpperBoundIndex(); i++) {
            if (!isEmpty(i)) this.data.set(0, i, this.data.get(0, i) / data);
        }
        minimum[0] /= data;
        maximum[0] /= data;
        mean[0] /= data;
        variance[0] /= (data * data);
        return this;
    }

    public Matrix getData() {
        return data;
    }

    public double getStandardError() {
        return this.getStandardError(0);
    }

    public double getAsymmetry() {
        return this.getAsimmetry(0);
    }

    public NumericalRecipesSeries asRecipes() {
        NumericalRecipesSeries series = new NumericalRecipesSeries();
        int size = lengthsArray[0];
        series.setData(NumericalRecipesUtils.vector(1, size));
        series.setDates(NumericalRecipesUtils.datesvector(1, size));
        int j = 1;
        for (int i = getLowerBoundIndex(); i <= getUpperBoundIndex(); i++) {
            if (!isEmpty(i)) {
                series.setValue(j, (float) data.get(0, i));
                getDate(i);
                series.setDate(j++, getDate(i));
            }
        }
        return series;
    }

    public double[] toArray() {
        int size = lengthsArray[0];
        double[] vals = new double[size];
        int j = 0;
        for (int i = getLowerBoundIndex(); i <= getUpperBoundIndex(); i++) {
            if (!isEmpty(i)) vals[j++] = data.get(0, i);
        }
        return vals;
    }

    public DateValue[] toDateValue() {
        int size = lengthsArray[0];
        DateValue[] vals = new DateValue[size];
        int j = 0;
        for (int i = getLowerBoundIndex(); i <= getUpperBoundIndex(); i++) {
            if (!isEmpty(i)) {
                DateValue dv = new DateValue();
                dv.setValue(data.get(0, i));
                dv.setDate(getDate(i));
                vals[j++] = dv;
                j++;
            }
        }
        return vals;
    }

    public void toTimeSeries(double[] vals) {
        int j = 0;
        for (int i = getLowerBoundIndex(); i <= getUpperBoundIndex(); i++) {
            if (!isEmpty(i)) data.set(0, j++, vals[i]);
        }
    }

    public void printDates() {
        for (int i = getLowerBoundIndex(); i <= getUpperBoundIndex(); i++)
            if (!isEmpty(i)) LogUtils.debug("i = " + getDate(i).toString());
    }

    public double[] getLastNData(int newSize) {
        double vals[] = new double[newSize];
        int n = vals.length;
        int i = getUpperBoundIndex();
        for (; n > 0; i--) {
            if (!isEmpty(i)) {
                vals[n - 1] = data.get(0, i);
                n--;
            }
        }
        return vals;
    }

    public void copriLastNData(double[] vals) {
        int n = vals.length;
        int i = getUpperBoundIndex();
        for (; n > 0; i--) {
            if (!isEmpty(i)) {
                this.set(i, vals[n - 1]);
                n--;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getExpansion() {
        return expansion;
    }

    public void setExpansion(int expansion) {
        this.expansion = expansion;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int[] getLengthsArray() {
        return lengthsArray;
    }

    public void setLengthsArray(int[] lengthsArray) {
        this.lengthsArray = lengthsArray;
    }

    public double[] getMinimum() {
        return minimum;
    }

    public void setMinimum(double[] minimum) {
        this.minimum = minimum;
    }

    public double[] getMaximum() {
        return maximum;
    }

    public void setMaximum(double[] maximum) {
        this.maximum = maximum;
    }

    public int getCorrelationPairs() {
        return correlationPairs;
    }

    public void setCorrelationPairs(int correlationPairs) {
        this.correlationPairs = correlationPairs;
    }

//    public String getTimeplotSeries() {
//        StringBuffer sb = null;
//        try {
//            sb = new StringBuffer();
//            TDay FirstInstrumentDate = getFirstDate();
//            TDay LastInstrumentDate = getLastDate();
//            SimpleDateFormat yyyydashMMdashdd = new SimpleDateFormat("yyyy-MM-dd");
//                for (TDay d = FirstInstrumentDate;
//                     DateUtils.beforeEqual( d, LastInstrumentDate);
//                     d = DateUtils.addDays(d, 1))  {
//                double v = this.getData(d);
//                if (!Double.isNaN(v))
//                    sb.append(yyyydashMMdashdd.format(d)).append(' ').append(v).append("\n");
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return sb.toString();
//    }
}