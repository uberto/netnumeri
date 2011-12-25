package com.netnumeri.shared.finance.ta;

import java.io.Serializable;

public class MarketStrength
        implements Serializable {
    public static double balanceOfPower(double d, double d1, double d2, double d3) {
        if (d < 0.0D || d1 < 0.0D || d2 < 0.0D || d3 < 0.0D) {
            throw new IllegalArgumentException("All method's parameteres must be positive.");
        } else {
            return (d1 - d) / (d2 - d3);
        }
    }

    public static double[] balanceOfPowerOverPeriod(double ad[], double ad1[], double ad2[], double ad3[]) {
        if (ad2.length != ad3.length || ad3.length != ad1.length || ad1.length != ad.length) {
            throw new IllegalArgumentException("The three arrays high, low, close, open do not have the same length.");
        }
        for (int i = 0; i < ad2.length; i++) {
            if (ad2[i] < 0.0D) {
                throw new IllegalArgumentException("The High array contains an element which is not positive.");
            }
        }

        for (int j = 0; j < ad3.length; j++) {
            if (ad3[j] < 0.0D) {
                throw new IllegalArgumentException("The Low array contains an element which is not positive.");
            }
        }

        for (int k = 0; k < ad1.length; k++) {
            if (ad1[k] < 0.0D) {
                throw new IllegalArgumentException("The Close array contains an element which is not positive.");
            }
        }

        for (int l = 0; l < ad.length; l++) {
            if (ad[l] < 0.0D) {
                throw new IllegalArgumentException("The Open  array contains an element which is not positive.");
            }
        }

        if (ad2 == null) {
            throw new IllegalArgumentException("The high array is null.");
        }
        if (ad2.length == 0) {
            throw new IllegalArgumentException("The high array cannot be empty.");
        }
        if (ad3 == null) {
            throw new IllegalArgumentException("The low array is null.");
        }
        if (ad3.length == 0) {
            throw new IllegalArgumentException("The low array cannot be empty.");
        }
        if (ad1 == null) {
            throw new IllegalArgumentException("The close array is null.");
        }
        if (ad1.length == 0) {
            throw new IllegalArgumentException("The close array cannot be empty.");
        }
        if (ad == null) {
            throw new IllegalArgumentException("The open array is null.");
        }
        if (ad.length == 0) {
            throw new IllegalArgumentException("The open array cannot be empty.");
        }
        double ad4[] = new double[ad2.length];
        for (int i1 = 0; i1 < ad2.length; i1++) {
            ad4[i1] = balanceOfPower(ad[i1], ad1[i1], ad2[i1], ad3[i1]);
        }

        return ad4;
    }

    public static double marketFacilitationIndex(double d, double d1, double d2) {
        if (d < 0.0D || d1 < 0.0D || d2 < 0.0D) {
            throw new IllegalArgumentException("All method's parameters must be positive.");
        } else {
            return ((d - d1) / d2) * 1000000D;
        }
    }

    public static double[] marketFacilitationIndexOverPeriod(double ad[], double ad1[], double ad2[]) {
        if (ad.length != ad1.length || ad1.length != ad2.length) {
            throw new IllegalArgumentException("The three arrays high, low and volume do not have the same length.");
        }
        for (int i = 0; i < ad.length; i++) {
            if (ad[i] < 0.0D) {
                throw new IllegalArgumentException("The High array contains an element which is not positive.");
            }
        }

        for (int j = 0; j < ad1.length; j++) {
            if (ad1[j] < 0.0D) {
                throw new IllegalArgumentException("The Low array contains an element which is not positive.");
            }
        }

        for (int k = 0; k < ad2.length; k++) {
            if (ad2[k] < 0.0D) {
                throw new IllegalArgumentException("The Volume array contains an element which is not positive.");
            }
        }

        if (ad == null) {
            throw new IllegalArgumentException("The high array is null.");
        }
        if (ad.length == 0) {
            throw new IllegalArgumentException("The high array cannot be empty.");
        }
        if (ad1 == null) {
            throw new IllegalArgumentException("The low array is null.");
        }
        if (ad1.length == 0) {
            throw new IllegalArgumentException("The low array cannot be empty.");
        }
        if (ad2 == null) {
            throw new IllegalArgumentException("The volume array is null.");
        }
        if (ad2.length == 0) {
            throw new IllegalArgumentException("The volume array cannot be empty.");
        }
        double ad3[] = new double[ad.length];
        for (int l = 0; l < ad.length; l++) {
            ad3[l] = marketFacilitationIndex(ad[l], ad1[l], ad2[l]);
        }

        return ad3;
    }

}

