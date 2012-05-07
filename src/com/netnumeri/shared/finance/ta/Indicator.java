package com.netnumeri.shared.finance.ta;

import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.beans.ParseException;
import com.netnumeri.shared.finance.beans.TimeSeries;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.Daily;
import com.netnumeri.shared.finance.finpojo.Instrument;
import com.netnumeri.shared.finance.finpojo.Parameters;
import com.netnumeri.shared.finance.utils.DateUtils;

public class Indicator extends TimeSeries {
    private int indicator;
    private String name;
    private String title;
    private int type;
    private TimeSeries series;
    private TimeSeries series1;
    private TimeSeries series2;
    private Parameters parm;
    Instrument instrument;
    private static final double DOUBLE100 = 100.0;

    public Indicator() {
    }

    public Indicator(TimeSeries series,
                     String name,
                     double param1,
                     double param2,
                     double param3,
                     double param4,
                     double param5) {
        super(name);
        parm = new Parameters(param1, param2, param3, param4, param5);
        this.series = series;
        initialize();
        build();
    }

    public Indicator(Instrument instrument,
                     String name,
                     double param1,
                     double param2,
                     double param3,
                     double param4,
                     double param5) {
        super(name);
        this.instrument = instrument;
        parm = new Parameters(param1, param2, param3, param4, param5);
        this.series = instrument.getCloseSeries();
        initialize();
        build();
    }

    public Indicator(TimeSeries series1,
                     TimeSeries series2,
                     String name, double param1, double param2,
                     double param3, double param4, double param5) {
        super(name);
        parm = new Parameters(param1, param2, param3, param4, param5);
        this.series1 = series1;
        this.series2 = series2;
        initialize();
        build();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void initialize() {
        if (getName().startsWith("SMA")) {
            indicator = FinConstants.kSMA;
            type = FinConstants.TREND;
            title = "Simple moving average";
            parm.nParameters = 1;
            parm.parameterName[0] = "SMA order";
            parm.parameter[1] = FinConstants.PRICE;
        } else if (getName().startsWith("WMA")) {
            type = FinConstants.TREND;
            indicator = FinConstants.kWMA;
            title = "Weighted moving average";
            parm.nParameters = 1;
            parm.parameterName[0] = "WMA order";
            parm.parameter[1] = FinConstants.PRICE;
        } else if ("EMA".startsWith(getName())) {
            indicator = FinConstants.kEMA;
            type = FinConstants.TREND;
            title = "Exponential moving average";
            parm.parameter[0] = 1;
            parm.parameterName[0] = "EMA order";
            if (parm.parameter[1] == -1) {
                parm.parameter[1] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("PCU")) {
            indicator = FinConstants.kPCU;
            type = FinConstants.TREND;
            title = "Price channel upper";
            parm.nParameters = 2;
            parm.parameterName[0] = "SMA order";
            parm.parameterName[1] = "K";
            if (parm.parameter[2] == -1) {
                parm.parameter[2] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("PCL")) {
            indicator = FinConstants.kPCL;
            type = FinConstants.TREND;
            title = "Price channel lower";
            parm.nParameters = 2;
            parm.parameterName[0] = "SMA order";
            parm.parameterName[1] = "K";
            if (parm.parameter[2] == -1) {
                parm.parameter[2] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("BBU")) {
            indicator = FinConstants.kBBU;
            type = FinConstants.TREND;
            title = "Bollinger band upper";
            parm.nParameters = 2;
            parm.parameterName[0] = "SMA order";
            parm.parameterName[1] = "K";
            if (parm.parameter[2] == -1) {
                parm.parameter[2] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("BBL")) {
            indicator = FinConstants.kBBL;
            type = FinConstants.TREND;
            title = "Bollinger band lower";
            parm.nParameters = 2;
            parm.parameterName[0] = "SMA order";
            parm.parameterName[1] = "K";
            if (parm.parameter[2] == -1) {
                parm.parameter[2] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("VHF")) {
            indicator = FinConstants.kVHF;
            type = FinConstants.OSCILLATOR;
            title = "Vertical-horizontal filter";
            parm.nParameters = 1;
            parm.parameterName[0] = "VHF order";
            if (parm.parameter[1] == -1) {
                parm.parameter[1] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("SMV")) {
            indicator = FinConstants.kSMV;
            type = FinConstants.OSCILLATOR;
            title = "Simpe moving variance";
            parm.nParameters = 1;
            parm.parameterName[0] = "SMV order";
            if (parm.parameter[1] == -1) {
                parm.parameter[1] = FinConstants.RETURN;
            }
        } else if (getName().startsWith("SMD")) {
            indicator = FinConstants.kSMD;
            type = FinConstants.OSCILLATOR;
            title = "Simpe moving deviation";
            parm.nParameters = 1;
            parm.parameterName[0] = "SMD order";
            if (parm.parameter[1] == -1) {
                parm.parameter[1] = FinConstants.RETURN;
            }
        } else if (getName().startsWith("SMC")) {
            indicator = FinConstants.kSMC;
            type = FinConstants.OSCILLATOR;
            title = "Simple moving correlation";
            parm.nParameters = 1;
            parm.parameterName[0] = "SMC order";
            if (parm.parameter[1] == -1) {
                parm.parameter[1] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("MOM")) {
            indicator = FinConstants.kMOM;
            type = FinConstants.OSCILLATOR;
            title = "Momentum oscillator";
            parm.nParameters = 1;
            parm.parameterName[0] = "Momentum order";
            if (parm.parameter[1] == -1) {
                parm.parameter[1] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("MACD")) {
            indicator = FinConstants.kMACD;
            type = FinConstants.OSCILLATOR;
            title = "Moving averages convergence divergence";
            parm.nParameters = 2;
            parm.parameterName[0] = "Short SMA order";
            parm.parameterName[1] = "Long SMA order";
            if (parm.parameter[2] == -1) {
                parm.parameter[2] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("MACDSignal")) {
            indicator = FinConstants.kMACDSignal;
            type = FinConstants.OSCILLATOR;
            title = "MACD Signal";
            parm.nParameters = 1;
            parm.parameterName[0] = "SMA order";
            if (parm.parameter[1] == -1) {
                parm.parameter[1] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("TO")) {
            indicator = FinConstants.kTO;
            type = FinConstants.OSCILLATOR;
            title = "Trend oscillator";
            parm.nParameters = 2;
            parm.parameterName[0] = "Short SMA order";
            parm.parameterName[1] = "Long SMA order";
            if (parm.parameter[2] == -1) {
                parm.parameter[2] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("ROC")) {
            indicator = FinConstants.kROC;
            type = FinConstants.OSCILLATOR;
            title = "Rate of change";
            parm.nParameters = 1;
            parm.parameterName[0] = "ROC order";
            if (parm.parameter[1] == -1) {
                parm.parameter[1] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("RSI")) {
            indicator = FinConstants.kRSI;
            type = FinConstants.OSCILLATOR;
            title = "Relative strength index";
            parm.nParameters = 1;
            parm.parameterName[0] = "RSI order";
            if (parm.parameter[1] == -1) {
                parm.parameter[1] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("MFI")) {
            indicator = FinConstants.kMFI;
            type = FinConstants.OSCILLATOR;
            title = "Money Flow Index";
            parm.nParameters = 1;
            parm.parameterName[0] = "MFI order";
            if (parm.parameter[1] == -1) {
                parm.parameter[1] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("AD")) {
            indicator = FinConstants.kAD;
            type = FinConstants.OSCILLATOR;
            title = "Accumulation Distribution";
            parm.nParameters = 1;
            parm.parameterName[0] = "Accumulation Distribution order";
            if (parm.parameter[1] == -1) {
                parm.parameter[1] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("AD.CHO")) {
            indicator = FinConstants.kADCHO;
            type = FinConstants.kVolumeIndicator;
            title = "Chaikin Oscillator";
            parm.nParameters = 1;
            parm.parameterName[0] = "Smooting";
        } else if (getName().startsWith("AD.CHOMF")) {
            indicator = FinConstants.kADCHOMF;
            type = FinConstants.kOSC;
            title = "Chaikin Money Flow Oscillator";
            parm.nParameters = 1;
            parm.parameterName[0] = "Period";

        } else if (getName().equals("AROON")) {
            indicator = FinConstants.kAroonIndicator;
            type = FinConstants.kOSC;
            title = "Aroon Oscillator";
            parm.nParameters = 1;
            parm.parameterName[0] = "Period";

        } else if (getName().equals("AROON.UP")) {
            indicator = FinConstants.kAroonIndicatorUp;
            type = FinConstants.kOSC;
            title = "Aroon Up";
            parm.nParameters = 1;
            parm.parameterName[0] = "Period";
        } else if (getName().equals("AROON.DOWN")) {
            indicator = FinConstants.kAroonIndicatorDown;
            type = FinConstants.kOSC;
            title = "Aroon Down";
            parm.nParameters = 1;
            parm.parameterName[0] = "Period";
        } else if (getName().equals("TrueRange")) {
            indicator = FinConstants.kTrueRange;
            type = FinConstants.kOSC;
            title = "True Range";
            parm.nParameters = 0;
        } else if (getName().equals("STO.D")) {
            indicator = FinConstants.kDStochastic;
            type = FinConstants.kOSC;
            title = "D stochastic";
            parm.nParameters = 0;
        } else if (getName().equals("STO.D.SMOOTHED")) {
            indicator = FinConstants.kDStochasticSmoothed;
            type = FinConstants.kOSC;
            title = "D smoothed stochastic ";
            parm.nParameters = 0;
        } else if (getName().equals("STO.K")) {
            indicator = FinConstants.kKStochastic;
            type = FinConstants.kOSC;
            title = "K stochastic";
            parm.nParameters = 0;
        } else if (getName().startsWith("CCI")) {
            indicator = FinConstants.kCCI;
            type = FinConstants.OSCILLATOR;
            title = "Commodity channel index";
            parm.nParameters = 1;
            parm.parameterName[0] = "Order";
            if (parm.parameter[1] == -1) {
                parm.parameter[1] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("KRI")) {
            indicator = FinConstants.kKRI;
            type = FinConstants.OSCILLATOR;
            title = "Kairi indicator";
            parm.nParameters = 1;
            parm.parameterName[0] = "KRI order";
            if (parm.parameter[1] == -1) {
                parm.parameter[1] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("OSC")) {
            indicator = FinConstants.kTO;
            type = FinConstants.OSCILLATOR;
            title = "Oscillator";
            parm.nParameters = 2;
            parm.parameterName[0] = "Short SMA order";
            parm.parameterName[1] = "Long SMA order";
            if (parm.parameter[2] == -1) {
                parm.parameter[2] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("PCR")) {
            indicator = FinConstants.kPCR;
            type = FinConstants.OSCILLATOR;
            title = "Percent of resistance";
            parm.nParameters = 1;
            parm.parameterName[0] = "PCR order";
            if (parm.parameter[1] == -1) {
                parm.parameter[1] = (double) FinConstants.PRICE;
            }
        } else if (getName().startsWith("FI")) {
            indicator = FinConstants.kFI;
            type = FinConstants.OSCILLATOR;
            title = "Force index short term";
            parm.nParameters = 1;
            parm.parameterName[0] = "FI order";
            if (parm.parameter[1] == -1) {
                parm.parameter[1] = FinConstants.PRICE;
            }
        } else if (getName().startsWith("%K")) {
            indicator = FinConstants.kK;
            type = FinConstants.OSCILLATOR;
            title = "%K Line";
            parm.nParameters = 1;
            parm.parameterName[0] = "%K order";
            if (parm.parameter[1] == -1) {
                parm.parameter[1] = FinConstants.PRICE;
            }
        }
        // Volume indicators
        else if (getName().startsWith("VA")) {
            indicator = FinConstants.kVA;
            type = FinConstants.kVolumeIndicator;
            title = "Volume accumulation";
            parm.nParameters = 1;
            parm.parameterName[0] = "Volume accumulation";
        } else if (getName().startsWith("VAI")) {
            indicator = FinConstants.kVAI;
            type = FinConstants.kVolumeIndicator;
            title = "Volume accumulation index";
            parm.nParameters = 1;
            parm.parameterName[0] = "VAI period";
        } else if (getName().equals("CHO")) {
            indicator = FinConstants.kCHOVOLATILITY;
            type = FinConstants.kVolumeIndicator;
            title = "Chaikin volatility";
            parm.nParameters = 2;
            parm.parameterName[0] = "Short SMA order";
            parm.parameterName[1] = "Long SMA order";
        }
    }

    public void build() {

        if (series1 != null && series2 != null && indicator == FinConstants.kSMC) {
            TDay first = DateUtils.max(series1.getFirstDate(), series2.getFirstDate());
            TDay last = DateUtils.min(series1.getLastDate(), series2.getLastDate());
            int count = 0;
            int order = (int) parm.parameter[0];
            for (TDay date = first; date.isLessEqual(last); date = date.addDays(1)) {
                add(date, SMC(series1, series2, date, (int) parm.parameter[0]));
            }
        }

        if (series != null) {
            int firstIndex = series.getFirstIndex();
            int index = 0;
            while (index <= getFirstIndicatorIndex() && firstIndex != -1) {
                firstIndex = series.getNextIndex(firstIndex);
                index++;
            }
            if (firstIndex == -1) {
                return;
            }
            int lastIndex = series.getLastIndex();

            if (indicator == FinConstants.kMACD) {
                Indicator shortEMA = new Indicator(series, "SMA", parm.parameter[0], -1, -1, -1, FinConstants.CLOSE);
                Indicator longEMA = new Indicator(series, "SMA", parm.parameter[1], -1, -1, -1, FinConstants.CLOSE);
                for (int i = firstIndex; i <= lastIndex; i++) {
                    if (!series.isEmpty(i)) {
                        TDay date = series.getDate(i);
                        add(date, shortEMA.getData(date) - longEMA.getData(date));
                    }
                }
                return;
            }

//            if (indicator == FinConstants.kMACDSignal) {
//                Indicator shortEMA = new Indicator(series, "SMA", parm.parameter[0], -1, -1, -1, FinConstants.CLOSE);
//                Indicator longEMA = new Indicator(series, "SMA", parm.parameter[1], -1, -1, -1, FinConstants.CLOSE);
//                for (int i = firstIndex; i <= lastIndex; i++) {
//                    if (!series.isEmpty(i)) {
//                        TDay date = series.getDate(i);
//                        add(date, shortEMA.getData(date) - longEMA.getData(date));
//                    }
//                }
//                return;
//            }

            if (indicator == FinConstants.kRSI) {
                double ad[] = Oscillators.relativeStrengthIndex(series.toArray(), (int) parm.parameter[0]);
                copyBackwords(ad);
                return;
            }

            if (indicator == FinConstants.kSMA) {
                double ad[] = MovingAverage.simpleMovingAverage(series.toArray(), (int) parm.parameter[0]);
                copyBackwords(ad);
                return;
            }

            if (indicator == FinConstants.kEMA) {
                double ad[] = MovingAverage.exponentiallyWeightedMovingAverage(series.toArray(), (int) parm.parameter[0], 10);
                copyBackwords(ad);
                return;
            }

            if (indicator == FinConstants.kMFI) {
                double[] ad = Oscillators.moneyFlowIndexOverPeriod(
                        instrument.getHighSeries().toArray(),
                        instrument.getLowSeries().toArray(),
                        instrument.getCloseSeries().toArray(),
                        instrument.getVolumeSeries().toArray(), (int) parm.parameter[0]);

                copyBackwords(ad);
                return;
            }

            if (indicator == FinConstants.kAD) {
                double[] ad = AccumulateDistribute.accumulateDistributionOverPeriod(
                        instrument.getHighSeries().toArray(),
                        instrument.getLowSeries().toArray(),
                        instrument.getVolumeSeries().toArray(), (int) parm.parameter[0]);

                copyBackwords(ad);
                return;
            }

            if (indicator == FinConstants.kADCHO) {

                double[] highs = instrument.getHighSeries().toArray();
                double[] lows = instrument.getLowSeries().toArray();
                double[] vols = instrument.getVolumeSeries().toArray();
                double smoothing = (int) parm.parameter[0];

                double[] ad = AccumulateDistribute.chaikinOscillatorOverPeriod(highs, lows, vols, 0.5);
                copyBackwords(ad);
                return;
            }

            if (indicator == FinConstants.kADCHOMF) {
                double[] highs = instrument.getHighSeries().toArray();
                double[] lows = instrument.getLowSeries().toArray();
                double[] closes = instrument.getCloseSeries().toArray();
                double[] vols = instrument.getVolumeSeries().toArray();
                double period = (int) parm.parameter[0];
                double[] ad = AccumulateDistribute.chaikinMoneyFlowOverPeriod(highs, lows, closes, vols, 14);
                copyBackwords(ad);
                return;
            }

            if (indicator == FinConstants.kCHOVOLATILITY) {
                double[] highs = instrument.getHighSeries().toArray();
                double[] lows = instrument.getLowSeries().toArray();
                double period = (int) parm.parameter[0];
                double[] ad = Volatility.chaikinVolatility(highs, lows, 5, 2);
                copyBackwords(ad);
                return;
            }

            if (indicator == FinConstants.kAroonIndicator) {
                double[] highs = instrument.getHighSeries().toArray();
                double[] lows = instrument.getLowSeries().toArray();
                double[] ad = Aroon.aroonOscillatorOverPeriod(highs, lows, 25);
                copyBackwords(ad);
                return;
            }

            if (indicator == FinConstants.kAroonIndicatorDown) {
                double[] lows = instrument.getLowSeries().toArray();
                double[] ad = Aroon.aroonDownOverPeriod(lows, 25);
                copyBackwords(ad);
                return;
            }

            if (indicator == FinConstants.kAroonIndicatorUp) {
                double[] highs = instrument.getHighSeries().toArray();
                double[] ad = Aroon.aroonUpOverPeriod(highs, 25);
                copyBackwords(ad);
                return;
            }

            if (indicator == FinConstants.kTrueRange) {
                double[] highs = instrument.getHighSeries().toArray();
                double[] lows = instrument.getLowSeries().toArray();
                double[] closes = instrument.getCloseSeries().toArray();
                double[] ar = DirectionalMovementIndicator.trueRangePeriod(highs, lows, closes);
                copyBackwords(MovingAverage.simpleMovingAverage(ar, 14));
                return;
            }

            if (indicator == FinConstants.kDStochastic) {
                double[] highs = instrument.getHighSeries().toArray();
                double[] lows = instrument.getLowSeries().toArray();
                double[] closes = instrument.getCloseSeries().toArray();
                double[] ar = Stochastics.kFastStochasticPeriod(highs, lows, closes, 10);
                copyBackwords(ar);
                return;
            }

            if (indicator == FinConstants.kDStochasticSmoothed) {
                double[] highs = instrument.getHighSeries().toArray();
                double[] lows = instrument.getLowSeries().toArray();
                double[] closes = instrument.getCloseSeries().toArray();
                double[] ar = Stochastics.kFastStochasticPeriod(highs, lows, closes, 10);
                copyBackwords(MovingAverage.simpleMovingAverage(ar, 3));
                return;
            }

            if (indicator == FinConstants.kKStochastic) {
                double[] highs = instrument.getHighSeries().toArray();
                double[] lows = instrument.getLowSeries().toArray();
                double[] closes = instrument.getCloseSeries().toArray();
                double[] ar = Stochastics.dStochastic(highs, lows, closes, 10, 1, 10);
                copyBackwords(ar);
                return;
            }

            if (indicator == FinConstants.kMACDSignal) {

                double[] closes = instrument.getCloseSeries().toArray();
                double[] ma12 = MovingAverage.simpleMovingAverage(closes, 12);
                double[] ma26 = MovingAverage.simpleMovingAverage(closes, 26);

                double[] macdSignal = new double[ma26.length];
                for (int i = 0; i < ma26.length; i++) {
                    macdSignal[i] = ma12[ma26.length - 1 - i] - ma26[ma26.length - 1 - i];
                }

                double[] maSignal = MovingAverage.simpleMovingAverage(macdSignal, 9);
                copyBackwords(maSignal);
                return;
            }

            for (int i = firstIndex; i <= lastIndex; i++) {
                if (!series.isEmpty(i)) {
                    switch (indicator) {
                        case FinConstants.kSMA:
                            double ma = SMA(series, i, (int) parm.parameter[0]);
                            TDay dt = series.getDate(i);
                            add(dt, ma);
                            break;
                        case FinConstants.kEMA:
                            double ema = EMA(series, i, (int) parm.parameter[0]);
                            TDay edt = series.getDate(i);
                            add(edt, ema);
                            break;
                        case FinConstants.kPCL:
                            add(series.getDate(i), PCL(series, i, (int) parm.parameter[0], parm.parameter[1]));
                            break;
                        case FinConstants.kPCU:
                            add(series.getDate(i), PCU(series, i, (int) parm.parameter[0], parm.parameter[1]));
                            break;
                        case FinConstants.kBBL:
                            add(series.getDate(i), BBL(series, i, (int) parm.parameter[0], parm.parameter[1]));
                            break;
                        case FinConstants.kBBU:
                            add(series.getDate(i), BBU(series, i, (int) parm.parameter[0], parm.parameter[1]));
                            break;
                        case FinConstants.kMOM:
                            add(series.getDate(i), MOM(series, i, (int) parm.parameter[0]));
                            break;
                        case FinConstants.kROC:
                            add(series.getDate(i), ROC(series, i, (int) parm.parameter[0]));
                            break;
                        case FinConstants.kRSI:
//                            Oscillators.relativeStrengthIndex(values(series, i, (int) parm.parameter[0]), (int) parm.parameter[0]);
//                            DateValue ad[] = Oscillators.relativeStrengthIndex(series.toDateValue(), (int)parm.parameter[0]);
//                            for (int j = 0; j < ad.length; j++) {
//                                DateValue dateValue = ad[j];
//                                add(dateValue.getDate(), dateValue.getValue());
//                            }
                            add(series.getDate(i), RSI(series, i, (int) parm.parameter[0]));
                            break;
                        case FinConstants.kSMV:
                            add(series.getDate(i), SMV(series, i, (int) parm.parameter[0]));
                            break;
                        case FinConstants.kSMD:
                            add(series.getDate(i), SMD(series, i, (int) parm.parameter[0]));
                            break;
                        case FinConstants.kSMC:
                            add(series.getDate(i), SMC(series,
                                    series1,
                                    series.getDate(i),
                                    (int) parm.parameter[0]));
                            break;
                        case FinConstants.kCCI:
                            add(series.getDate(i), CCI(series, i, (int) parm.parameter[0]));
                            break;

                        case FinConstants.kVHF:
                            throw new IllegalArgumentException("VHF can be calculated on Instruments only and not time series");

                            //   case kK:
                            //       add(series.getDate(i), K(series, i, (int) parm.parameter[0]));
                            //    break;
                        case FinConstants.kTO:
                            add(series.getDate(i), TO(series, i, (int) parm.parameter[0], (int) parm.parameter[1]));
                            break;
                    }
                }
            }
        }
    }

    private void copyBackwords(double[] ad) {
        TDay date = series.getUpperRangeDate();
        for (int i = 0; i < ad.length; i++) {
            double value = ad[ad.length - i - 1];
            add(date, value);
            date = series.getPrevDate(date);
        }
    }

//    public void buildInstrument() {
//
//        if (instrument != null && indicator != kTO) {
//            int firstIndex = instrument.getFirstIndex();
//            int IndexCount = 1;
//            while (IndexCount <= getFirstIndicatorIndex() && firstIndex != -1) {
//                firstIndex = instrument.getNextIndex(firstIndex);
//                IndexCount++;
//            }
//            if (firstIndex == -1) {
//                return;
//            }
//            int lastIndex = instrument.getLastIndex();
//
//            if (indicator == kMACD) {
//                Indicator shortEMA = new Indicator(instrument, "SMA", parm.parameter[0], -1, -1, -1, CLOSE);
//                Indicator longEMA = new Indicator(instrument, "SMA", parm.parameter[1], -1, -1, -1, CLOSE);
//                series = instrument.closeSeries();
//                for (int i = firstIndex; i <= lastIndex; i++) {
//                    if (!series.isEmpty(i)) {
//                        TDay date = series.getDate(i);
//                        add(date, shortEMA.getData(date) - longEMA.getData(date));
//                    }
//                }
//                return;
//            }
//
//            for (int i = firstIndex; i <= lastIndex; i++) {
//                Daily daily = instrument.getDaily(i);
//                if (daily.getState() != NOTAVAILABLE) {
//                    TDay date = daily.getDailydate();
//                    switch (indicator) {
//                        case kSMA:
//                            add(date, SMA(instrument, i, (int) parm.parameter[0], (int) parm.parameter[1]));
//                            break;
//                        case kEMA:
//                            add(date, EMA(instrument, i, (int) parm.parameter[0], (int) parm.parameter[1]));
//                            break;
//                        case kRSI:
//                            add(date, RSI(instrument, i, (int) parm.parameter[0]));
//                            break;
//                        case kPCL:
//                            add(date, PCL(instrument, i, (int) parm.parameter[0], parm.parameter[1], (int) parm.parameter[2]));
//                            break;
//                        case kPCU:
//                            add(date, PCU(instrument, i, (int) parm.parameter[0], parm.parameter[1], (int) parm.parameter[2]));
//                            break;
//                        case kBBL:
//                            add(date, BBL(instrument, i, (int) parm.parameter[0], parm.parameter[1], (int) parm.parameter[2]));
//                            break;
//                        case kBBU:
//                            add(date, BBU(instrument, i, (int) parm.parameter[0], parm.parameter[1], (int) parm.parameter[2]));
//                            break;
//                        case kMOM:
//                            add(date, MOM(instrument, i, (int) parm.parameter[0], (int) parm.parameter[1]));
//                            break;
//                        case kROC:
//                            add(date, ROC(instrument, i, (int) parm.parameter[0], (int) parm.parameter[1]));
//                            break;
//                        case kSMV:
//                            add(date, SMV(instrument, i, (int) parm.parameter[0], (int) parm.parameter[1]));
//                            break;
//                        case kSMD:
//                            add(date, SMD(instrument, i, (int) parm.parameter[0], (int) parm.parameter[1]));
//                            break;
//                        case kVHF:
//                            add(date, VHF(instrument, i, (int) parm.parameter[0]));
//                            break;
//                        case kCCI:
//                            add(date, CCI(instrument, i, (int) parm.parameter[0]));
//                            break;
//                        case kK:
//                            add(date, K(instrument, i, (int) parm.parameter[0]));
//                            break;
///*
//                        case kD:
//                            add(date, D(instrument, i, (int) parm.parameter[0],(int) parm.parameter[1]));
//*/
//                        //         break;
//                        case kTO:
//                            add(date, TO(instrument, i, (int) parm.parameter[0], (int) parm.parameter[1], (int) parm.parameter[2]));
//                            break;
//                    }
//                }
//            }
//        }
//    }

    private Instrument getInstrument() {
        return instrument;
    }

//    public static double RSI(Instrument instrument, int index, int order) {
//        TS series = instrument.closeSeries();
//        return RSI(series, index, order);
//    }
//
//    public static double RSI(Instrument instrument, TDay date, int order) {
//        return RSI(instrument, instrument.getIndex(date), order);
//    }
//
//    public static double RSI(Instrument instrument, String date, int order) {
//        return RSI(instrument, date, order);
//    }

//    public static double SMA(Instrument instrument,
//                             TDay date,
//                             int order,
//                             int priceType) {
//        double SMA = 0;
//        int count = 0;
//        Daily daily = instrument.getDaily(date);
//        if (daily.getState() == NOTAVAILABLE)
//            return 0;
//        int PrevIndex = index;
//        while (PrevIndex != -1 && count < order) {
//            daily = instrument.getDaily(PrevIndex);
//            switch (priceType) {
//                case HIGH:
//                    SMA += daily.getHigh();
//                    break;
//                case LOW:
//                    SMA += daily.getLow();
//                    break;
//                case CLOSE:
//                    SMA += daily.getCloseprice();
//                    break;
//                case PRICE:
//                    SMA += daily.getPrice();
//                    break;
//                case MEDIANPRICE:
//                    SMA += daily.getPrice(MEDIANPRICE);
//                    break;
//                case WEIGHTEDPRICE:
//                    SMA += daily.getPrice(WEIGHTEDPRICE);
//                    break;
//                case VOLUME:
//                    SMA += daily.getVolume();
//                    break;
//                case AVERAGEPRICE:
//                    SMA += daily.getPrice(AVERAGEPRICE);
//                    break;
//                case RETURN:
//                    SMA += instrument.getReturn(PrevIndex);
//                    break;
//            }
//            PrevIndex = instrument.getPrevIndex(PrevIndex);
//            count++;
//        }
//        return SMA / count;
//    }

//    public static double SMA(Instrument instrument, TDay date, int order, int option) {
//        return SMA(instrument, instrument.getIndex(date), order, option);
//    }

//    public static double SMA(Instrument instrument, String date, int order, int option) {
//        return SMA(instrument, date, order, option);
//    }

    public static double SMA(TimeSeries series, int index, int order) {
        double movingaverage = 0;
        int counter = 0;
        if (series.isEmpty(index)) {
            return 0;
        }
        while (index != -1 && counter < order) {
            movingaverage += series.getData(index);
            index = series.getPrevIndex(index);
            counter++;
        }
        return movingaverage / counter;
    }

    public static double SMA(TimeSeries series, TDay date, int order) {
        if (series == null) throw new IllegalArgumentException("series cannot be null");
        return SMA(series, series.getIndex(date), order);
    }

    // date ,must be in format MMddyyyy
    public static double SMA(TimeSeries series, String date, int order) {
        if (series == null) throw new IllegalArgumentException("series cannot be null");
//        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
        TDay dd = new TDay(date);
        return SMA(series, series.getIndex(dd), order);
    }

    // Exponential moving average
//    public double EMA(Instrument instrument, int index, int order, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        double EMA = 0;
//        double Smooth = 2 / ((double) order + 1);
//        Daily daily = instrument.getDaily(index);
//        if (daily.getState() == NOTAVAILABLE) return 0;
//        int CurIndex = instrument.getFirstIndex();
//        daily = instrument.getDaily(CurIndex);
//
//        switch (option) {
//            case HIGH:
//                EMA = daily.getHigh();
//                break;
//            case LOW:
//                EMA = daily.getLow();
//                break;
//            case CLOSE:
//                EMA = daily.getCloseprice();
//                break;
//            case PRICE:
//                EMA = daily.getPrice();
//                break;
//            case MEDIANPRICE:
//                EMA = daily.getPrice(MEDIANPRICE);
//                break;
//            case WEIGHTEDPRICE:
//                EMA = daily.getPrice(WEIGHTEDPRICE);
//                break;
//            case AVERAGEPRICE:
//                EMA = daily.getPrice(AVERAGEPRICE);
//                break;
//            case VOLUME:
//                EMA = daily.getVolume();
//                break;
//            case RETURN:
//                EMA = instrument.getReturn(CurIndex);
//                break;
//        }
//        CurIndex = instrument.getNextIndex(CurIndex);
//
//        while (CurIndex != -1 && CurIndex <= index) {
//            daily = instrument.getDaily(CurIndex);
//            switch (option) {
//                case HIGH:
//                    EMA += Smooth * (daily.getHigh() - EMA);
//                    break;
//                case LOW:
//                    EMA += Smooth * (daily.getLow() - EMA);
//                    break;
//                case CLOSE:
//                    EMA += Smooth * (daily.getCloseprice() - EMA);
//                    break;
//                case PRICE:
//                    EMA += Smooth * (daily.getPrice() - EMA);
//                    break;
//                case MEDIANPRICE:
//                    EMA += Smooth * (daily.getPrice(MEDIANPRICE) - EMA);
//                    break;
//                case WEIGHTEDPRICE:
//                    EMA += Smooth * (daily.getPrice(WEIGHTEDPRICE) - EMA);
//                    break;
//                case AVERAGEPRICE:
//                    EMA += Smooth * (daily.getPrice(AVERAGEPRICE) - EMA);
//                    break;
//                case VOLUME:
//                    EMA += Smooth * (daily.getVolume() - EMA);
//                    break;
//                case RETURN:
//                    EMA += Smooth * (instrument.getReturn(CurIndex) - EMA);
//                    break;
//            }
//            CurIndex = instrument.getNextIndex(CurIndex);
//        }
//        return EMA;
//    }
//
//    public double EMAp(Instrument instrument, int index, int order, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        double EMA = 0;
//
//        if (getNData() == 0) {
//            EMA = EMA(instrument, index, order, option);
//        } else {
//            int PrevIndex = getLastIndex();
//            double Smooth = 2 / ((double) order + 1);
//            EMA = getData(PrevIndex);
//            Daily daily = instrument.getDaily(index);
//            if (daily.getState() == NOTAVAILABLE) return EMA;
//            switch (option) {
//                case HIGH:
//                    EMA += Smooth * (daily.getHigh() - EMA);
//                    break;
//                case LOW:
//                    EMA += Smooth * (daily.getLow() - EMA);
//                    break;
//                case CLOSE:
//                    EMA += Smooth * (daily.getCloseprice() - EMA);
//                    break;
//                case PRICE:
//                    EMA += Smooth * (daily.getPrice() - EMA);
//                    break;
//                case MEDIANPRICE:
//                    EMA += Smooth * (daily.getPrice(MEDIANPRICE) - EMA);
//                    break;
//                case WEIGHTEDPRICE:
//                    EMA += Smooth * (daily.getPrice(WEIGHTEDPRICE) - EMA);
//                    break;
//                case AVERAGEPRICE:
//                    EMA += Smooth * (daily.getPrice(AVERAGEPRICE) - EMA);
//                    break;
//                case VOLUME:
//                    EMA += Smooth * (daily.getVolume() - EMA);
//                    break;
//                case RETURN:
//                    EMA += Smooth * (instrument.getReturn(index) - EMA);
//                    break;
//            }
//        }
//        return EMA;
//    }
//
//    double EMA(Instrument instrument, TDay date, int order, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return EMA(instrument, instrument.getIndex(date), order, option);
//    }
//
//    double EMA(Instrument instrument, String date, int order, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return EMA(instrument, date, order, option);
//    }

    double EMA(TimeSeries series, int index, int order) {
        if (series == null) throw new IllegalArgumentException("series cannot be null");
        double EMA = 0;
        double smooth = 2 / ((double) order + 1);
        if (series.isEmpty(index)) {
            return 0;
        }
        int i = series.getFirstIndex();
        EMA = series.getData(i);
        i = series.getNextIndex(i);
        while (i != -1 && i <= index) {
            EMA += smooth * (series.getData(i) - EMA);
            i = series.getNextIndex(i);
        }
        return EMA;
    }

    double EMAp(TimeSeries series, int index, int order) {
        double EMA = 0;
        if (series.isEmpty(index)) {
            return 0;
        }
        if (getNData() == 0) {
            EMA = EMA(series, index, order);
        } else {
            int PrevIndex = getLastIndex();

            double smooth = 2 / ((double) order + 1);
            EMA = getData(PrevIndex);
            EMA += smooth * (series.getData(index) - EMA);
        }
        return EMA;
    }

    double EMA(TimeSeries series, TDay date, int order) {
        return EMA(series, series.getIndex(date), order);
    }

    double EMA(TimeSeries series, String date, int order) {
        return EMA(series, date, order);
    }


//    public static double SMV(Instrument instrument, int index, int order, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        double SMA = SMA(instrument, index, order, option);
//        double SMV = 0;
//        int Count = 0;
//        Daily daily = new Daily();
//        daily.set(instrument.getDaily(index));
//        if (daily.getState() == NOTAVAILABLE) {
//            return 0;
//        }
//        int PrevIndex = index;
//        while (PrevIndex != -1 && Count < order) {
//            daily = instrument.getDaily(PrevIndex);
//            switch (option) {
//                case HIGH:
//                    SMV += (SMA - daily.getHigh()) * (SMA - daily.getHigh());
//                    break;
//                case LOW:
//                    SMV += (SMA - daily.getLow()) * (SMA - daily.getLow());
//                    break;
//                case CLOSE:
//                    SMV += (SMA - daily.getCloseprice()) * (SMA - daily.getCloseprice());
//                    break;
//                case PRICE:
//                    SMV += (SMA - daily.getPrice()) * (SMA - daily.getPrice());
//                    break;
//                case MEDIANPRICE:
//                    SMV += (SMA - daily.getPrice(MEDIANPRICE)) * (SMA - daily.getPrice(MEDIANPRICE));
//                    break;
//                case AVERAGEPRICE:
//                    SMV += (SMA - daily.getPrice(AVERAGEPRICE)) * (SMA - daily.getPrice(AVERAGEPRICE));
//                    break;
//                case WEIGHTEDPRICE:
//                    SMV += (SMA - daily.getPrice(WEIGHTEDPRICE)) * (SMA - daily.getPrice(WEIGHTEDPRICE));
//                    break;
//                case VOLUME:
//                    SMV += (SMA - daily.getVolume()) * (SMA - daily.getVolume());
//                    break;
//                case RETURN:
//                    SMV += (SMA - instrument.getReturn(PrevIndex)) * (SMA -
//                            instrument.getReturn(PrevIndex));
//                    break;
//            }
//            PrevIndex = instrument.getPrevIndex(PrevIndex);
//            Count++;
//        }
//        return SMV / (Count - 1);
//    }
//
//    public static double SMV(Instrument instrument, TDay date, int order, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return SMV(instrument, instrument.getIndex(date), order, option);
//    }
//
//    public static double SMV(Instrument instrument, String date, int order, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return SMV(instrument, date, order, option);
//    }

    public static double SMV(TimeSeries series, int index, int order) {
        double SMA = SMA(series, index, order);
        double SMV = 0;
        int Count = 0;
        if (series.isEmpty(index)) {
            return 0;
        }
        while (index != -1 && Count < order) {
            SMV += (SMA - series.getData(index)) * (SMA - series.getData(index));
            index = series.getPrevIndex(index);
            Count++;
        }
        return SMV / (Count - 1);
    }

    public static double SMV(TimeSeries series, TDay date, int order) {
        return SMV(series, series.getIndex(date), order);
    }

    public static double SMV(TimeSeries series, String date, int order) {
        return SMV(series, date, order);
    }

    // Simple moving covariance
    public static double SMC(TimeSeries series1, TimeSeries series2, TDay date, int order) {
        TDay firstDate = new TDay();
        TDay lastDate = new TDay();
        firstDate = DateUtils.max(series1.getFirstDate(), series2.getFirstDate());
        lastDate = DateUtils.min(series1.getLastDate(), series2.getLastDate());
        if (date.isGreater(lastDate)) {
            return 0;
        }
        int FirstIndex1 = series1.getFirstIndex();
        int FirstIndex2 = series2.getFirstIndex();
        int Index1 = series1.getIndex(date);
        int Index2 = series2.getIndex(date);
        double Mean1 = 0;
        double Mean2 = 0;
        double SMC = 0;
        int NPairs = 0;
        while (NPairs < order) {
            if (Index1 < FirstIndex1 || Index2 < FirstIndex2) {
                return 0;
            }
            if (!series1.isEmpty(Index1) && !(series2.isEmpty(Index2))) {
                Mean1 += series1.getData(Index1);
                Mean2 += series2.getData(Index2);

                NPairs++;
            }
            Index1--;
            Index2--;
        }
        Mean1 /= order;
        Mean2 /= order;
        Index1 = series1.getIndex(date);
        Index2 = series2.getIndex(date);
        NPairs = 0;
        double StdDev1 = 0;
        double StdDev2 = 0;
        double Data1;
        double Data2;
        while (NPairs < order) {
            if (!series1.isEmpty(Index1) && !(series2.isEmpty(Index2))) {
                Data1 = series1.getData(Index1);
                Data2 = series2.getData(Index2);
                StdDev1 += (Data1 - Mean1) * (Data1 - Mean1);
                StdDev2 += (Data2 - Mean2) * (Data2 - Mean2);
                SMC += (Data1 - Mean1) * (Data2 - Mean2);
                NPairs++;
            }
            Index1--;
            Index2--;
        }
        StdDev1 = Math.sqrt(StdDev1 / (order - 1));
        StdDev2 = Math.sqrt(StdDev2 / (order - 1));
        SMC /= ((order - 1) * StdDev1 * StdDev2);
        return SMC;
    }

    public static double SMC(TimeSeries series1, TimeSeries series2, String date, int order) {
        return SMC(series1, series2, date, order);
    }

//    public static double MOM(Instrument instrument, int index, int order, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        double MOM = 0;
//        int Count = 0;
//        Daily daily = new Daily();
//        daily.set(instrument.getDaily(index));
//        if (daily.getState() == NOTAVAILABLE) {
//            return 0;
//        } else {
//            switch (option) {
//                case HIGH:
//                    MOM = daily.getHigh();
//                    break;
//                case LOW:
//                    MOM = daily.getLow();
//                    break;
//                case CLOSE:
//                    MOM = daily.getCloseprice();
//                    break;
//                case PRICE:
//                    MOM = daily.getPrice();
//                    break;
//                case MEDIANPRICE:
//                    MOM = daily.getPrice(MEDIANPRICE);
//                    break;
//                case WEIGHTEDPRICE:
//                    MOM = daily.getPrice(WEIGHTEDPRICE);
//                    break;
//                case AVERAGEPRICE:
//                    MOM = daily.getPrice(AVERAGEPRICE);
//                    break;
//                case VOLUME:
//                    MOM = daily.getVolume();
//                    break;
//                case RETURN:
//                    MOM = instrument.getReturn(index);
//                    break;
//            }
//        }
//        while (index != -1 && Count < order) {
//            index = instrument.getPrevIndex(index);
//            Count++;
//        }
//        if (index == -1) {
//            return 0;
//        } else {
//            daily.set(instrument.getDaily(index));
//            switch (option) {
//                case HIGH:
//                    MOM -= daily.getHigh();
//                    break;
//                case LOW:
//                    MOM -= daily.getLow();
//                    break;
//                case CLOSE:
//                    MOM -= daily.getCloseprice();
//                    break;
//                case PRICE:
//                    MOM -= daily.getPrice();
//                    break;
//                case MEDIANPRICE:
//                    MOM -= daily.getPrice(MEDIANPRICE);
//                    break;
//                case WEIGHTEDPRICE:
//                    MOM -= daily.getPrice(WEIGHTEDPRICE);
//                    break;
//                case AVERAGEPRICE:
//                    MOM -= daily.getPrice(AVERAGEPRICE);
//                    break;
//                case VOLUME:
//                    MOM -= daily.getVolume();
//                    break;
//                case RETURN:
//                    MOM -= instrument.getReturn(index);
//                    break;
//            }
//        }
//        return MOM;
//    }
//
//    public static double MOM(Instrument instrument, TDay date, int order, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return MOM(instrument, instrument.getIndex(date), order, option);
//    }
//
//    public static double MOM(Instrument instrument, String date, int order, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return MOM(instrument, date, order, option);
//    }

    public static double MOM(TimeSeries series, int index, int order) {
        double MOM = 0;
        int Count = 0;
        if (series.isEmpty(index)) {
            return 0;
        } else {
            MOM = series.getData(index);
        }
        while (index != -1 && Count < order) {
            index = series.getPrevIndex(index);
            Count++;
        }
        if (index == -1) {
            return 0;
        } else {
            MOM -= series.getData(index);
        }
        return MOM;
    }

    public static double MOM(TimeSeries series, TDay date, int order) {
        return MOM(series, series.getIndex(date), order);
    }

    public static double MOM(TimeSeries series, String date, int order) {
        return MOM(series, date, order);
    }

    // Rate of change
//    public static double ROC(Instrument instrument, int index, int order, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        double ROC = 0;
//        int Count = 0;
//        Daily daily = new Daily();
//        daily.set(instrument.getDaily(index));
//        if (daily.getState() == NOTAVAILABLE) {
//            return 0;
//        } else {
//            switch (option) {
//                case HIGH:
//                    ROC = daily.getHigh();
//                    break;
//                case LOW:
//                    ROC = daily.getLow();
//                    break;
//                case CLOSE:
//                    ROC = daily.getCloseprice();
//                    break;
//                case PRICE:
//                    ROC = daily.getPrice();
//                    break;
//                case MEDIANPRICE:
//                    ROC = daily.getPrice(MEDIANPRICE);
//                    break;
//                case WEIGHTEDPRICE:
//                    ROC = daily.getPrice(WEIGHTEDPRICE);
//                    break;
//                case AVERAGEPRICE:
//                    ROC = daily.getPrice(AVERAGEPRICE);
//                    break;
//                case VOLUME:
//                    ROC = daily.getVolume();
//                    break;
//                case RETURN:
//                    ROC = instrument.getReturn(index);
//                    break;
//            }
//        }
//        while (index != -1 && Count < order) {
//            index = instrument.getPrevIndex(index);
//            Count++;
//        }
//        if (index == -1) {
//            return 0;
//        } else {
//            daily.set(instrument.getDaily(index));
//            switch (option) {
//                case HIGH:
//                    ROC /= daily.getHigh();
//                    break;
//                case LOW:
//                    ROC /= daily.getLow();
//                    break;
//                case CLOSE:
//                    ROC /= daily.getCloseprice();
//                    break;
//                case PRICE:
//                    ROC /= daily.getPrice();
//                    break;
//                case MEDIANPRICE:
//                    ROC /= daily.getPrice(MEDIANPRICE);
//                    break;
//                case WEIGHTEDPRICE:
//                    ROC /= daily.getPrice(WEIGHTEDPRICE);
//                    break;
//                case AVERAGEPRICE:
//                    ROC /= daily.getPrice(AVERAGEPRICE);
//                    break;
//                case VOLUME:
//                    ROC /= daily.getVolume();
//                    break;
//                case RETURN:
//                    ROC /= instrument.getReturn(index);
//                    break;
//            }
//        }
//        return ROC;
//    }
//
//    public static double ROC(Instrument instrument, TDay date, int order, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return ROC(instrument, instrument.getIndex(date), order, option);
//    }
//
//    public static double ROC(Instrument instrument, String date, int order, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return ROC(instrument, date, order, option);
//    }

    public static double ROC(TimeSeries series, int index, int order) {
        double ROC = 0;
        int Count = 0;
        if (series.isEmpty(index)) {
            return 0;
        } else {
            ROC = series.getData(index);
        }
        while (index != -1 && Count < order) {
            index = series.getPrevIndex(index);
            Count++;
        }
        if (index == -1) {
            return 0;
        } else {
            ROC /= series.getData(index);
        }
        return ROC;
    }

    public static double ROC(TimeSeries series, TDay date, int order) {
        return ROC(series, series.getIndex(date), order);
    }

    public static double ROC(TimeSeries series, String date, int order) {
        return ROC(series, date, order);
    }

    public DateValue[] values(TimeSeries series, int index, int order) {
        DateValue[] ar = new DateValue[order];
        int Count = 0;
        while (index != -1 && Count < order) {
            index = series.getPrevIndex(index);
            ar[order - Count - 1].setValue(series.getData(index));
            ar[order - Count - 1].setDate(series.getDate(index));
            Count++;
        }
        return ar;
    }

    public static double RSI(TimeSeries series, int index, int order) {
        double previous = 0.0;
        if (series.isEmpty(index)) return 0.0;
        previous = series.getData(index);
        double up = 0;
        double down = 0;
        int count = 0;
        double curr = 0.0;
        while (index != -1 && count < order) {
            index = series.getPrevIndex(index);
            curr = series.getData(index);
            if (curr > previous) {
                up += curr - previous;
            } else {
                down += previous - curr;
            }
            previous = curr;
            count++;
        }
        if (index == -1) {
            return 50.0;
        }
        if (down == 0.0) {
            return DOUBLE100;
        } else {
            return (DOUBLE100 - (DOUBLE100 / (1.0 + (up / down))));
        }
    }

    public static double RSI(TimeSeries series, TDay date, int order) {
        return RSI(series, series.getIndex(date), order);
    }

    public static double RSI(TimeSeries series, String date, int order) {
        return RSI(series, date, order);
    }

    // Vertical-horizontal filter
    public static double VHF(Instrument instrument, int index, int order) {
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        double VHF = 0;
        double H;
        double L;
        double C;
        int count = 0;
        Daily daily = new Daily();
        daily.set(instrument.getDaily(index));
        if (daily.getState() == FinConstants.NOTAVAILABLE) {
            return 0;
        } else {
            C = daily.getCloseprice();
            H = C;
            L = C;
        }
        while (count < order) {
            index = instrument.getPrevIndex(index);
            if (index == -1) {
                return 0;
            }
            daily.set(instrument.getDaily(index));
            VHF += Math.abs(C - daily.getCloseprice());
            C = daily.getCloseprice();
            H = Math.max(H, C);
            L = Math.min(L, C);
            count++;
        }
        return Math.abs(H - L) / VHF;
    }

    public static double VHF(Instrument instrument, TDay date, int order) {
        return VHF(instrument, instrument.getIndex(date), order);
    }

    public static double VHF(Instrument instrument, String date, int order) {
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        return VHF(instrument, date, order);
    }

    // Commodity channel index
//    public static double CCI(Instrument instrument, int index, int order) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        double D = 0;
//        double P;
//        double SMA;
//        Daily daily = new Daily();
//        daily.set(instrument.getDaily(index));
//        if (daily.getState() == NOTAVAILABLE) {
//            return 0;
//        } else {
//            P = daily.getPrice(TYPICALPRICE);
//            SMA = SMA(instrument, index, order, TYPICALPRICE);
//        }
//        int Count = 0;
//        while (Count < order) {
//            index = instrument.getPrevIndex(index);
//            if (index == -1) {
//                return 0;
//            }
//            daily.set(instrument.getDaily(index));
//            D += Math.abs(daily.getPrice(TYPICALPRICE) - SMA);
//            Count++;
//        }
//        D /= Count;
//        return (P - SMA) / (0.015 * D);
//    }
//
//    public static double CCI(Instrument instrument, TDay date, int order) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return CCI(instrument, instrument.getIndex(date), order);
//    }
//
//    public static double CCI(Instrument instrument, String date, int order) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return CCI(instrument, date, order);
//    }


    // Commodity channel index
    public static double CCI(TimeSeries series, int index, int order) {
        double D = 0;
        double P;
        double SMA;
        if (series.isEmpty(index)) {
            return 0;
        } else {
            P = series.getData(index);
            SMA = SMA(series, index, order);
        }
        int Count = 0;
        while (Count < order) {
            index = series.getPrevIndex(index);
            if (index == -1) {
                return 0;
            }
            D += Math.abs(series.getData(index) - SMA);
            Count++;
        }
        D /= Count;
        return (P - SMA) / (0.015 * D);
    }

    public static double CCI(TimeSeries series, TDay date, int order) {
        return CCI(series, date, order);
    }

    public static double CCI(TimeSeries series, String date, int order) {
        return CCI(series, date, order);
    }

    // K Line
//    public static double K(Instrument instrument, int index, int order) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        double L;
//        double H;
//        double C;
//        Daily daily = new Daily();
//        daily.set(instrument.getDaily(index));
//        if (daily.getState() == NOTAVAILABLE) {
//            return 0;
//        } else {
//            C = daily.getCloseprice();
//            L = daily.getLow();
//            H = daily.getHigh();
//        }
//        int Count = 1;
//        while (Count < order) {
//            index = instrument.getPrevIndex(index);
//            if (index == -1) {
//                return 0;
//            }
//            daily.set(instrument.getDaily(index));
//            L = Math.min(daily.getLow(), L);
//            H = Math.max(daily.getHigh(), H);
//            Count++;
//        }
//        return DOUBLE100 * (C - L) / (H - L);
//    }
//
//    public static double K(Instrument instrument, TDay date, int order) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return K(instrument, instrument.getIndex(date), order);
//    }
//
//    public static double K(Instrument instrument, String date, int order) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return K(instrument, date, order);
//    }


    // D Line
/*
    public static double D(Instrument instrument, int index, int order,int order2)
    {
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        return SMA( new Indicator(instrument, "%K", order, -1, -1, -1, -1), index, order2);
    }

    public static double D(Instrument instrument, TDay date, int order,int order2)
    {
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        return D(instrument, instrument.getIndex(date), order,order2);
    }

    public static double D(Instrument instrument, String date, int order,int order2)
    {
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        return D(instrument, date, order,order2);
    }
*/


    //    // Simple moving (standard) deviation
//    public static double SMD(Instrument instrument, int Index, int order, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return Math.sqrt(SMV(instrument, Index, order, option));
//    }
//
//    public static double SMD(Instrument instrument, TDay date, int order, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return Math.sqrt(SMV(instrument, date, order, option));
//    }
//
//    public static double SMD(Instrument instrument, String date, int order, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return Math.sqrt(SMV(instrument, date, order, option));
//    }
//
    public static double SMD(TimeSeries series, int index, int order) {
        return Math.sqrt(SMV(series, index, order));
    }

    public static double SMD(TimeSeries series, TDay date, int order) {
        return Math.sqrt(SMV(series, date, order));
    }

    public static double SMD(TimeSeries series, String date, int order) {
        return Math.sqrt(SMV(series, date, order));
    }

    // Trade oscillator
//    public static double TO(Instrument instrument, int index, int order1, int order2, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return SMA(instrument, index, order1, option) / SMA(instrument, index, order2, option);
//    }
//
//    public static double TO(Instrument instrument, TDay date, int order1, int order2, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");

    //        return SMA(instrument, date, order1, option) / SMA(instrument, date, order2, option);
//    }
//
//    public static double TO(Instrument instrument, String date, int order1, int order2, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return SMA(instrument, date, order1, option) / SMA(instrument, date, order2, option);
//    }
//
    public static double TO(TimeSeries series, int index, int order1, int order2) {
        return SMA(series, index, order1) / SMA(series, index, order2);
    }

    public static double TO(TimeSeries series, TDay date, int order1, int order2) {
        return SMA(series, date, order1) / SMA(series, date, order2);
    }

    public static double TO(TimeSeries series, String date, int order1, int order2) throws ParseException {
        return SMA(series, date, order1) / SMA(series, date, order2);
    }

    // Price channel lower
//    public static double PCL(Instrument instrument, int index, int order, double K, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return (1.0 - K / DOUBLE100) * SMA(instrument, index, order, option);
//    }
//
//    public static double PCL(Instrument instrument, TDay date, int order, double K, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return (1.0 - K / DOUBLE100) * SMA(instrument, date, order, option);
//    }
//
//    public static double PCL(Instrument instrument, String date, int order, double K, int Option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return (1.0 - K / DOUBLE100) * SMA(instrument, date, order, Option);
//    }

    public static double PCL(TimeSeries series, int Index, int Order, double K) {
        return (1.0 - K / DOUBLE100) * SMA(series, Index, Order);
    }

    public static double PCL(TimeSeries series, TDay date, int Order, double K) {
        return (1.0 - K / DOUBLE100) * SMA(series, date, Order);
    }

    public static double PCL(TimeSeries series, String date, int Order, double K) throws ParseException {
        return (1.0 - K / DOUBLE100) * SMA(series, date, Order);
    }

    // Price channel upper
//    public static double PCU(Instrument instrument, int Index, int Order, double K, int Option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return (1.0 + K / DOUBLE100) * SMA(instrument, Index, Order, Option);
//    }
//
//    public static double PCU(Instrument instrument, TDay date, int Order, double K, int Option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return (1.0 + K / DOUBLE100) * SMA(instrument, date, Order, Option);
//    }
//
//    public static double PCU(Instrument instrument, String date, int Order, double K, int Option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return (1.0 + K / DOUBLE100) * SMA(instrument, date, Order, Option);
//    }

    public static double PCU(TimeSeries series, int index, int order, double K) {
        return (1.0 + K / DOUBLE100) * SMA(series, index, order);
    }

    public static double PCU(TimeSeries series, String date, int order, double K) throws ParseException {
        return (1.0 + K / DOUBLE100) * SMA(series, date, order);
    }

    // Bollinger band lower
//    public static double BBL(Instrument instrument, int index, int order, double K, int option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return SMA(instrument, index, order, option) - K * SMD(instrument, index, order, option);
//    }
//
//    public static double BBL(Instrument instrument, TDay date, int Order, double K, int Option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return SMA(instrument, date, Order, Option) - K * SMD(instrument, date, Order, Option);
//    }
//
//    public static double BBL(Instrument instrument, String date, int Order, double K, int Option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return SMA(instrument, date, Order, Option) - K * SMD(instrument, date, Order, Option);
//    }

    public static double BBL(TimeSeries series, int Index, int Order, double K) {
        return SMA(series, Index, Order) - K * SMD(series, Index, Order);
    }

    public static double BBL(TimeSeries series, String date, int Order, double K) throws ParseException {
        return SMA(series, date, Order) - K * SMD(series, date, Order);
    }

//    // Bollinger band upper
//    public static double BBU(Instrument instrument, int Index, int Order, double K, int Option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return SMA(instrument, Index, Order, Option) + K * SMD(instrument, Index, Order, Option);
//    }
//
//    public static double BBU(Instrument instrument, TDay date, int Order, double K, int Option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return SMA(instrument, date, Order, Option) + K * SMD(instrument, date, Order, Option);
//    }
//
//    public static double BBU(Instrument instrument, String date, int Order, double K, int Option) {
//        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
//        return SMA(instrument, date, Order, Option) + K * SMD(instrument, date, Order, Option);
//    }

    public static double BBU(TimeSeries series, int Index, int Order, double K) {
        double d = SMA(series, Index, Order) + K * SMD(series, Index, Order);
        return d;
    }

    public static double BBU(TimeSeries series, String date, int Order, double K) throws ParseException {
        return SMA(series, date, Order) + K * SMD(series, date, Order);
    }

    public int getFirstIndicatorIndex() {
        switch (indicator) {
            case FinConstants.kSMA:
                return (int) parm.parameter[0];
            case FinConstants.kWMA:
                return (int) parm.parameter[0];
            case FinConstants.kEMA:
                return (int) parm.parameter[0];
            case FinConstants.kPCL:
                return (int) parm.parameter[0];
            case FinConstants.kPCU:
                return (int) parm.parameter[0];
            case FinConstants.kBBU:
                return (int) parm.parameter[0];
            case FinConstants.kBBL:
                return (int) parm.parameter[0];
            // Oscillators
            case FinConstants.kVHF:
                return (int) parm.parameter[0];
            case FinConstants.kSMV:
                return (int) parm.parameter[0];
            case FinConstants.kSMD:
                return (int) parm.parameter[0];
            case FinConstants.kMOM:
                return (int) parm.parameter[0];
            case FinConstants.kMACD:
                return (int) Math.max(parm.parameter[0], parm.parameter[1]);
            case FinConstants.kTO:
                return (int) Math.max(parm.parameter[0], parm.parameter[1]);
            case FinConstants.kROC:
                return (int) parm.parameter[0];
            case FinConstants.kRSI:
                return (int) parm.parameter[0] + 1;
            case FinConstants.kCCI:
                return (int) Math.max(parm.parameter[0], parm.parameter[1]);
            case FinConstants.kKRI:
                return (int) parm.parameter[0];
            case FinConstants.kOSC:
                return (int) Math.max(parm.parameter[0], parm.parameter[1]);
            case FinConstants.kPCR:
                return (int) parm.parameter[0];
            case FinConstants.kFI:
                return (int) parm.parameter[0];
            case FinConstants.kK:
                return (int) parm.parameter[0];
            case FinConstants.kVA:
                return 0;
            case FinConstants.kVAI:
                return (int) parm.parameter[0];
            case FinConstants.kCHO:
                return (int) Math.max(parm.parameter[0], parm.parameter[1]);
        }
        return 0;
    }

}


