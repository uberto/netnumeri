package com.netnumeri.shared.finance.finpojo;

import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.beans.TimeSeries;
import com.netnumeri.shared.finance.data.DateBound;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.utils.DateUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeMap;

public abstract class Instrument extends DateBound implements Serializable {

    protected double delta = 1;

    public abstract double modelPrice(int model);

    private double spot;
    private double volatility;
    private boolean isSpotFixed;
    private boolean isVolatilityFixed = false;

    private TDay tempToday;
    private double tempSpot;
    private double tempVolatility;
    private boolean isTempSpotFixed;
    private boolean isTempVolatilityFixed;

    private TimeSeries priceSeries = null;
    private TimeSeries returnSeries = null;
    private TimeSeries logReturnSeries = null;
    private TimeSeries highSeries = null;
    private TimeSeries lowSeries = null;
    private TimeSeries openSeries = null;
    private TimeSeries closeSeries = null;
    private TimeSeries volumeSeries = null;
    private TimeSeries volumeReturnSeries = null;
    private TimeSeries volumeLogReturnSeries = null;

    private double marketSpotShift = 1.0;
    private double marketVolatilityShift = 1.0;

    public TreeMap<TDay, Daily> dailyarray = new TreeMap<TDay, Daily>();
    public TreeMap<TDay, Daily> getDailyarray() {
        return dailyarray;
    }

    public void setDailyarray(TreeMap<TDay, Daily> dailyarray) {
        this.dailyarray = dailyarray;
    }

    public Instrument() {
        init();
    }

    public Instrument(String name) {
        setName(name);
        init();
    }

    private void init() {
        initFields();
        initSeries();
    }

    private void initFields() {
        isSpotFixed = false;
        isVolatilityFixed = false;
    }

    private void initSeries() {
        priceSeries = new TimeSeries("PriceSeries");
        returnSeries = new TimeSeries("ReturnSeries");
        logReturnSeries = new TimeSeries("LogReturnSeries");
        highSeries = new TimeSeries("HighSeries");
        lowSeries = new TimeSeries("LowSeries");
        openSeries = new TimeSeries("OpenSeries");
        closeSeries = new TimeSeries("CloseSeries");
        volumeSeries = new TimeSeries("VolumeSeries");
        volumeReturnSeries = new TimeSeries("VolumeReturnSeries");
        volumeLogReturnSeries = new TimeSeries("VolumeLogReturnSeries");
    }

    public TDay tempToday() {
        return tempToday;
    }

    public void setTempToday(TDay tempToday) {
        this.tempToday = tempToday;
    }


    public double getTempSpot() {
        return tempSpot;
    }

    public void setTempSpot(double tempSpot) {
        this.tempSpot = tempSpot;
    }

    public double getTempVolatility() {
        return tempVolatility;
    }

    public void setTempVolatility(double tempVolatility) {
        this.tempVolatility = tempVolatility;
    }

    public boolean isTempSpotFixed() {
        return isTempSpotFixed;
    }

    public void setTempSpotFixed(boolean tempSpotFixed) {
        this.isTempSpotFixed = tempSpotFixed;
    }

    public boolean isTempVolatilityFixed() {
        return isTempVolatilityFixed;
    }

    public void setTempVolatilityFixed(boolean tempVolatilityFixed) {
        this.isTempVolatilityFixed = tempVolatilityFixed;
    }


    public boolean isVolatilityFixed() {
        return isVolatilityFixed;
    }

    public double getFixedSpot() {
        return spot;
    }

    public double getFixedVolatility() {
        return volatility;
    }

    public double S() {
        return spot();
    }

    public double s() {
        return volatility();
    }

    public void setFixedSpot(double Spot) {
        spot = Spot;
    }

    public void setFixedVolatility(double Volatility) {
        volatility = Volatility;
    }

    public double historicalSpot() {
        double ret = 0;
        ret = getHistoricalSpot(new TDay());
        return ret;
    }

    public double getHistoricalSpot(TDay d) {
        return getPrice(d);
    }

    public double historicalVolatility() {
        return getHistoricalVolatility();
    }

    public double getHistoricalVolatility() {
        return getStandardDeviation(FinConstants.LOGRETURN) * Math.sqrt(365.0);
    }

    public boolean isSpotFixed() {
        return isSpotFixed;
    }

    /**
     * Return spot premium. In case of derivative instruments usually return spot premium of the underlying instrument
     *
     * @return Return fixed value if spot premium is set with setSpot() call else return last historical spot value
     */
    public double spot() {
        if (isSpotFixed()) {
            return marketSpotShift * getFixedSpot();
        } else {
            return marketSpotShift * historicalSpot();
        }
    }

    /**
     * Return volatility. In case of derivative instruments usually return volatility of the underlying instrument
     * Volatility is calculated as annualized standard deviation of instrument daily log returns
     *
     * @return Return fixed value if volatility is set with setVolatility() call else return historical volatility
     */
    public double volatility() {
        if (isVolatilityFixed()) {
            return marketVolatilityShift * getFixedVolatility();
        } else {
            return marketVolatilityShift * historicalVolatility();
        }
    }

    /**
     * Set spot value
     *
     * @param spot value
     */
    public void setSpot(double spot) {
        this.spot = spot;
        isSpotFixed = true;
    }

    /**
     * Set volatility value
     */
    public void setVolatility(double v) {
        volatility = v;
        isVolatilityFixed = true;
    }

    public void resetSpot() {
        isSpotFixed = false;
    }

    public void resetVolatility() {
        isVolatilityFixed = false;
    }

    public void storeSettings() {
        tempToday = new TDay();
        tempSpot = spot;
        tempVolatility = volatility;
        isTempSpotFixed = isSpotFixed;
        isTempVolatilityFixed = isVolatilityFixed;
    }

    public void restoreSettings() {
        DateUtils.setToday(tempToday);
        spot = tempSpot;
        volatility = tempVolatility;
        isSpotFixed = isTempSpotFixed;
        isVolatilityFixed = isTempVolatilityFixed;
    }

    public void addDaily(int index, Daily daily) {
        add(this, index, daily.getDailydate(),
                daily.getHigh(), daily.getLow(), daily.getOpenprice(), daily.getCloseprice(), daily.getVolume(),
                daily.getOpenInterest(), FinConstants.VALID);
        setLowerBoundDate(DateUtils.min(daily.getDailydate(), getLowerBoundDate()));
        setUpperBoundTDay(DateUtils.max(daily.getDailydate(), getUpperBoundTDay()));
    }

    public void addDaily(int index, TDay date, double high, double low, double open, double close, int volume, int openInterest, int option) {
        add(this, index, date, high, low, open, close, volume, openInterest, option);
        setLowerBoundDate(DateUtils.min(date, getLowerBoundDate()));
        setUpperBoundTDay(DateUtils.max(date, getUpperBoundTDay()));
    }


    public boolean dataAvailable() {
        return isDataAvailable(-1);
    }

    public boolean isDataAvailable(int index) {
        if (index == -1) {
            index = getLastIndex();
        }
        return dataAvailable(index);
    }

    public boolean isDataAvailable(TDay date) {
        // Return flase if data for date have NOTAVAILABLE option
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return dataAvailable(date);
    }

    public int getFirstIndex() {
        return index(firstDate());
    }

    public int getLastIndex() {
        return index(lastDate());
    }

    /**
     * Return historical data, if Index == -1, return last historical data Index Note that data must be copied,
     * otherwise they will be overwritten at any further operation with fDaily
     */
    public Daily getDaily() {
        return getDaily(-1, false);
    }

    public Daily getDaily(int index) {
        return getDaily(index, false);
    }

    public Daily getDaily(int index, boolean neww) {
        return daily(index, neww);
    }

    public Daily getDaily(TDay date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return daily(date, false);
    }

    public int getPrevIndex(int index) {
        return prevIndex(index);
    }

    public int getNextIndex(int index) {
        return nextIndex(index);
    }

    public int getIndex(TDay date) {
        if (date == null) throw new IllegalArgumentException("date cannot be null");
        return index(date);
    }

    public TDay getDate(int index) {
        return date(index);
    }

    public double premium() {
        return getPrice(-1, FinConstants.TYPICALPRICE);
    }

    public double getPrice(int index) {
        return price(index, FinConstants.TYPICALPRICE);
    }

    public double getPrice(int index, int option) {
        return price(index, option);
    }

    public double getLow() {
        return getLow(-1);
    }

    public double getLow(int index) {
        // Return low, if Index == -1, return low from last Index
        return low(index);
    }

    public double getHigh() {
        return getHigh(-1);
    }

    public double getHigh(int index) {
        // Return high, if Index == -1, return high from last Index
        return high(index);
    }

    public double getOpen() {
        return getOpen(-1);
    }

    public double getOpen(int index) {
        // Return open premium, if Index == -1, return close from last Index
        return open(index);
    }

    public double getClose() {
        return getClose(-1);
    }

    public double getClose(int index) {
        // Return close premium, if Index == -1, return close from last Index
        return close(index);
    }

    
    public double getReturn() {
        return getReturn(-1);
    }

    public double getReturn(int index) {
        return fReturn(index);
    }

    public double getLogReturn() {
        return getLogReturn(-1);
    }

    public double getLogReturn(int index) {
        // getAndRemove log return, if Index == -1, getAndRemove return from last Index
        return logReturn(index);
    }

    public double getPrice(TDay date) {
        if (date == null) throw new IllegalArgumentException("date cannot be null");
        return getPrice(date, FinConstants.TYPICALPRICE);
    }

    /**
     *  Option TYPICALPRICE, MEDIANPRICE , WEIGHTEDPRICE ,AVERAGEPRICE, LOGAVERAGEPRICE;
     */
    public double getPrice(TDay date, int Option) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return getPrice(getIndex(date), Option);
    }

    public double getLow(TDay date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return getLow(getIndex(date));
    }

    public double getHigh(TDay date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return getHigh(getIndex(date));
    }

    public double getOpen(TDay date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return getOpen(getIndex(date));
    }

    public double getClose(TDay date) {
        return getClose(getIndex(date));
    }

    public double getReturn(TDay date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return getReturn(getIndex(date));
    }

    public double getLogReturn(TDay date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return getLogReturn(getIndex(date));
    }

    public int getVolume(TDay date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return getVolume(getIndex(date));
    }

    public double get(int index, int option) {
        switch (option) {
            case FinConstants.HIGH:
                return getHigh(index);
            case FinConstants.LOW:
                return getLow(index);
            case FinConstants.OPEN:
                return getOpen(index);
            case FinConstants.CLOSE:
                return getClose(index);
            case FinConstants.VOLUME:
                return getVolume(index);
            case FinConstants.PRICE:
                return getPrice(index);
            case FinConstants.MEDIANPRICE:
                return getPrice(index, option);
            case FinConstants.TYPICALPRICE:
                return getPrice(index, option);
            case FinConstants.WEIGHTEDPRICE:
                return getPrice(index, option);
            case FinConstants.RETURN:
                return getReturn(index);
            case FinConstants.LOGRETURN:
                return getLogReturn(index);
        }
        return 0;
    }

    public double getLast(TDay date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        Daily daily = getDaily(date);
        while (!daily.valid()) {
            daily = getPrevDaily(date);
        }
        if (daily != null) {
            return daily.getCloseprice();
        } else {
            return 0;
        }
    }

    
    public double getLast() {
        TDay lastDailyDate = getLastDay();
        return getLast(lastDailyDate);
    }

    public double getLast(String date, String pattern) {
        return getLast(date, pattern);
    }


    
    public TimeSeries getPriceSeries() {
//        if (priceSeriesChanged)
        {
            priceSeries.clear();
            int firstIndex = getFirstIndex();
            int lastIndex = getLastIndex();

            for (int i = firstIndex; i <= lastIndex; i++) {
                Daily daily = getDaily(i);
                TDay date = daily.getDailydate();
                double price = daily.getPrice();
                if (daily != null && daily.valid() && price != 0) {
                    priceSeries.add(date, price);
                }
            }
            //          priceSeriesChanged = false;
        }
        return priceSeries;
    }

    public TimeSeries getSeries(int what) {
        return getSeries(what, null, null);
    }

    public TimeSeries getSeries(int what, TDay firstDate, TDay lastDate) {
        if (firstDate == null) {
            firstDate = firstDate();
        }
        if (lastDate == null) {
            lastDate = lastDate();
        }
        switch (what) {
            case FinConstants.HIGH:
                return getHighSeries(firstDate, lastDate);
            case FinConstants.LOW:
                return getLowSeries(firstDate, lastDate);
            case FinConstants.OPEN:
                return getOpenSeries(firstDate, lastDate);
            case FinConstants.CLOSE:
                return getCloseSeries(firstDate, lastDate);
            case FinConstants.VOLUME:
                return getVolumeSeries(firstDate, lastDate);
        }
        return null;
    }

    
    public TimeSeries getHighSeries() {
        return getHighSeries(null, null);
    }

    // Return high time series
    public TimeSeries getHighSeries(TDay firstDate, TDay lastDate) {
        if (firstDate != null) {
            if (lastDate == null) {
                lastDate = getLastDay();
            }
            return highSeries(firstDate, lastDate);
        }
        firstDate = firstDate();
        lastDate = lastDate();
        //   if (highSeriesChanged)
        {
            highSeries = highSeries(firstDate, lastDate);
            //       highSeriesChanged = false;
        }
        return highSeries;
    }

    
    public TimeSeries getLowSeries() {
        return getLowSeries(null, null);
    }

    public TimeSeries getLowSeries(TDay firstDate, TDay lastDate) {
        if (firstDate != null) {
            if (lastDate == null) {
                lastDate = getLastDay();
            }
            return lowSeries(firstDate, lastDate);
        }
        firstDate = firstDate();
        lastDate = lastDate();
        //    if (lowSeriesChanged)
        {
            lowSeries = lowSeries(firstDate, lastDate);
            //        lowSeriesChanged = false;
        }
        return lowSeries;
    }

    
    public TimeSeries getOpenSeries() {
        return getOpenSeries(null, null);
    }

    public TimeSeries getOpenSeries(TDay firstDate, TDay lastDate) {
        if (firstDate != null) {
            if (lastDate == null) {
                lastDate = getLastDay();
            }
            return openSeries(firstDate, lastDate);
        }
        firstDate = firstDate();
        lastDate = lastDate();
        //  if (openSeriesChanged)
        {
            openSeries = openSeries(firstDate, lastDate);
            //      openSeriesChanged = false;
        }
        return openSeries;
    }

    
    public TimeSeries getCloseSeries() {
        return getCloseSeries(null, null);
    }

    public TimeSeries getCloseSeries(TDay firstDate, TDay lastDate) {
        if (firstDate == null) firstDate = firstDailyDate();
        if (lastDate == null) lastDate = getLastDay();
        closeSeries = closeSeries(firstDate, lastDate);
        return closeSeries;
    }

    
    public TimeSeries getVolumeSeries() {
        return getVolumeSeries(null, null);
    }

    public TimeSeries getVolumeSeries(TDay firstDate, TDay lastDate) {
        if (firstDate != null) {
            if (lastDate == null) {
                lastDate = getLastDay();
            }
            return volumeSeries(firstDate, lastDate);
        }
        firstDate = firstDate();
        lastDate = lastDate();
        volumeSeries = volumeSeries(firstDate, lastDate);
        return volumeSeries;
    }

    public TimeSeries returnSeries() {
        //  if (returnSeriesChanged)
        {
            returnSeries.clear();
            TDay date = null;
            double price = 0;
            Daily daily = null;
            int firstIndex = getFirstIndex();
            int lastIndex = getLastIndex();
            daily = getDaily(firstIndex);
            if (daily != null && !daily.valid()) {
                firstIndex = getNextIndex(firstIndex);
            }
            double fLastPrice = daily.getCloseprice();
            for (int i = firstIndex + 1; i <= lastIndex; i++) {
                daily = getDaily(i);
                price = daily.getCloseprice();
                date = daily.getDailydate();
                if (daily != null && daily.valid() && price != 0) {
                    returnSeries.add(date, price / fLastPrice);
                    fLastPrice = price;
                }
            }
//            returnSeriesChanged = false;
        }
        return returnSeries;
    }

    public TimeSeries logReturnSeries() {
        logReturnSeries.clear();
        TDay date = null;
        double price = 0;
        Daily daily = null;
        int firstIndex = getFirstIndex();
        int lastIndex = getLastIndex();

        daily = getDaily(firstIndex);
        if (daily != null && !daily.valid()) {
            firstIndex = getNextIndex(firstIndex);
        }

        assert daily != null;
        double fLastPrice = daily.getCloseprice();
        for (int i = firstIndex + 1; i <= lastIndex; i++) {
            daily = getDaily(i);
            date = daily.getDailydate();
            price = daily.getCloseprice();
            if (daily.valid() && price != 0) {
                double logprice = Math.log(price / fLastPrice);
                logReturnSeries.add(date, Math.log(price / fLastPrice));
                fLastPrice = price;
            }
        }
        return logReturnSeries;
    }

    
    public TimeSeries getVolumeReturnSeries() {
        //    if (volumeReturnSeriesChanged)
        {
            volumeReturnSeries.clear();
            TDay date = null;
            double volume = 0;
            Daily daily = null;
            int firstIndex = getFirstIndex();
            int lastIndex = getLastIndex();
            daily = getDaily(firstIndex);
            if (daily != null && !daily.valid()) {
                firstIndex = getNextIndex(firstIndex);
            }
            double lastVolume = daily.getVolume();
            for (int i = firstIndex + 1; i <= lastIndex; i++) {
                daily = getDaily(i);
                date = daily.getDailydate();
                volume = daily.getVolume();
                if (daily != null && daily.valid() && volume != 0) {
                    volumeReturnSeries.add(date, volume / lastVolume);
                    lastVolume = volume;
                }
            }
            //           volumeReturnSeriesChanged = false;
        }
        return volumeReturnSeries;
    }

    public TimeSeries getVolumeLogReturnSeries() {
        //     if (volumeLogReturnSeriesChanged)
        {
            volumeLogReturnSeries.clear();
            TDay date = null;
            double volume = 0;
            Daily daily = null;
            int firstIndex = getFirstIndex();
            int lastIndex = getLastIndex();
            daily = getDaily(firstIndex);
            if (daily != null && !daily.valid()) {
                firstIndex = getNextIndex(firstIndex);
            }
            double lastVolume = daily.getVolume();
            for (int i = firstIndex + 1; i <= lastIndex; i++) {
                daily = getDaily(i);
                date = daily.getDailydate();
                volume = daily.getVolume();
                if (daily != null && daily.valid() && volume != 0) {
                    volumeLogReturnSeries.add(date, Math.log(volume / lastVolume));
                    lastVolume = volume;
                }
            }
            //        volumeLogReturnSeriesChanged = false;
        }
        return volumeLogReturnSeries;
    }

    public double expectedReturn() {
        return getExpectedReturn(FinConstants.RETURN);
    }

    public double getExpectedReturn(int option) {
        double Return = 0;
        switch (option) {
            case FinConstants.RETURN:
                Return = returnSeries().getMean();
                break;

            case FinConstants.LOGRETURN:
                Return = logReturnSeries().getMean();
                break;
        }
        return Return;
    }

    public double variance() {
        return getVariance(FinConstants.RETURN);
    }

    public double getVariance(int option) {
        double Variance = 0;
        switch (option) {
            case FinConstants.RETURN:
                Variance = returnSeries().getVariance();
                break;
            case FinConstants.LOGRETURN:
                Variance = logReturnSeries().getVariance();
                break;
        }
        return Variance;
    }

    public double standardDeviation() {
        return getStandardDeviation(FinConstants.RETURN);
    }

    public double getStandardDeviation(int option) {
        return Math.sqrt(getVariance(option));
    }

    public double annualExpectedReturn() {
        return (Math.pow(expectedReturn(), 365) - 1);
    }

    public double annualVariance() {
        return variance() * 365.0;
    }

    public double annualStandardDeviation() {
        return standardDeviation() * Math.sqrt(365.0);
    }

    public double getCovariance(Instrument instrument) {
        return getCovariance(instrument, FinConstants.RETURN);
    }

    public double getCovariance(Instrument instrument, int option) {
        double Covariance = 0;
        switch (option) {
            case FinConstants.RETURN:
                Covariance = returnSeries().getCovariance(instrument.returnSeries());
                break;
            case FinConstants.LOGRETURN:
                Covariance = logReturnSeries().getCovariance(instrument.logReturnSeries());
                break;
        }
        return Covariance;
    }

    public double getCorrelation(Instrument instrument) {
        return getCorrelation(instrument, FinConstants.RETURN);
    }

    public double getCorrelation(Instrument instrument, int option) {
        double Correlation = 0;
        switch (option) {
            case FinConstants.RETURN:
                Correlation = returnSeries().getCorrelation(instrument.returnSeries());
                break;
            case FinConstants.LOGRETURN:
                Correlation = logReturnSeries().getCorrelation(instrument.logReturnSeries());
                break;
        }
        return Correlation;
    }

    public int getNCorrelationPairs(Instrument instrument) {
        return returnSeries().getNCorrelationPairs(instrument.returnSeries());
    }

    public boolean isDailyData() {
        return isDailyData(null);
    }

    public boolean isDailyData(TDay date) {
        return dailyData(date);
    }

    public TDay getFirstDay() {
        return firstDailyDate();
    }

    public TDay getLastDay() {
        return lastDailyDate();
    }

    
    public int getNDaily() {
        return lenght();
    }

    public int getPrevIndex(TDay date) {
        return getPrevIndex(getIndex(date));
    }

    public int getNextIndex(TDay date) {
        return getNextIndex(getIndex(date));
    }

    public TDay getPrevDate(TDay date) {
        return prevDate(date);
    }

    public TDay getNextDate(TDay date) {
        return nextDate(date);
    }

    public Daily getPrevDaily(int Index) {
        return getPrevDaily(Index, false);
    }

    public Daily getPrevDaily(int Index, boolean New) {
        return prevDaily(Index, New);
    }

    public Daily getPrevDaily(TDay date) {
        return getPrevDaily(date, false);
    }

    public Daily getPrevDaily(TDay date, boolean New) {
        return prevDaily(date, New);
    }

    public Daily getNextDaily(int Index) {
        return getNextDaily(Index, false);
    }

    public Daily getNextDaily(int Index, boolean New) {
        return nextDaily(Index, New);
    }

    public Daily getNextDaily(TDay date) {
        return getNextDaily(date, false);
    }

    public Daily getNextDaily(TDay date, boolean New) {
        return nextDaily(date, New);
    }

    public void setMarketSpotShift(double Shift) {
        marketSpotShift = Shift;
    }

    public void setMarketVolatilitySpotShift(double Shift) {
        marketVolatilityShift = Shift;
    }

    public double getMarketSpotShift() {
        return marketSpotShift;
    }

    public double getMarketVolatilityShift() {
        return marketVolatilityShift;
    }

    public void setSpotFixed(boolean spotFixed) {
        isSpotFixed = spotFixed;
    }

    public void setVolatilityFixed(boolean volatilityFixed) {
        isVolatilityFixed = volatilityFixed;
    }

    public void setPriceSeries(TimeSeries priceSeries) {
        this.priceSeries = priceSeries;
    }

    public void setReturnSeries(TimeSeries returnSeries) {
        this.returnSeries = returnSeries;
    }

    public void setLogReturnSeries(TimeSeries logReturnSeries) {
        this.logReturnSeries = logReturnSeries;
    }

    public void setHighSeries(TimeSeries highSeries) {
        this.highSeries = highSeries;
    }

    public void setLowSeries(TimeSeries lowSeries) {
        this.lowSeries = lowSeries;
    }

    public void setOpenSeries(TimeSeries openSeries) {
        this.openSeries = openSeries;
    }

    public void setCloseSeries(TimeSeries closeSeries) {
        this.closeSeries = closeSeries;
    }

    public void setVolumeSeries(TimeSeries volumeSeries) {
        this.volumeSeries = volumeSeries;
    }

    public void setVolumeReturnSeries(TimeSeries volumeReturnSeries) {
        this.volumeReturnSeries = volumeReturnSeries;
    }

    public void setVolumeLogReturnSeries(TimeSeries volumeLogReturnSeries) {
        this.volumeLogReturnSeries = volumeLogReturnSeries;
    }

    public void setMarketVolatilityShift(double marketVolatilityShift) {
        this.marketVolatilityShift = marketVolatilityShift;
    }

    public TDay firstDate() {
        return firstDailyDate();
    }

    public TDay lastDate() {
        return lastDailyDate();
    }

    public Daily daily() {
        return daily(-1, false);
    }

    public Daily daily(int index) {
        if (index == -1) {
            Daily daily = daily(this.lastIndex(), false);
            if (daily != null) {
                return daily;
            }
        } else {
            return daily(index, false);
        }
        return null;
    }

    public Daily daily(TDay date) {
        return daily(date, false);
    }

    /**
     * Return flase if data for index have NOTAVAILABLE option
     */
    public boolean dataAvailable(int index) {
        if (index == -1) {
            index = lastIndex();
        }
        Daily daily = daily(index);
        return (daily.getState() != FinConstants.NOTAVAILABLE);
    }

    /**
     * Return flase if data for date have NOTAVAILABLE option
     */
    public boolean dataAvailable(TDay date) {
        Daily daily = dailyarray.get(date);
        if (daily == null) {
            return false;
        }
        return daily.getState() != FinConstants.NOTAVAILABLE;
    }

    /**
     * Return previous index with available historical data Return -1 if there is no such index fDaily points to the
     * previous index
     */
    public int prevIndex(int index) {
        int FirstIndex = firstIndex();
        int PrevIndex = index - 1;
        while (PrevIndex >= FirstIndex) {
            if (daily(PrevIndex).valid()) {
                break;
            } else {
                PrevIndex--;
            }
        }
        if (PrevIndex >= FirstIndex) {
            return PrevIndex;
        } else {
            return -1;
        }
    }

    /**
     * Return next Index with available historical data Return -1 if there is no such Index fDaily points to the next
     * Index data
     */
    public int nextIndex(int Index) {
        int LastIndex = lastIndex();
        int NextIndex = Index + 1;
        while (NextIndex <= LastIndex) {
            if (daily(NextIndex).valid()) {
                break;
            } else {
                NextIndex++;
            }
        }
        if (NextIndex <= LastIndex) {
            return NextIndex;
        } else {
            return -1;
        }
    }

    /**
     * Return previous TDay with available historical data Return zero TDay if there is no such TDay fDaily
     * points to previous TDay data
     */
    public TDay prevDate(TDay date) {
        int PrevIndex = prevIndex(index(date));
        if (PrevIndex == -1) {
            return null;
        } else {
            return date(PrevIndex);
        }
    }

    /**
     * Return next TDay with available historical data Return zero TDay if there is no such TDay fDaily points
     * to next TDay data
     */
    public TDay nextDate(TDay date) {
        int NextIndex = nextIndex(index(date));
        if (NextIndex == -1) {
            return null;
        } else {
            return date(NextIndex);
        }
    }

    /**
     * Return daily data for previous Index
     * <p/>
     * If New flag is set, create and return new daily object. It is webuser responsibility to delete it then.
     * <p/>
     * If New flag is not set, pointer to internally adopted daily object is returned. Note that data fields of this
     * object will be changed during next I/O operation with daily data.
     */
    public Daily prevDaily(int Index) {
        return prevDaily(Index, false);
    }

    public Daily prevDaily(int Index, boolean New) {
        int PrevIndex = prevIndex(Index);
        if (PrevIndex == -1) {
            return null;
        } else {
            return daily(PrevIndex, New);
        }
    }

    public Daily prevDaily(TDay date) {
        return prevDaily(date, false);
    }

    // Return daily data for previous TDay
    // If New flag is set, newInstance and return new daily object.
    // It is webuser responsibility to delete it then.
    // If New flag is not set, pointer to internally adopted
    // daily object is returned. Note that data fields of this
    // object will be changed during next I/O operation with
    // daily data.
    public Daily prevDaily(TDay date, boolean New) {
        TDay prevDate = prevDate(date);
        if (prevDate == null)
            return null;
        else
            return daily(prevDate, New);
    }

    public Daily nextDaily(int Index) {
        return nextDaily(Index, false);
    }

    // Return daily data for next Index
    // If New flag is set, newInstance and return new daily object.
    // It is webuser responsibility to delete it then.
    //
    // If New flag is not set, pointer to internally adopted
    // daily object is returned. Note that data fields of this
    // object will be changed during next I/O operation with
    // daily data.
    public Daily nextDaily(int Index, boolean New) {
        int NextIndex = nextIndex(Index);
        if (NextIndex == -1)
            return null;
        else
            return daily(NextIndex, New);
    }

    /**
     * Return daily data for next TDay
     * <p/>
     * If New flag is set, create and return new daily object.
     * <p/>
     * If New flag is not set, pointer to internally adopted daily object is returned. Note that data fields of this
     * object will be changed during next I/O operation with daily data.
     */
    public Daily nextDaily(TDay date) {
        return nextDaily(date, false);
    }

    public Daily nextDaily(TDay date, boolean New) {
        TDay nextDate = nextDate(date);
        if (nextDate == null)
            return null;
        else
            return daily(nextDate);
    }

    /**
     * Return date, if Index == -1, return date of last Index
     */
    public TDay date() {
        return date(-1);
    }

    public TDay date(int Index) {
        Daily daily = daily(Index);
        if (daily != null)
            return daily.getDailydate();
        return null;
    }

    /**
     * Return premium, if Index == -1, return premium from last Index
     */
    public double price() {
        return price(-1, FinConstants.TYPICALPRICE);
    }

    public double price(int Index) {
        return price(Index, FinConstants.TYPICALPRICE);
    }

    public double price(int Index, int Option) {
        Daily daily = daily(Index);
        if (daily != null)
            return daily.getPrice(Option);
        return -1;
    }

    /**
     * Return low, if Index == -1, return low from last Index
     */
    public double low() {
        return low(-1);
    }

    public double low(int Index) {
        Daily daily = daily(Index);
        if (daily != null) return daily.getLow();
        return -1;
    }

    /**
     * Return high, if Index == -1, return high from last Index
     */
    public double high() {
        return high(-1);
    }

    public double high(int Index) {
        Daily daily = daily(Index);
        if (daily != null) return daily.getHigh();
        return -1;
    }

    /**
     * Return open premium, if Index == -1, return close from last Index
     */
    public double open() {
        return open(-1);
    }

    public double open(int Index) {
        Daily daily = daily(Index);
        if (daily != null) return daily.getOpenprice();
        else
            throw new IllegalArgumentException("getOpenprice No Index " + Index);
    }

    /**
     * Return close premium, if Index == -1, return close from last Index
     *
     * @return
     */
    public double close() {
        return close(-1);
    }

    public double close(int Index) {
        Daily daily = daily(Index);
        if (daily != null)
            return daily.getCloseprice();
        else
            throw new IllegalStateException("getCloseprice No Index " + Index);
    }

    /**
     * Return volume, if Index == -1, return volume from last enter
     */
    public int volume() {
        return getVolume(-1);
    }

    public int getVolume(int Index) {
        Daily daily = daily(Index);
        if (daily != null)
            return daily.getVolume();
        else
            throw new IllegalArgumentException("getVolume No Index " + Index);
    }

    /**
     * getAndRemove return, if Index == -1, getAndRemove return from last Index
     */
    public double freturn() {
        return fReturn(-1);
    }

    public double fReturn(int Index) {
        if (Index == firstIndex())
            return 1;
        if (Index == -1)
            Index = lastIndex();
        Daily daily = daily(Index);
        if (!daily.valid())
            return 1;
        double Price = daily.getPrice();
        int PrevIndex = prevIndex(Index);
        if (PrevIndex != -1)
            return Price / price(PrevIndex);
        else
            return 1;
    }

    public double logReturn() {
        return logReturn(-1);
    }

    public double logReturn(int Index) {
        return Math.log(fReturn(Index));
    }

    public double price(TDay date) {
        return price(date, FinConstants.TYPICALPRICE);
    }

    public double price(TDay date, int Option) {
        return price(index(date), Option);
    }

    public double low(TDay date) {
        return low(index(date));
    }

    public double high(TDay date) {
        return high(index(date));
    }

    public double open(TDay date) {
        return open(index(date));
    }

    public double close(TDay date) {
        return dailyarray.get(date).getCloseprice();
    }

    public double fReturn(TDay date) {
        Daily daily = dailyarray.get(date);
        return fReturn(index(date));
    }

    public int volume(TDay date) {
        return getVolume(dailyarray.get(date).getVolume());
    }

    public double get(TDay date, int Option) {
        return get(index(date), Option);
    }

    public TimeSeries timeSeries(int What, TDay firstDate, TDay lastDate) {
        TimeSeries series = new TimeSeries();
        TDay firstDailyDate = firstDailyDate();
        TDay lastDailyDate = lastDailyDate();
        if (firstDate == null) firstDate = firstDailyDate;
        if (lastDate == null) lastDate = lastDailyDate;
        if (firstDate.isLess(firstDailyDate)) firstDate = firstDailyDate;
        if (lastDate.isGreater(lastDailyDate)) lastDate = lastDailyDate;
        double value;
        for (TDay date = firstDate;
             date.isLessEqual(lastDate);
             date = date.addDays(1)) {
            Daily daily = daily(date);
            if ((daily != null) && daily.valid()) {
                value = daily.get(What);
                if (value != 0) {
                    series.add(daily.getDailydate(), value);
                }
            }
        }
        return series;
    }

    public TimeSeries highSeries(TDay firstDate, TDay lastDate) {
        return timeSeries(FinConstants.HIGH, firstDate, lastDate);
    }

    public TimeSeries lowSeries(TDay firstDate, TDay lastDate) {
        return timeSeries(FinConstants.LOW, firstDate, lastDate);
    }

    public TimeSeries openSeries(TDay firstDate, TDay lastDate) {
        return timeSeries(FinConstants.OPEN, firstDate, lastDate);
    }

    public TimeSeries closeSeries(TDay firstDate, TDay lastDate) {
        return timeSeries(FinConstants.CLOSE, firstDate, lastDate);
    }

    public TimeSeries volumeSeries(TDay firstDate, TDay lastDate) {
        return timeSeries(FinConstants.VOLUME, firstDate, lastDate);
    }

    public TimeSeries highSeries() {
        return timeSeries(FinConstants.HIGH, null, null);
    }

    public TimeSeries lowSeries() {
        return timeSeries(FinConstants.LOW, null, null);
    }

    public TimeSeries openSeries() {
        return timeSeries(FinConstants.OPEN, null, null);
    }

    public TimeSeries closeSeries() {
        return timeSeries(FinConstants.CLOSE, null, null);
    }

    public TimeSeries logAverageSeries() {
        return timeSeries(FinConstants.LOGAVERAGEPRICE, null, null);
    }

    public TimeSeries volumeSeries() {
        return timeSeries(FinConstants.VOLUME, null, null);
    }

    public boolean dailyData(TDay date) {
        // Check if there is daily data available for this TDay
        if (dailyarray == null) {
            return false;
        }
        if (date == null) {
            if (dailyarray.size() == 0) {
                return false;
            } else {
                return true;
            }
        }
        return dataAvailable(date);
    }

    public int firstIndex() {
        return 0;
    }

    public int lastIndex() {
        return dailyarray.size() - 1;
    }

    public int lenght() {
        if (dailyarray != null)
            return dailyarray.size();
        else
            return -1;
    }

    public TDay firstDailyDate() {
        Daily daily = (Daily) dailyarray.values().toArray()[0];
        if (daily != null)
            return daily.getDailydate();
        else
            return null;
    }

    public TDay lastDailyDate() {
        if (dailyarray == null) return null;
        Collection c = dailyarray.values();
        Object[] array = c.toArray();
        Daily last = (Daily) array[c.size() - 1];
        return last.getDailydate();
    }

    public int index(TDay date) {
        if (dailyarray == null) throw new IllegalArgumentException("dailyarray cannot be null");
        if (date == null) throw new IllegalArgumentException("date cannot be null");
        Daily daily = dailyarray.get(date);
        if (daily == null) throw new InstrumentException("no price assoiated to this date");
        return daily.getArrayindex();
    }

    public void add(Daily daily) {
        TDay d = daily.getDailydate();
        dailyarray.put(d, daily);
    }

    public void add(Instrument instrument,
                    int index,
                    TDay date,
                    double high,
                    double low,
                    double open,
                    double close,
                    int volume,
                    int openInterest,
                    int option) {
        Daily daily = new Daily(instrument, index, date, high, low, open, close, volume, openInterest, option);
        add(daily);
    }

    // Return pointer to daily data for index
    // If New flag is set, newInstance and return new daily object.
    public Daily daily(int index, boolean isNew) {
        if (index == -1) index = lastIndex();
        Daily daily = (Daily) dailyarray.values().toArray()[(index)];
        if (daily == null) {
            daily = new Daily();
            daily.setState(FinConstants.NOTAVAILABLE);
            return daily;
        }
        if (isNew)
            return (Daily) daily.clone();
        else
            return daily;
    }

    public Daily daily(TDay date, boolean create) {
        Daily newdaily;
        newdaily = dailyarray.get(date);
        if (newdaily == null) {
            Daily mydaily = new Daily();
            mydaily.setState(FinConstants.NOTAVAILABLE);
            return mydaily;
        }
        if (create) {
            return (Daily) newdaily.clone();
        } else {
            return newdaily;
        }
    }

    public double getSpot() {
        return spot;
    }

    public double getVolatility() {
        return volatility;
    }

    public TDay getTempToday() {
        return tempToday;
    }

    public TimeSeries getReturnSeries() {
        return returnSeries;
    }

    public TimeSeries getLogReturnSeries() {
        return logReturnSeries;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Instrument that = (Instrument) o;

        if (getId() != that.getId()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId() != null ? getId().hashCode() : 0;
        return result;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }
}