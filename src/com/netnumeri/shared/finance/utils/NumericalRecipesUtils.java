package com.netnumeri.shared.finance.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayString;
import com.netnumeri.shared.finance.beans.Moment;
import com.netnumeri.shared.finance.beans.NRError;
import com.netnumeri.shared.finance.beans.ParseException;
import com.netnumeri.shared.finance.date.SimpleDateFormat;
import com.netnumeri.shared.finance.date.TDay;

import java.util.Date;


public class NumericalRecipesUtils {

//    private static native JsArrayString regExpMatch(String regEx, String target) /*-{
//        var re = new RegExp();
//        return re.compile(regEx).exec(target);
//    }-*/;
//
//    public static Long getPortfolioLongId(String portfolioId) {
//        JsArrayString match = regExpMatch("\\/(\\d+)$", portfolioId);
//        if (match.length() > 1) {
//            portfolioId = match.get(1);
//        } else {
//            GWT.log("Error parsing the portfolio id.", null);
//        }
//        return Long.parseLong(portfolioId);
//    }

    public static int[] ivector(int startIndex, int size) {
        int[] v = new int[startIndex + size];
        for (int i = 0; i < v.length; i++) {
            v[i] = 0;
        }
        return (v);
    }

    public static float[] vector(int startIndex, int size) {
        float[] v = new float[startIndex + size];
        for (int i = 0; i < v.length; i++) {
            v[i] = 0.0F;
        }
        return (v);
    }

    public static TDay[] datesvector(int startIndex, int size) {
        TDay[] v = new TDay[startIndex + size];
        for (int i = 0; i < v.length; i++) {
            v[i] = null;
        }
        return (v);
    }

    public static int[] intvector(int startIndex, int size) {
        int[] v = new int[startIndex + size];
        for (int i = 0; i < v.length; i++) {
            v[i] = 0;
        }
        return (v);
    }

    public static float[][] matrix(int start1, int size1, int start2, int size2) {
        float[][] v = new float[start1 + size1][];

        for (int i = 0; i < v.length; i++) {
            v[i] = vector(start2, size2);
        }
        return (v);
    }

    public static void swap(float[] data, int a, int b) {
        float tempr = data[a];
        data[a] = data[b];
        data[b] = tempr;
    }

    public static float sign(float a, float b) {
        return (b >= 0.0F ? Math.abs(a) : -Math.abs(a));
    }

    public static void nrerror(String message)
            throws NRError {
        throw (new NRError(message));
    }

    public static int imin(int x, int y) {
        return (x < y ? x : y);
    }

    public static float max(float x, float y) {
        return (x > y ? x : y);
    }


    public static float[] sumSeries(float[] s1, float[] s2) {
        int size = s1.length - 1;
        float[] sum = NumericalRecipesUtils.vector(1, size);
        for (int i = 1; i <= size; i++)
            sum[i] = s1[i] + s2[i];
        return sum;
    }

    public static float[] diffSeries(float[] s1, float[] s2) {
        int size = s1.length - 1;
        float[] diff = NumericalRecipesUtils.vector(1, size);
        for (int i = 1; i <= size; i++)
            diff[i] = s1[i] - s2[i];
        return diff;
    }

    public static float[] exponential(float[] s1) {
        int size = s1.length - 1;
        float[] sum = NumericalRecipesUtils.vector(1, size);
        for (int i = 1; i <= size; i++)
            sum[i] = (float) Math.exp(s1[i]);
        return sum;
    }

    public static float[] augmentByMovingAverage(float[] s1) throws NRError {
        float[] aug = NumericalRecipesUtils.vector(1, size(s1));
        float[] madata = NumericalRecipesUtils.vector(1, 10);
        Moment.MomentResult ma = null;
        for (int i = 1; i < size(s1); i++)
            aug[i] = s1[i + 1];
        float add = 0;
        for (int j = 1; j <= 10; j++) {
            float val = s1[size(s1) - 10 + j];
            madata[j] = val;
            add += val;
        }
        ma = Moment.Moment(madata, 10);
        aug[size(s1)] = ma.ave;
        return aug;
    }

    public static float[] concatenateSeries(float[] s1, float[] s2) {
        float[] totSerie = NumericalRecipesUtils.vector(1, NumericalRecipesUtils.size(s1) + NumericalRecipesUtils.size(s2));
        int i = -1;
        for (i = 1; i <= NumericalRecipesUtils.size(s1); i++) {
            totSerie[i] = s1[i];
        }
        for (int j = 1; j <= NumericalRecipesUtils.size(s2); j++) {
            totSerie[i] = s2[j];
            i++;
        }
        return totSerie;
    }

    public static float[] getPrevN(float[] series, int seriesSize, int today) {
        float[] totSerie = NumericalRecipesUtils.vector(1, seriesSize);
        int i = -1;
        for (i = 1; i <= seriesSize; i++)
            totSerie[i] = series[today - seriesSize + i];
        return totSerie;
    }

    public static float[] getNextN(float[] series, int seriesSize, int today) {
        float[] totSerie = NumericalRecipesUtils.vector(1, seriesSize);
        int i = -1;
        for (i = 1; i <= seriesSize; i++)
            totSerie[i] = series[today + i];
        return totSerie;
    }

    public static float getLastValue(float[] series) {
        return series[series.length - 1];
    }

    public static int size(float[] s1) {
        return s1.length - 1;
    }

    public static void setLastValue(float[] series, float x) {
        series[size(series)] = x;
    }

    public static double[] convert(float[] serie) {
        double[] ar = new double[serie.length];
        for (int i = 1; i < serie.length; i++) {
            ar[i - 1] = (double) serie[i];
        }
        return ar;
    }

    public static TDay toYYmmDD(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date da = sdf.parse(date);
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
            return new TDay(sdf2.format(da));
        } catch (ParseException e) {
            GWT.log(e.toString(), e);
        }
        return null;
    }

}