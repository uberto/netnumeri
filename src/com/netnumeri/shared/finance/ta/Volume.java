package com.netnumeri.shared.finance.ta;

import java.io.Serializable;

public class Volume implements Serializable {
    public static double[] negativeVolumeIndex(double close[], double volume[]) {
        validateVolumeStandardParameters(close, volume);
        double ad2[] = new double[volume.length - 1];
        double d = volume[volume.length - 1];
        for (int i = volume.length - 1; --i >= 0;) {
            if (volume[i] >= volume[i + 1]) {
                ad2[i] = d;
            } else {
                ad2[i] = d + ((close[i] - close[i + 1]) / close[i + 1]) * d;
                d = ad2[i];
            }
        }

        return ad2;
    }

    public static double[] positiveVolumeIndex(double close[], double volume[]) {
        validateVolumeStandardParameters(close, volume);
        double ad2[] = new double[volume.length - 1];
        double d = volume[volume.length - 1];
        for (int i = volume.length - 1; --i >= 0;) {
            if (volume[i] <= volume[i + 1]) {
                ad2[i] = d;
            } else {
                ad2[i] = d + ((close[i] - close[i + 1]) / close[i + 1]) * d;
                d = ad2[i];
            }
        }
        return ad2;
    }

    public static double[] onBalanceVolume(double volume[]) {
        if (volume == null || volume.length == 0) {
            throw new IllegalArgumentException("The array of volumes must contain at least one element");
        }
        for (int i = 0; i < volume.length; i++) {
            if (volume[i] < 0.0D) {
                throw new IllegalArgumentException("An element of the volumes array is a strictly negative number.");
            }
        }
        double ad1[] = new double[volume.length];
        double d = volume[volume.length - 1];
        for (int j = volume.length - 1; --j >= 0;) {
            if (volume[j] > volume[j + 1]) {
                ad1[j] = d + volume[j];
                d = ad1[j];
            } else if (volume[j] < volume[j + 1]) {
                ad1[j] = d - volume[j];
                d = ad1[j];
            } else {
                ad1[j] = d;
            }
        }
        return ad1;
    }

    private static void validateVolumeStandardParameters(double closes[], double volume[]) {
        if (closes == null || closes.length == 0) {
            throw new IllegalArgumentException("The array of close prices must contain at least one element");
        }
        for (int i = 0; i < closes.length; i++) {
            if (closes[i] < 0.0D) {
                throw new IllegalArgumentException("An element of the closes array is a strictly negative number.");
            }
        }
        if (volume == null || volume.length == 0) {
            throw new IllegalArgumentException("The array of volumes must contain at least one element");
        }
        for (int j = 0; j < volume.length; j++) {
            if (volume[j] < 0.0D) {
                throw new IllegalArgumentException("An element of the volumes array is a strictly negative number.");
            }
        }

        if (closes.length != volume.length) {
            throw new IllegalArgumentException("The arrays closes and volumes must have the same length");
        } else {
            return;
        }
    }

}

