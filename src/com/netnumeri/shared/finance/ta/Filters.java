package com.netnumeri.shared.finance.ta;

import java.io.Serializable;

public class Filters
        implements Serializable {
    private static class FiltersImplementation
            implements Serializable {

        public double typicalPrice(double d, double d1, double d2) {
            if (d1 < 0.0D) {
                throw new IllegalArgumentException("The low price must be a positive number.");
            }
            if (d < 0.0D) {
                throw new IllegalArgumentException("The high price must be a positive number.");
            }
            if (d2 < 0.0D) {
                throw new IllegalArgumentException("The closing price must be a positive number.");
            } else {
                return (d + d1 + d2) / 3D;
            }
        }

        public double[] typicalPriceOverPeriod(double ad[], double ad1[], double ad2[]) {
            if (ad.length != ad1.length || ad1.length != ad2.length) {
                throw new IllegalArgumentException("The three arrays highs, lows, closes  must have the same length.");
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
                    throw new IllegalArgumentException("An element of the closes array is a strictly negative number.");
                }
            }

            double ad3[] = new double[ad.length];
            for (int l = 0; l < ad.length; l++) {
                ad3[l] = (ad[l] + ad1[l] + ad2[l]) / 3D;
            }

            return ad3;
        }

        public double medianPrice(double d, double d1) {
            if (d1 < 0.0D) {
                throw new IllegalArgumentException("The low price must be a positive number.");
            }
            if (d < 0.0D) {
                throw new IllegalArgumentException("The high price must be a positive number.");
            } else {
                return (d + d1) / 2D;
            }
        }

        public double[] medianPriceOverPeriod(double ad[], double ad1[]) {
            if (ad.length != ad1.length) {
                throw new IllegalArgumentException("The two  arrays highs, lows must have the same length.");
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

            double ad2[] = new double[ad.length];
            for (int k = 0; k < ad.length; k++) {
                ad2[k] = (ad[k] + ad1[k]) / 2D;
            }

            return ad2;
        }

        public double averagePrice(double d, double d1, double d2, double d3) {
            if (d2 < 0.0D || d3 < 0.0D || d < 0.0D || d1 < 0.0D) {
                throw new IllegalArgumentException("All method's parameteres must be positive.");
            } else {
                return (d + d1 + d2 + d3) / 4D;
            }
        }

        public double[] averagePriceOverPeriod(double ad[], double ad1[], double ad2[], double ad3[]) {
            validateStandardParameters(ad, ad1, ad2, ad3);
            double ad4[] = new double[ad.length];
            for (int i = 0; i < ad.length; i++) {
                ad4[i] = averagePrice(ad[i], ad1[i], ad2[i], ad3[i]);
            }

            return ad4;
        }

        public double priceAction(double d, double d1, double d2, double d3) {
            if (d2 < 0.0D || d3 < 0.0D || d < 0.0D || d1 < 0.0D) {
                throw new IllegalArgumentException("All method's parameteres must be positive.");
            } else {
                return ((d3 - d2) + (d3 - d) + (d3 - d1)) / 2D;
            }
        }

        public double[] priceActionOverPeriod(double ad[], double ad1[], double ad2[], double ad3[]) {
            validateStandardParameters(ad, ad1, ad2, ad3);
            double ad4[] = new double[ad.length];
            for (int i = 0; i < ad.length; i++) {
                ad4[i] = priceAction(ad[i], ad1[i], ad2[i], ad3[i]);
            }

            return ad4;
        }

        public double[] finiteImpulseResponse(double ad[], double ad1[]) {
            if (ad.length - ad1.length < 0) {
                throw new IllegalArgumentException("To calculate this indicator the historicalValues array must have a length greater then the length of the weights array ");
            }
            for (int i = 0; i < ad.length; i++) {
                if (ad[i] < 0.0D) {
                    throw new IllegalArgumentException("The Close array contains an element which is not positive.");
                }
            }

            double ad2[] = new double[(ad.length - ad1.length) + 1];
            double d = 0.0D;
            for (int j = 0; j < ad1.length; j++) {
                d += ad1[j];
            }

            for (int k = ad2.length - 1; k >= 0; k--) {
                ad2[k] = 0.0D;
                for (int l = 0; l < ad1.length; l++) {
                    ad2[k] += ad[k + l] * ad1[l];
                }

                ad2[k] /= d;
            }

            return ad2;
        }

        private void validateStandardParameters(double ad[], double ad1[], double ad2[], double ad3[]) {
            if (ad.length != ad1.length || ad1.length != ad3.length || ad3.length != ad2.length) {
                throw new IllegalArgumentException("The three arrays high, low, close, open do not have the same length.");
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

            for (int k = 0; k < ad3.length; k++) {
                if (ad3[k] < 0.0D) {
                    throw new IllegalArgumentException("The Close array contains an element which is not positive.");
                }
            }

            for (int l = 0; l < ad2.length; l++) {
                if (ad2[l] < 0.0D) {
                    throw new IllegalArgumentException("The Open  array contains an element which is not positive.");
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
            if (ad3 == null) {
                throw new IllegalArgumentException("The close array is null.");
            }
            if (ad3.length == 0) {
                throw new IllegalArgumentException("The close array cannot be empty.");
            }
            if (ad2 == null) {
                throw new IllegalArgumentException("The open array is null.");
            }
            if (ad2.length == 0) {
                throw new IllegalArgumentException("The open array cannot be empty.");
            } else {
                return;
            }
        }

        private FiltersImplementation() {
        }

    }


    public Filters() {
        reference = null;
        reference = new FiltersImplementation();
    }

    public double typicalPrice(double d, double d1, double d2) {
        return reference.typicalPrice(d, d1, d2);
    }

    public double[] typicalPriceOverPeriod(double ad[], double ad1[], double ad2[]) {
        return reference.typicalPriceOverPeriod(ad, ad1, ad2);
    }

    public double medianPrice(double d, double d1) {
        return reference.medianPrice(d, d1);
    }

    public double[] medianPriceOverPeriod(double ad[], double ad1[]) {
        return reference.medianPriceOverPeriod(ad, ad1);
    }

    public double averagePrice(double d, double d1, double d2, double d3) {
        return reference.averagePrice(d, d1, d2, d3);
    }

    public double[] averagePriceOverPeriod(double ad[], double ad1[], double ad2[], double ad3[]) {
        return reference.averagePriceOverPeriod(ad, ad1, ad2, ad3);
    }

    public double priceAction(double d, double d1, double d2, double d3) {
        return reference.priceAction(d, d1, d2, d3);
    }

    public double[] priceActionOverPeriod(double ad[], double ad1[], double ad2[], double ad3[]) {
        return reference.priceActionOverPeriod(ad, ad1, ad2, ad3);
    }

    public double[] finiteImpulseResponse(double ad[], double ad1[]) {
        return reference.finiteImpulseResponse(ad, ad1);
    }

    private FiltersImplementation reference;
}
