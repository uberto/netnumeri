package com.netnumeri.shared.finance.ta;


import java.io.Serializable;


public class DirectionalMovementIndicator implements Serializable {

    public static int classifyMovements(double d, double d1, double d2, double d3) {
        if (d < 0.0D) throw new IllegalArgumentException("Todays high (i.e. todaysHigh) must be a positive number.");
        if (d1 < 0.0D) throw new IllegalArgumentException("Todays low (i.e. todaysLow) must be a positive number.");
        if (d3 < 0.0D)
            throw new IllegalArgumentException("Yesterdays low (i.e. yesterdaysLow) must be a positive number.");
        if (d2 < 0.0D)
            throw new IllegalArgumentException("Yesterdays high (i.e. yesterdaysHigh) must be a positive number.");
        if (d > d2 && d1 > d3) return 1;
        if (d < d2 && d1 < d3) return 2;
        if (d > d2 && d1 > d2) return 3;
        if (d < d3 && d1 < d3) return 4;
        return d < d2 || d1 > d3 ? 6 : 5;
    }

    public static double positiveDirectionalMovement(double d, double d1, double d2, double d3) {
        if (d < 0.0D) throw new IllegalArgumentException("Todays high (i.e. todaysHigh) must be a positive number.");
        if (d1 < 0.0D) throw new IllegalArgumentException("Todays low (i.e. todaysLow) must be a positive number.");
        if (d3 < 0.0D)
            throw new IllegalArgumentException("Yesterdays low (i.e. yesterdaysLow) must be a positive number.");
        if (d2 < 0.0D)
            throw new IllegalArgumentException("Yesterdays high (i.e. yesterdaysHigh) must be a positive number.");
        if (d > d2 && d1 > d3) {
            return d - d2;
        }
        if (d > d2 && d1 > d2) {
            return d - d2;
        }
        if (d >= d2 && d1 <= d3 && d - d2 > d3 - d1) {
            return d - d2;
        } else {
            return 0.0D;
        }
    }

    public static double minusDirectionalMovement(double d, double d1, double d2, double d3) {
        if (d < 0.0D) throw new IllegalArgumentException("Todays high (i.e. todaysHigh) must be a positive number.");
        if (d1 < 0.0D) throw new IllegalArgumentException("Todays low (i.e. todaysLow) must be a positive number.");
        if (d3 < 0.0D)
            throw new IllegalArgumentException("Yesterdays low (i.e. yesterdaysLow) must be a positive number.");
        if (d2 < 0.0D)
            throw new IllegalArgumentException("Yesterdays high (i.e. yesterdaysHigh) must be a positive number.");
        if (d < d2 && d1 < d3) {
            return d3 - d1;
        }
        if (d < d3 && d1 < d3) {
            return d3 - d1;
        }
        if (d >= d2 && d1 <= d3 && d3 - d1 > d - d2) {
            return d3 - d1;
        } else {
            return 0.0D;
        }
    }

    public static double trueRange(double d, double d1, double d2) {
        if (d < 0.0D) throw new IllegalArgumentException("Todays high (i.e. todaysHigh) must be a positive number.");
        if (d1 < 0.0D) throw new IllegalArgumentException("Todays low (i.e. todaysLow) must be a positive number.");
        if (d2 < 0.0D)
            throw new IllegalArgumentException("Yesterdays close (i.e. yesterdaysClose) must be a positive number.");
        else {
            double d3 = Math.max(d, d2);
            double d4 = Math.min(d1, d2);
            return d3 - d4;
        }
    }

    public static double[] trueRangePeriod(double highs[], double lows[], double closes[]) {
        if (highs.length != lows.length || lows.length != closes.length)
            throw new IllegalArgumentException("The three arrays highs, lows, closes  must have the same length.");
        for (int i = 0; i < highs.length; i++) {
            if (highs[i] < 0.0D)
                throw new IllegalArgumentException("An element of the highs array is a strictly negative number.");
        }

        for (int j = 0; j < lows.length; j++) {
            if (lows[j] < 0.0D)
                throw new IllegalArgumentException("An element of the lows array is a strictly negative number.");
        }

        for (int k = 0; k < closes.length; k++) {
            if (closes[k] < 0.0D)
                throw new IllegalArgumentException("An element of the closes array is a strictly negative number.");
        }

        double ad3[] = new double[highs.length - 1];
        for (int l = 0; l < highs.length - 1; l++) {
            ad3[l] = trueRange(highs[l], lows[l], closes[l + 1]);
        }

        return ad3;
    }

    public static double averageDailyTrueRange(double ad[]) {
        if (ad == null) throw new IllegalArgumentException("The trueRange cannot be null.");
        for (int i = 0; i < ad.length; i++) {
            if (ad[i] < 0.0D)
                throw new IllegalArgumentException("An element of the trueRange array is strictly negative.");
        }

        double d = 0.0D;
        for (int j = 0; j < ad.length; j++) {
            d += ad[j];
        }

        return d / (double) ad.length;
    }

    public static double plusDirectionalMovement(double d, double d1) {
        if (d < 0.0D) throw new IllegalArgumentException("The true range must be positive.");
        if (d == 0.0D) {
            return 0.0D;
        } else {
            return d1 / d;
        }
    }

    public static double minusDirectionalMovement(double d, double d1) {
        if (d < 0.0D) throw new IllegalArgumentException("The true range must be positive.");
        if (d == 0.0D) {
            return 0.0D;
        } else {
            return d1 / d;
        }
    }

    public static double plusDirectionalMovement(double d, double d1, double d2, double d3, double d4) {
        if (d < 0.0D) throw new IllegalArgumentException("Todays high (i.e. todaysHigh) must be a positive number.");
        if (d1 < 0.0D) throw new IllegalArgumentException("Todays low (i.e. todaysLow) must be a positive number.");
        if (d3 < 0.0D)
            throw new IllegalArgumentException("Yesterdays low (i.e. yesterdaysLow) must be a positive number.");
        if (d2 < 0.0D)
            throw new IllegalArgumentException("Yesterdays high (i.e. yesterdaysHigh) must be a positive number.");
        if (d4 < 0.0D)
            throw new IllegalArgumentException("Yesterdays close (i.e. yesterdaysClose) must be a positive number.");
        double d5 = trueRange(d, d1, d4);
        double d6 = positiveDirectionalMovement(d, d1, d2, d3);
        if (d5 == 0.0D) {
            return 0.0D;
        } else {
            return d6 / d5;
        }
    }

    public static double[] plusDirectionalMovementPeriod(double ad[], double ad1[], double ad2[]) {
        if (ad.length != ad1.length || ad1.length != ad2.length || ad2.length != ad.length) {
            throw new IllegalArgumentException("The arrays high, low and close must have the same length");
        }
        double ad3[] = new double[ad.length - 1];
        for (int i = 0; i < ad.length - 1; i++) {
            ad3[i] = plusDirectionalMovement(ad[i], ad1[i], ad[i + 1], ad1[i + 1], ad2[i + 1]);
        }

        return ad3;
    }

    public static double minusDirectionalMovement(double d, double d1, double d2, double d3, double d4) {
        if (d < 0.0D) throw new IllegalArgumentException("Todays high (i.e. todaysHigh) must be a positive number.");
        if (d1 < 0.0D) throw new IllegalArgumentException("Todays low (i.e. todaysLow) must be a positive number.");
        if (d3 < 0.0D)
            throw new IllegalArgumentException("Yesterdays low (i.e. yesterdaysLow) must be a positive number.");
        if (d2 < 0.0D)
            throw new IllegalArgumentException("Yesterdays high (i.e. yesterdaysHigh) must be a positive number.");
        if (d4 < 0.0D)
            throw new IllegalArgumentException("Yesterdays close (i.e. yesterdaysClose) must be a positive number.");
        double d5 = trueRange(d, d1, d4);
        double d6 = minusDirectionalMovement(d, d1, d2, d3);
        if (d5 == 0.0D) {
            return 0.0D;
        } else {
            return d6 / d5;
        }
    }

    public static double[] minusDirectionalMovementPeriod(double ad[], double ad1[], double ad2[]) {
        if (ad.length != ad1.length || ad1.length != ad2.length || ad2.length != ad.length) {
            throw new IllegalArgumentException("The arrays high, low and close must have the same length");
        }
        double ad3[] = new double[ad.length - 1];
        for (int i = 0; i < ad.length - 1; i++) {
            ad3[i] = minusDirectionalMovement(ad[i], ad1[i], ad[i + 1], ad1[i + 1], ad2[i + 1]);
        }

        return ad3;
    }

    public static double directionalMotion(double d, double d1, double d2, double d3, double d4) {
        if (d < 0.0D) throw new IllegalArgumentException("Todays high (i.e. todaysHigh) must be a positive number.");
        if (d1 < 0.0D) throw new IllegalArgumentException("Todays low (i.e. todaysLow) must be a positive number.");
        if (d3 < 0.0D)
            throw new IllegalArgumentException("Yesterdays low (i.e. yesterdaysLow) must be a positive number.");
        if (d2 < 0.0D)
            throw new IllegalArgumentException("Yesterdays high (i.e. yesterdaysHigh) must be a positive number.");
        if (d4 < 0.0D)
            throw new IllegalArgumentException("Yesterdays close (i.e. yesterdaysClose) must be a positive number.");
        else {
            double d5 = minusDirectionalMovement(d, d1, d2, d3, d4);
            double d6 = plusDirectionalMovement(d, d1, d2, d3, d4);
            return ((d6 - d5) / (d6 + d5)) * 100D;
        }
    }

    public static double directionalMotion(double d, double d1) {
        double d3;
        d3 = d;
        return ((d3 - d1) / (d3 + d1)) * 100D;
    }

    public static int dmiSignal(double ad[], double ad1[], int i)
            throws RuntimeException {
        double d;
        double d1;
        double d2;
        double d3;
        //  int j = ad.length - 1;
        double ad2[] = new double[ad.length];
        double ad3[] = new double[ad1.length];
        for (int k = 0; k <= ad.length - 1; k++) {
            ad2[k] = ad[k];
            ad3[k] = ad1[k];
        }

        double ad4[] = new double[ad.length - 1];
        double ad5[] = new double[ad1.length - 1];
        for (int l = 1; l <= ad.length - 1; l++) {
            ad4[l - 1] = ad[l];
            ad5[l - 1] = ad1[l];
        }

        if (i == 1) {
            d = MovingAverage.simpleMovingAverage(ad2, ad2.length)[0];
            d1 = MovingAverage.simpleMovingAverage(ad3, ad3.length)[0];
            d2 = MovingAverage.simpleMovingAverage(ad4, ad4.length)[0];
            d3 = MovingAverage.simpleMovingAverage(ad5, ad5.length)[0];
        } else if (i == 2) {
            d = MovingAverage.geometricMovingAverage(ad2, ad2.length)[0];
            d1 = MovingAverage.geometricMovingAverage(ad3, ad3.length)[0];
            d2 = MovingAverage.geometricMovingAverage(ad4, ad4.length)[0];
            d3 = MovingAverage.geometricMovingAverage(ad5, ad5.length)[0];
        } else if (i == 3) {
            d = MovingAverage.linearlyWeightedMovingAverage(ad2, ad2.length)[0];
            d1 = MovingAverage.linearlyWeightedMovingAverage(ad3, ad3.length)[0];
            d2 = MovingAverage.linearlyWeightedMovingAverage(ad4, ad4.length)[0];
            d3 = MovingAverage.linearlyWeightedMovingAverage(ad5, ad5.length)[0];
        } else if (i == 4) {
            d = MovingAverage.exponentiallyWeightedMovingAverage(ad2, 0.5D, ad2.length)[0];
            d1 = MovingAverage.exponentiallyWeightedMovingAverage(ad3, 0.5D, ad3.length)[0];
            d2 = MovingAverage.exponentiallyWeightedMovingAverage(ad4, 0.5D, ad4.length)[0];
            d3 = MovingAverage.exponentiallyWeightedMovingAverage(ad5, 0.5D, ad5.length)[0];
        } else {
            throw new IllegalArgumentException("The method parameter must take one of the parameters 1, 2, 3, 4.");
        }
        if (d > d1 && d2 < d3) {
            return 1;
        }
        if (d < d1 && d2 > d3) {
            return -1;
        }
        return 0;
    }

    public static double wilderAverageDirectionalMotion(double high,
                                                        double low,
                                                        double yesterday,
                                                        double yesterdayLow,
                                                        double yesterdayClose,
                                                        double NDaysHigh,
                                                        double NDaysLow,
                                                        double NPlus1High,
                                                        double NPlus1Low,
                                                        double NPlus1Close) {
        if (high < 0.0D) throw new IllegalArgumentException("Todays high (i.e. todaysHigh) must be a positive number.");
        if (low < 0.0D) throw new IllegalArgumentException("Todays low (i.e. todaysLow) must be a positive number.");
        if (yesterdayLow < 0.0D)
            throw new IllegalArgumentException("Yesterdays low (i.e. yesterdaysLow) must be a positive number.");
        if (yesterday < 0.0D)
            throw new IllegalArgumentException("Yesterdays high (i.e. yesterdaysHigh) must be a positive number.");
        if (yesterdayClose < 0.0D)
            throw new IllegalArgumentException("Yesterdays close (i.e. yesterdaysClose) must be a positive number.");
        if (NDaysHigh < 0.0D)
            throw new IllegalArgumentException("N days ago high (i.e. nDaysHigh) must be a positive number.");
        if (NDaysLow < 0.0D)
            throw new IllegalArgumentException("N days ago low (i.e. nDaysLow) must be a positive number.");
        if (NPlus1High < 0.0D)
            throw new IllegalArgumentException("N+1 days ago high (i.e. yesterdaysNDaysHigh) must be a positive number.");
        if (NPlus1Low < 0.0D)
            throw new IllegalArgumentException("N+1 days ago low (i.e. yesterdaysNDaysLow) must be a positive number.");
        if (NPlus1Close < 0.0D)
            throw new IllegalArgumentException("N+1 days ago close (i.e. yesterdaysNDaysClose) must be a positive number.");
        else {
            double d10 = directionalMotion(high, low, yesterday, yesterdayLow, yesterdayClose);
            double d11 = directionalMotion(NDaysHigh, NDaysLow, NPlus1High, NPlus1Low, yesterdayClose);
            return (d10 + d11) / 2D;
        }
    }

    public static double[] twoDoubles2Array(double d, double d1) {
        double ad[] = new double[2];
        ad[0] = d;
        ad[1] = d1;
        return ad;
    }

}


