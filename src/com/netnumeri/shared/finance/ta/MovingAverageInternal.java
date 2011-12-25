package com.netnumeri.shared.finance.ta;


public class MovingAverageInternal {
    public MovingAverageInternal() {
    }

    public double[] simpleMovingAverage(double ad[], int i) {
        validateStandardMAParameters(ad, i);
        double ad1[] = new double[(ad.length - i) + 1];
        for (int j = ad1.length; --j >= 0;) {
            ad1[j] = oneSimpleMovingAverage(ad, j, i);
        }

        return ad1;
    }

    public double[] geometricMovingAverage(double ad[], int i) {
        validateStandardMAParameters(ad, i);
        double ad1[] = new double[(ad.length - i) + 1];
        for (int j = ad1.length; --j >= 0;) {
            ad1[j] = oneGeometricMovingAverage(ad, j, i);
        }

        return ad1;
    }

    public double[] linearlyWeightedMovingAverage(double ad[], int i) {
        validateStandardMAParameters(ad, i);
        double ad1[] = new double[(ad.length - i) + 1];
        for (int j = ad1.length; --j >= 0;) {
            ad1[j] = oneLinearlyWeightedMovingAverage(ad, j, i);
        }

        return ad1;
    }

    public double[] triangularMovingAverage(double ad[], int i) {
        validateStandardMAParameters(ad, i);
        double ad1[] = new double[(ad.length - i) + 1];
        for (int j = ad1.length; --j >= 0;) {
            ad1[j] = oneTriangularMovingAverage(ad, j, i);
        }

        return ad1;
    }

    public double[] exponentiallyWeightedMovingAverage(double ad[], double d, int i) {
        validateStandardMAParameters(ad, i);
        validateEWMASmoothingFactor(d);
        double ad1[] = new double[(ad.length - i) + 1];
        for (int j = ad1.length; --j >= 0;) {
            ad1[j] = oneExponentiallyWeightedMovingAverage(ad, d, j, i);
        }

        return ad1;
    }

    public double[] weightedXDayMovingAverage(double ad[], double ad1[]) {
        validateWMAWeights(ad1);
        int i = ad1.length;
        validateStandardMAParameters(ad, i);
        double d = 0.0D;
        for (int j = 0; j < i; j++) {
            d += ad1[j];
        }

        double ad2[] = new double[(ad.length - i) + 1];
        for (int k = ad2.length; --k >= 0;) {
            double d1 = 0.0D;
            int l = k + i;
            for (int i1 = i - 1; --l >= k; i1--) {
                d1 += ad1[i1] * ad[l];
            }

            ad2[k] = d1 / d;
        }

        return ad2;
    }

    public double[] weightedXDayMovingAverage(double ad[], double ad1[], int i) {
        validateStandardMAParameters(ad, i);
        validateWMAWeights(ad1);
        validateWMAParameters(ad, ad1);
        double ad2[] = new double[(ad.length - i) + 1];
        for (int j = ad2.length; --j >= 0;) {
            double d = 0.0D;
            double d1 = 0.0D;
            for (int k = j + i; --k >= j;) {
                d += ad1[k] * ad[k];
                d1 += ad1[k];
            }

            ad2[j] = d / d1;
        }

        return ad2;
    }

    public double[] medianMovingAverage(double ad[], double ad1[], int i) {
        validateStandardMAParameters(ad, i);
        validateStandardMAParameters(ad1, i);
        int j = Math.min(ad.length, ad1.length);
        double ad2[] = new double[(j - i) + 1];
        for (int k = ad2.length; --k >= 0;) {
            ad2[k] = oneMedianMovingAverage(ad, ad1, k, i);
        }

        return ad2;
    }

    public double[] kairi(double ad[], double ad1[]) {
        validateKairiParameters(ad, ad1);
        double ad2[] = new double[ad.length];
        int i = ad1.length - 1;
        for (int j = ad.length; --j >= 0;) {
            ad2[j] = kairiIndicator(ad[j], ad1[i]);
            i--;
        }

        return ad2;
    }

    public int simpleCrossingSignal(double d, double d1, double d2, double d3) {
        if ((d > d2) & (d1 <= d3)) return 1;
        return !((d < d2) & (d1 >= d3)) ? 0 : -1;
    }

    public double selectElementOfArray(double ad[], int i) {
        return ad[i];
    }

    private double oneSimpleMovingAverage(double ad[], int i, int j) {
        double d = 0.0D;
        for (int k = i + j; --k >= i;) {
            d += ad[k];
        }

        return d / (double) j;
    }

    private double oneGeometricMovingAverage(double ad[], int i, int j) {
        double d = 1.0D;
        for (int k = i + j; --k >= i;) {
            d *= ad[k];
        }

        return Math.pow(d, 1.0D / (double) j);
    }

    private double oneLinearlyWeightedMovingAverage(double ad[], int i, int j) {
        double d = 0.0D;
        int k = (j * (j + 1)) / 2;
        int l = i + j;
        for (int i1 = j; --l >= i; i1--) {
            d += ad[l] * (double) i1;
        }

        return d / (double) k;
    }

    private double oneTriangularMovingAverage(double ad[], int i, int j) {
        double d = 0.0D;
        int k = j / 2;
        int l = k * (k + 1);
        int i1 = 1;
        int j1 = i;
        for (int k1 = (i + j) - 1; i1 <= k; k1--) {
            d += (double) i1 * (ad[j1] + ad[k1]);
            i1++;
            j1++;
        }

        if (j % 2 == 1) {
            d += (double) (k + 1) * ad[i + k];
            l += k + 1;
        }
        return d / (double) l;
    }

    public double oneExponentiallyWeightedMovingAverage(double ad[], double d, int i, int j) {
        double d1 = 0.0D;
        double d2 = 1.0D - d;
        if (d == 0.0D) return ad[0];
        if (d == 1.0D) return ad[i];
        int k = i + j;
        for (int l = 0; --k >= i; l++) d1 += ad[k] * Math.pow(d2, l);
        return d * d1;
    }

    private double oneMedianMovingAverage(double ad[], double ad1[], int i, int j) {
        double d = 0.0D;
        for (int k = i + j; --k >= i;) d += (ad[k] + ad1[k]) / 2D;
        return d / (double) j;
    }

    public double kairiIndicator(double d, double d1) {
        return (d - d1) / d1;
    }

    private void validateStandardMAParameters(double ad[], int i) {
        if (ad == null || ad.length == 0)
            throw new IllegalArgumentException("The array of historical values must contain at least one element");
        if (i < 1)
            throw new IllegalArgumentException("The length of the moving average must be greater than one (" + i + " < 1)");
        if (i > ad.length)
            throw new IllegalArgumentException("The length of the moving average must not exceed the length of the historical prices period (" + i + " > " + ad.length + ")");
        else return;
    }

    private void validateEWMASmoothingFactor(double d) {
        if (d < 0.0D || d > 1.0D)
            throw new IllegalArgumentException("The smoothing factor must not be less than 0 or greater than 1");
        else return;
    }

    private void validateWMAWeights(double ad[]) {
        if (ad == null || ad.length == 0)
            throw new IllegalArgumentException("The array of weights must contain at least one element");
        else return;
    }

    private void validateWMAParameters(double ad[], double ad1[]) {
        if (ad1.length < ad.length)
            throw new IllegalArgumentException("The number of weights must equal or be greater than the number of historical values (" + ad1.length + " < " + ad.length + ")");
        else return;
    }

    private void validateKairiParameters(double ad[], double ad1[]) {
        if (ad1 == null || ad1.length == 0)
            throw new IllegalArgumentException("The array of historical values must contain at least one element");
        if (ad == null || ad.length == 0)
            throw new IllegalArgumentException("The array of moving averages must contain at least one element");
        if (ad1.length < ad.length)
            throw new IllegalArgumentException("The number of moving averages must be less than or equal to the number of historical values (" + ad.length + " > " + ad1.length + ")");
        else return;
    }
}
