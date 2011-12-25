package com.netnumeri.shared.finance.ta;


import java.io.Serializable;

public class Aroon
        implements Serializable {
    public static double aroonUp(double ad[]) {
        if (ad == null)
            throw new IllegalArgumentException("The high array is null.");
        if (ad.length == 0) {
            throw new IllegalArgumentException("The high array cannot be empty.");
        } else {
            double d = ad.length;
            int i = maxArrayPosition(ad);
            return ((d - (double) i) * 100D) / d;
        }
    }

    public static double[] aroonUpOverPeriod(double ad[], int i) {
        if (ad == null)
            throw new IllegalArgumentException("The high array is null.");
        if (ad.length == 0)
            throw new IllegalArgumentException("The high array cannot be empty.");
        if (i <= 0 || i > ad.length)
            throw new IllegalArgumentException("The length of the period over which the indicator is evaluatuate" +
                    "d must be a strictly positive integer which is less than or equa" +
                    "l to the length of the array highs"
            );
        double ad1[] = new double[(ad.length - i) + 1];
        double ad2[] = new double[i];
        for (int j = 0; j < (ad.length - i) + 1; j++) {
            for (int k = 0; k < i; k++)
                ad2[k] = ad[j + k];

            ad1[j] = aroonUp(ad2);
        }

        return ad1;
    }

    public static double aroonDown(double lows[]) {
        if (lows == null)
            throw new IllegalArgumentException("The low array is null.");
        if (lows.length == 0) {
            throw new IllegalArgumentException("The low array is empty.");
        } else {
            double d = lows.length;
            int i = minArrayPosition(lows);
            return ((d - (double) i) * 100D) / d;
        }
    }

    public static double[] aroonDownOverPeriod(double ad[], int i) {
        if (i <= 0 || i > ad.length)
            throw new IllegalArgumentException("The length of the period over which the indicator is evaluated m" +
                    "ust be a strictly positive integer which is less than or equal t" +
                    "o the length of the array lows"
            );
        double ad1[] = new double[(ad.length - i) + 1];
        double ad2[] = new double[i];
        for (int j = 0; j < (ad.length - i) + 1; j++) {
            for (int k = 0; k < i; k++)
                ad2[k] = ad[j + k];

            ad1[j] = aroonDown(ad2);
        }

        return ad1;
    }

    public static double aroonOscillator(double d, double d1) {
        return d1 - d;
    }

    public static double aroonOscillator(double ad[], double ad1[]) {
        if (ad.length != ad1.length)
            throw new IllegalArgumentException("The length of the highs and lows arrays must be the same");
        else
            return aroonUp(ad) - aroonDown(ad1);
    }

    public static double[] aroonOscillatorOverPeriod(double ad[], double ad1[]) {
        if (ad.length != ad1.length)
            throw new IllegalArgumentException("The length of the aroonUpIndicators and aroonDownIndicators seri" +
                    "es must be the same."
            );
        if (ad.length == 0)
            throw new IllegalArgumentException("The aroonUpIndicators and aroonDownIndicators indicators  must n" +
                    "ot be empty."
            );
        double ad2[] = new double[ad.length];
        for (int i = 0; i < ad.length; i++)
            ad2[i] = aroonOscillator(ad[i], ad1[i]);
        return ad2;
    }

    public static double[] aroonOscillatorOverPeriod(double highs[], double lows[], int i) {
        if (highs.length != lows.length)
            throw new IllegalArgumentException("The length of the highs and lows series must be the same.");
        if (highs.length == 0) throw new IllegalArgumentException("The highs and lows arrays  must not be empty.");
        else
            return aroonOscillatorOverPeriod(aroonUpOverPeriod(highs, i), aroonDownOverPeriod(lows, i));
    }

    public static int maxArrayPosition(double ad[])
            throws IllegalArgumentException {
        if (ad.length == 0 || ad == null)
            throw new IllegalArgumentException("The Array cannot be empty.");
        double d = ad[0];
        for (int i = 1; i < ad.length; i++)
            if (ad[i] > d)
                d = ad[i];

        int j = 0;
        for (j = 0; j < ad.length; j++)
            if (d == ad[j])
                break;

        return j;
    }

    public static int minArrayPosition(double ad[])
            throws IllegalArgumentException {
        if (ad.length == 0 || ad == null)
            throw new IllegalArgumentException("The Array cannot be empty.");
        double d = ad[0];
        for (int i = 1; i < ad.length; i++)
            if (ad[i] < d)
                d = ad[i];

        int j = 0;
        for (j = 0; j < ad.length; j++)
            if (d == ad[j])
                break;

        return j;
    }

}

