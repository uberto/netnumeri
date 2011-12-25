package com.netnumeri.shared.finance.ta;

import java.io.Serializable;

public class Oscillators
        implements Serializable {
    public static double moneyFlowIndex(double ad[], double ad1[], double ad2[], double ad3[]) {
        if (ad == null || ad.length == 0) {
            throw new IllegalArgumentException("The high array is empty.");
        }
        if (ad1 == null || ad1.length == 0) {
            throw new IllegalArgumentException("The low array is empty.");
        }
        if (ad2 == null || ad2.length == 0) {
            throw new IllegalArgumentException("The close array is empty.");
        }
        if (ad3 == null || ad3.length == 0) {
            throw new IllegalArgumentException("The volume array is empty.");
        }
        if (ad.length != ad1.length || ad1.length != ad2.length || ad2.length != ad3.length) {
            throw new IllegalArgumentException("The three arrays do not have the same lengths.");
        }
        for (int i = 0; i < ad.length; i++) {
            if (ad[i] < 0.0D) {
                throw new IllegalArgumentException("The high array contains elements which are not positive.");
            }
            if (ad1[i] < 0.0D) {
                throw new IllegalArgumentException("The low array contains elements which are not positive.");
            }
            if (ad2[i] < 0.0D) {
                throw new IllegalArgumentException("The close array contains elements which are not positive.");
            }
            if (ad3[i] < 0.0D) {
                throw new IllegalArgumentException("The volume array contains elements which are not positive.");
            }
        }

        double ad4[] = new double[ad.length];
        double ad5[] = new double[ad.length];
        for (int j = 0; j < ad.length; j++) {
            ad5[j] = typicalPrice(ad[j], ad1[j], ad2[j]);
        }

        for (int k = 0; k < ad.length; k++) {
            ad4[k] = ad5[k] * ad3[k];
        }

        double d = 0.0D;
        double d1 = 0.0D;
        for (int l = 0; l < ad5.length - 1; l++) {
            if (ad5[l] > ad5[l + 1]) {
                d += ad4[l];
            } else if (ad5[l] < ad5[l + 1]) {
                d1 += ad4[l];
            }
        }

        if (d1 == 0.0D) {
            throw new IllegalArgumentException("The total negative money flow is zero.");
        } else {
            return d / d1;
        }
    }

    public static double[] moneyFlowIndexOverPeriod(double ad[], double ad1[], double ad2[], double ad3[], int i) {
        if (ad.length != ad1.length || ad1.length != ad3.length || ad3.length != ad2.length) {
            throw new IllegalArgumentException("The length of the high, low, close, and volume series must be the same.");
        }
        if (i == 0 || i > ad.length) {
            throw new IllegalArgumentException("The length of the period over which the indicatoer is evaluation must be a strictly positive integer which is less than or equal to the length of the array low, high, volume given.");
        }
        double ad4[] = new double[ad1.length - i];
        double ad5[] = new double[i];
        double ad6[] = new double[i];
        double ad7[] = new double[i];
        double ad8[] = new double[i];
        for (int j = 0; j < ad1.length - i; j++) {
            for (int k = 0; k < i; k++) {
                ad5[k] = ad[j + k];
                ad6[k] = ad1[j + k];
                ad7[k] = ad2[j + k];
                ad8[k] = ad3[j + k];
            }

            ad4[j] = moneyFlowIndex(ad5, ad6, ad7, ad8);
        }

        return ad4;
    }

    public static double rateOfChange(double d, double d1) {
        if (d < 0.0D) {
            throw new IllegalArgumentException("The closing price of an asset must have been positive.");
        }
        if (d1 < 0.0D) {
            throw new IllegalArgumentException("The price n days ago of an asset must have been positive.");
        } else {
            return d / d1;
        }
    }

    public static double[] rateOfChangePeriod(double ad[], int i) {
        if (i > ad.length) {
            throw new IllegalArgumentException("The n-day ago price can not be found in the closingPrices array. The argument nDay must be less than the length of the closingPrices array");
        }
        double ad1[] = new double[ad.length - i];
        for (int j = 0; j < ad.length - i; j++) {
            ad1[j] = rateOfChange(ad[j], ad[j + i]);
        }

        return ad1;
    }

    public static double[] relativeStrengthIndex(double values[], int i) {
        validateStandardParameters(values, i);
        double ad1[] = new double[values.length - i];
        double d = 0.0D;
        double d2 = 0.0D;
        double d4 = 0.0D;
        double d6 = calculateAverageGain(values, ad1.length - 1, i);
        double d7 = calculateAverageLoss(values, ad1.length - 1, i);
        d = d6 / d7;
        ad1[ad1.length - 1] = 100D - 100D / (1.0D + d);
        for (int j = ad1.length - 2; j >= 0; j--) {
            double d3;
            double d5;
            if (values[j] - values[j + 1] > 0.0D) {
                d3 = values[j] - values[j + 1];
                d5 = 0.0D;
            } else {
                d3 = 0.0D;
                d5 = values[j + 1] - values[j];
            }
            d6 = (d6 * 13D + d3) / 14D;
            d7 = (d7 * 13D + d5) / 14D;
            double d1 = d6 / d7;
            ad1[j] = 100D - 100D / (1.0D + d1);
        }

        return ad1;
    }

    public static double typicalPrice(double d, double d1, double d2) {
        return (d + d1 + d2) / 3D;
    }

    private static void validateStandardParameters(double values[], int noPeriods) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("The array of historical values must contain at least one element");
        }
        if (noPeriods < 1) {
            throw new IllegalArgumentException("The number of periods must be greater than one (" + noPeriods + " < 1)");
        }
        if (noPeriods > values.length) {
            throw new IllegalArgumentException("The number of periods must not exceed the length of the historical prices period (" + noPeriods + " > " + values.length + ")");
        } else {
            return;
        }
    }

    private static double calculateAverageGain(double ad[], int i, int j) {
        double d = 0.0D;
        for (int k = i + j; k > i; k--) {
            if (ad[k] - ad[k - 1] < 0.0D) {
                d += ad[k - 1] - ad[k];
            }
        }

        return d / (double) j;
    }

    private static double calculateAverageLoss(double ad[], int i, int j) {
        double d = 0.0D;
        for (int k = i + j; k > i; k--) {
            if (ad[k] - ad[k - 1] > 0.0D) {
                d += ad[k] - ad[k - 1];
            }
        }
        return d / (double) j;
    }

}
