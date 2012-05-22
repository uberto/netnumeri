package com.netnumeri.shared.finance.ta;


import com.netnumeri.shared.finance.beans.NRError;
import com.netnumeri.shared.finance.math.NumericalRecipes;
import com.netnumeri.shared.finance.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/* Driver for routine convlv */

public class Filter {

    public static void log(float s) {
        LogUtils.debug("" + s);
    }

    public static void log(String s) {
        LogUtils.debug(s);
    }

    protected static final float TINY = 1.0e-20F;

    public static void filter(float[] data,
                              float[] smoothedData,
                              int n,
                              int nl,
                              int nr,
                              int m,
                              int derivative)
            throws NRError {
        int isign;
        float[] respns = NumericalRecipes.vector(1, n);
        int np = nl + nr + 1;
        isign = 1;
        savgol(respns, np, nl, nr, derivative, m);
        convlv(data, n, respns, np, isign, smoothedData);
    }

    public static void convlv(float[] data, int n, float[] respns, int m,
                              int isign, float[] ans)
            throws NRError {
        int i, no2;
        float dum, mag2;
        float[] fft;
        fft = NumericalRecipes.vector(1, n << 1);
        for (i = 1; i <= (m - 1) / 2; i++) {
            respns[n + 1 - i] = respns[m + 1 - i];
        }
        for (i = (m + 3) / 2; i <= n - (m - 1) / 2; i++) {
            respns[i] = 0.0F;
        }

        twofft(data, respns, fft, ans, n);
        no2 = n >> 1;
        for (i = 2; i <= n + 2; i += 2) {
            if (isign == 1) {
                ans[i - 1] = (fft[i - 1] * (dum = ans[i - 1]) - fft[i] * ans[i]) / no2;
                ans[i] = (fft[i] * dum + fft[i - 1] * ans[i]) / no2;
            } else if (isign == -1) {
                if ((mag2 = (ans[i - 1] * ans[i - 1]) + (ans[i] * ans[i])) == 0.0F) {
                    NumericalRecipes.nrerror("Deconvolving at response zero in convlv");
                }
                ans[i - 1] = (fft[i - 1] * (dum = ans[i - 1]) + fft[i] * ans[i]) / mag2 / no2;
                ans[i] = (fft[i] * dum - fft[i - 1] * ans[i]) / mag2 / no2;
            } else {
                NumericalRecipes.nrerror("No meaning for isign in convlv");
            }
        }
        ans[2] = ans[n + 1];
        realft(ans, n, -1);
    }

    public static void four1(float data[], int nn, int isign)
            throws NRError {
        int n, mmax, m, j, istep, i;
        double wtemp, wr, wpr, wpi, wi, theta;
        float tempr, tempi;

        n = nn << 1;
        j = 1;
        for (i = 1; i < n; i += 2) {
            if (j > i) {
                NumericalRecipes.swap(data, j, i);
                NumericalRecipes.swap(data, j + 1, i + 1);
            }
            m = n >> 1;
            while (m >= 2 && j > m) {
                j -= m;
                m >>= 1;
            }
            j += m;
        }
        mmax = 2;
        while (n > mmax) {
            istep = mmax << 1;
            theta = isign * (6.28318530717959 / mmax);
            wtemp = Math.sin(0.5F * theta);
            wpr = -2.0F * wtemp * wtemp;
            wpi = Math.sin(theta);
            wr = 1.0F;
            wi = 0.0F;
            for (m = 1; m < mmax; m += 2) {
                for (i = m; i <= n; i += istep) {
                    j = i + mmax;
                    tempr = (float) (wr * data[j] - wi * data[j + 1]);
                    tempi = (float) (wr * data[j + 1] + wi * data[j]);
                    data[j] = data[i] - tempr;
                    data[j + 1] = data[i + 1] - tempi;
                    data[i] += tempr;
                    data[i + 1] += tempi;
                }
                wr = (wtemp = wr) * wpr - wi * wpi + wr;
                wi = wi * wpr + wtemp * wpi + wi;
            }
            mmax = istep;
        }
    }

    public static void lubksb(float[][] a, int n, int[] indx, float[] b)
            throws NRError {
        int i, ii = 0, ip, j;
        float sum;

        for (i = 1; i <= n; i++) {
            ip = indx[i];
            sum = b[ip];
            b[ip] = b[i];
            if (ii != 0) {
                for (j = ii; j <= i - 1; j++) {
                    sum -= a[i][j] * b[j];
                }
            } else if (sum != 0) {
                ii = i;
            }
            b[i] = sum;
        }
        for (i = n; i >= 1; i--) {
            sum = b[i];
            for (j = i + 1; j <= n; j++) {
                sum -= a[i][j] * b[j];
            }
            b[i] = sum / a[i][i];
        }
    }

    public static float ludcmp(float[][] a, int n, int[] indx)
            throws NRError {
        int i, imax = -1, j, k;
        float big, dum, sum, temp;
        float[] vv;
        float d;

        vv = NumericalRecipes.vector(1, n);
        d = 1.0F;
        for (i = 1; i <= n; i++) {
            big = 0.0F;
            for (j = 1; j <= n; j++) {
                if ((temp = Math.abs(a[i][j])) > big) {
                    big = temp;
                }
            }
            if (big == 0.0F) {
                NumericalRecipes.nrerror("Singular matrix in routine ludcmp");
            }
            vv[i] = 1.0F / big;
        }
        for (j = 1; j <= n; j++) {
            for (i = 1; i < j; i++) {
                sum = a[i][j];
                for (k = 1; k < i; k++) {
                    sum -= a[i][k] * a[k][j];
                }
                a[i][j] = sum;
            }
            big = 0.0F;
            for (i = j; i <= n; i++) {
                sum = a[i][j];
                for (k = 1; k < j; k++) {
                    sum -= a[i][k] * a[k][j];
                }
                a[i][j] = sum;
                if ((dum = vv[i] * Math.abs(sum)) >= big) {
                    big = dum;
                    imax = i;
                }
            }
            if (j != imax) {
                for (k = 1; k <= n; k++) {
                    dum = a[imax][k];
                    a[imax][k] = a[j][k];
                    a[j][k] = dum;
                }
                d = -d;
                vv[imax] = vv[j];
            }
            indx[j] = imax;
            if (a[j][j] == 0.0F) {
                a[j][j] = TINY;
            }
            if (j != n) {
                dum = 1.0F / (a[j][j]);
                for (i = j + 1; i <= n; i++) {
                    a[i][j] *= dum;
                }
            }
        }
        return (d);
    }

    public static void realft(float[] data, int n, int isign)
            throws NRError {
        int i, i1, i2, i3, i4, np3;
        float c1 = 0.5F, c2, h1r, h1i, h2r, h2i;
        double wr, wi, wpr, wpi, wtemp, theta;

        theta = 3.141592653589793 / (double) (n >> 1);
        if (isign == 1) {
            c2 = -0.5F;
            four1(data, n >> 1, 1);
        } else {
            c2 = 0.5F;
            theta = -theta;
        }
        wtemp = Math.sin(0.5F * theta);
        wpr = -2.0F * wtemp * wtemp;
        wpi = Math.sin(theta);
        wr = 1.0F + wpr;
        wi = wpi;
        np3 = n + 3;
        for (i = 2; i <= (n >> 2); i++) {
            i4 = 1 + (i3 = np3 - (i2 = 1 + (i1 = i + i - 1)));
            h1r = c1 * (data[i1] + data[i3]);
            h1i = c1 * (data[i2] - data[i4]);
            h2r = -c2 * (data[i2] + data[i4]);
            h2i = c2 * (data[i1] - data[i3]);
            data[i1] = (float) (h1r + wr * h2r - wi * h2i);
            data[i2] = (float) (h1i + wr * h2i + wi * h2r);
            data[i3] = (float) (h1r - wr * h2r + wi * h2i);
            data[i4] = (float) (-h1i + wr * h2i + wi * h2r);
            wr = (wtemp = wr) * wpr - wi * wpi + wr;
            wi = wi * wpr + wtemp * wpi + wi;
        }
        if (isign == 1) {
            data[1] = (h1r = data[1]) + data[2];
            data[2] = h1r - data[2];
        } else {
            data[1] = c1 * ((h1r = data[1]) + data[2]);
            data[2] = c1 * (h1r - data[2]);
            four1(data, n >> 1, -1);
        }
    }

    public static void savgol(float[] c, int np, int nl, int nr, int ld, int m)
            throws NRError {
        int imj, ipj, j, k, kk, mm;
        int[] indx;
        float d, fac, sum;
        float[][] a;
        float[] b;

        if (np < nl + nr + 1 || nl < 0 || nr < 0 || ld > m || nl + nr < m) {
            NumericalRecipes.nrerror("bad args in savgol");
        }
        a = NumericalRecipes.matrix(1, m + 1, 1, m + 1);
        indx = NumericalRecipes.ivector(1, m + 1);
        b = NumericalRecipes.vector(1, m + 1);
        for (ipj = 0; ipj <= (m << 1); ipj++) {
            sum = ((ipj != 0) ? 0.0F : 1.0F);
            for (k = 1; k <= nr; k++) {
                sum += Math.pow((double) k, (double) ipj);
            }
            for (k = 1; k <= nl; k++) {
                sum += Math.pow((double) -k, (double) ipj);
            }
            mm = NumericalRecipes.imin(ipj, 2 * m - ipj);
            for (imj = -mm; imj <= mm; imj += 2) {
                a[1 + (ipj + imj) / 2][1 + (ipj - imj) / 2] = sum;
            }
        }
        d = ludcmp(a, m + 1, indx);
        for (j = 1; j <= m + 1; j++) {
            b[j] = 0.0F;
        }
        b[ld + 1] = 1.0F;
        lubksb(a, m + 1, indx, b);
        for (kk = 1; kk <= np; kk++) {
            c[kk] = 0.0F;
        }
        for (k = -nl; k <= nr; k++) {
            sum = b[1];
            fac = 1.0F;
            for (mm = 1; mm <= m; mm++) {
                sum += b[mm + 1] * (fac *= k);
            }
            kk = ((np - k) % np) + 1;
            c[kk] = sum;
        }
    }

    public static void twofft(float data1[], float data2[], float fft1[], float fft2[],
                              int n)
            throws NRError {
        int nn3, nn2, jj, j;
        float rep, rem, aip, aim;

        nn3 = 1 + (nn2 = 2 + n + n);
        for (j = 1, jj = 2; j <= n; j++, jj += 2) {
            fft1[jj - 1] = data1[j];
            fft1[jj] = data2[j];
        }
        four1(fft1, n, 1);
        fft2[1] = fft1[2];
        fft1[2] = fft2[2] = 0.0F;
        for (j = 3; j <= n + 1; j += 2) {
            rep = 0.5F * (fft1[j] + fft1[nn2 - j]);
            rem = 0.5F * (fft1[j] - fft1[nn2 - j]);
            aip = 0.5F * (fft1[j + 1] + fft1[nn3 - j]);
            aim = 0.5F * (fft1[j + 1] - fft1[nn3 - j]);
            fft1[j] = rep;
            fft1[j + 1] = aim;
            fft1[nn2 - j] = rep;
            fft1[nn3 - j] = -aim;
            fft2[j] = aip;
            fft2[j + 1] = -rem;
            //log("nn2 - j: " + (nn2 - j) + "Len: " + fft2.length);
            fft2[nn2 - j] = aip;
            fft2[nn3 - j] = rem;
        }
    }

    private static float[] velfilter(float[] data, int N, int derivative, int nl, int nr)
            throws NRError {
        int i, j;
        int isign;
        float cmp;
        float[] respns;
        float[] resp;
        float[] ans;
        int np = nl + nr + 1;
        int m = 4;
        int N2 = 2 * N;
        //  int NPTS = 512;

        respns = NumericalRecipes.vector(1, N);
        resp = NumericalRecipes.vector(1, N);
        ans = NumericalRecipes.vector(1, N2);
        isign = 1;
        savgol(respns, np, nl, nr, derivative, m);

/*
        */
/* compare with a direct convolution */
/*
        for (i = 1; i <= 10; i++) {
            log("" + respns[i]);
        }
        log("call convolv");
*/
        convlv(data, N, respns, np, isign, ans);
/*

        */
/* compare with a direct convolution */
/*
        for (i = 1; i <= 100; i++) {
            log(data[i] + ":" + ans[i]);
        }
*/
        return ans;
    }

    public static void test_velfilter(float[] data)
            throws NRError {
        int i, j;
        int isign;
        float[] respns;
        float[] ans;
        int nl = 10;
        int nr = 10;
        int np = 21;
        int m = 4;
        int N = NumericalRecipes.size(data);
        int N2 = 2 * N;
        respns = NumericalRecipes.vector(1, N);
        ans = NumericalRecipes.vector(1, N2);
        isign = 1;
        savgol(respns, np, nl, nr, 0, m);
        convlv(data, N, respns, np, isign, ans);
        /* compare with a direct convolution */
    }

    private static void test_convlv()
            throws NRError {
        int i, j;
        int isign;
        float cmp;
        float[] data;
        float[] respns;
        float[] resp;
        float[] ans;
        int N = 16;        /* data array size */
        int M = 9;         /* response function dimension - must be odd */
        int N2 = 2 * N;

        data = NumericalRecipes.vector(1, N);
        respns = NumericalRecipes.vector(1, N);
        resp = NumericalRecipes.vector(1, N);
        ans = NumericalRecipes.vector(1, N2);
        for (i = 1; i <= N; i++) {
            if ((i >= N / 2 - N / 8) && (i <= N / 2 + N / 8)) {
                data[i] = 1.0F;
            } else {
                data[i] = 0.0F;
            }
        }
        for (i = 1; i <= M; i++) {
            if ((i > 2) && (i < 7)) {
                respns[i] = 1.0F;
            } else {
                respns[i] = 0.0F;
            }
            resp[i] = respns[i];
        }
        isign = 1;
        convlv(data, N, resp, M, isign, ans);

        /* compare with a direct convolution */
        log("i\tCONVLV\tExpected");
        for (i = 1; i <= N; i++) {
            cmp = 0.0F;
            for (j = 1; j <= M / 2; j++) {
                cmp += data[((i - j - 1 + N) % N) + 1] * respns[j + 1];
                cmp += data[((i + j - 1) % N) + 1] * respns[M - j + 1];
            }
            cmp += data[i] * respns[1];
            log("" + i + "\t" + ans[i] + "\t" + cmp);
        }
    }

    private static final int NMAX = 1000;
    private static final int NTEST = 6;

    private static void test_savgol()
            throws NRError {
        int i, j, m, nl, np, nr;
        float[] c;
        float sum;
        int[] mtest = {0, 2, 2, 2, 2, 4, 4};
        int[] nltest = {0, 2, 3, 4, 5, 4, 5};
        int[] nrtest = {0, 2, 1, 0, 5, 4, 5};
        String[] ans = {
                "",
                "                      -0.086  0.343  0.486  0.343 -0.086",
                "               -0.143  0.171  0.343  0.371  0.257",
                "         0.086 -0.143 -0.086  0.257  0.886",
                " -0.084  0.021  0.103  0.161  0.196  0.207  0.196  0.161  0.103  0.021 -0.084",
                "         0.035 -0.128  0.070  0.315  0.417  0.315  0.070 -0.128  0.035",
                "  0.042 -0.105 -0.023  0.140  0.280  0.333  0.280  0.140 -0.023 -0.105  0.042"
        };

        c = NumericalRecipes.vector(1, NMAX);
        log("M nl nr");
        log("\t\t\tSample Savitzky-Golay Coefficients");
        for (i = 1; i <= NTEST; i++) {
            m = mtest[i];
            nl = nltest[i];
            nr = nrtest[i];
            np = nl + nr + 1;
            savgol(c, np, nl, nr, 0, m);
            sum = 0.0F;
            for (j = 1; j <= np; j++) {
                sum += c[j];
            }
            log("" + m + " " + nl + " " + nr);
            for (j = nl; j < 5; j++) {
                System.out.print("\t");
            }
            for (j = nl + 1; j >= 1; j--) {
                System.out.print(c[j] + "\t");
            }
            for (j = 0; j < nr; j++) {
                System.out.print(c[np - j] + "\t");
            }
            log("Sum = " + sum);
            log("Compare answer:");
            log(ans[i]);
        }
    }

    public static Map computeArmonics(float[] data) throws NRError {
        Map ret = new HashMap();
        ret.put("filter", velfilter(data, data.length - 1, 0, 10, 10));
        ret.put("doublefilter", velfilter(data, data.length - 1, 0, 20, 20));
        ret.put("d1", velfilter(data, data.length - 1, 1, 10, 10));
        ret.put("d2", velfilter(data, data.length - 1, 2, 10, 10));
        return ret;
    }

    public static void main(String[] args) {
        try {
            int N = 512;

            LogUtils.debug("=====================================================================");
            float[] data = NumericalRecipes.vector(1, N);
            for (int i = 1; i <= N; i++) {
                data[i] = (float) Math.random();
                log(data[i]);
            }

            Map ret = computeArmonics(data);

            LogUtils.debug("=====================================================================");
            float[] filter = (float[]) ret.get("filter");
            for (int i = 1; i <= N; i++) {
                log(filter[i]);
            }

            LogUtils.debug("=====================================================================");
            float[] doublefilter = (float[]) ret.get("doublefilter");
            for (int i = 1; i <= N; i++) {
                log(filter[i]);
            }

            LogUtils.debug("=====================================================================");
            float[] d1 = (float[]) ret.get("d1");
            for (int i = 1; i <= N; i++) {
                log(d1[i]);
            }

            LogUtils.debug("=====================================================================");
            float[] d2 = (float[]) ret.get("d2");
            for (int i = 1; i <= N; i++) {
                log(d2[i]);
            }

        } catch (NRError nrError) {
            nrError.printStackTrace();
        }
    }

}