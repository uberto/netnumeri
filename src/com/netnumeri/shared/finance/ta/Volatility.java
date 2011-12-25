package com.netnumeri.shared.finance.ta;

import java.io.Serializable;

public class Volatility implements Serializable {

    public static double[] chaikinVolatility(double highs[], double lows[], int lma, int noPeriods) {
        validateVolatilityStandardParameters(highs, lows, lma, noPeriods);
        double ad2[] = new double[highs.length];
        double ad3[] = new double[(ad2.length - lma) + 1];
        double ad4[] = new double[ad3.length - noPeriods];
        for (int k = 0; k < highs.length; k++) ad2[k] = highs[k] - lows[k];
        ad3 = exponentiallyMovingAverage(ad2, lma);
        int l = ad3.length - 1;
        for (int i1 = ad4.length - 1; l >= noPeriods; i1--) {
            ad4[i1] = ((ad3[l] - ad3[l - noPeriods]) / ad3[l - noPeriods]) * 100D;
            l--;
        }
        return ad4;
    }

    public static double historicalEstimate(double ad[]) {
        double d = 0.0D;
        double ad1[] = new double[ad.length - 1];
        for (int i = 0; i < ad.length - 1; i++) ad1[i] = ad[i] / ad[i + 1];
        for (int j = 0; j < ad1.length; j++) d += ad1[j];
        double d1 = d / (double) ad1.length;
        double d2 = 0.0D;
        for (int k = 0; k < ad1.length; k++) d2 += Math.pow(ad1[k] - d1, 2D);
        return Math.sqrt((1.0D / ((double) ad1.length - 1.0D)) * d2);
    }

    public static double historicalEstimateStandardError(double d, double d1) {
        return d1 / Math.pow(2D * d, 0.5D);
    }

    public static double historicalEstimateWithDividends(double prices[], double ad1[])
            throws IndicatorsException {
        if (prices.length != ad1.length)
            throw new IndicatorsException("The number of asset prices must be equal to the number of dividends paid.");
        double ad2[] = new double[prices.length];
        for (int i = 0; i < prices.length; i++) {
            ad2[i] = prices[i] + ad1[i];
        }
        return historicalEstimate(ad2);
    }

    public static double returnDuringithPeriod(double d, double d1) {
        return Math.log(d / d1);
    }

    private static double[] exponentiallyMovingAverage(double historical[], int i) {
        validateStandardMAParameters(historical, i);
        double d = 2D / (double) (i + 1);
        double ad1[] = new double[(historical.length - i) + 1];
        for (int j = ad1.length; --j >= 0;) ad1[j] = oneExponentiallyWeightedMovingAverage(historical, d, j, i);
        return ad1;
    }

    private static double oneExponentiallyWeightedMovingAverage(double ad[], double d, int i, int j) {
        double d1 = 0.0D;
        double d2 = 1.0D - d;
        int k = i + j;
        for (int l = 0; --k >= i; l++) d1 += ad[k] * Math.pow(d2, l);

        return d * d1;
    }

    private static void validateStandardMAParameters(double historical[], int lma) {
        if (historical == null || historical.length == 0)
            throw new IllegalArgumentException("The array of historical values must contain at least one element");
        if (lma < 1)
            throw new IllegalArgumentException("The length of the moving average must be greater than one (" + lma + " < 1)");
        if (lma > historical.length)
            throw new IllegalArgumentException("The length of the moving average must not exceed the length of the historical prices period (" + lma + " > " + historical.length + ")");
        else return;
    }

    private static void validateVolatilityStandardParameters(double highs[], double lows[], int lma, int noOfPeriods) {
        if (highs == null || highs.length == 0)
            throw new IllegalArgumentException("The array of high prices must contain at least one element");
        for (int k = 0; k < highs.length; k++)
            if (highs[k] < 0.0D)
                throw new IllegalArgumentException("An element of the highs array is a strictly negative number.");
        if (lows == null || lows.length == 0)
            throw new IllegalArgumentException("The array of low prices must contain at least one element");
        for (int l = 0; l < lows.length; l++)
            if (lows[l] < 0.0D)
                throw new IllegalArgumentException("An element of the lows array is a strictly negative number.");
        if (highs.length != lows.length)
            throw new IllegalArgumentException("The highs and lows arrays must have the same length");
        if (lma < 1)
            throw new IllegalArgumentException("The length of the moving average must be greater than one (" + lma + " < 1)");
        if (lma > lows.length)
            throw new IllegalArgumentException("The length of the moving average must not exceed the length of the lows or highs array");
        if (noOfPeriods < 0)
            throw new IllegalArgumentException("The noOfPeriods parameter must me a strictly positive number (" + noOfPeriods + "< 0)");
        if (noOfPeriods > lows.length)
            throw new IllegalArgumentException("The noOfPeriods value must not exceed the length of the highs or lows array");
        if (noOfPeriods > (lows.length - lma) + 1)
            throw new IllegalArgumentException("The noOfPeriods value must not exceed the length of the calculated exponentially moving average array");
        else return;
    }
}

