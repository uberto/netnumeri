package com.netnumeri.shared.finance.ta;

import java.io.Serializable;

public class AccumulateDistribute implements Serializable {

    public static double accumulationDistribution(double d, double d1, double d2) {
        if (d < 0.0D || d1 < 0.0D || d2 < 0.0D) {
            throw new IllegalArgumentException("Each of the three parameters high, low, volume must be strictly positive.");
        } else {
            return ((d - d1) * d2) / d;
        }
    }

    public static double accumulationDistributionPeriod(double ad[], double ad1[], double ad2[]) {
        if (ad.length != ad1.length || ad1.length != ad2.length) {
            throw new IllegalArgumentException("The three arrays highs, lows, volume must have the same length.");
        }
        for (int i = 0; i < ad.length; i++) {
            if (ad[i] < 0.0D) {
                throw new IllegalArgumentException("An element of the highs array is a strictly negative number.");
            }
        }

        for (int j = 0; j < ad1.length; j++) {
            if (ad1[j] < 0.0D) {
                throw new IllegalArgumentException("An element of the lows array is a strictly negative number.");
            }
        }

        for (int k = 0; k < ad2.length; k++) {
            if (ad2[k] < 0.0D) {
                throw new IllegalArgumentException("An element of the volume array is a strictly negative number.");
            }
        }

        double d = 0.0D;
        double d1 = 0.0D;
        double d2 = 0.0D;
        for (int l = 0; l < ad.length - 1; l++) {
            d = Math.max(ad[l], ad[l + 1]);
        }

        for (int i1 = 0; i1 < ad1.length - 1; i1++) {
            d1 = Math.min(ad1[i1], ad1[i1 + 1]);
        }

        for (int j1 = 0; j1 < ad2.length; j1++) {
            d2 += ad2[j1];
        }

        return accumulationDistribution(d, d1, d2);
    }

    public static double[] accumulateDistributionOverPeriod(double ad[], double ad1[], double ad2[], int i) {
        if (ad.length != ad1.length || ad1.length != ad2.length) {
            throw new IllegalArgumentException("The length of the high, low, and volume series must be the same.");
        }
        if (i == 0 || i > ad.length) {
            throw new IllegalArgumentException("The length of the period over which the indicatoer is evaluation must be a strictly positive integer which is less than or equal to the length of the array low, high, volume given.");
        }
        double ad3[] = new double[(ad1.length - i) + 1];
        double ad4[] = new double[i];
        double ad5[] = new double[i];
        double ad6[] = new double[i];
        for (int j = 0; j < (ad1.length - i) + 1; j++) {
            for (int k = 0; k < i; k++) {
                ad4[k] = ad[j + k];
                ad5[k] = ad1[j + k];
                ad6[k] = ad2[j + k];
            }

            ad3[j] = accumulationDistributionPeriod(ad4, ad5, ad6);
        }

        return ad3;
    }

    public static double accumulationDistributionChange(double d, double d1) {
        return d1 - d;
    }

    public static double accumulationDistributionPeriodChange(double d, double d1) {
        return d1 - d;
    }

    public static double chaikinOscillator(double ad[], double ad1[], double ad2[], double d) {
        if (ad.length != ad1.length || ad1.length != ad2.length)
            throw new IllegalArgumentException("The three arrays high, low, volume must has the same length");
        if (d < 0.0D || d > 1.0D)
            throw new IllegalArgumentException("The value given for the smoothing factor must lie within the closed interval [0,1].");
        if (ad.length < 10)
            throw new IllegalArgumentException("The high parameter must be given at least over the last 10 periods (i.e. must have a length greater than equal to 10).");
        if (ad1.length < 10)
            throw new IllegalArgumentException("The low parameter must be given at least over the last 10 periods (i.e. must have a length greater than equal to 10).");
        if (ad2.length < 10)
            throw new IllegalArgumentException("The volume parameter must be given at least over the last 10 periods (i.e. must have a length greater than equal to 10).");
        double ad3[] = new double[3];
        double ad4[] = new double[3];
        double ad5[] = new double[3];
        for (int i = 0; i < 3; i++) {
            ad3[i] = ad[i];
            ad4[i] = ad1[i];
            ad5[i] = ad2[i];
        }

        double ad6[] = new double[10];
        double ad7[] = new double[10];
        double ad8[] = new double[10];
        for (int j = 0; j < 10; j++) {
            ad6[j] = ad[j];
            ad7[j] = ad1[j];
            ad8[j] = ad2[j];
        }

        double ad9[] = new double[10];
        double ad10[] = new double[3];
        for (int k = 0; k < 3; k++) {
            ad10[k] = accumulationDistribution(ad3[k], ad4[k], ad5[k]);
        }

        for (int l = 0; l < 10; l++) {
            ad9[l] = accumulationDistribution(ad6[l], ad7[l], ad8[l]);
        }

        return exponentiallyWeightedMovingAverage(ad10, d) - exponentiallyWeightedMovingAverage(ad9, d);
    }

    public static double[] chaikinOscillatorOverPeriod(double highs[], double lows[], double volumes[], double smoothing) {
        if (smoothing < 0.0D || smoothing > 1.0D)
            throw new IllegalArgumentException("The value given for the smoothing factor must lie within the closed interval [0,1].");
        if (highs.length < 10)
            throw new IllegalArgumentException("The high parameter must be given at least over the last 10 periods (i.e. must have a length greater than equal to 10).");
        if (lows.length < 10)
            throw new IllegalArgumentException("The low parameter must be given at least over the last 10 periods (i.e. must have a length greater than equal to 10).");
        if (volumes.length < 10)
            throw new IllegalArgumentException("The volume parameter must be given at least over the last 10 periods (i.e. must have a length greater than equal to 10).");
        if (highs.length != lows.length || lows.length != volumes.length)
            throw new IllegalArgumentException("The length of the high, low, and volume series must be the same.");
        byte byte0 = 10;
        if (byte0 == 0 || byte0 > highs.length)
            throw new IllegalArgumentException("The length of the period over which the indicator is evaluation must be a strictly positive integer which is less than or equal to the length of the array low, high, volume given.");
        double ad3[] = new double[(lows.length - byte0) + 1];
        double ad4[] = new double[byte0];
        double ad5[] = new double[byte0];
        double ad6[] = new double[byte0];
        for (int i = 0; i < (lows.length - byte0) + 1; i++) {
            for (int j = 0; j < byte0; j++) {
                ad4[j] = highs[i + j];
                ad5[j] = lows[i + j];
                ad6[j] = volumes[i + j];
            }
            ad3[i] = chaikinOscillator(ad4, ad5, ad6, smoothing);
        }
        return ad3;
    }

    public static double chaikinMoneyFlow(double ad[], double ad1[], double ad2[], double ad3[]) {
        if (ad.length != ad1.length || ad1.length != ad2.length || ad2.length != ad3.length) {
            throw new IllegalArgumentException("The three arrays high, low, close, volume do not have the same length");
        }
        for (int i = 0; i < ad.length; i++) {
            if (ad[i] < 0.0D) {
                throw new IllegalArgumentException("An element of the highs array is a strictly negative number.");
            }
        }

        for (int j = 0; j < ad1.length; j++) {
            if (ad1[j] < 0.0D) {
                throw new IllegalArgumentException("An element of the lows array is a strictly negative number.");
            }
        }

        for (int k = 0; k < ad2.length; k++) {
            if (ad2[k] < 0.0D) {
                throw new IllegalArgumentException("An element of the close array is a strictly negative number.");
            }
        }

        for (int l = 0; l < ad3.length; l++) {
            if (ad3[l] < 0.0D) {
                throw new IllegalArgumentException("An element of the volume array is a strictly negative number.");
            }
        }

        double ad4[] = new double[ad.length];
        for (int i1 = 0; i1 < ad.length; i1++) {
            ad4[i1] = ((ad2[i1] - ad1[i1] - (ad[i1] - ad2[i1])) / (ad[i1] - ad1[i1])) * ad3[i1];
        }

        double d = 0.0D;
        double d1 = 0.0D;
        for (int j1 = 0; j1 < ad.length; j1++) {
            d += ad4[j1];
            d1 += ad3[j1];
        }

        return d / d1;
    }

    public static double[] chaikinMoneyFlowOverPeriod(double ad[], double ad1[], double ad2[], double ad3[], int i) {
        if (ad.length != ad1.length || ad1.length != ad3.length) {
            throw new IllegalArgumentException("The length of the high, low, and volume series must be the same.");
        }
        if (i == 0 || i > ad.length) {
            throw new IllegalArgumentException("The length of the period over which the indicator is evaluation must be a strictly positive integer which is less than or equal to the length of the array low, high, volume given.");
        }
        double ad4[] = new double[(ad1.length - i) + 1];
        double ad5[] = new double[i];
        double ad6[] = new double[i];
        double ad7[] = new double[i];
        double ad8[] = new double[i];
        for (int j = 0; j < (ad1.length - i) + 1; j++) {
            for (int k = 0; k < i; k++) {
                ad5[k] = ad[j + k];
                ad6[k] = ad1[j + k];
                ad7[k] = ad2[j + k];
                ad8[k] = ad3[j + k];
            }

            ad4[j] = chaikinMoneyFlow(ad5, ad6, ad7, ad8);
        }

        return ad4;
    }

    private static double exponentiallyWeightedMovingAverage(double ad[], double d) {
        if (ad.length == 0 || ad == null) throw new IllegalArgumentException("The timeSeries array is empty.");
        if (d < 0.0D || d > 1.0D)
            throw new IllegalArgumentException("The smoothing factor does not lie in the closed range [0.1].");
        double d1 = 0.0D;
        for (int i = 0; i < ad.length; i++) {
            d1 += d * Math.pow(1.0D - d, i) * ad[(-1 + ad.length) - i];
        }
        return d1;
    }
}