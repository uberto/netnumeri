package com.netnumeri.shared.finance.ta;

import java.io.Serializable;

public class Stochastics implements Serializable {

    public static double kFastStochastic(double highs[], double lows[], double order) {
        if (highs == null || highs.length == 0) throw new IllegalArgumentException("The array high is empty.");
        if (lows == null || lows.length == 0) throw new IllegalArgumentException("The array low is empty.");
        if (highs.length != lows.length)
            throw new IllegalArgumentException("The lengths of the Low and High arrays should be identical.");
        double d1 = lows[0];
        for (int i = 1; i < lows.length; i++) {
            if (lows[i] < d1) {
                d1 = lows[i];
            }
        }
        double d2 = highs[0];
        for (int j = 1; j < highs.length; j++) {
            if (highs[j] > d2) {
                d2 = highs[j];
            }
        }
        return ((order - d1) / (d2 - d1)) * 100D;
    }

    public static double[] kFastStochasticPeriod(double highs[], double lows[], double closes[], int i) {
        if (highs == null || highs.length == 0) throw new IllegalArgumentException("The array highs is empty.");
        if (lows == null || lows.length == 0) throw new IllegalArgumentException("The array lows is empty.");
        if (highs.length != lows.length || lows.length != closes.length || closes.length != highs.length) {
            throw new IllegalArgumentException("The lengths of the lows and highs and close arrays should be identical.");
        }
        if (i > highs.length)
            throw new IllegalArgumentException("The noOfPeriods must not exceed the length of the arrays.");
        double ad3[] = new double[i];
        double ad4[] = new double[i];
        double ad5[] = new double[highs.length - i];
        for (int j = 0; j < highs.length - i; j++) {
            int k = j;
            for (int l = 0; k < j + i; l++) {
                ad3[l] = highs[k];
                ad4[l] = lows[k];
                k++;
            }
            ad5[j] = kFastStochastic(ad3, ad4, closes[j]);
        }
        return ad5;
    }

    public static double dStochastic(double fast_stochastic[], int i) {
        double d;
        if (fast_stochastic == null || fast_stochastic.length == 0)
            throw new IllegalArgumentException("The array fast_stochastic is empty.");
        d = (0.0D / 0.0D);
        if (i == 1) d = MovingAverage.simpleMovingAverage(fast_stochastic, fast_stochastic.length)[0];
        else if (i == 2) d = MovingAverage.geometricMovingAverage(fast_stochastic, fast_stochastic.length)[0];
        else if (i == 3) d = MovingAverage.linearlyWeightedMovingAverage(fast_stochastic, fast_stochastic.length)[0];
        else if (i == 4)
            d = MovingAverage.exponentiallyWeightedMovingAverage(fast_stochastic, 0.5D, fast_stochastic.length)[0];
        else throw new IllegalArgumentException("The `method' parameter must be one of 1, 2, 3, 4");
        return d;
    }

    public static double[] dStochastic(double fast_stochastic[], int method, int lenghtOfMA) throws RuntimeException {
        double ad1[];
        if (fast_stochastic == null || fast_stochastic.length == 0)
            throw new IllegalArgumentException("The array fast_stochastic is empty.");
        if (lenghtOfMA > fast_stochastic.length)
            throw new IllegalArgumentException("The lenghtOfMA must not exceed the lengt of fast_stochastic array.");
        ad1 = new double[(fast_stochastic.length - lenghtOfMA) + 1];
        if (method == 1) ad1 = MovingAverage.simpleMovingAverage(fast_stochastic, lenghtOfMA);
        else if (method == 2) ad1 = MovingAverage.geometricMovingAverage(fast_stochastic, lenghtOfMA);
        else if (method == 3) ad1 = MovingAverage.linearlyWeightedMovingAverage(fast_stochastic, lenghtOfMA);
        else if (method == 4) ad1 = MovingAverage.exponentiallyWeightedMovingAverage(fast_stochastic, 0.5D, lenghtOfMA);
        else throw new IllegalArgumentException("The `method' parameter must be one of 1, 2, 3, 4");
        return ad1;
    }

    public static double[] dStochastic(double highs[], double lows[], double close[], int noOfPeriods, int method, int lenghtOfMA)
            throws RuntimeException {
        double ad3[];
        if (highs == null || highs.length == 0) throw new IllegalArgumentException("The array highs is empty.");
        if (lows == null || lows.length == 0) throw new IllegalArgumentException("The array lows is empty.");
        if (highs.length != lows.length || lows.length != close.length || close.length != highs.length)
            throw new IllegalArgumentException("The lengths of the lows and highs and close arrays should be identical.");
        if (noOfPeriods > highs.length)
            throw new IllegalArgumentException("The noOfPeriods must not exceed the length of the arrays.");
        ad3 = new double[highs.length - noOfPeriods];
        ad3 = kFastStochasticPeriod(highs, lows, close, noOfPeriods);
        return dStochastic(ad3, method, lenghtOfMA);
    }

    public static int extremeValueSignal(double extremeLow, double extremeHigh, double d2, double d3) {
        if (extremeLow > 100D || extremeLow < 0.0D)
            throw new IllegalArgumentException("The extremeLow must lie in the interval [0,100].");
        if (extremeHigh > 100D || extremeHigh < 0.0D)
            throw new IllegalArgumentException("The extremeHigh must lie in the interval [0.100].");
        if ((d3 >= extremeHigh) & (d2 < extremeHigh)) return -1;
        return !((d3 <= extremeLow) & (d2 > extremeLow)) ? 0 : 1;
    }

    public static int[] extremeValueKSignalPeriod(double d, double d1, double highs[], double lows[], double ad2[], int i) {
        if (highs == null || highs.length == 0) throw new IllegalArgumentException("The array high is empty.");
        if (lows == null || lows.length == 0) throw new IllegalArgumentException("The array low is empty.");
        if (highs.length != lows.length || lows.length != ad2.length || ad2.length != highs.length)
            throw new IllegalArgumentException("The lengths of the low and high and close arrays should be identical.");
        if (i > highs.length)
            throw new IllegalArgumentException("The noOfPeriods must not exceed the length of the arrays.");
        double ad3[] = new double[highs.length - i];
        ad3 = kFastStochasticPeriod(highs, lows, ad2, i);
        int ai[] = new int[ad3.length - 1];
        for (int j = ad3.length; --j >= 1; ) ai[j - 1] = extremeValueSignal(d, d1, ad3[j], ad3[j - 1]);

        return ai;
    }

    public static int[] extremeValueDSignalPeriod(double d, double d1, double ad[], double ad1[], double ad2[], int i, int j, int k) {
        if (ad == null || ad.length == 0) throw new IllegalArgumentException("The array high is empty.");
        if (ad1 == null || ad1.length == 0) throw new IllegalArgumentException("The array low is empty.");
        if (ad.length != ad1.length || ad1.length != ad2.length || ad2.length != ad.length)
            throw new IllegalArgumentException("The lengths of the low and high and close arrays should be identical.");
        if (i + k > ad.length)
            throw new IllegalArgumentException("The (noOfPeriods + lengthOfMA) must not exceed the length of the arrays.");
        double ad3[] = new double[(ad.length - i - k) + 1];
        ad3 = dStochastic(ad, ad1, ad2, i, j, k);
        int ai[] = new int[ad3.length - 1];
        for (int l = ad3.length; --l >= 1; ) ai[l - 1] = extremeValueSignal(d, d1, ad3[l], ad3[l - 1]);
        return ai;
    }

    public static int crossingSignal(double d, double d1, double d2, double d3) {
        if ((d1 <= d3) & (d > d2)) {
            return 1;
        }
        return !((d1 >= d3) & (d < d2)) ? 0 : -1;
    }

    public static int[] crossingSignalPeriod(double ad[], double ad1[]) {
        if (ad == null || ad.length < 2)
            throw new IllegalArgumentException("The array firstStochastic does not contain sufficient elements for calculating CrossingSignal. There must be at least two elements.");
        if (ad1 == null || ad1.length < 2)
            throw new IllegalArgumentException("The array secondStochastic does not contain sufficient elements for calculating CrossingSignal. There must be at least two elements.");
        if (ad1.length > ad.length)
            throw new IllegalArgumentException("The length of secondStochastic array must be less then or equal to length of firstStochastic array.");
        int ai[] = new int[ad1.length - 1];
        for (int i = ad1.length; --i >= 1; ) ai[i - 1] = crossingSignal(ad[i], ad[i - 1], ad1[i], ad1[i - 1]);
        return ai;
    }

    public static int[] crossingSignal(double highs[], double lows[], double closes[], int noOfPeriods, int j, int lengthOfMA) {
        if (highs == null || highs.length == 0) throw new IllegalArgumentException("The array high is empty.");
        if (lows == null || lows.length == 0) throw new IllegalArgumentException("The array low is empty.");
        if (highs.length != lows.length || lows.length != closes.length || closes.length != highs.length) {
            throw new IllegalArgumentException("The lengths of the low and high and close arrays should be identical.");
        }
        if (noOfPeriods + lengthOfMA + 1 > highs.length) {
            throw new IllegalArgumentException("The (noOfPeriods + lengthOfMA + 1) must not exceed the length of the arrays.");
        } else {
            double ad3[] = new double[highs.length - noOfPeriods];
            double ad4[] = new double[highs.length - noOfPeriods - lengthOfMA - 1];
            ad3 = kFastStochasticPeriod(highs, lows, closes, noOfPeriods);
            ad4 = dStochastic(highs, lows, closes, noOfPeriods, j, lengthOfMA);
            int ai[] = new int[ad4.length - 1];
            ai = crossingSignalPeriod(ad3, ad4);
            return ai;
        }
    }

    public static int crossingExtremeSignal(double d, double d1, double d2, double d3, double d4, double d5) {
        if (d > 100D || d < 0.0D)
            throw new IllegalArgumentException("The extremeLow must lie in the interval [0,100].");
        if (d1 > 100D || d1 < 0.0D)
            throw new IllegalArgumentException("The extremeHigh must lie in the interval [0.100].");
        if ((d5 < d3) & (d4 > d2) & (d5 > d1) & (d3 > d1) & (d4 > d1) & (d2 > d1)) return -1;
        return !((d5 > d3) & (d4 < d2) & (d5 < d) & (d3 < d) & (d4 < d) & (d2 < d)) ? 0 : 1;
    }

    public static int[] crossingExtremeSignalPeriod(double d, double d1, double ad[], double ad1[], double ad2[],
                                                    int i, int j, int k) {
        if (ad == null || ad.length == 0) throw new IllegalArgumentException("The array high is empty.");
        if (ad1 == null || ad1.length == 0) throw new IllegalArgumentException("The array low is empty.");
        if (ad.length != ad1.length || ad1.length != ad2.length || ad2.length != ad.length)
            throw new IllegalArgumentException("The lengths of the low and high and close arrays should be identical.");
        if (i + k + 1 > ad.length)
            throw new IllegalArgumentException("The (noOfPeriods + lengthOfMA + 1) must not exceed the length of the arrays.");
        double ad3[] = new double[ad.length - i];
        double ad4[] = new double[ad.length - i - k - 1];
        int ai[] = new int[ad4.length - 1];
        for (int l = ad4.length; --l >= 1; ) {
            ai[l - 1] = crossingExtremeSignal(d, d1, ad3[l], ad3[l - 1], ad4[l], ad4[l - 1]);
        }
        return ai;
    }
}
