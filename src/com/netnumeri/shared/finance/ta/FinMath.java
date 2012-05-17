package com.netnumeri.shared.finance.ta;

import com.google.gwt.user.client.Random;
import com.netnumeri.shared.finance.beans.FinConstants;

import java.util.Date;

public class FinMath implements FinConstants {

    public FinMath() {
    }

    public static double N(double z) {
        double c1 = 2.506628D;
        double c2 = 0.3193815D;
        double c3 = -0.3565638D;
        double c4 = 1.7814779D;
        double c5 = -1.821256D;
        double c6 = 1.3302744D;
        double w;
        if (z > 0 || z == 0) {
            w = 1;
        } else {
            w = -1D;
        }
        double y = 1 / (1 + 0.2316419D * w * z);
        return 0.5D + w * (0.5D - (Math.exp((-z * z) / 2D) / c1) * (y * (c2 + y * (c3 + y * (c4 + y * (c5 + y * c6))))));
    }

    public static double n(double z) {
        return (1 / Math.sqrt(6.2831853071795862D)) * Math.exp(-0.5D * z * z);
    }

    public static double Fact(int n) {
        double F = 1;
        for (int i = 1; i <= n; i++) {
            F *= i;
        }
        return F;
    }

    public static double C(int m, int n) {
        return Fact(n) / (Fact(m) * Fact(n - m));
    }

    public static double Binom(int m, int n, double p) {
        return C(m, n) * Math.pow(p, m) * Math.pow(1 - p, n - m);
    }

    public static double BM(double spot,
                            double strike,
                            double time,
                            double volatility,
                            double rate) {
        return BM(spot, strike, time, volatility, rate, kCall, 1000);
    }

    public static double BM(double spot,
                            double strike,
                            double time,
                            double volatility,
                            double rate,
                            int Option,
                            int steps) {
        double P = 0;
        double u_ = u(time, volatility, steps);
        double d_ = d(time, volatility, steps);
        double p_ = p(time, volatility, steps, rate);
        for (int m = 0; m <= steps; m++) {
            double binom = Binom(m, steps, p_);
            double payoff = Payoff(spot * Math.pow(u_, m) * Math.pow(d_, steps - m), strike, Option);
            P += binom * payoff;
        }
        return PV4(P, rate, time);
    }

    public static double MC(double S, double X, double t, double s,
                            double r) {
        return MC(S, X, t, s, r, kCall, 0x186a0);
    }

    public static double MC(double S, double X, double t, double s,
                            double r, int Option, int n) {
        double x_ = (r - 0.5D * s * s) * t;
        double s_ = s * Math.sqrt(t);
        double P = 0;
        for (int i = 0; i < n; i++) {
            P += Payoff(S * Math.exp(x_ + s_ * Random.nextDouble()), X, Option);
        }
        P /= n;
        return PV4(P, r, t);
    }

    public static double d1(double S, double X, double t, double s,
                            double r) {
        return (Math.log(S / X) + (r + (s * s) / 2D) * t) / (s * Math.sqrt(t));
    }

    public static double d2(double S, double X, double t, double s,
                            double r) {
        return d1(S, X, t, s, r) - s * Math.sqrt(t);
    }

    public static double BS(double S,
                            double X,
                            double t,
                            double s,
                            double r) {
        return BS(S, X, t, s, r, kCall);
    }

    public static double BS(double S,
                            double X,
                            double t,
                            double s,
                            double r,
                            int Option) {
        if (Option == kCall || Option == kEuropean || Option == kAmerican) {
            return S * N(d1(S, X, t, s, r)) - X * Math.exp(-r * t) * N(d2(S, X, t, s, r));
        }
        if (Option == kPut || Option == kEuropeanPut || Option == kAmericanPut) {
            return -S * N(-d1(S, X, t, s, r)) + X * Math.exp(-r * t) * N(-d2(S, X, t, s, r));
        } else {
            throw new IllegalArgumentException("Error in < Black Scholes> Unknown Option type (%d)\n" + Option);
        }
    }

    public static double Delta(double S, double X, double t, double s,
                               double r) {
        return Delta(S, X, t, s, r, kCall);
    }

    public static double Delta(double S, double X, double t, double s,
                               double r, int Option) {
        if (Option == kCall || Option == kEuropean || Option == kAmerican) {
            return N(d1(S, X, t, s, r));
        }
        if (Option == kPut || Option == kEuropeanPut || Option == kAmericanPut) {
            return N(d1(S, X, t, s, r)) - 1;
        } else {
            throw new IllegalArgumentException("Error in < Delta> Unknown Option type (%d)\n" + Option);
        }
    }

    public static double Gamma(double S, double X, double t, double s,
                               double r) {
        return n(d1(S, X, t, s, r)) / (S * s * Math.sqrt(t));
    }

    public static double Theta(double S, double X, double t, double s,
                               double r) {
        return Theta(S, X, t, s, r, kCall);
    }

    public static double Theta(double S,
                               double X,
                               double t,
                               double s,
                               double r,
                               int Option) {
        if (Option == kCall || Option == kEuropean || Option == kAmerican) {
            return (-S * n(d1(S, X, t, s, r)) * s) / (2D * Math.sqrt(t)) - r * X * Math.exp(-r * t) * N(d2(S, X, t, s, r));
        }
        if (Option == kPut || Option == kEuropeanPut || Option == kAmericanPut) {
            return (-S * n(d1(S, X, t, s, r)) * s) / (2D * Math.sqrt(t)) + r * X * Math.exp(-r * t) * N(-d2(S, X, t, s, r));
        } else {
            throw new IllegalArgumentException("Error in < Theta> Unknown Option \n" + Option);
        }
    }

    public static double Vega(double S, double X, double t, double s,
                              double r) {
        return S * Math.sqrt(t) * n(d1(S, X, t, s, r));
    }

    public static double Rho(double S, double X, double t, double s,
                             double r) {
        return Rho(S, X, t, s, r, kCall);
    }

    public static double Rho(double S, double X, double t, double s,
                             double r, int Option) {
        if (Option == kCall || Option == kEuropean || Option == kAmerican) {
            return X * t * Math.exp(-r * t) * N(d2(S, X, t, s, r));
        }
        if (Option == kPut || Option == kEuropeanPut || Option == kAmericanPut) {
            return -X * t * Math.exp(-r * t) * N(-d2(S, X, t, s, r));
        } else {
            throw new IllegalArgumentException("Error in < Rho> Unknown Option  (%d)\n" + Option);
        }
    }

    public static double ImpliedVolatility(double S, double X, double t, double r,
                                           double P) {
        return ImpliedVolatility(S, X, t, r, P, kCall, kBlackScholes, -1, 0.001D);
    }

    public static double ImpliedVolatility(double S, double X, double t, double r,
                                           double P, int a, int b) {
        return ImpliedVolatility(S, X, t, r, P, a, b, -1, 0.001D);
    }

    public static double ImpliedVolatility(double S, double X, double t, double r,
                                           double P, int Option, int Method, int n, double Eps) {
        if (n == -1) {
            if (Method == kBinomial) {
                n = 100;
            } else if (Method == kMonteCarlo) {
                n = 0x186a0;
            }
        }
        double sMin = 0;
        double sMax = 10D;
        double p = 0;
        double s = 0;
        while (Math.abs(sMax - sMin) > Eps) {
            s = sMin + (sMax - sMin) / 2D;
            if (Method == kBlackScholes) {
                p = BS(S, X, t, s, r, Option);
            } else if (Method == kBinomial) {
                p = BM(S, X, t, s, r, Option, n);
            } else if (Method == kMonteCarlo) {
                p = MC(S, X, t, s, r, Option, n);
            } else {
                throw new IllegalArgumentException("Error in < ImpliedVolatility> Unknown Method type (%d)\n" + Method);
            }
            if (p > P) {
                sMax = s;
            } else {
                sMin = s;
            }
        }
        return s;
    }

    public static double Parity(double P, double S, double X, double t,
                                double r, int Option) {
        if (Option == kCall) {
            return P - (S - X * Math.exp(-r * t));
        }
        if (Option == kPut) {
            return P + (S - X * Math.exp(-r * t));
        } else {
            return -1D;
        }
    }

    public static double FuturesPrice(double S, double t, double r) {
        return S * Math.exp(r * t);
    }

    public static double FuturesPrice(double S, double t, double r, double I) {
        return (S - I) * Math.exp(r * t);
    }

    public static double Percent(double P, double Base) {
        return (Base / 100D) * P;
    }

    public static double FV1(double A, double r, double n) {
        return A * (1 + r * n);
    }

    // HULL 3.1 discrete capitalization 1 time

    public static double FV2(double A, double r, double n) {
        return A * Math.pow(1 + r, n);
    }

    // HULL 3.1 discrete capitalization m times

    public static double FV3(double A, double r, double n, double m) {
        return A * Math.pow(1 + r / m, n * m);
    }

    /**
     * HULL 3.1 continuos capitalization
     *
     * @param A
     * @param r
     * @param n
     */
    public static double FV4(double A, double r, double n) {
        return A * Math.exp(r * n);
    }

    public static double discreteToContinuos(double discreteRate, double m) {
        return m * Math.log(1 + discreteRate / m);
    }

    public static double continuosToDiscrete(double continuosRate, double m) {
        return m * (Math.exp(continuosRate / m) - 1);
    }

    public static double PV1(double CF, double r, double n) {
        return CF / (1 + r * n);
    }

    public static double PV2(double CF, double r, double n) {
        return CF / Math.pow(1 + r, n);
    }

    public static double dPV2(double CF, double r, double n) {
        return (-CF * n) / Math.pow(1 + r, n + 1);
    }

    public static double d2PV2(double CF, double r, double n) {
        return (CF * n * (n + 1)) / Math.pow(1 + r, n + 2D);
    }

    public static double PV3(double CF, double r, double n, double m) {
        return CF / Math.pow(1 + r / m, n * m);
    }

    public static double PV4(double CF, double r, double n) {
        return CF / Math.exp(r * n);
    }

    public static double u(double t, double s, int n) {
        return Math.exp(s * Math.sqrt(t / (double) n));
    }

    public static double d(double t, double s, int n) {
        return Math.exp(-s * Math.sqrt(t / (double) n));
    }

    public static double p(double t, double s, int n, double r) {
        double R = FV2(1, r, t / (double) n);
        double u_ = u(t, s, n);
        double d_ = d(t, s, n);
        return (R - d_) / (u_ - d_);
    }

    public static double Call(double S, double X) {
        return Math.max(0, S - X);
    }

    public static double Put(double S, double X) {
        return Math.max(0, X - S);
    }

    public static double Payoff(double S, double X, int Option) {
        if (Option == kCall || Option == kEuropean || Option == kAmerican) {
            return Call(S, X);
        }
        if (Option == kPut || Option == kEuropeanPut || Option == kAmericanPut) {
            return Put(S, X);
        } else {
            throw new IllegalArgumentException("Error in < Payoff> Unknown option type (%d)\n" + Option);
        }
    }

    public static double n(double r, double mu, double sigma) {
        double nv = 1 / (Math.sqrt(6.2831853071795862D) * sigma);
        double z = (r - mu) / sigma;
        nv *= Math.exp(-0.5D * z * z);
        return nv;
    }

    public static double f(double x, double y, double aprime, double bprime,
                           double rho) {
        double r = aprime * (2D * x - aprime) + bprime * (2D * y - bprime) + 2D * rho * (x - aprime) * (y - bprime);
        return Math.exp(r);
    }

    public static double sgn(double x) {
        return x < 0 ? -1D : 1;
    }

    public static double N(double a, double b, double rho) {
        if (a <= 0 && b <= 0 && rho <= 0) {
            double aprime = a / Math.sqrt(2D * (1 - rho * rho));
            double bprime = b / Math.sqrt(2D * (1 - rho * rho));
            double A[] = {
                    0.32530300000000001D, 0.42110710000000001D, 0.13344249999999999D, 0.0063743230000000003D
            };
            double B[] = {
                    0.13377639999999999D, 0.62432469999999995D, 1.3425377999999999D, 2.2626645000000001D
            };
            double sum = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    sum += A[i] * A[j] * f(B[i], B[j], aprime, bprime, rho);
                }

            }

            sum *= Math.sqrt(1 - rho * rho) / 3.1415926535897931D;
            return sum;
        }
        if (a * b * rho <= 0) {
            if (a <= 0 && b >= 0 && rho >= 0) {
                if (a <= 0 && b >= 0 && rho >= 0) {
                    return N(a) - N(a, -b, -rho);
                }
                if (a >= 0 && b <= 0 && rho >= 0) {
                    return N(b) - N(-a, b, -rho);
                }
                if (a >= 0 && b >= 0 && rho <= 0) {
                    return ((N(a) + N(b)) - 1) + N(-a, -b, rho);
                }
            } else if (a * b * rho >= 0) {
                double denum = Math.sqrt((a * a - 2D * rho * a * b) + b * b);
                double rho1 = ((rho * a - b) * sgn(a)) / denum;
                double rho2 = ((rho * b - a) * sgn(b)) / denum;
                double delta = (1 - sgn(a) * sgn(b)) / 4D;
                return (N(a, 0, rho1) + N(b, 0, rho2)) - delta;
            }
        }
        return -99.900000000000006D;
    }

    public static double StubPeriod(double d, double d1, int i) {
        double d2 = 0;
        double d3 = RoundDown((d1 - d) * (double) i);
        double d4 = (d1 - d) * (double) i;
        if (d4 - d3 < 0.005D) {
            d2 = 1;
        } else {
            d2 = (d4 - d3) / (double) i;
        }
        return d2;
    }


    public static double CriticalValueOptionsOnOptions(String s,
                                                       double X,
                                                       double d1,
                                                       double time,
                                                       double rate,
                                                       double dividend,
                                                       double volatility) {
        double S = X;
        double d7 = BlackPrice(s, S, X, rate, dividend, volatility, time);
        double d8 = BlackDelta(s, S, X, rate, dividend, volatility, time);
        for (double d9 = 9.9999999999999995E-007D; Math.abs(d7 - d1) > d9;) {
            S -= (d7 - d1) / d8;
            d7 = BlackPrice(s, S, X, rate, dividend, volatility, time);
            d8 = BlackDelta(s, S, X, rate, dividend, volatility, time);
        }

        return S;
    }

    public static double RoundDown(double d) {
        double d1 = Math.round(d);
        if (d1 > d) {
            d1--;
        }
        return d1;
    }

    public static double Max(double d, double d1) {
        double d2 = d1;
        if (d >= d1) {
            d2 = d;
        } else {
            d2 = d1;
        }
        return d2;
    }

    public static double DiscreteAdjustedBarrier(double d, double d1, double d2, double d3) {
        double d4 = d1;
        if (d1 > d) {
            d4 = d1 * Math.exp(0.58260000000000001D * d2 * Math.sqrt(d3));
        } else if (d1 < d) {
            d4 = d1 * Math.exp(-0.58260000000000001D * d2 * Math.sqrt(d3));
        }
        return d4;
    }

    public static double YearFaction(String s, String s1, int i) {
        double d = 0;
        Date date = new Date(s);
        Date date1 = new Date(s1);
        double d1 = Date.parse(s);
        double d2 = Date.parse(s1);
        if (i == 0 || i == 3 || i == 2) {
            d = (d2 - d1) / 86400000D / 365D;
        } else if (i == 1) {
            d = (d2 - d1) / 86400000D / 360D;
        }
        return d;
    }

    public static double YearFaction(Date date, Date date1, int i) {
        double d = 0;
        double d1 = date.getTime();
        double d2 = date1.getTime();
        if (i == 0 || i == 3 || i == 2) {
            d = (d2 - d1) / 86400000D / 365D;
        } else if (i == 1) {
            d = (d2 - d1) / 86400000D / 360D;
        }
        return d;
    }

    public static double phi(double d, double d1, double d2, double d3,
                             double d4, double d5, double d6, double d7) {
        double d8 = (-d5 + d2 * d6 + 0.5D * d2 * (d2 - 1) * d7 * d7) * d1;
        double d10 = -(Math.log(d / d3) + (d6 + (d2 - 0.5D) * d7 * d7) * d1) / (d7 * Math.sqrt(d1));
        double d9 = (2D * d6) / (d7 * d7) + (2D * d2 - 1);
        double d11 = Math.exp(d8) * Math.pow(d, d2) * (CND(d10) - Math.pow(d4 / d, d9) * CND(d10 - (2D * Math.log(d4 / d)) / (d7 * Math.sqrt(d1))));
        return d11;
    }

    /**
     * @param optionType
     * @param spot
     * @param strike
     * @param interestRate
     * @param dividend
     * @param volatility
     * @param expiration
     */
    public static double BlackPrice(String optionType,
                                    double spot,
                                    double strike,
                                    double interestRate,
                                    double dividend,
                                    double volatility,
                                    double expiration) {
        double price = 0;
        double d7 = (Math.log(spot / strike) + ((interestRate - dividend) + (volatility * volatility) / 2) * expiration) / (volatility * Math.sqrt(expiration));
        double d8 = d7 - volatility * Math.sqrt(expiration);
        if (optionType.equalsIgnoreCase("European Call")) {
            price = spot * Math.exp(-dividend * expiration) * CND(d7) - strike * Math.exp(-interestRate * expiration) * CND(d8);
        } else if (optionType.equalsIgnoreCase("European Put")) {
            price = -(spot * Math.exp(-dividend * expiration) * CND(-d7) - strike * Math.exp(-interestRate * expiration) * CND(-d8));
        }
        return price;
    }

    /* public static double BlackPrice(int optionType,
                                        double spot,
                                        double strike,
                                        double interestRate,
                                        double dividend,
                                        double volatility,
                                        double t)
        {
            double price = 0;
            double d7 = (Math.log(spot / strike) + ((interestRate - dividend) + (volatility * volatility) / 2) * t) / (volatility * Math.sqrt(t));
            double d8 = d7 - volatility * Math.sqrt(t);
            if (optionType == 0)
                price = spot * Math.exp(-dividend * t) * CND(d7) - strike * Math.exp(-interestRate * t) * CND(d8);
            else if (optionType == 1)
                price = -(spot * Math.exp(-dividend * t) * CND(-d7) - strike * Math.exp(-interestRate * t) * CND(-d8));
            if (logger.isInfoEnabled()) logger.info("returning price = " + price);
            return price;
        }
    */

    public static double Sgn(double d) {
        double d1 = 0;
        if (d > 0) {
            d1 = 1;
        } else if (d == 0) {
            d1 = 0;
        } else if (d < 0) {
            d1 = -1D;
        }
        return d1;
    }

    public static double BlackDelta(String s,
                                    double spot,
                                    double strike,
                                    double interestrate,
                                    double dividend,
                                    double volatility,
                                    double time) {
        double d7 = interestrate - dividend;
        double d8 = 0;
        double d6 = (Math.log(spot / strike) + (d7 + (volatility * volatility) / 2D) * time) / (volatility * Math.sqrt(time));
        if (s.equalsIgnoreCase("European Call")) {
            d8 = Math.exp((d7 - interestrate) * time) * CND(d6);
        } else if (s.equalsIgnoreCase("European Put")) {
            d8 = Math.exp((d7 - interestrate) * time) * (CND(d6) - 1);
        }
        return d8;
    }

    public static double Spline(double d, double ad[], double ad1[], int i) {
        double ad2[] = new double[i];
        double ad3[] = new double[i];
        double ad4[] = new double[i];
        int j = i - 1;
        ad4[1] = ad[2] - ad[1];
        ad3[2] = (ad1[2] - ad1[1]) / ad4[1];
        for (int l = 2; l <= j; l++) {
            ad4[l] = ad[l + 1] - ad[l];
            ad2[l] = 2D * (ad4[l - 1] + ad4[l]);
            ad3[l + 1] = (ad1[l + 1] - ad1[l]) / ad4[l];
            ad3[l] = ad3[l + 1] - ad3[l];
        }

        ad2[1] = -ad4[1];
        ad2[i] = -ad4[i - 1];
        ad3[1] = 0;
        ad3[i] = 0;
        if (i != 3) {
            ad3[1] = ad3[3] / (ad[4] - ad[2]) - ad3[2] / (ad[3] - ad[1]);
            ad3[i] = ad3[i - 1] / (ad[i] - ad[i - 2]) - ad3[i - 2] / (ad[i - 1] - ad[i - 3]);
            ad3[1] = (ad3[1] * Math.pow(ad4[1], 2D)) / (ad[4] - ad[1]);
            ad3[i] = (-ad3[i] * Math.pow(ad4[i - 1], 2D)) / (ad[i] - ad[i - 3]);
        }
        for (int i1 = 2; i1 <= i; i1++) {
            double d1 = ad4[i1 - 1] / ad2[i1 - 1];
            ad2[i1] = ad2[i1] - d1 * ad4[i1 - 1];
            ad3[i1] = ad3[i1] - d1 * ad3[i1 - 1];
        }

        ad3[i] = ad3[i] / ad2[i];
        for (int k = 1; k <= j; k++) {
            int j1 = i - k;
            ad3[j1] = (ad3[j1] - ad4[j1] * ad3[j1 + 1]) / ad2[j1];
        }

        ad2[i] = (ad1[i] - ad1[j]) / ad4[j] + ad4[j] * (ad3[j] + 2D * ad3[i]);
        for (int k1 = 1; k1 <= j; k1++) {
            ad2[k1] = (ad1[k1 + 1] - ad1[k1]) / ad4[k1] - ad4[k1] * (ad3[k1 + 1] + 2D * ad3[k1]);
            ad4[k1] = (ad3[k1 + 1] - ad3[k1]) / ad4[k1];
            ad3[k1] = 3D * ad3[k1];
        }

        ad3[i] = 3D * ad3[i];
        ad4[i] = ad4[i - 1];
        int l1 = 1;
        if (l1 >= i) {
            l1 = 1;
        }
        if (d >= ad[l1] && d <= ad[l1 + 1]) {
            double d2 = d - ad[l1];
            return ad1[l1] + d2 * (ad2[l1] + d2 * (ad3[l1] + d2 * ad4[l1]));
        }
        l1 = 1;
        int i2 = i + 1;
        do {
            int j2 = (l1 + i2) / 2;
            if (d < ad[j2]) {
                i2 = j2;
            } else {
                l1 = j2;
            }
        }
        while (i2 > l1 + 1);
        double d3 = d - ad[l1];
        return ad1[l1] + d3 * (ad2[l1] + d3 * (ad3[l1] + d3 * ad4[l1]));
    }

    public static double CBND(double d, double d1, double d2) {
        double ad[] = {
                0.24840614999999999D, 0.39233107D, 0.21141819000000001D, 0.033246659999999997D, 0.00082485334000000001D
        };
        double ad1[] = {
                0.10024215D, 0.48281396999999998D, 1.0609497999999999D, 1.7797293999999999D, 2.6697603999999999D
        };
        double d9 = 0;
        double d6 = d / Math.sqrt(2D * (1 - d2 * d2));
        double d7 = d1 / Math.sqrt(2D * (1 - d2 * d2));
        if ((d <= 0) & (d1 <= 0) & (d2 <= 0)) {
            double d8 = 0;
            int i = 0;
            do {
                int j = 0;
                do {
                    d8 += ad[i] * ad[j] * Math.exp(d6 * (2D * ad1[i] - d6) + d7 * (2D * ad1[j] - d7) + 2D * d2 * (ad1[i] - d6) * (ad1[j] - d7));
                }
                while (++j <= 4);
            }
            while (++i <= 4);
            d9 = (Math.sqrt(1 - d2 * d2) / 3.1415926535897931D) * d8;
        } else if ((d <= 0) & (d1 >= 0) & (d2 >= 0)) {
            d9 = CND(d) - CBND(d, -d1, -d2);
        } else if ((d >= 0) & (d1 <= 0) & (d2 >= 0)) {
            d9 = CND(d1) - CBND(-d, d1, -d2);
        } else if ((d >= 0) & (d1 >= 0) & (d2 <= 0)) {
            d9 = ((CND(d) + CND(d1)) - 1) + CBND(-d, -d1, d2);
        } else if (d * d1 * d2 > 0) {
            double d3 = ((d2 * d - d1) * Sgn(d)) / Math.sqrt((d * d - 2D * d2 * d * d1) + d1 * d1);
            double d4 = ((d2 * d1 - d) * Sgn(d1)) / Math.sqrt((d * d - 2D * d2 * d * d1) + d1 * d1);
            double d5 = (1 - Sgn(d) * Sgn(d1)) / 4D;
            d9 = (CBND(d, 0, d3) + CBND(d1, 0, d4)) - d5;
        }
        return d9;
    }

    public static double Kc(double strike,
                            double time,
                            double rate,
                            double dividend,
                            double volatility) {
        double d5 = (2D * dividend) / (volatility * volatility);
        double d6 = (2D * rate) / (volatility * volatility);
        double d14 = (-(d5 - 1) + Math.sqrt((d5 - 1) * (d5 - 1) + 4D * d6)) / 2D;
        double d7 = strike / (1 - 1 / d14);
        double d9 = (-(dividend * time + 2D * volatility * Math.sqrt(time)) * strike) / (d7 - strike);
        double d8 = strike + (d7 - strike) * (1 - Math.exp(d9));
        double d10 = (2D * rate) / (volatility * volatility * (1 - Math.exp(-rate * time)));
        double d11 = (Math.log(d8 / strike) + (dividend + (volatility * volatility) / 2D) * time) / (volatility * Math.sqrt(time));
        double d13 = (-(d5 - 1) + Math.sqrt((d5 - 1) * (d5 - 1) + 4D * d10)) / 2D;
        double d15 = d8 - strike;
        double d16 = BlackPrice("European Call", d8, strike, rate, rate - dividend, volatility, time) + ((1 - Math.exp((dividend - rate) * time) * CND(d11)) * d8) / d13;
        double d17 = Math.exp((dividend - rate) * time) * CND(d11) * (1 - 1 / d13) + (1 - (Math.exp((dividend - rate) * time) * CND(d11)) / (volatility * Math.sqrt(time))) / d13;
        for (double d18 = 9.9999999999999995E-007D; Math.abs(d15 - d16) / strike > d18;) {
            d8 = ((strike + d16) - d17 * d8) / (1 - d17);
            double d12 = (Math.log(d8 / strike) + (dividend + (volatility * volatility) / 2D) * time) / (volatility * Math.sqrt(time));
            d15 = d8 - strike;
            d16 = BlackPrice("European Call", d8, strike, rate, rate - dividend, volatility, time) + ((1 - Math.exp((dividend - rate) * time) * CND(d12)) * d8) / d13;
            d17 = Math.exp((dividend - rate) * time) * CND(d12) * (1 - 1 / d13) + (1 - (Math.exp((dividend - rate) * time) * ND(d12)) / (volatility * Math.sqrt(time))) / d13;
        }

        double d19 = d8;
        return d19;
    }

    public static double CND(double d) {
        double a1 = 0.31938153000000002D;
        double a2 = -0.356563782D;
        double a3 = 1.781477937D;
        double a4 = -1.8212559777999999D;
        double a5 = 1.3302744289999999D;
        double d7 = Math.abs(d);
        double d8 = 1 / (1 + 0.23164190000000001D * d7);
        double d1 = 1 - (1 / Math.sqrt(6.2831853071795862D)) * Math.exp(-Math.pow(d7, 2D) / 2D) * (a1 * d8 + a2 * Math.pow(d8, 2D) + a3 * Math.pow(d8, 3D) + a4 * Math.pow(d8, 4D) + a5 * Math.pow(d8, 5D));
        if (d < 0) {
            d1 = 1 - d1;
        }
        return d1;
    }

    public static double ND(double d) {
        return (1 / Math.sqrt(6.2831853071795862D)) * Math.exp((-d * d) / 2D);
    }

    public static double Coupons(double d, double d1, int i) {
        int j = 0;
        double d2 = RoundDown((d1 - d) * (double) i);
        double d3 = (d1 - d) * (double) i;
        if (d3 - d2 < 0.0055555555555555558D) {
            j = (int) d2;
        } else {
            j = (int) d2 + 1;
        }
        return (double) j;
    }

    /*  public static String convertToString(Date date)
        {
            int day = date.day();
            int j = date.month();
            int year = date.year();
            String s = "";
            switch (j)
            {
                case 0: // '\0'
                    s = "Jan";
                    break;

                case 1: // '\001'
                    s = "Feb";
                    break;

                case 2: // '\002'
                    s = "Mar";
                    break;

                case 3: // '\003'
                    s = "Apr";
                    break;

                case 4: // '\004'
                    s = "May";
                    break;

                case 5: // '\005'
                    s = "Jun";
                    break;

                case 6: // '\006'
                    s = "Jul";
                    break;

                case 7: // '\007'
                    s = "Aug";
                    break;

                case 8: // '\b'
                    s = "Sep";
                    break;

                case 9: // '\t'
                    s = "Oct";
                    break;

                case 10: // '\n'
                    s = "Nov";
                    break;

                case 11: // '\013'
                    s = "Dec";
                    break;
            }
            return day > 9 ? day + "-" + s + "-" + year : "0" + day;
        }
    */

    public static String sDate(double d) {
        Date date = new Date();
        Date date1 = new Date(1970, 1, 1);
        int i;
        int j;
        int k;
        if (d < 0) {
            i = date.getDate() + 2;
            j = date.getMonth();
            k = date.getYear() + 1900;
        } else {
            date1.setTime((long) d);
            i = date1.getDate();
            j = date1.getMonth();
            k = date1.getYear() + 1900;
        }
        String s = "Jan";
        switch (j) {
            case 0:
                s = "Jan";
                break;

            case 1:
                s = "Feb";
                break;

            case 2:
                s = "Mar";
                break;

            case 3:
                s = "Apr";
                break;

            case 4: // '\004'
                s = "May";
                break;

            case 5: // '\005'
                s = "Jun";
                break;

            case 6: // '\006'
                s = "Jul";
                break;

            case 7: // '\007'
                s = "Aug";
                break;

            case 8: // '\b'
                s = "Sep";
                break;

            case 9: // '\t'
                s = "Oct";
                break;

            case 10: // '\n'
                s = "Nov";
                break;

            case 11:
                s = "Dec";
                break;
        }
        return i + "-" + s + "-" + k;
    }

    public static double Min(double d, double d1) {
        double d2 = d1;
        if (d <= d1) {
            d2 = d;
        } else {
            d2 = d1;
        }
        return d2;
    }

    public static double BAWAmericanCallApprox(double spot,
                                               double strike,
                                               double T,
                                               double rate,
                                               double dividend,
                                               double volatility) {
        double d12 = 0;
        if (dividend >= rate) {
            d12 = BlackPrice("European Call", spot, strike, rate, dividend, volatility, T);
        } else {
            double d6 = Kc(strike, T, rate, dividend, volatility);
            double d7 = (2D * dividend) / (volatility * volatility);
            double d8 = (2D * rate) / (volatility * volatility * (1 - Math.exp(-rate * T)));
            double d9 = (Math.log(d6 / strike) + (dividend + (volatility * volatility) / 2) * T) / (volatility * Math.sqrt(T));
            double d10 = (-(d7 - 1) + Math.sqrt((d7 - 1) * (d7 - 1) + 4 * d8)) / 2;
            double d11 = (d6 / d10) * (1 - Math.exp((dividend - rate) * T) * CND(d9));
            if (spot < d6) {
                d12 = BlackPrice("European Call", spot, strike, rate, rate - dividend, volatility, T) + d11 * Math.pow(spot / d6, d10);
            } else {
                d12 = spot - strike;
            }
        }
        return d12;
    }

    public static double RoundUp(double d) {
        double d1 = Math.round(d);
        if (d1 < d) {
            d1++;
        }
        return d1;
    }

    public static double BAWAmericanPutApprox(double spot,
                                              double strike,
                                              double expiration,
                                              double rate,
                                              double dividend,
                                              double volatility) {
        double d12 = 0;
        double d6 = Kp(strike, expiration, rate, dividend, volatility);
        double d7 = (2 * dividend) / (volatility * volatility);
        double d8 = (2 * rate) / (volatility * volatility * (1 - Math.exp(-rate * expiration)));
        double d9 = (Math.log(d6 / strike) + (dividend + (volatility * volatility) / 2) * expiration) / (volatility * Math.sqrt(expiration));
        double d10 = (-(d7 - 1) - Math.sqrt((d7 - 1) * (d7 - 1) + 4 * d8)) / 2;
        double d11 = -(d6 / d10) * (1 - Math.exp((dividend - rate) * expiration) * CND(-d9));
        if (spot > d6) {
            d12 = BlackPrice("European Put", spot, strike, rate, rate - dividend, volatility, expiration) + d11 *
                    Math.pow(spot / d6, d10);
        } else {
            d12 = strike - spot;
        }
        return d12;
    }

    public static double BSAmericanCallApprox(double spot,
                                              double strike,
                                              double expiration,
                                              double rate,
                                              double dividend,
                                              double volatility) {
        double d9 = 0;
        double d10 = 0;
        double d11 = 0;
        double d12 = 0;
        if (dividend >= rate) {
            d12 = BlackPrice("European Call", spot, strike, rate, rate - dividend, volatility, expiration);
        } else {
            d11 = (0.5 - dividend / (volatility * volatility)) + Math.sqrt(Math.pow(dividend / (volatility * volatility) - 0.5, 2) + (2 * rate) / (volatility * volatility));
            double d6 = (d11 / (d11 - 1)) * strike;
            double d7 = Math.max(strike, (rate / (rate - dividend)) * strike);
            double d8 = (-(dividend * expiration + 2 * volatility * Math.sqrt(expiration)) * d7) / (d6 - d7);
            d9 = d7 + (d6 - d7) * (1 - Math.exp(d8));
            d10 = (d9 - strike) * Math.pow(d9, -d11);
        }
        if (spot >= d9) {
            d12 = spot - strike;
        } else {
            d12 = (((d10 * Math.pow(spot, d11) - d10 * phi(spot, expiration, d11, d9, d9, rate, dividend, volatility)) + phi(spot, expiration, 1, d9, d9, rate, dividend, volatility)) - phi(spot, expiration, 1, strike, d9, rate, dividend, volatility) - strike * phi(spot, expiration, 0, d9, d9, rate, dividend, volatility)) + strike * phi(spot, expiration, 0, strike, d9, rate, dividend, volatility);
        }
        return d12;
    }

    public static double BSAmericanPutApprox(double d, double d1, double d2, double d3,
                                             double d4, double d5) {
        return BSAmericanCallApprox(d1, d, d2, d3 - d4, -d4, d5);
    }

    public static double Kp(double d, double d1, double d2, double d3,
                            double d4) {
        double d5 = (2 * d3) / (d4 * d4);
        double d6 = (2 * d2) / (d4 * d4);
        double d14 = (-(d5 - 1) - Math.sqrt((d5 - 1) * (d5 - 1) + 4 * d6)) / 2;
        double d7 = d / (1 - 1 / d14);
        double d9 = ((d3 * d1 - 2 * d4 * Math.sqrt(d1)) * d) / (d - d7);
        double d8 = d7 + (d - d7) * Math.exp(d9);
        double d10 = (2 * d2) / (d4 * d4 * (1 - Math.exp(-d2 * d1)));
        double d11 = (Math.log(d8 / d) + (d3 + (d4 * d4) / 2) * d1) / (d4 * Math.sqrt(d1));
        double d13 = (-(d5 - 1) - Math.sqrt((d5 - 1) * (d5 - 1) + 4 * d10)) / 2D;
        double d15 = d - d8;
        double d16 = BlackPrice("European Put", d8, d, d2, d2 - d3, d4, d1) - ((1 - Math.exp((d3 - d2) * d1) * CND(-d11)) * d8) / d13;
        double d17 = -Math.exp((d3 - d2) * d1) * CND(-d11) * (1 - 1 / d13) - (1 + (Math.exp((d3 - d2) * d1) * ND(-d11)) / (d4 * Math.sqrt(d1))) / d13;
        for (double d18 = 9.9999999999999995E-007D; Math.abs(d15 - d16) / d > d18;) {
            d8 = ((d - d16) + d17 * d8) / (1 + d17);
            double d12 = (Math.log(d8 / d) + (d3 + (d4 * d4) / 2D) * d1) / (d4 * Math.sqrt(d1));
            d15 = d - d8;
            d16 = BlackPrice("European Put", d8, d, d2, d2 - d3, d4, d1) - ((1 - Math.exp((d3 - d2) * d1) * CND(-d12)) * d8) / d13;
            d17 = -Math.exp((d3 - d2) * d1) * CND(-d12) * (1 - 1 / d13) - (1 + (Math.exp((d3 - d2) * d1) * CND(-d12)) / (d4 * Math.sqrt(d1))) / d13;
        }
        return d8;
    }
}