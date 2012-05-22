package com.netnumeri.shared.finance.math;

import com.google.gwt.user.client.Random;
import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.matrix.Matrix;
import com.netnumeri.shared.finance.utils.LogUtils;


public class FinRecipes extends FinMath implements FinConstants {

    public static class OptionResult {
        public double delta;
        public double gamma;
        public double theta;
        public double vega;
        public double rho;
    }

    public static double optionPriceAmericanCallDividend(double S,
                                                         double X,
                                                         double r,
                                                         double sigma,
                                                         double tau,
                                                         double D1,
                                                         double tau1) {
        if (D1 <= X * (1.0 - Math.exp(-r * (tau - tau1)))) // check for no exercise
        {
            return optionBlackScholesCall(S - Math.exp(-r * tau1) * D1, X, r, sigma, tau);
        }

        double sigma_sqr = sigma * sigma;
        double tau_sqrt = Math.sqrt(tau);
        double tau1_sqrt = Math.sqrt(tau1);
        double rho = -Math.sqrt(tau1 / tau);

        double S_bar = 0;  // first find the S_bar that solves c=S_bar+D1-X
        double S_low = 0;    // the simplest: binomial search
        double S_high = S;  // start by finding a very high S above S_bar
        double c = optionBlackScholesCall(S_high, X, r, sigma, tau - tau1);
        double test = c - S_high - D1 + X;
        while ((test > 0.0)
                && (S_high <= 1e10)) {
            S_high *= 2.0;
            c = optionBlackScholesCall(S_high, X, r, sigma, tau - tau1);
            test = c - S_high - D1 + X;
        }
        if (S_high > 1e10) { // early exercise never optimal, find BS value
            return optionBlackScholesCall(S - D1 * Math.exp(-r * tau1), X, r, sigma, tau);
        }

        S_bar = 0.5 * S_high;  // now find S_bar that solves c=S_bar-D+X
        c = optionBlackScholesCall(S_bar, X, r, sigma, tau - tau1);
        test = c - S_bar - D1 + X;

        while ((Math.abs(test) > ACCURACY)
                && ((S_high - S_low) > ACCURACY)) {
            if (test < 0.0) {
                S_high = S_bar;
            } else {
                S_low = S_bar;
            }
            ;
            S_bar = 0.5 * (S_high + S_low);
            c = optionBlackScholesCall(S_bar, X, r, sigma, tau - tau1);
            test = c - S_bar - D1 + X;
        }
        ;
        double a1 = (Math.log((S - D1 * Math.exp(-r * tau1)) / X) + (r + 0.5 * sigma_sqr) * tau)
                / (sigma * tau_sqrt);
        double a2 = a1 - sigma * tau_sqrt;
        double b1 = (Math.log((S - D1 * Math.exp(-r * tau1)) / S_bar) + (r + 0.5 * sigma_sqr) * tau1)
                / (sigma * tau1_sqrt);
        double b2 = b1 - sigma * tau1_sqrt;
        double C = (S - D1 * Math.exp(-r * tau1)) * N(b1)
                + (S - D1 * Math.exp(-r * tau1)) * N(a1, -b1, rho)
                - (X * Math.exp(-r * tau)) * N(a2, -b2, rho)
                - (X - D1) * Math.exp(-r * tau1) * N(b2);
        return C;
    }

    public static double optionPriceAmericanCallApproximatedBaw(double S,
                                                                double X,
                                                                double r,
                                                                double b,
                                                                double sigma,
                                                                double time) {
        double sigma_sqr = sigma * sigma;
        double time_sqrt = Math.sqrt(time);
        double nn = 2.0 * b / sigma_sqr;
        double m = 2.0 * r / sigma_sqr;
        double K = 1.0 - Math.exp(-r * time);
        double q2 = (-(nn - 1) + Math.sqrt(Math.pow((nn - 1), 2.0) + (4 * m / K))) * 0.5;

        // seed value from paper
        double q2_inf = 0.5 * ((-nn - 1.0) + Math.sqrt(Math.pow((nn - 1), 2.0) + 4.0 * m));
        double S_star_inf = X / (1.0 - 1.0 / q2_inf);
        double h2 = -(b * time + 2.0 * sigma * time_sqrt) * (X / (S_star_inf - X));
        double S_seed = X + (S_star_inf - X) * (1.0 - Math.exp(h2));

        int no_iterations = 0; // iterate on S to find S_star, using Newton steps
        double Si = S_seed;
        double g = 1;
        double gprime = 1.0;
        while ((Math.abs(g) > ACCURACY)
                && (Math.abs(gprime) > ACCURACY) // to avoid exploding Newton's
                && (no_iterations++ < 500)
                && (Si > 0.0)) {
            double c = optionEuropeanCallPayout(Si, X, r, b, sigma, time);
            double d1 = (Math.log(Si / X) + (b + 0.5 * sigma_sqr) * time) / (sigma * time_sqrt);
            g = (1.0 - 1.0 / q2) * Si - X - c + (1.0 / q2) * Si * Math.exp((b - r) * time) * N(d1);
            gprime = (1.0 - 1.0 / q2) * (1.0 - Math.exp((b - r) * time) * N(d1))
                    + (1.0 / q2) * Math.exp((b - r) * time) * n(d1) * (1.0 / (sigma * time_sqrt));
            Si = Si - (g / gprime);
        }
        ;
        double S_star = 0;
        if (Math.abs(g) > ACCURACY) {
            S_star = S_seed;
        } // did not converge
        else {
            S_star = Si;
        }
        ;
        double C = 0;
        double c = optionEuropeanCallPayout(S, X, r, b, sigma, time);
        if (S >= S_star) {
            C = S - X;
        } else {
            double d1 = (Math.log(S_star / X) + (b + 0.5 * sigma_sqr) * time) / (sigma * time_sqrt);
            double A2 = (1.0 - Math.exp((b - r) * time) * N(d1)) * (S_star / q2);
            C = c + A2 * Math.pow((S / S_star), q2);
        }
        ;
        return Math.max(C, c); // know value will never be less than BS value
    }

    public static double optionPriceAmericanPutApproximatedBaw(double S,
                                                               double X,
                                                               double r,
                                                               double b,
                                                               double sigma,
                                                               double time) {
        double sigma_sqr = sigma * sigma;
        double time_sqrt = Math.sqrt(time);
        double M = 2.0 * r / sigma_sqr;
        double NN = 2.0 * b / sigma_sqr;
        double K = 1.0 - Math.exp(-r * time);
        double q1 = 0.5 * (-(NN - 1) - Math.sqrt(Math.pow((NN - 1), 2.0) + (4.0 * M / K)));

        // now find initial S value
        double q1_inf = 0.5 * (-(NN - 1) - Math.sqrt(Math.pow((NN - 1), 2.0) + 4.0 * M));
        double S_star_star_inf = X / (1.0 - 1.0 / q1_inf);
        double h1 = (b * time - 2 * sigma * time_sqrt) * (X / (X - S_star_star_inf));
        double S_seed = S_star_star_inf + (X - S_star_star_inf) * Math.exp(h1);

        double Si = S_seed;  // now do Newton iterations to solve for S**
        int no_iterations = 0;
        double g = 1;
        double gprime = 1;
        while ((Math.abs(g) > ACCURACY)
                && (Math.abs(gprime) > ACCURACY) // to avoid non-convergence
                && (no_iterations++ < 500)
                && Si > 0.0) {
            double p = optionPriceEuropeanPutPayout(Si, X, r, b, sigma, time);
            double d1 = (Math.log(Si / X) + (b + 0.5 * sigma_sqr) * time) / (sigma * time_sqrt);
            g = X - Si - p + (1 - Math.exp((b - r) * time) * N(-d1)) * Si / q1;
            gprime = (1.0 / q1 - 1.0) * (1.0 - Math.exp((b - r) * time) * N(-d1))
                    + (1.0 / q1) * Math.exp((b - r) * time) * (1.0 / (sigma * time_sqrt)) * n(-d1);
            Si = Si - (g / gprime);
        }
        double S_star_star = Si;
        if (g > ACCURACY) {
            S_star_star = S_seed;
        }
        double P = 0;
        double p = optionPriceEuropeanPutPayout(S, X, r, b, sigma, time);
        if (S > S_star_star) {
            double d1 = (Math.log(S_star_star / X)
                    + (b + 0.5 * sigma_sqr) * time) / (sigma * time_sqrt);
            double A1 = -(S_star_star / q1) * (1 - Math.exp((b - r) * time) * N(-d1));
            P = p + A1 * Math.pow((S / S_star_star), q1);
        } else {
            P = X - S;
        }
        return Math.max(P, p);  // should not be lower than Black Scholes value
    }

    public static double optionCallAmericanBinomial(double S, // spot premium
                                                    double X, // exercice premium
                                                    double r, // interest rate
                                                    double sigma, // volatility
                                                    double t, // time to maturity
                                                    int steps) {    // no steps in binomial grid
        double R = Math.exp(r * (t / steps));            // interest rate for each step
        double Rinv = 1.0 / R;                    // inverse of interest rate
        double u = Math.exp(sigma * Math.sqrt(t / steps));    // up movement
        double uu = u * u;
        double d = 1.0 / u;
        double p_up = (R - d) / (u - d);
        double p_down = 1.0 - p_up;
        double[] prices = new double[steps + 1];       // premium of underlying
        double[] call_values = new double[steps + 1];       // value of corresponding call
        prices[0] = S * Math.pow(d, steps);  // padright in the endnodes.
        for (int i = 1; i <= steps; ++i) {
            prices[i] = uu * prices[i - 1];
        }
        for (int i = 0; i <= steps; ++i) {
            call_values[i] = Math.max(0.0, (prices[i] - X));
        }
        for (int step = steps - 1; step >= 0; --step) {
            for (int i = 0; i <= step; ++i) {
                call_values[i] = (p_up * call_values[i + 1] + p_down * call_values[i]) * Rinv;
                prices[i] = d * prices[i + 1];
                call_values[i] = Math.max(call_values[i], prices[i] - X);       // check for exercise
            }
        }
        return call_values[0];
    }


    public static double optionPriceDeltaAmericanCallBinomial(double S,
                                                              double X,
                                                              double r,
                                                              double sigma,
                                                              double t,
                                                              int no_steps) // steps in binomial
    {
        double[] prices = new double[no_steps + 1];
        double[] call_values = new double[no_steps + 1];

        double R = Math.exp(r * (t / no_steps));
        double Rinv = 1.0 / R;
        double u = Math.exp(sigma * Math.sqrt(t / no_steps));
        double d = 1.0 / u;
        double uu = u * u;
        double pUp = (R - d) / (u - d);
        double pDown = 1.0 - pUp;
        prices[0] = S * Math.pow(d, no_steps);
        int i;
        for (i = 1; i <= no_steps; ++i) {
            prices[i] = uu * prices[i - 1];
        }
        for (i = 0; i <= no_steps; ++i) {
            call_values[i] = Math.max(0.0, (prices[i] - X));
        }
        for (int CurrStep = no_steps - 1; CurrStep >= 1; --CurrStep) {
            for (i = 0; i <= CurrStep; ++i) {
                prices[i] = d * prices[i + 1];
                call_values[i] = (pDown * call_values[i] + pUp * call_values[i + 1]) * Rinv;
                call_values[i] = Math.max(call_values[i], prices[i] - X);        // check for exercise
            }
        }
        double delta = (call_values[1] - call_values[0]) / (S * u - S * d);
        return delta;
    }


    public static double optionPriceDeltaAmericanPutBinomial(double S, // spot premium
                                                             double X, // Exercise premium,
                                                             double r, // interest rate
                                                             double sigma, // volatility
                                                             double t, // time to maturity
                                                             int no_steps) // steps in binomial
    {
        double[] prices = new double[no_steps + 1];
        double[] put_values = new double[no_steps + 1];
        double R = Math.exp(r * (t / no_steps));
        double Rinv = 1.0 / R;
        double u = Math.exp(sigma * Math.sqrt(t / no_steps));
        double d = 1.0 / u;
        double uu = u * u;
        double pUp = (R - d) / (u - d);
        double pDown = 1.0 - pUp;
        prices[0] = S * Math.pow(d, no_steps);
        int i;
        for (i = 1; i <= no_steps; ++i) {
            prices[i] = uu * prices[i - 1];
        }
        for (i = 0; i <= no_steps; ++i) {
            put_values[i] = Math.max(0.0, (X - prices[i]));
        }
        for (int CurrStep = no_steps - 1; CurrStep >= 1; --CurrStep) {
            for (i = 0; i <= CurrStep; ++i) {
                prices[i] = d * prices[i + 1];
                put_values[i] = (pDown * put_values[i] + pUp * put_values[i + 1]) * Rinv;
                put_values[i] = Math.max(put_values[i], X - prices[i]);        // check for exercise
            }
        }
        double delta = (put_values[1] - put_values[0]) / (S * u - S * d);
        return delta;
    }

    public static double optionPriceCallAmericanDiscreteDividendsBinomial(double S,
                                                                          double X,
                                                                          double r,
                                                                          double sigma,
                                                                          double t,
                                                                          int steps,
                                                                          double[] dividend_times,
                                                                          double[] dividend_amounts)
    // given an amount of dividend, the binomial grid does not recombine, have to
    // start a new grid at each ex-dividend date.
    // do this recursively, at each ex dividend date, at each step, call the
    // binomial formula starting at that point to calculate the value of the live
    // option, and compare that to the value of exercising now.
    {
        int no_dividends = dividend_times.length;
        if (no_dividends == 0)               // just take the regular binomial
        {
            return optionCallAmericanBinomial(S, X, r, sigma, t, steps);
        }
        int steps_before_dividend = (int) (dividend_times[0] / t * steps);
        double R = Math.exp(r * (t / steps));
        double Rinv = 1.0 / R;
        double u = Math.exp(sigma * Math.sqrt(t / steps));
        double uu = u * u;
        double d = 1.0 / u;
        double pUp = (R - d) / (u - d);
        double pDown = 1.0 - pUp;
        double dividend_amount = dividend_amounts[0];
        double[] tmp_dividend_times = new double[no_dividends - 1];
        double[] tmp_dividend_amounts = new double[no_dividends - 1];
        for (int i = 0; i < no_dividends - 1; ++i) {
            tmp_dividend_amounts[i] = dividend_amounts[i + 1];
            tmp_dividend_times[i] = dividend_times[i + 1] - dividend_times[0];
        }
        double[] prices = new double[steps_before_dividend + 1];
        double[] call_values = new double[steps_before_dividend + 1];
        prices[0] = S * Math.pow(d, steps_before_dividend);
        for (int i = 1; i <= steps_before_dividend; ++i) {
            prices[i] = uu * prices[i - 1];
        }
        for (int i = 0; i <= steps_before_dividend; ++i) {
            double value_alive
                    = optionPriceCallAmericanDiscreteDividendsBinomial(prices[i] - dividend_amount,
                    X, r, sigma,
                    t - dividend_times[0], // time after first dividend
                    steps - steps_before_dividend,
                    tmp_dividend_times,
                    tmp_dividend_amounts);
            // what is the value of keeping the option alive?  Found recursively,
            // with one less dividend, the stock premium is current value
            // less the dividend.
            call_values[i] = Math.max(value_alive, (prices[i] - X));  // compare to exercising now
        }
        for (int step = steps_before_dividend - 1; step >= 0; --step) {
            for (int i = 0; i <= step; ++i) {
                prices[i] = d * prices[i + 1];
                call_values[i] = (pDown * call_values[i] + pUp * call_values[i + 1]) * Rinv;
                call_values[i] = Math.max(call_values[i], prices[i] - X);         // check for exercise
            }
        }
        return call_values[0];
    }

    public static double optionPricePutAmericanDiscreteDividendsBinomial(double S,
                                                                         double X,
                                                                         double r,
                                                                         double sigma,
                                                                         double t,
                                                                         int steps,
                                                                         double[] dividend_times,
                                                                         double[] dividend_amounts)
    // given an amount of dividend, the binomial grid does not recombine, have to
    // start a new grid at each ex-dividend date.
    // do this recursively, at each ex dividend date, at each step, put the
    // binomial formula starting at that point to calculate the value of the live
    // option, and compare that to the value of exercising now.
    {
        int no_dividends = dividend_times.length;
        if (no_dividends == 0)               // just take the regular binomial
        {
            return optionPricePutAmericanBinomial(S, X, r, sigma, t, steps);
        }
        int steps_before_dividend = (int) (dividend_times[0] / t * steps);

        double R = Math.exp(r * (t / steps));
        double Rinv = 1.0 / R;
        double u = Math.exp(sigma * Math.sqrt(t / steps));
        double uu = u * u;
        double d = 1.0 / u;
        double pUp = (R - d) / (u - d);
        double pDown = 1.0 - pUp;
        double dividend_amount = dividend_amounts[0];
        double[] tmp_dividend_times = new double[no_dividends - 1];
        double[] tmp_dividend_amounts = new double[no_dividends - 1];
        for (int i = 0; i < no_dividends - 1; ++i) {
            tmp_dividend_amounts[i] = dividend_amounts[i + 1];
            tmp_dividend_times[i] = dividend_times[i + 1] - dividend_times[0];
        }
        //   double[] prices(steps_before_dividend+1);
        //   double[] put_values(steps_before_dividend+1);
        double[] prices = new double[steps_before_dividend + 1];
        double[] put_values = new double[steps_before_dividend + 1];

        prices[0] = S * Math.pow(d, steps_before_dividend);
        for (int i = 1; i <= steps_before_dividend; ++i) {
            prices[i] = uu * prices[i - 1];
        }
        for (int i = 0; i <= steps_before_dividend; ++i) {
            double value_alive
                    = optionPricePutAmericanDiscreteDividendsBinomial(prices[i] - dividend_amount, X, r, sigma,
                    t - dividend_times[0], // time after first dividend
                    steps - steps_before_dividend,
                    tmp_dividend_times, tmp_dividend_amounts);
            // what is the value of keeping the option alive?  Found recursively,
            // with one less dividend, the stock premium is current value
            // less the dividend.
            put_values[i] = Math.max(value_alive, (X - prices[i]));  // compare to exercising now
        }
        for (int step = steps_before_dividend - 1; step >= 0; --step) {
            for (int i = 0; i <= step; ++i) {
                prices[i] = d * prices[i + 1];
                put_values[i] = (pDown * put_values[i] + pUp * put_values[i + 1]) * Rinv;
                put_values[i] = Math.max(put_values[i], X - prices[i]);         // check for exercise
            }
        }
        return put_values[0];
    }

    public static OptionResult optionPricePartialsAmericanCallBinomial(double S, // spot premium
                                                                       double X, // Exercise premium,
                                                                       double r, // interest rate
                                                                       double sigma, // volatility
                                                                       double time, // time to maturity
                                                                       int no_steps // steps in binomial
    ) {
        OptionResult opt = new OptionResult();
        double[] prices = new double[no_steps + 1];
        double[] call_values = new double[no_steps + 1];
        double delta_t = (time / no_steps);
        double R = Math.exp(r * delta_t);
        double Rinv = 1.0 / R;
        double u = Math.exp(sigma * Math.sqrt(delta_t));
        double d = 1.0 / u;
        double uu = u * u;
        double pUp = (R - d) / (u - d);
        double pDown = 1.0 - pUp;
        prices[0] = S * Math.pow(d, no_steps);
        for (int i = 1; i <= no_steps; ++i) {
            prices[i] = uu * prices[i - 1];
        }
        for (int i = 0; i <= no_steps; ++i) {
            call_values[i] = Math.max(0.0, (prices[i] - X));
        }
        for (int CurrStep = no_steps - 1; CurrStep >= 2; --CurrStep) {
            for (int i = 0; i <= CurrStep; ++i) {
                prices[i] = d * prices[i + 1];
                call_values[i] = (pDown * call_values[i] + pUp * call_values[i + 1]) * Rinv;
                call_values[i] = Math.max(call_values[i], prices[i] - X);        // check for exercise
            }
        }
        double f22 = call_values[2];
        double f21 = call_values[1];
        double f20 = call_values[0];
        for (int i = 0; i <= 1; i++) {
            prices[i] = d * prices[i + 1];
            call_values[i] = (pDown * call_values[i] + pUp * call_values[i + 1]) * Rinv;
            call_values[i] = Math.max(call_values[i], prices[i] - X);        // check for exercise
        }
        double f11 = call_values[1];
        double f10 = call_values[0];

        prices[0] = d * prices[1];
        call_values[0] = (pDown * call_values[0] + pUp * call_values[1]) * Rinv;
        call_values[0] = Math.max(call_values[0], S - X);        // check for exercise on first date

        double f00 = call_values[0];
        opt.delta = (f11 - f10) / (S * u - S * d);
        double h = 0.5 * S * (uu - d * d);
        opt.gamma = ((f22 - f21) / (S * (uu - 1)) - (f21 - f20) / (S * (1 - d * d))) / h;
        opt.theta = (f21 - f00) / (2 * delta_t);
        double diff = 0.02;
        double tmp_sigma = sigma + diff;
        double tmp_prices
                = optionCallAmericanBinomial(S, X, r, tmp_sigma, time, no_steps);
        opt.vega = (tmp_prices - f00) / diff;
        diff = 0.05;
        double tmp_r = r + diff;
        tmp_prices = optionCallAmericanBinomial(S, X, tmp_r, sigma, time, no_steps);
        opt.rho = (tmp_prices - f00) / diff;
        return opt;
    }

    public static OptionResult optionPricePartialsAmericanPutBinomial(double S, // spot prices
                                                                      double X, // Exercise prices,
                                                                      double r, // interest rate
                                                                      double sigma, // volatility
                                                                      double time, // time to maturity
                                                                      int no_steps // steps in binomial
    ) {
        OptionResult opt = new OptionResult();
        double[] prices = new double[no_steps + 1];
        double[] put_values = new double[no_steps + 1];

        double delta_t = (time / no_steps);
        double R = Math.exp(r * delta_t);
        double Rinv = 1.0 / R;
        double u = Math.exp(sigma * Math.sqrt(delta_t));
        double d = 1.0 / u;
        double uu = u * u;
        double pUp = (R - d) / (u - d);
        double pDown = 1.0 - pUp;
        prices[0] = S * Math.pow(d, no_steps);
        int i;
        for (i = 1; i <= no_steps; ++i) {
            prices[i] = uu * prices[i - 1];
        }
        for (i = 0; i <= no_steps; ++i) {
            put_values[i] = Math.max(0.0, (X - prices[i]));
        }
        for (int CurrStep = no_steps - 1; CurrStep >= 2; --CurrStep) {
            for (i = 0; i <= CurrStep; ++i) {
                prices[i] = d * prices[i + 1];
                put_values[i] = (pDown * put_values[i] + pUp * put_values[i + 1]) * Rinv;
                put_values[i] = Math.max(put_values[i], X - prices[i]); // check for exercise
            }
            ;
        }
        ;
        double f22 = put_values[2];
        double f21 = put_values[1];
        double f20 = put_values[0];
        for (i = 0; i <= 1; i++) {
            prices[i] = d * prices[i + 1];
            put_values[i] = (pDown * put_values[i] + pUp * put_values[i + 1]) * Rinv;
            put_values[i] = Math.max(put_values[i], X - prices[i]); // check for exercise
        }
        ;
        double f11 = put_values[1];
        double f10 = put_values[0];
        prices[0] = d * prices[1];
        put_values[0] = (pDown * put_values[0] + pUp * put_values[1]) * Rinv;
        put_values[0] = Math.max(put_values[0], X - prices[i]); // check for exercise
        double f00 = put_values[0];
        opt.delta = (f11 - f10) / (S * (u - d));
        double h = 0.5 * S * (uu - d * d);
        opt.gamma = ((f22 - f21) / (S * (uu - 1.0)) - (f21 - f20) / (S * (1.0 - d * d))) / h;
        opt.theta = (f21 - f00) / (2 * delta_t);
        double diff = 0.02;
        double tmp_sigma = sigma + diff;
        double tmp_prices = optionPricePutAmericanBinomial(S, X, r, tmp_sigma, time, no_steps);
        opt.vega = (tmp_prices - f00) / diff;
        diff = 0.05;
        double tmp_r = r + diff;
        tmp_prices = optionPricePutAmericanBinomial(S, X, tmp_r, sigma, time, no_steps);
        opt.rho = (tmp_prices - f00) / diff;
        return opt;
    }

    // given a dividend yield, the binomial grid recombines

    public static double optionPriceCallAmericanProportionalDividendsBinomial(double S,
                                                                              double X,
                                                                              double r,
                                                                              double sigma,
                                                                              double time,
                                                                              int no_steps,
                                                                              double[] dividend_times,
                                                                              double[] dividend_yields) {
        int no_dividends = dividend_times.length;
        if (no_dividends == 0)               // just take the regular binomial
        {
            return optionCallAmericanBinomial(S, X, r, sigma, time, no_steps);
        }
        double R = Math.exp(r * (time / no_steps));
        double Rinv = 1.0 / R;
        double u = Math.exp(sigma * Math.sqrt(time / no_steps));
        double uu = u * u;
        double d = 1.0 / u;
        double pUp = (R - d) / (u - d);
        double pDown = 1.0 - pUp;

        int[] dividend_steps = new int[no_dividends]; // when dividends are paid
        for (int i = 0; i < no_dividends; ++i) {
            dividend_steps[i] = (int) (dividend_times[i] / time * no_steps);
        }
        double[] prices = new double[no_steps + 1];
        double[] call_prices = new double[no_steps + 1];

        prices[0] = S * Math.pow(d, no_steps);
        for (int i = 0; i < no_dividends; ++i) {
            prices[0] *= (1.0 - dividend_yields[i]);
        }
        for (int i = 1; i <= no_steps; ++i) {
            prices[i] = uu * prices[i - 1]; // terminal grid nodes
        }
        for (int i = 0; i <= no_steps; ++i) {
            call_prices[i] = Math.max(0.0, (prices[i] - X));
        }
        for (int step = no_steps - 1; step >= 0; --step) {
            for (int i = 0; i < no_dividends; ++i) {   // check whether dividend paid
                if (step == dividend_steps[i]) {
                    for (int j = 0; j <= step; ++j) {
                        prices[j] *= (1.0 / (1.0 - dividend_yields[i]));
                    }
                }
            }
            for (int i = 0; i <= step; ++i) {
                prices[i] = d * prices[i + 1];
                call_prices[i] = (pDown * call_prices[i] + pUp * call_prices[i + 1]) * Rinv;
                call_prices[i] = Math.max(call_prices[i], prices[i] - X);
            }
        }
        return call_prices[0];
    }

    public static double optionPricePutAmericanProportionalDividendsBinomial(double S,
                                                                             double X,
                                                                             double r,
                                                                             double sigma,
                                                                             double time,
                                                                             int no_steps,
                                                                             double[] dividend_times,
                                                                             double[] dividend_yields)
    // given a dividend yield, the binomial grid recombines
    {
        int no_dividends = dividend_times.length;
        if (no_dividends == 0)               // just take the regular binomial
        {
            return optionPricePutAmericanBinomial(S, X, r, sigma, time, no_steps);
        }
        double R = Math.exp(r * (time / no_steps));
        double Rinv = 1.0 / R;
        double u = Math.exp(sigma * Math.sqrt(time / no_steps));
        double uu = u * u;
        double d = 1.0 / u;
        double pUp = (R - d) / (u - d);
        double pDown = 1.0 - pUp;

        //  vector<int>  dividend_steps(no_dividends); // when dividends are paid
        int[] dividend_steps = new int[no_dividends];
        for (int i = 0; i < no_dividends; ++i) {
            dividend_steps[i] = (int) (dividend_times[i] / time * no_steps);
        }
        double[] prices = new double[no_steps + 1];
        double[] put_prices = new double[no_steps + 1];
        prices[0] = S * Math.pow(d, no_steps);
        for (int i = 0; i < no_dividends; ++i) {
            prices[0] *= (1.0 - dividend_yields[i]);
        }
        for (int i = 1; i <= no_steps; ++i) {
            prices[i] = uu * prices[i - 1]; // terminal grid nodes
        }
        for (int i = 0; i <= no_steps; ++i) {
            put_prices[i] = Math.max(0.0, (X - prices[i]));
        }
        for (int step = no_steps - 1; step >= 0; --step) {
            for (int i = 0; i < no_dividends; ++i) {   // check whether dividend paid
                if (step == dividend_steps[i]) {
                    for (int j = 0; j <= step; ++j) {
                        prices[j] *= (1.0 / (1.0 - dividend_yields[i]));
                    }
                }
            }
            for (int i = 0; i <= step; ++i) {
                prices[i] = d * prices[i + 1];
                put_prices[i] = (pDown * put_prices[i] + pUp * put_prices[i + 1]) * Rinv;
                put_prices[i] = Math.max(put_prices[i], X - prices[i]);         // check for exercise
            }
        }
        return put_prices[0];
    }

    public static double optionPricePutAmericanBinomial(double S, // spot premium
                                                        double X, // exercice premium
                                                        double r, // interest rate
                                                        double sigma, // volatility
                                                        double t, // time to maturity
                                                        int steps)  // no steps in binomial grid
    {
        double R = Math.exp(r * (t / steps));            // interest rate for each step
        double Rinv = 1.0 / R;                    // inverse of interest rate
        double u = Math.exp(sigma * Math.sqrt(t / steps));    // up movement
        double uu = u * u;
        double d = 1.0 / u;
        double p_up = (R - d) / (u - d);
        double p_down = 1.0 - p_up;
        // double[] prices(steps+1);       // premium of underlying
        // double[] put_values(steps+1);       // value of corresponding put
        double[] prices = new double[steps + 1];
        double[] put_values = new double[steps + 1];
        prices[0] = S * Math.pow(d, steps);  // padright in the endnodes.
        for (int i = 1; i <= steps; ++i) {
            prices[i] = uu * prices[i - 1];
        }
        for (int i = 0; i <= steps; ++i) {
            put_values[i] = Math.max(0.0, (X - prices[i]));
        }
        for (int step = steps - 1; step >= 0; --step) {
            for (int i = 0; i <= step; ++i) {
                put_values[i] = (p_up * put_values[i + 1] + p_down * put_values[i]) * Rinv;
                prices[i] = d * prices[i + 1];
                put_values[i] = Math.max(put_values[i], (X - prices[i]));       // check for exercise
            }
        }
        return put_values[0];
    }

    public static double optionPriceCallEuropeanBinomial(double S, // spot premium
                                                         double X, // exercice premium
                                                         double r, // interest rate
                                                         double sigma, // volatility
                                                         double t, // time to maturity
                                                         int steps)  // no steps in binomial grid
    {
        double R = Math.exp(r * (t / steps));            // interest rate for each step
        double Rinv = 1.0 / R;                    // inverse of interest rate
        double u = Math.exp(sigma * Math.sqrt(t / steps));    // up movement
        double uu = u * u;
        double d = 1.0 / u;
        double p_up = (R - d) / (u - d);
        double p_down = 1.0 - p_up;
        double[] prices = new double[steps + 1];
        prices[0] = S * Math.pow(d, steps);
        for (int i = 1; i <= steps; ++i) {
            prices[i] = uu * prices[i - 1];
        }
        double[] call_values = new double[steps + 1];
        for (int i = 0; i <= steps; ++i) {
            call_values[i] = Math.max(0.0, (prices[i] - X));
        }
        for (int step = steps - 1; step >= 0; --step) {
            for (int i = 0; i <= step; ++i) {
                call_values[i] = (p_up * call_values[i + 1] + p_down * call_values[i]) * Rinv;
            }
        }
        return call_values[0];
    }

    public static double optionPricePutEuropeanBinomial(double S, // spot premium
                                                        double X, // exercice premium
                                                        double r, // interest rate
                                                        double sigma, // volatility
                                                        double t, // time to maturity
                                                        int steps)  // no steps in binomial grid
    {
        double R = Math.exp(r * (t / steps));            // interest rate for each step
        double Rinv = 1.0 / R;                    // inverse of interest rate
        double u = Math.exp(sigma * Math.sqrt(t / steps));    // up movement
        double uu = u * u;
        double d = 1.0 / u;
        double p_up = (R - d) / (u - d);
        double p_down = 1.0 - p_up;
        double[] prices = new double[steps + 1];
        prices[0] = S * Math.pow(d, steps);  // padright in the endnodes.
        for (int i = 1; i <= steps; ++i) {
            prices[i] = uu * prices[i - 1];
        }
        double[] put_values = new double[steps + 1];
        for (int i = 0; i <= steps; ++i) {
            put_values[i] = Math.max(0.0, (X - prices[i]));
        }
        for (int step = steps - 1; step >= 0; --step) {
            for (int i = 0; i <= step; ++i) {
                put_values[i] = (p_up * put_values[i + 1] + p_down * put_values[i]) * Rinv;
            }
        }
        return put_values[0];
    }

    public static double optionBlackScholesCall(double S, // spot premium
                                                double X, // Strike (exercise) premium,
                                                double r, // interest rate
                                                double sigma,
                                                double time) {
        double time_sqrt = Math.sqrt(time);
        double d1 = (Math.log(S / X) + r * time) / (sigma * time_sqrt) + 0.5 * sigma * time_sqrt;
        double d2 = d1 - (sigma * time_sqrt);
        double c = S * N(d1) - X * Math.exp(-r * time) * N(d2);
        return c;
    }

    public static double optionPriceEuropeanCallDividends(double S,
                                                          double X,
                                                          double r,
                                                          double sigma,
                                                          double time_to_maturity,
                                                          double[] dividend_times,
                                                          double[] dividend_amounts)
    // reduce the current stock premium by the amount of dividends.
    {
        for (int i = 0; i < dividend_times.length; i++) {
            if (dividend_times[i] <= time_to_maturity) {
                S -= dividend_amounts[i] * Math.exp(-r * dividend_times[i]);
            }
            ;
        }
        ;
        return optionBlackScholesCall(S, X, r, sigma, time_to_maturity);
    }

    ;


    public static double optionPriceDeltaCallBlackScholes(double S, // spot premium
                                                          double X, // Strike (exercise) premium,
                                                          double r, // interest rate
                                                          double sigma, // volatility
                                                          double time) {  // time to maturity
        double time_sqrt = Math.sqrt(time);
        double d1 = (Math.log(S / X) + r * time) / (sigma * time_sqrt) + 0.5 * sigma * time_sqrt;
        double delta = N(d1);
        return delta;
    }

    public static double optionPriceDeltaPutBlackScholes(double S, // spot premium
                                                         double X, // Strike (exercise) premium,
                                                         double r, // interest rate
                                                         double sigma,
                                                         double time) {
        double time_sqrt = Math.sqrt(time);
        double d1 = (Math.log(S / X) + r * time) / (sigma * time_sqrt) + 0.5 * sigma * time_sqrt;
        double delta = -N(-d1);
        return delta;
    }

    // file black_scholes_imp_vol_bisect.cc
    // author: Bernt A Oedegaard

    public static double optionPriceImpliedVolatilityCallBlackScholesBisections(double S, double X, double r, double time, double option_price) { // check for arbitrage violations:
        // if premium at almost zero volatility greater than premium, return 0

        double sigma_low = 0.0001;
        double price = optionBlackScholesCall(S, X, r, sigma_low, time);
        if (price > option_price) {
            return 0.0;
        }

        // simple binomial search for the implied volatility.
        // relies on the value of the option increasing in volatility
        double ACCURACY = 1.0e-5; // make this smaller for higher accuracy
        int MAX_ITERATIONS = 100;
        double HIGH_VALUE = 1e10;
        double ERROR = -1e40;

        // want to bracket sigma. first find a maximum sigma by finding a sigma
        // with a estimated premium higher than the actual premium.
        double sigma_high = 0.3;
        price = optionBlackScholesCall(S, X, r, sigma_high, time);
        while (price < option_price) {
            sigma_high = 2.0 * sigma_high; // keep doubling.
            price = optionBlackScholesCall(S, X, r, sigma_high, time);
            if (sigma_high > HIGH_VALUE) {
                return ERROR; // panic, something wrong.
            }
        }
        ;
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            double sigma = (sigma_low + sigma_high) * 0.5;
            price = optionBlackScholesCall(S, X, r, sigma, time);
            double test = (price - option_price);
            if (Math.abs(test) < ACCURACY) {
                return sigma;
            }
            ;
            if (test < 0.0) {
                sigma_low = sigma;
            } else {
                sigma_high = sigma;
            }
        }
        ;
        return ERROR;
    }

    public static double optionPriceImpliedVolatilityCallBlackScholesNewton(double S, double X, double r, double time, double option_price) {
        // check for arbitrage violations:
        // if premium at almost zero volatility greater than premium, return 0
        double sigma_low = 1e-5;
        double price = optionBlackScholesCall(S, X, r, sigma_low, time);
        if (price > option_price) {
            return 0.0;
        }

        int MAX_ITERATIONS = 100;
        double ACCURACY = 1.0e-4;
        double t_sqrt = Math.sqrt(time);

        double sigma = (option_price / S) / (0.398 * t_sqrt);    // find initial value
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            price = optionBlackScholesCall(S, X, r, sigma, time);
            double diff = option_price - price;
            if (Math.abs(diff) < ACCURACY) {
                return sigma;
            }
            double d1 = (Math.log(S / X) + r * time) / (sigma * t_sqrt) + 0.5 * sigma * t_sqrt;
            double vega = S * t_sqrt * n(d1);
            sigma = sigma + diff / vega;
        }
        ;
        return -99e10;  // something screwy happened, should throw exception
    }

    public static OptionResult optionPricePartialsCallBlackScholes(double S, // spot premium
                                                                   double X, // Strike (exercise) premium,
                                                                   double r, // interest rate
                                                                   double sigma, // volatility
                                                                   double time  // time to maturity
    ) {
        OptionResult opt = new OptionResult();
        double time_sqrt = Math.sqrt(time);
        double d1 = (Math.log(S / X) + r * time) / (sigma * time_sqrt) + 0.5 * sigma * time_sqrt;
        double d2 = d1 - (sigma * time_sqrt);

        opt.delta = N(d1);
        opt.gamma = n(d1) / (S * sigma * time_sqrt);
        opt.theta = -(S * sigma * n(d1)) / (2 * time_sqrt) - r * X * Math.exp(-r * time) * N(d2);
        opt.vega = S * time_sqrt * n(d1);
        opt.rho = X * time * Math.exp(-r * time) * N(d2);
        return opt;
    }

    public static OptionResult optionPricePartialsPutBlackScholes(double S, // spot premium
                                                                  double X, // Strike (exercise) premium,
                                                                  double r, // interest rate
                                                                  double sigma,
                                                                  double time) {
        OptionResult opt = new OptionResult();
        double time_sqrt = Math.sqrt(time);
        double d1 = (Math.log(S / X) + r * time) / (sigma * time_sqrt) + 0.5 * sigma * time_sqrt;
        double d2 = d1 - (sigma * time_sqrt);
        opt.delta = -N(-d1);
        opt.gamma = n(d1) / (S * sigma * time_sqrt);
        opt.theta = -(S * sigma * n(d1)) / (2 * time_sqrt) + r * X * Math.exp(-r * time) * N(-d2);
        opt.vega = S * time_sqrt * n(d1);
        opt.rho = -X * time * Math.exp(-r * time) * N(-d2);
        return opt;
    }

    public static double optionEuropeanCallPayout(double S, // spot premium
                                                  double X, // Strike (exercise) premium,
                                                  double r, // interest rate
                                                  double q, // yield on underlying
                                                  double sigma, // volatility
                                                  double time) { // time to maturity
        double sigma_sqr = Math.pow(sigma, 2);
        double time_sqrt = Math.sqrt(time);
        double d1 = (Math.log(S / X) + (r - q + 0.5 * sigma_sqr) * time) / (sigma * time_sqrt);
        double d2 = d1 - (sigma * time_sqrt);
        double call_price = S * Math.exp(-q * time) * N(d1) - X * Math.exp(-r * time) * N(d2);
        return call_price;
    }

    public static double optionPriceEuropeanPutPayout(double S, // spot premium
                                                      double X, // Strike (exercise) premium,
                                                      double r, // interest rate
                                                      double q, // yield on underlying
                                                      double sigma,
                                                      double time) {
        double sigma_sqr = Math.pow(sigma, 2);
        double time_sqrt = Math.sqrt(time);
        double d1 = (Math.log(S / X) + (r - q + 0.5 * sigma_sqr) * time) / (sigma * time_sqrt);
        double d2 = d1 - (sigma * time_sqrt);
        double put_price = X * Math.exp(-r * time) * N(-d2) - S * Math.exp(-q * time) * N(-d1);
        return put_price;
    }

    public static double optionPricePutBlackScholes(double S, // spot premium
                                                    double X, // Strike (exercise) premium,
                                                    double r, // interest rate
                                                    double sigma, // volatility
                                                    double time) {
        double time_sqrt = Math.sqrt(time);
        double d1 = (Math.log(S / X) + r * time) / (sigma * time_sqrt) + 0.5 * sigma * time_sqrt;
        double d2 = d1 - (sigma * time_sqrt);
        double p = X * Math.exp(-r * time) * N(-d2) - S * N(-d1);
        return p;
    }

    public static double optionPriceEuropeanPutDividends(double S, // spot premium
                                                         double X, // Strike (exercise) premium,
                                                         double r, // interest rate
                                                         double sigma,
                                                         double time_to_maturity,
                                                         double[] dividend_times,
                                                         double[] dividend_amounts) {
        // reduce the current stock premium by the amount of dividends.
        for (int i = 0; i < dividend_times.length; i++) {
            if (dividend_times[i] <= time_to_maturity) {
                S -= dividend_amounts[i] * Math.exp(-r * dividend_times[i]);
            }
            ;
        }
        ;
        return optionPricePutBlackScholes(S, X, r, sigma, time_to_maturity);
    }


    public static double bondOptionPriceCallZeroBlackScholes(double B, double X, double r, double sigma, double time) {
        double time_sqrt = Math.sqrt(time);
        double d1 = (Math.log(B / X) + r * time) / (sigma * time_sqrt) + 0.5 * sigma * time_sqrt;
        double d2 = d1 - (sigma * time_sqrt);
        double c = B * N(d1) - X * Math.exp(-r * time) * N(d2);
        return c;
    }

    public static double bondOptionPriceCallCouponBondBlackScholes(double B, double X, double r, double sigma, double time,
                                                                   double[] coupon_times, double[] coupon_amounts) {
        for (int i = 0; i < coupon_times.length; i++) { // subtract present value of coupons
            if (coupon_times[i] <= time) { // coupon paid befor option expiry
                B -= coupon_amounts[i] * Math.exp(-r * coupon_times[i]);
            }
        }
        return bondOptionPriceCallZeroBlackScholes(B, X, r, sigma, time);
    }

    public static double bondOptionPriceCallZeroAmericanRendlemanBartter(double X,
                                                                         double option_maturity,
                                                                         double S,
                                                                         double M, // term structure paramters
                                                                         double interest, // current short interest rate
                                                                         double bond_maturity, // time to maturity for underlying bond
                                                                         double maturity_payment,
                                                                         int no_steps)
    //  call on a zero coupon bond.
    {
        double delta_t = bond_maturity / no_steps;
        double u = Math.exp(S * Math.sqrt(delta_t));
        double d = 1 / u;
        double p_up = (Math.exp(M * delta_t) - d) / (u - d);
        double p_down = 1.0 - p_up;

        //double[] r(no_steps+1);
        double[] r = new double[no_steps + 1];
        r[0] = interest * Math.pow(d, no_steps);
        double uu = u * u;
        int i;
        for (i = 1; i <= no_steps; ++i) {
            r[i] = r[i - 1] * uu;
        }
        //        double[] P(no_steps+1);
        double[] P = new double[no_steps + 1];
        for (i = 0; i <= no_steps; ++i) {
            P[i] = maturity_payment;
        }
        int no_call_steps = (int) doubleToLongBits(no_steps * option_maturity / bond_maturity);
        for (int curr_step = no_steps; curr_step > no_call_steps; --curr_step) {
            for (i = 0; i < curr_step; i++) {
                r[i] = r[i] * u;
                P[i] = Math.exp(-r[i] * delta_t) * (p_down * P[i] + p_up * P[i + 1]);
            }
        }
        //       double[] C(no_call_steps+1);
        double[] C = new double[no_call_steps + 1];
        for (i = 0; i <= no_call_steps; ++i) {
            C[i] = Math.max(0.0, P[i] - X);
        }
        for (int curr_step = no_call_steps; curr_step >= 0; --curr_step) {
            for (i = 0; i < curr_step; i++) {
                r[i] = r[i] * u;
                P[i] = Math.exp(-r[i] * delta_t) * (p_down * P[i] + p_up * P[i + 1]);
                C[i] = Math.max(P[i] - X, Math.exp(-r[i] * delta_t) * (p_up * C[i + 1] + p_down * C[i]));
            }
        }
        return C[0];
    }

    public static long doubleToLongBits(final double v) {
        if (Double.isNaN(v)) {
// IEEE754, NaN exponent bits all 1s, and mantissa is non-zero
            return 0x0FFFl << 51;
        }

        long sign = (v < 0 ? 0x1l << 63 : 0);
        long exponent = 0;

        double absV = Math.abs(v);
// IEEE754 infinite numbers, exponent all 1s, mantissa is 0
        if (Double.isInfinite(v)) {
            exponent = 0x07FFl << 52;
        } else {
            if (absV == 0.0) {
// IEEE754, exponent is 0, mantissa is zero
// we don't handle negative zero at the moment, it is treated as
// positive zero
                exponent = 0l;
            } else {
// get an approximation to the exponent
                int guess = (int) Math.floor(Math.log(absV) / Math.log(2));
// force it to -1023, 1023 interval (<= -1023 = denorm/zero)
                guess = Math.max(-1023, Math.min(guess, 1023));

// divide away exponent guess
                double exp = Math.pow(2, guess);
                absV = absV / exp;

// while the number is still bigger than a normalized number
// increment exponent guess
                while (absV > 2.0) {
                    guess++;
                    absV /= 2.0;
                }
// if the number is smaller than a normalized number
// decrement exponent
                while (absV < 1 && guess > 1024) {
                    guess--;
                    absV *= 2;
                }
                exponent = (guess + 1023l) << 52;
            }
        }
// if denorm
        if (exponent <= 0) {
            absV /= 2;
        }

// the input value has now been stripped of its exponent // and is in the range [0,2), we strip off the leading decimal // and use the remainer as a percentage of the significand value (2^52)
        long mantissa = (long) ((absV % 1) * Math.pow(2, 52));
        return sign | exponent | (mantissa & 0xfffffffffffffl);
    }

    public static double bondOptionPriceCallZeroVasicek(double X, // exercise premium
                                                        double r, // current interest rate
                                                        double option_time_to_maturity,
                                                        double bond_time_to_maturity,
                                                        double a, // parameters
                                                        double b,
                                                        double sigma) {
        double T_t = option_time_to_maturity;
        double s_t = bond_time_to_maturity;
        double T_s = s_t - T_t;
        double v_t_T;
        double sigma_P;
        if (a == 0.0) {
            v_t_T = sigma * Math.sqrt(T_t);
            sigma_P = sigma * T_s * Math.sqrt(T_t);
        } else {
            v_t_T = Math.sqrt(sigma * sigma * (1 - Math.exp(-2 * a * T_t)) / (2 * a));
            double B_T_s = (1 - Math.exp(-a * T_s)) / a;
            sigma_P = v_t_T * B_T_s;
        }
        double h = (1.0 / sigma_P) * Math.log(term_structure_discount_factor_vasicek(s_t, r, a, b, sigma) /
                (term_structure_discount_factor_vasicek(T_t, r, a, b, sigma) * X))
                + sigma_P / 2.0;
        double c = term_structure_discount_factor_vasicek(s_t, r, a, b, sigma) * N(h)
                - X * term_structure_discount_factor_vasicek(T_t, r, a, b, sigma) * N(h - sigma_P);
        return c;
    }

    public static double bondOptionPricePutZeroBlackScholes(double B, double X, double r, double sigma, double time) {
        double time_sqrt = Math.sqrt(time);
        double d1 = (Math.log(B / X) + r * time) / (sigma * time_sqrt) + 0.5 * sigma * time_sqrt;
        double d2 = d1 - (sigma * time_sqrt);
        double p = X * Math.exp(-r * time) * N(-d2) - B * N(-d1);
        return p;
    }

    public static double bondOptionPricePutCouponBondBlackScholes(double B, double X, double r, double sigma, double time,
                                                                  double[] coupon_times, double[] coupon_amounts) {
        for (int i = 0; i < coupon_times.length; i++) {
            if (coupon_times[i] <= time) {
                B -= coupon_amounts[i] * Math.exp(-r * coupon_times[i]);
            }
        }
        return bondOptionPricePutZeroBlackScholes(B, X, r, sigma, time);
    }

    public static double bondOptionPricePutZeroVasicek(double X, // exercise premium
                                                       double r, // current interest rate
                                                       double option_time_to_maturity,
                                                       double bond_time_to_maturity,
                                                       double a, // parameters
                                                       double b,
                                                       double sigma) {
        double s_t = bond_time_to_maturity;
        double T_t = option_time_to_maturity;
        double T_s = s_t - T_t;
        double v_t_T;
        double sigma_P;
        if (a == 0.0) {
            v_t_T = sigma * Math.sqrt(T_t);
            sigma_P = sigma * T_s * Math.sqrt(T_t);
        } else {
            v_t_T = Math.sqrt(sigma * sigma * (1 - Math.exp(-2 * a * T_t)) / (2 * a));
            double B_T_s = (1 - Math.exp(-a * T_s)) / a;
            sigma_P = v_t_T * B_T_s;
        }
        double h = (1.0 / sigma_P) * Math.log(term_structure_discount_factor_vasicek(s_t, r, a, b, sigma) /
                (term_structure_discount_factor_vasicek(T_t, r, a, b, sigma) * X))
                + sigma_P / 2.0;
        double p =
                term_structure_discount_factor_vasicek(T_t, r, a, b, sigma) * N(-h + sigma_P)
                        - term_structure_discount_factor_vasicek(s_t, r, a, b, sigma) * N(-h);
        return p;
    }

    public static double bondsConvexity(double[] cashflow_times, double[] cashflow_amounts, double y) {
        double C = 0;
        for (int i = 0; i < cashflow_times.length; i++) {
            double t = cashflow_times[i];
            C += cashflow_amounts[i] * t * t * Math.exp(-y * t);
        }
        return C;
    }

    //   calculate the duration of a bond, simple case where the term
    //   structure is flat, interest rate r.

    public static double bondsDuration(double[] cashflow_times, double[] cashflows, double r) {
        double S = 0;
        double D1 = 0;
        for (int i = 0; i < cashflow_times.length; i++) {
            S += cashflows[i] * Math.exp(-r * cashflow_times[i]);
            D1 += cashflow_times[i] * cashflows[i] * Math.exp(-r * cashflow_times[i]);
        }
        ;
        return D1 / S;
    }

    public static double bondsDurationMacaulay(double[] cashflow_times,
                                               double[] cashflows, double bond_price) {
        double y = bondsYieldToMaturity(cashflow_times, cashflows, bond_price);
        return bondsDuration(cashflow_times, cashflows, y); // use YTM in duration
    }

    public static double bondsDurationModified(double[] cashflow_times,
                                               double[] cashflow_amounts, double bond_price, double r) {
        double dur = bondsDuration(cashflow_times, cashflow_amounts, r);
        double y = bondsYieldToMaturity(cashflow_times, cashflow_amounts,
                bond_price);
        return dur / y;

    }

    public static double bondsDurationModified(double[] cashflow_times,
                                               double[] cashflow_amounts,
                                               double bond_price,
                                               TermStructure d) {
        double y = bondsYieldToMaturity(cashflow_times, cashflow_amounts, bond_price);
        double dur = bondsDuration(cashflow_times, cashflow_amounts, d);
        return dur / y;
    }

    public static double bondsDuration(double[] cashflow_times,
                                       double[] cashflow_amounts,
                                       TermStructure d) {
        double S = 0;
        double D1 = 0;
        for (int i = 0; i < cashflow_times.length; i++) {
            S += cashflow_amounts[i] * d.discount_factor(cashflow_times[i]);
            D1 += cashflow_times[i] * cashflow_amounts[i] * d.discount_factor(cashflow_times[i]);
        }
        ;
        return D1 / S;
    }

    // calculate bond premium when term structure is flat,

    public static double bondsPrice(double[] cashflow_times,
                                    double[] cashflows,
                                    double r) {
        double p = 0;
        for (int i = 0; i < cashflow_times.length; i++) {
            p += Math.exp(-r * cashflow_times[i]) * cashflows[i];
        }
        return p;
    }

    /* calculate bond premium when term structure is flat,
     * given both coupon and principals
     */

    public static double bondsPrice(double[] coupon_times,
                                    double[] coupon_amounts,
                                    double[] principal_times,
                                    double[] principal_amounts,
                                    double r) {
        double p = 0;
        for (int i = 0; i < coupon_times.length; i++) {
            p += Math.exp(-r * coupon_times[i]) * coupon_amounts[i];
        }
        ;
        for (int i = 0; i < principal_times.length; i++) {
            p += Math.exp(-r * principal_times[i]) * principal_amounts[i];
        }
        ;
        return p;
    }

    // calculate bond premium when term structure is flat,

    public static double bondsPriceDiscrete(double[] cashflow_times,
                                            double[] cashflows,
                                            double r) {
        double p = 0;
        for (int i = 0; i < cashflow_times.length; i++) {
            p += cashflows[i] / (Math.pow((1 + r), cashflow_times[i]));
        }
        return p;
    }

    public static double bondsPrice(double[] cashflow_times,
                                    double[] cashflows,
                                    TermStructure d) {
        double p = 0;
        for (int i = 0; i < cashflow_times.length; i++) {
            p += d.discount_factor(cashflow_times[i]) * cashflows[i];
        }
        return p;
    }

    public static double bondsYieldToMaturity(double[] cashflow_times,
                                              double[] cashflow_amounts,
                                              double bondprice) {
        double ACCURACY = 1e-5;
        int MAX_ITERATIONS = 200;
        double bot = 0, top = 1.0;
        while (bondsPrice(cashflow_times, cashflow_amounts, top) > bondprice) {
            top = top * 2;
        }
        double r = 0.5 * (top + bot);
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            double diff = bondsPrice(cashflow_times, cashflow_amounts, r) - bondprice;
            if (Math.abs(diff) < ACCURACY) {
                return r;
            }
            if (diff > 0.0) {
                bot = r;
            } else {
                top = r;
            }
            ;
            r = 0.5 * (top + bot);
        }
        return r;
    }

    // simple minded irr function.  Will find one root (if it exists.)
    // adapted from routine in Numerical Recipes in C.

    /**
     * @param cflow_times
     * @param cflow_amounts
     */
    public static double cashFlowIrr(double[] cflow_times, double[] cflow_amounts) {
        if (cflow_times.length != cflow_amounts.length) {
            return ERROR;
        }
        double ACCURACY = 1.0e-5;
        int MAX_ITERATIONS = 50;
        double x1 = 0.0;
        double x2 = 0.2;

        // newInstance an initial bracket, with a root somewhere between bot,top
        double f1 = cashFlowPv(cflow_times, cflow_amounts, x1);
        double f2 = cashFlowPv(cflow_times, cflow_amounts, x2);
        int i;
        for (i = 0; i < MAX_ITERATIONS; i++) {
            if ((f1 * f2) < 0.0) {
                break;
            }
            if (Math.abs(f1) < Math.abs(f2)) {
                f1 = cashFlowPv(cflow_times, cflow_amounts, x1 += 1.6 * (x1 - x2));
            } else {
                f2 = cashFlowPv(cflow_times, cflow_amounts, x2 += 1.6 * (x2 - x1));
            }
        }
        if (f2 * f1 > 0.0) {
            return ERROR;
        }
        double f = cashFlowPv(cflow_times, cflow_amounts, x1);
        double rtb;
        double dx = 0;
        if (f < 0.0) {
            rtb = x1;
            dx = x2 - x1;
        } else {
            rtb = x2;
            dx = x1 - x2;
        }
        ;
        for (i = 0; i < MAX_ITERATIONS; i++) {
            dx *= 0.5;
            double x_mid = rtb + dx;
            double f_mid = cashFlowPv(cflow_times, cflow_amounts, x_mid);
            if (f_mid <= 0.0) {
                rtb = x_mid;
            }
            if ((Math.abs(f_mid) < ACCURACY) || (Math.abs(dx) < ACCURACY)) {
                return x_mid;
            }
        }
        return ERROR;   // error.
    }

    /**
     * @param cflow_times
     * @param cflow_amounts
     */
    public static boolean cashFlowUniqueIrr(double[] cflow_times, double[] cflow_amounts) {
        int sign_changes = 0;
        for (int t = 1; t < cflow_times.length; ++t) {
            if (sgn(cflow_amounts[t - 1]) != sgn(cflow_amounts[t])) {
                sign_changes++;
            }
        }
        ;
        if (sign_changes == 0) {
            return false;  // can not find any irr
        }
        if (sign_changes == 1) {
            return true;
        }
        double A = cflow_amounts[0]; // check the aggregate cash flows, due to Norstrom
        sign_changes = 0;
        for (int t = 1; t < cflow_times.length; ++t) {
            if (sgn(A) != sgn(A += cflow_amounts[t])) {
                sign_changes++;
            }
        }
        if (sign_changes <= 1) {
            return true;
        }
        return false;
    }

    /**
     * @param cflow_times
     * @param cflow_amounts
     * @param r
     */
    public static double cashFlowPv(double[] cflow_times,
                                    double[] cflow_amounts,
                                    double r) {
        double PV = 0.0;
        for (int t = 0; t < cflow_times.length; t++) {
            PV += cflow_amounts[t] * Math.exp(-r * cflow_times[t]);
        }
        return PV;
    }

    /**
     * @param cflow_times
     * @param cflow_amounts
     * @param r
     */
    public static double cashFlowPvDiscrete(double[] cflow_times,
                                            double[] cflow_amounts,
                                            double r) {
        double PV = 0.0;
        for (int t = 0; t < cflow_times.length; t++) {
            PV += cflow_amounts[t] / Math.pow(1 + r, cflow_times[t]);
        }
        return PV;
    }

    public static double currencyOptionPriceCallAmericanBinomial(double S, double X, double r, double r_f, double sigma,
                                                                 double time, int no_steps) {
        double[] exchange_rates = new double[no_steps + 1];
        double[] call_values = new double[no_steps + 1];
        double t_delta = time / no_steps;
        double Rinv = Math.exp(-r * (t_delta));
        double u = Math.exp(sigma * Math.sqrt(t_delta));
        double d = 1.0 / u;
        double uu = u * u;
        double pUp = (Math.exp((r - r_f) * t_delta) - d) / (u - d); // adjust for foreign int.rate
        double pDown = 1.0 - pUp;
        exchange_rates[0] = S * Math.pow(d, no_steps);
        int i;
        for (i = 1; i <= no_steps; ++i) {
            exchange_rates[i] = uu * exchange_rates[i - 1]; // terminal grid nodes
        }
        for (i = 0; i <= no_steps; ++i) {
            call_values[i] = Math.max(0.0, (exchange_rates[i] - X));
        }
        for (int step = no_steps - 1; step >= 0; --step) {
            for (i = 0; i <= step; ++i) {
                exchange_rates[i] = d * exchange_rates[i + 1];
                call_values[i] = (pDown * call_values[i] + pUp * call_values[i + 1]) * Rinv;
                call_values[i] = Math.max(call_values[i], exchange_rates[i] - X); // check for exercise
            }
        }
        return call_values[0];
    }

    public static double currencyOptionPricePutAmericanBinomial(double S, double X, double r, double r_f, double sigma,
                                                                double time, int no_steps) {
        double[] exchange_rates = new double[no_steps + 1];
        double[] put_values = new double[no_steps + 1];
        double t_delta = time / no_steps;
        double Rinv = Math.exp(-r * (t_delta));
        double u = Math.exp(sigma * Math.sqrt(t_delta));
        double d = 1.0 / u;
        double uu = u * u;
        double pUp = (Math.exp((r - r_f) * t_delta) - d) / (u - d); // adjust for foreign int.rate
        double pDown = 1.0 - pUp;
        exchange_rates[0] = S * Math.pow(d, no_steps);
        int i;
        for (i = 1; i <= no_steps; ++i) {
            exchange_rates[i] = uu * exchange_rates[i - 1]; // terminal grid nodes
        }
        for (i = 0; i <= no_steps; ++i) {
            put_values[i] = Math.max(0.0, (exchange_rates[i] - X));
        }
        for (int step = no_steps - 1; step >= 0; --step) {
            for (i = 0; i <= step; ++i) {
                exchange_rates[i] = d * exchange_rates[i + 1];
                put_values[i] = (pDown * put_values[i] + pUp * put_values[i + 1]) * Rinv;
                put_values[i] = Math.max(put_values[i], X - exchange_rates[i]); // check for exercise
            }
        }
        return put_values[0];
    }

    public static double currencyOptionPriceCallEuropean(double S, // exchange_rate,
                                                         double X, // exercise,
                                                         double r, // r_domestic,
                                                         double r_f, // r_foreign,
                                                         double sigma, // volatility,
                                                         double time)   // time to maturity
    {
        double sigma_sqr = sigma * sigma;
        double time_sqrt = Math.sqrt(time);
        double d1 = (Math.log(S / X) + (r - r_f + (0.5 * sigma_sqr)) * time) / (sigma * time_sqrt);
        double d2 = d1 - sigma * time_sqrt;
        return S * Math.exp(-r_f * time) * N(d1) - X * Math.exp(-r * time) * N(d2);
    }

    public static double currencyOptionPricePutEuropean(double S, // exchange_rate,
                                                        double X, // exercise,
                                                        double r, // r_domestic,
                                                        double r_f, // r_foreign,
                                                        double sigma, // volatility,
                                                        double time)    // time to maturity
    {
        double sigma_sqr = sigma * sigma;
        double time_sqrt = Math.sqrt(time);
        double d1 = (Math.log(S / X) + (r - r_f + (0.5 * sigma_sqr)) * time) / (sigma * time_sqrt);
        double d2 = d1 - sigma * time_sqrt;
        return X * Math.exp(-r * time) * N(-d2) - S * Math.exp(-r_f * time) * N(-d1);
    }

    public static double exoticsLookbackEuropeanCall(double S,
                                                     double Smin,
                                                     double r,
                                                     double q,
                                                     double sigma,
                                                     double time) {
        if (r == q) {
            return 0;
        }
        double sigma_sqr = sigma * sigma;
        double time_sqrt = Math.sqrt(time);
        double a1 = (Math.log(S / Smin) + (r - q + sigma_sqr / 2.0) * time) / (sigma * time_sqrt);
        double a2 = a1 - sigma * time_sqrt;
        double a3 = (Math.log(S / Smin) + (-r + q + sigma_sqr / 2.0) * time) / (sigma * time_sqrt);
        double Y1 = 2.0 * (r - q - sigma_sqr / 2.0) * Math.log(S / Smin) / sigma_sqr;
        return S * Math.exp(-q * time) * N(a1)
                - S * Math.exp(-q * time) * (sigma_sqr / (2.0 * (r - q))) * N(-a1)
                - Smin * Math.exp(-r * time) * (N(a2) - (sigma_sqr / (2 * (r - q))) * Math.exp(Y1) * N(-a3));
    }

    public static double exoticsLookbackEuropeanPut(double S,
                                                    double Smax,
                                                    double r,
                                                    double q,
                                                    double sigma,
                                                    double time) {
        if (r == q) {
            return 0;
        }
        double sigma_sqr = sigma * sigma;
        double time_sqrt = Math.sqrt(time);
        double b1 = (Math.log(S / Smax) + (-r + q + sigma_sqr / 2.0) * time) / (sigma * time_sqrt);
        double b2 = b1 - sigma * time_sqrt;
        double b3 = (Math.log(S / Smax) + (r - q - sigma_sqr / 2.0) * time) / (sigma * time_sqrt);
        double Y2 = (2.0 * (r - q - sigma_sqr / 2.0) * Math.log(Smax / S)) / sigma_sqr;
        double p =
                Smax * Math.exp(-r * time) * (N(b1) - (sigma_sqr / (2 * (r - q))) * Math.exp(Y2) * N(-b3))
                        + S * Math.exp(-q * time) * (sigma_sqr / (2.0 * (r - q))) * N(-b2)
                        - S * Math.exp(-q * time) * N(b2);
        return p;
    }

    public static double optionPriceCallAmericanFiniteDiffExplicit(double S,
                                                                   double X,
                                                                   double r,
                                                                   double sigma,
                                                                   double time,
                                                                   int no_S_steps,
                                                                   int no_t_steps) {
        double sigma_sqr = sigma * sigma;
        int M = no_S_steps;           // need M = no_S_steps to be even:
        if ((no_S_steps % 2) == 1) {
            M = no_S_steps + 1;
        } else {
            M = no_S_steps;
        }
        double delta_S = 2.0 * S / M;
        double[] S_values = new double[M + 1];
        for (int m = 0; m <= M; m++) {
            S_values[m] = m * delta_S;
        }
        int N = no_t_steps;
        double delta_t = time / N;

        double[] a = new double[M];
        double[] b = new double[M];
        double[] c = new double[M];
        double r1 = 1.0 / (1.0 + r * delta_t);
        double r2 = delta_t / (1.0 + r * delta_t);
        for (int j = 1; j < M; j++) {
            a[j] = r2 * 0.5 * j * (-r + sigma_sqr * j);
            b[j] = r1 * (1.0 - sigma_sqr * j * j * delta_t);
            c[j] = r2 * 0.5 * j * (r + sigma_sqr * j);
        }
        double[] f_next = new double[M + 1];
        for (int m = 0; m <= M; ++m) {
            f_next[m] = Math.max(0.0, S_values[m] - X);
        }
        double[] f = new double[M + 1];
        for (int t = N - 1; t >= 0; --t) {
            f[0] = 0;
            for (int m = 1; m < M; ++m) {
                f[m] = a[m] * f_next[m - 1] + b[m] * f_next[m] + c[m] * f_next[m + 1];
                f[m] = Math.max(f[m], S_values[m] - X);  // check for exercise
            }
            f[M] = S_values[M] - X;
            for (int m = 0; m <= M; ++m) {
                f_next[m] = f[m];
            }
        }
        double C = f[M / 2];
        return C;
    }

    public static double optionPricePutAmericanFiniteDiffExplicit(double S,
                                                                  double X,
                                                                  double r,
                                                                  double sigma,
                                                                  double time,
                                                                  int no_S_steps,
                                                                  int no_t_steps) {
        double sigma_sqr = sigma * sigma;
        int M;           // need M = no_S_steps to be even:
        if ((no_S_steps % 2) == 1) {
            M = no_S_steps + 1;
        } else {
            M = no_S_steps;
        }
        double delta_S = 2.0 * S / M;
        double[] S_values = new double[M + 1];
        for (int m = 0; m <= M; m++) {
            S_values[m] = m * delta_S;
        }
        int N = no_t_steps;
        double delta_t = time / N;
        double[] a = new double[M];
        double[] b = new double[M];
        double[] c = new double[M];
        double r1 = 1.0 / (1.0 + r * delta_t);
        double r2 = delta_t / (1.0 + r * delta_t);
        for (int j = 1; j < M; j++) {
            a[j] = r2 * 0.5 * j * (-r + sigma_sqr * j);
            b[j] = r1 * (1.0 - sigma_sqr * j * j * delta_t);
            c[j] = r2 * 0.5 * j * (r + sigma_sqr * j);
        }
        double[] f_next = new double[M + 1];
        for (int m = 0; m <= M; ++m) {
            f_next[m] = Math.max(0.0, X - S_values[m]);
        }
        double[] f = new double[M + 1];
        for (int t = N - 1; t >= 0; --t) {
            f[0] = X;
            for (int m = 1; m < M; ++m) {
                f[m] = a[m] * f_next[m - 1] + b[m] * f_next[m] + c[m] * f_next[m + 1];
                f[m] = Math.max(f[m], X - S_values[m]);  // check for exercise
            }
            f[M] = 0;
            for (int m = 0; m <= M; ++m) {
                f_next[m] = f[m];
            }
        }
        return f[M / 2];
    }

    public static double optionPriceCallEuropeanFiniteDiffExplicit(double S,
                                                                   double X,
                                                                   double r,
                                                                   double sigma,
                                                                   double time,
                                                                   int no_S_steps,
                                                                   int no_t_steps) {
        double sigma_sqr = sigma * sigma;
        int M;           // need M = no_S_steps to be even:
        if ((no_S_steps % 2) == 1) {
            M = no_S_steps + 1;
        } else {
            M = no_S_steps;
        }
        double delta_S = 2.0 * S / M;
        double[] S_values = new double[M + 1];
        for (int m = 0; m <= M; m++) {
            S_values[m] = m * delta_S;
        }
        int N = no_t_steps;
        double delta_t = time / N;

        double[] a = new double[M];
        double[] b = new double[M];
        double[] c = new double[M];
        double r1 = 1.0 / (1.0 + r * delta_t);
        double r2 = delta_t / (1.0 + r * delta_t);
        for (int j = 1; j < M; j++) {
            a[j] = r2 * 0.5 * j * (-r + sigma_sqr * j);
            b[j] = r1 * (1.0 - sigma_sqr * j * j * delta_t);
            c[j] = r2 * 0.5 * j * (r + sigma_sqr * j);
        }
        double[] f_next = new double[M + 1];
        for (int m = 0; m <= M; ++m) {
            f_next[m] = Math.max(0.0, S_values[m] - X);
        }
        double[] f = new double[M + 1];
        for (int t = N - 1; t >= 0; --t) {
            f[0] = 0;
            for (int m = 1; m < M; ++m) {
                f[m] = a[m] * f_next[m - 1] + b[m] * f_next[m] + c[m] * f_next[m + 1];
            }
            f[M] = 0;
            for (int m = 0; m <= M; ++m) {
                f_next[m] = f[m];
            }
        }
        return f[M / 2];
    }

    public static double optionPricePutEuropeanFiniteDiffExplicit(double S,
                                                                  double X,
                                                                  double r,
                                                                  double sigma,
                                                                  double time,
                                                                  int no_S_steps,
                                                                  int no_t_steps) {
        double sigma_sqr = sigma * sigma;

        int M;           // need M = no_S_steps to be even:
        if ((no_S_steps % 2) == 1) {
            M = no_S_steps + 1;
        } else {
            M = no_S_steps;
        }
        double delta_S = 2.0 * S / M;
        double[] S_values = new double[M + 1];
        for (int m = 0; m <= M; m++) {
            S_values[m] = m * delta_S;
        }
        int N = no_t_steps;
        double delta_t = time / N;
        double[] a = new double[M];
        double[] b = new double[M];
        double[] c = new double[M];
        double r1 = 1.0 / (1.0 + r * delta_t);
        double r2 = delta_t / (1.0 + r * delta_t);
        for (int j = 1; j < M; j++) {
            a[j] = r2 * 0.5 * j * (-r + sigma_sqr * j);
            b[j] = r1 * (1.0 - sigma_sqr * j * j * delta_t);
            c[j] = r2 * 0.5 * j * (r + sigma_sqr * j);
        }
        double[] f_next = new double[M + 1];
        for (int m = 0; m <= M; ++m) {
            f_next[m] = Math.max(0.0, X - S_values[m]);
        }
        //    double f[M+1];
        double[] f = new double[M + 1];
        for (int t = N - 1; t >= 0; --t) {
            f[0] = X;
            for (int m = 1; m < M; ++m) {
                f[m] = a[m] * f_next[m - 1] + b[m] * f_next[m] + c[m] * f_next[m + 1];
            }
            f[M] = 0;
            for (int m = 0; m <= M; ++m) {
                f_next[m] = f[m];
            }
        }
        return f[M / 2];
    }

    public static double optionPriceCallAmericanFiniteDiffImplicit(double S, double X,
                                                                   double r, double sigma, double time,
                                                                   int no_S_steps, int no_t_steps) {
        double sigma_sqr = sigma * sigma;
        int M;
        if ((no_S_steps % 2) == 1) {
            M = no_S_steps + 1;
        } else {
            M = no_S_steps;
        }
        ; // need no_S_steps to be even:
        double delta_S = 2.0 * S / M;
        double[] S_values = new double[M + 1];
        for (int m = 0; m <= M; m++) {
            S_values[m] = m * delta_S;
        }
        int N = no_t_steps;
        double delta_t = time / N;
        Matrix A = new Matrix(M + 1, M + 1);
        A.set(0, 0, 1.0);
        for (int j = 1; j < M; ++j) {
            A.set(j, j - 1, 0.5 * j * delta_t * (r - sigma_sqr * j));    // a[j]
            A.set(j, j, 1.0 + delta_t * (r + sigma_sqr * j * j));  // b[j];
            A.set(j, j + 1, 0.5 * j * delta_t * (-r - sigma_sqr * j));   // c[j];
        }
        A.set(M, M, 1.0);
        double[] B = new double[M + 1];
        for (int m = 0; m <= M; ++m) {
            B[m] = Math.max(0.0, S_values[m] - X);
        }

        Matrix F = A.inverse().times(new Matrix(B, M + 1));
        for (int t = N - 1; t > 0; --t) {
            B = F.getColumnPackedCopy();
            F = A.inverse().times(new Matrix(B, M + 1));
            for (int m = 1; m < M; ++m) {
                // now check for exercise
                F.getColumnPackedCopy()[m] = Math.max(F.getColumnPackedCopy()[m], S_values[m] - X);
            }
        }
        return F.getColumnPackedCopy()[M / 2];
    }

    public static double optionPricePutAmericanFiniteDiffImplicit(double S, double X, double r, double sigma, double time,
                                                                  int no_S_steps, int no_t_steps) {
        double sigma_sqr = sigma * sigma;
        // need no_S_steps to be even:
        int M;
        if ((no_S_steps % 2) == 1) {
            M = no_S_steps + 1;
        } else {
            M = no_S_steps;
        }
        double delta_S = 2.0 * S / M;
        double[] S_values = new double[M + 1];
        for (int m = 0; m <= M; m++) {
            S_values[m] = m * delta_S;
        }
        int N = no_t_steps;
        double delta_t = time / N;
        Matrix A = new Matrix(M + 1, M + 1);

        A.set(0, 0, 1.0);
        for (int j = 1; j < M; ++j) {
            A.set(j, j - 1, 0.5 * j * delta_t * (r - sigma_sqr * j));    // a[j]
            A.set(j, j, 1.0 + delta_t * (r + sigma_sqr * j * j));  // b[j];
            A.set(j, j + 1, 0.5 * j * delta_t * (-r - sigma_sqr * j));   // c[j];
        }
        A.set(M, M, 1.0);
        //  double[] B(M+1);
        double[] B = new double[M + 1];
        for (int m = 0; m <= M; ++m) {
            B[m] = Math.max(0.0, X - S_values[m]);
        }
        //  double[] F=A.i()*B;
        //  double[] F = A.inverse().times(B);
        Matrix F = A.inverse().times(new Matrix(B, M + 1));
        for (int t = N - 1; t > 0; --t) {
            //  B = F;
            B = F.getColumnPackedCopy();
            //            F = A.inverse().times(B);
            F = A.inverse().times(new Matrix(B, M + 1));
            for (int m = 1; m < M; ++m) {   // now check for exercise
                //  F[m] = Math.max(F[m], X-S_values[m]);
                F.getColumnPackedCopy()[m] = Math.max(F.getColumnPackedCopy()[m], X - S_values[m]);
            }
        }
        //  return F[M/2];
        return F.getColumnPackedCopy()[M / 2];
    }

    public static double optionPriceCallEuropeanFiniteDiffImplicit(double S, double X, double r, double sigma, double time,
                                                                   int no_S_steps, int no_t_steps) {
        double sigma_sqr = sigma * sigma;
        // need no_S_steps to be even:
        int M;
        if ((no_S_steps % 2) == 1) {
            M = no_S_steps + 1;
        } else {
            M = no_S_steps;
        }
        double delta_S = 2.0 * S / M;
        double[] S_values = new double[M + 1];
        for (int m = 0; m <= M; m++) {
            S_values[m] = m * delta_S;
        }
        int N = no_t_steps;
        double delta_t = time / N;
        //  BandMatrix A(M+1,1,1);
        Matrix A = new Matrix(M + 1, M + 1);
        A.set(0, 0, 1.0);
        for (int j = 1; j < M; ++j) {
            A.set(j, j - 1, 0.5 * j * delta_t * (r - sigma_sqr * j));    // a[j]
            A.set(j, j, 1.0 + delta_t * (r + sigma_sqr * j * j));  // b[j];
            A.set(j, j + 1, 0.5 * j * delta_t * (-r - sigma_sqr * j));   // c[j];
        }
        A.set(M, M, 1.0);

        double[] B = new double[M + 1];
        for (int m = 0; m <= M; ++m) {
            B[m] = Math.max(0.0, S_values[m] - X);
        }
        //  double[] F=A.i()*B;
        Matrix F = A.inverse().times(new Matrix(B, M + 1));
        for (int t = N - 1; t > 0; --t) {
            B = F.getColumnPackedCopy();
            //  F = A.inverse().times(B);
            F = A.inverse().times(new Matrix(B, M + 1));
            //  B = F;
            //  F = A.i()*B;
        }
        return F.getColumnPackedCopy()[M / 2];
    }

    public static double optionPricePutEuropeanFiniteDiffImplicit(double S, double X, double r, double sigma, double time,
                                                                  int no_S_steps, int no_t_steps) {
        double sigma_sqr = sigma * sigma;
        // need no_S_steps to be even:
        int M;
        if ((no_S_steps % 2) == 1) {
            M = no_S_steps + 1;
        } else {
            M = no_S_steps;
        }
        double delta_S = 2.0 * S / M;
        double[] S_values = new double[M + 1];
        for (int m = 0; m <= M; m++) {
            S_values[m] = m * delta_S;
        }
        int N = no_t_steps;
        double delta_t = time / N;
        //        BandMatrix A(M+1,1,1); A=0.0;
        Matrix A = new Matrix(M + 1, M + 1);
        A.set(0, 0, 1.0);
        for (int j = 1; j < M; ++j) {
            A.set(j, j - 1, 0.5 * j * delta_t * (r - sigma_sqr * j));    // a[j]
            A.set(j, j, 1.0 + delta_t * (r + sigma_sqr * j * j));  // b[j];
            A.set(j, j + 1, 0.5 * j * delta_t * (-r - sigma_sqr * j));   // c[j];
        }
        A.set(M, M, 1.0);
        double[] B = new double[M + 1];
        for (int m = 0; m <= M; ++m) {
            B[m] = Math.max(0.0, X - S_values[m]);
        }
        //        double[] F=A.inverse().times(B);
        Matrix F = A.inverse().times(new Matrix(B, M + 1));
        for (int t = N - 1; t > 0; --t) {
            //            B = F;
            B = F.getColumnPackedCopy();
            F = A.inverse().times(new Matrix(B, M + 1));
            //            F = A.inverse().times(B);
        }
        //        return F.element(M/2);
        return F.getColumnPackedCopy()[M / 2];
    }

    public static double futuresOptionPriceCallAmericanBinomial(double F, double X, double r, double sigma, double time, int no_steps) {
        double[] futures_prices = new double[no_steps + 1];
        double[] call_values = new double[no_steps + 1];
        double t_delta = time / no_steps;
        double Rinv = Math.exp(-r * (t_delta));
        double u = Math.exp(sigma * Math.sqrt(t_delta));
        double d = 1.0 / u;
        double uu = u * u;
        double pUp = (1 - d) / (u - d);   // note how probability is calculated
        double pDown = 1.0 - pUp;
        futures_prices[0] = F * Math.pow(d, no_steps);
        int i;
        for (i = 1; i <= no_steps; ++i) {
            futures_prices[i] = uu * futures_prices[i - 1]; // terminal grid nodes
        }
        for (i = 0; i <= no_steps; ++i) {
            call_values[i] = Math.max(0.0, (futures_prices[i] - X));
        }
        for (int step = no_steps - 1; step >= 0; --step) {
            for (i = 0; i <= step; ++i) {
                futures_prices[i] = d * futures_prices[i + 1];
                call_values[i] = (pDown * call_values[i] + pUp * call_values[i + 1]) * Rinv;
                call_values[i] = Math.max(call_values[i], futures_prices[i] - X); // check for exercise
            }
        }
        return call_values[0];
    }

    public static double futuresOptionPriceCallEuropeanBlack(double F, // futures premium
                                                             double X, // exercise premium
                                                             double r, // interest rate
                                                             double sigma, // volatility
                                                             double time)   // time to maturity
    {
        double sigma_sqr = sigma * sigma;
        double time_sqrt = Math.sqrt(time);
        double d1 = (Math.log(F / X) + 0.5 * sigma_sqr * time) / (sigma * time_sqrt);
        double d2 = d1 - sigma * time_sqrt;
        return Math.exp(-r * time) * (F * N(d1) - X * N(d2));
    }

    public static double futuresOptionPricePutAmericanBinomial(double F, double X, double r, double sigma, double time, int no_steps) {
        double[] futures_prices = new double[no_steps + 1];
        double[] put_values = new double[no_steps + 1];
        double t_delta = time / no_steps;
        double Rinv = Math.exp(-r * (t_delta));
        double u = Math.exp(sigma * Math.sqrt(t_delta));
        double d = 1.0 / u;
        double uu = u * u;
        double uInv = 1.0 / u;
        double pUp = (1 - d) / (u - d);
        double pDown = 1.0 - pUp;
        futures_prices[0] = F * Math.pow(d, no_steps);
        int i;
        for (i = 1; i <= no_steps; ++i) {
            futures_prices[i] = uu * futures_prices[i - 1]; // terminal grid nodes
        }
        for (i = 0; i <= no_steps; ++i) {
            put_values[i] = Math.max(0.0, (X - futures_prices[i]));
        }
        for (int step = no_steps - 1; step >= 0; --step) {
            for (i = 0; i <= step; ++i) {
                futures_prices[i] = uInv * futures_prices[i + 1];
                put_values[i] = (pDown * put_values[i] + pUp * put_values[i + 1]) * Rinv;
                put_values[i] = Math.max(put_values[i], X - futures_prices[i]); // check for exercise
            }
        }
        return put_values[0];
    }

    public static double futuresOptionPricePutEuropeanBlack(double F, // futures premium
                                                            double X, // exercise premium
                                                            double r, // interest rate
                                                            double sigma, // volatility
                                                            double time) {
        double sigma_sqr = sigma * sigma;
        double time_sqrt = Math.sqrt(time);
        double d1 = (Math.log(F / X) + 0.5 * sigma_sqr * time) / (sigma * time_sqrt);
        double d2 = d1 - sigma * time_sqrt;
        return Math.exp(-r * time) * (X * N(-d2) - F * N(-d1));
    }

    public static double futures_price(double S, // value of underlying
                                       double r, // risk free interest
                                       double time_to_maturity) {
        return Math.exp(r * time_to_maturity) * S;
    }

    public static double optionPriceCallMertonJumpDiffusion(double S,
                                                            double X,
                                                            double r,
                                                            double sigma,
                                                            double time_to_maturity,
                                                            double lambda,
                                                            double kappa,
                                                            double delta) {
        int MAXN = 50;
        double tau = time_to_maturity;
        double sigma_sqr = sigma * sigma;
        double delta_sqr = delta * delta;
        double lambdaprime = lambda * (1 + kappa);
        double gamma = Math.log(1 + kappa);
        double c = Math.exp(-lambdaprime * tau) *
                optionBlackScholesCall(S, X, sigma, r - lambda * kappa, tau);
        double log_n = 0;
        for (int n = 1; n <= MAXN; ++n) {
            log_n += Math.log(n);
            double sigma_n = Math.sqrt(sigma_sqr + n * delta_sqr / tau);
            double r_n = r - lambda * kappa + n * gamma / tau;
            c += Math.exp(-lambdaprime * tau + n * Math.log(lambdaprime * tau) - log_n) *
                    optionBlackScholesCall(S, X, sigma_n, r_n, tau);
        }
        ;
        return c;
    }

    // payoff with two parameters double, double[]

    public static double derivativePriceEuropeanSimulated(double S,
                                                          double X,
                                                          double r,
                                                          double sigma,
                                                          double time,
                                                          //double payoff(double& premium, double& X),
                                                          int payoff,
                                                          int no_sims) {
        double sum_payoffs = 0;
        double S_T = 0;
        double put_payoff = 0;
        double call_payoff = 0;
        for (int n = 0; n < no_sims; n++) {
            switch (payoff) {
                case PAYOFF_EUROPEAN_CALL:
                    S_T = simulate_terminal_price(S, r, sigma, time);
                    call_payoff = payoffEuropeanCall(S_T, X);
                    sum_payoffs = sum_payoffs + call_payoff;
                    break;
                case PAYOFF_EUROPEAN_PUT:
                    S_T = simulate_terminal_price(S, r, sigma, time);
                    put_payoff = payoffEuropeanPut(S_T, X);
                    sum_payoffs = sum_payoffs + put_payoff;
                    break;
                default:
                    LogUtils.debug("payoff not applicable");
                    break;
            }
        }
        return Math.exp(-r * time) * (sum_payoffs / no_sims);
    }

    // payoff with one parameter []
    // no strike premium

    public static double derivativePriceEuropeanSimulated(double S,
                                                          double r,
                                                          double sigma,
                                                          double time,
                                                          //double payoff(double[] prices),
                                                          int payoff,
                                                          int no_steps,
                                                          int no_sims) {
        double sum_payoffs = 0;
        double[] prices = new double[no_steps];
        for (int n = 0; n < no_sims; n++) {
            switch (payoff) {
                case PAYOFF_ARITHMETRIC_AVERAGE:
                    simulate_price_sequence(S, r, sigma, time, no_steps, prices);
                    sum_payoffs += payoffArithmetricAverage(prices);
                    break;
                case PAYOFF_GEOMETRIC_AVERAGE:
                    simulate_price_sequence(S, r, sigma, time, no_steps, prices);
                    sum_payoffs += payoffGeometricAverage(prices);
                    break;
                case PAYOFF_MAX:
                    simulate_price_sequence(S, r, sigma, time, no_steps, prices);
                    sum_payoffs += payoff_max(prices);
                    break;
                case PAYOFF_MIN:
                    simulate_price_sequence(S, r, sigma, time, no_steps, prices);
                    sum_payoffs += payoff_min(prices);
                    break;
            }
        }
        return Math.exp(-r * time) * (sum_payoffs / no_sims);
    }

    // two vectors double[], double[]

    public static double derivativePriceEuropeanSimulated(double S,
                                                          double X,
                                                          double r,
                                                          double sigma,
                                                          double time,
                                                          int payoff,
                                                          int no_steps,
                                                          int no_sims) {
        double sum_payoffs = 0;
        //   vector <double> prices(no_steps);
        double[] prices = new double[no_steps];
        for (int n = 0; n < no_sims; n++) {
            switch (payoff) {
                case PAYOFF_ARITHMETRIC_AVERAGE:
                    simulate_price_sequence(S, r, sigma, time, no_steps, prices);
                    sum_payoffs += payoffArithmetricAverage(prices);
                    break;
                case PAYOFF_GEOMETRIC_AVERAGE:
                    simulate_price_sequence(S, r, sigma, time, no_steps, prices);
                    sum_payoffs += payoffGeometricAverage(prices);
                    break;
            }
        }
        return Math.exp(-r * time) * (sum_payoffs / no_sims);
    }

    public static double derivativePriceEuropeanSimulatedControlVariate(double S,
                                                                        double r,
                                                                        double sigma,
                                                                        double time,
                                                                        int payoff,
                                                                        int no_steps,
                                                                        int no_sims) {
        double X = S;
        double c_bs = optionBlackScholesCall(S, X, r, sigma, time);
        //   vector <double> prices(no_steps,0.0);
        double[] prices = new double[no_steps];
        double sum_payoffs = 0;
        double sum_payoffs_bs = 0;
        for (int n = 0; n < no_sims; n++) {
            simulate_price_sequence(S, r, sigma, time, no_steps, prices);
            switch (payoff) {
                case PAYOFF_GEOMETRIC_AVERAGE:
                    sum_payoffs += payoffGeometricAverage(prices);
                    sum_payoffs_bs += payoffEuropeanCall(prices[prices.length - 1], X);
                    break;
            }
        }
        double c_sim = Math.exp(-r * time) * (sum_payoffs / no_sims);
        double c_bs_sim = Math.exp(-r * time) * (sum_payoffs_bs / no_sims);
        c_sim += (c_bs - c_bs_sim);
        return c_sim;
    }

    // payoff algorithms

    public static double payoffArithmetricAverage(double[] prices) {
        double sum = 0;
        //=accumulate(prices[0], prices[prices.length-1],0.0);
        for (int i = 0; i < prices.length; ++i) {
            sum += prices[i];
        }
        ;
        double avg = sum / prices.length;
        return Math.max(0.0, avg - prices[prices.length - 1]);
    }

    public static double payoffGeometricAverage(double[] prices) {
        double logsum = 0;
        for (int i = 0; i < prices.length; ++i) {
            logsum += Math.log(prices[i]);
        }
        ;
        double avg = Math.exp(logsum / prices.length);
        return Math.max(0.0, avg - prices[prices.length - 1]);
    }

    public static double payoffEuropeanCall(double price, double X) {
        return Math.max(0.0, price - X);
    }

    public static double payoffEuropeanPut(double price, double X) {
        return Math.max(0.0, X - price);
    }

    /*
    public static double accumulate(double a, double b, double c){
        log("finrecipes: accumulate not implemented yet");
        return -1;
    }
     */

    public static double max_element(double[] prices) {
        double max = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] > max) {
                max = prices[i];
            }
        }
        return max;
    }

    public static double min_element(double[] prices) {
        double min = 1000000;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < min) {
                min = prices[i];
            }
        }
        return min;
    }

    public static double payoff_max(double[] prices) {
        double m = max_element(prices);
        return m - prices[prices.length - 1]; // Math.max is always larger or equal.
    }

    public static double payoff_min(double[] prices) {
        double m = min_element(prices);
        return prices[prices.length - 1] - m; // always positive or zero
    }

    // getAndRemove normal distributed paths
    // fills the prices array with all the simulated results

    public static void simulate_price_sequence(double S, // current value of underlying
                                               double r, // interest rate
                                               double sigma, // volatitily
                                               double time, // time to final date
                                               int no_steps, // number of steps
                                               double[] prices) {
        if (prices.length < no_steps) {
            prices = new double[no_steps];
        }
        double delta_t = time / no_steps;
        double R = (r - 0.5 * sigma * sigma) * delta_t;
        double SD = sigma * Math.sqrt(delta_t);
        double S_t = S;                       // initialize at current premium
        for (int i = 0; i < no_steps; ++i) {
            S_t = S_t * Math.exp(R + SD * Random.nextDouble());
            prices[i] = S_t;
        }
    }

    /**
     * @param S     current value of underlying
     * @param r     interest rate
     * @param sigma volatitily
     * @param time  time to final date
     */
    public static double simulate_terminal_price(double S,
                                                 double r,
                                                 double sigma,
                                                 double time) {
        double sigma_sqr = sigma * sigma;
        double R = (r - 0.5 * sigma_sqr) * time;
        double SD = sigma * Math.sqrt(time);
        return S * Math.exp(R + SD * Random.nextDouble());
    }

    // estimate the premium using two different S values

    public static double optionPriceCallEuropeanSimulated(double S,
                                                          double X,
                                                          double r,
                                                          double sigma,
                                                          double time,
                                                          int no_sims) {
        double sigma_sqr = sigma * sigma;
        double R = (r - 0.5 * sigma_sqr) * time;
        double SD = sigma * Math.sqrt(time);
        double sum_payoffs = 0.0;
        for (int n = 1; n <= no_sims; n++) {
            double S_T = S * Math.exp(R + SD * Random.nextDouble());
            sum_payoffs += Math.max(0.0, S_T - X);
        }
        double c = Math.exp(-r * time) * (sum_payoffs / (double) (no_sims));
        return c;
    }

    public static double optionPriceDeltaCallEuropeanSimulated(double S,
                                                               double X,
                                                               double r,
                                                               double sigma,
                                                               double time,
                                                               int no_sims) {
        double sigma_sqr = sigma * sigma;
        double R = (r - 0.5 * sigma_sqr) * time;
        double SD = sigma * Math.sqrt(time);
        double sum_payoffs = 0.0;
        double sum_payoffs_2 = 0.0;
        double q = S * 0.01;
        for (int n = 1; n <= no_sims; n++) {
            double Z = Random.nextDouble();

            double S_T = S * Math.exp(R + SD * Z);
            sum_payoffs += Math.max(0.0, S_T - X);

            double S_T_2 = (S + q) * Math.exp(R + SD * Z);
            sum_payoffs_2 += Math.max(0.0, S_T_2 - X);
        }
        double c = Math.exp(-r * time) * (sum_payoffs / no_sims);
        double c2 = Math.exp(-r * time) * (sum_payoffs_2 / no_sims);
        return (c2 - c) / q;
    }

    // estimate the premium using two different S values

    public static double optionPriceDeltaPutEuropeanSimulated(double S,
                                                              double X,
                                                              double r,
                                                              double sigma,
                                                              double time,
                                                              int no_sims) {
        double sigma_sqr = sigma * sigma;
        double R = (r - 0.5 * sigma_sqr) * time;
        double SD = sigma * Math.sqrt(time);
        double sum_payoffs = 0.0;
        double sum_payoffs_2 = 0.0;
        double q = S * 0.01;
        for (int n = 1; n <= no_sims; n++) {
            double Z = Random.nextDouble();
            double S_T = S * Math.exp(R + SD * Z);
            double S_T_2 = (S + q) * Math.exp(R + SD * Z);
            sum_payoffs += Math.max(0.0, X - S_T);
            sum_payoffs_2 += Math.max(0.0, X - S_T_2);
        }
        ;
        double p = Math.exp(-r * time) * (sum_payoffs / no_sims);
        double p2 = Math.exp(-r * time) * (sum_payoffs_2 / no_sims);
        return (p2 - p) / q;
    }

    public static double optionPricePutEuropeanSimulated(double S,
                                                         double X,
                                                         double r,
                                                         double sigma,
                                                         double time,
                                                         int no_sims) {
        double sigma_sqr = sigma * sigma;
        double R = (r - 0.5 * sigma_sqr) * time;
        double SD = sigma * Math.sqrt(time);
        double sum_payoffs = 0.0;
        for (int n = 1; n <= no_sims; n++) {
            double S_T = S * Math.exp(R + SD * Random.nextDouble());
            sum_payoffs += Math.max(0.0, X - S_T);
        }
        return Math.exp(-r * time) * (sum_payoffs / (double) (no_sims));
    }

    // this is the original CIR formulation of their term structure.

    public static double term_structure_discount_factor_cir(double t,
                                                            double r,
                                                            double kappa,
                                                            double lambda,
                                                            double theta,
                                                            double sigma) {
        double sigma_sqr = Math.pow(sigma, 2);
        double gamma = Math.sqrt(Math.pow((kappa + lambda), 2) + 2.0 * sigma_sqr);
        double denum = (gamma + kappa + lambda) * (Math.exp(gamma * t) - 1) + 2 * gamma;
        double p = 2 * kappa * theta / sigma_sqr;
        double enum1 = 2 * gamma * Math.exp(0.5 * (kappa + lambda + gamma) * t);
        double A = Math.pow((enum1 / denum), p);
        double B = (2 * (Math.exp(gamma * t) - 1)) / denum;
        double dfact = A * Math.exp(-B * r);
        return dfact;
    }

    public static double term_structure_discount_factor_cubic_spline(double t,
                                                                     double b1,
                                                                     double c1,
                                                                     double d1,
                                                                     double[] f,
                                                                     double[] knots) {
        //  calculate the discount factor for the spline functional form.
        double d = 1.0
                + b1 * t
                + c1 * (Math.pow(t, 2))
                + d1 * (Math.pow(t, 3));
        for (int i = 0; i < knots.length; i++) {
            if (t >= knots[i]) {
                d += f[i] * (Math.pow((t - knots[i]), 3));
            } else {
                break;
            }
        }
        return d;
    }

    public static double term_structure_discount_factor_estimated_cir(double t, // time to maturity.
                                                                      double r, // short interest rate.
                                                                      double phi1,
                                                                      double phi2,
                                                                      double phi3) {
        double tmp = (phi2 * (Math.exp(phi1 * t) - 1.0) + phi1);
        double A = (phi1 * Math.exp(phi2 * t)) / tmp;
        A = Math.pow(A, phi3);
        double B = (Math.exp(phi1 * t) - 1.0) / tmp;
        double dfact = A * Math.exp(-B * r);
        return dfact;
    }

    public static double term_structure_discount_factor_vasicek(double time,
                                                                double r,
                                                                double a, double b, double sigma) {
        double A, B;
        double sigma_sqr = sigma * sigma;
        double aa = a * a;
        if (a == 0.0) {
            B = time;
            A = Math.exp(sigma_sqr * Math.pow(time, 3)) / 6.0;
        } else {
            B = (1.0 - Math.exp(-a * time)) / a;
            A = Math.exp(((B - time) * (aa * b - 0.5 * sigma_sqr)) / aa - ((sigma_sqr * B * B) / (4 * a)));
        }
        double d = A * Math.exp(-B * r);
        return d;
    }

    public static double term_structure_yield_from_discount_factor(double dfact, double t) {
        return (-Math.log(dfact) / t);
    }

    public static double term_structure_discount_factor_from_yield(double r, double t) {
        return Math.exp(-r * t);
    }

    public static double term_structure_forward_rate_from_disc_facts(double d_t, double d_T,
                                                                     double time) {
        return (Math.log(d_t / d_T)) / time;
    }

    public static double term_structure_forward_rate_from_yields(double r_t1, double r_T,
                                                                 double t1, double T) {
        return (r_T * (T / (T - t1)) - r_t1 * (t1 / T));
    }

    public static double term_structure_yield_bliss(double t,
                                                    double gamma0,
                                                    double gamma1,
                                                    double gamma2,
                                                    double lambda1,
                                                    double lambda2) {
        double r;
        double t1 = t / lambda1;
        double t2 = t / lambda2;
        r = gamma0 + gamma1 * ((1 - Math.exp(-t1)) / t1)
                + gamma2 * ((1 - Math.exp(-t2)) / t2)
                + gamma2 * (-Math.exp(-t2));
        return r;
    }

    public static double term_structure_yield_linearly_interpolated(double time,
                                                                    double[] obs_times,
                                                                    double[] obs_yields)
    // assume the yields are in increasing time to maturity order.
    {
        int no_obs = obs_times.length;
        if (no_obs < 1) {
            return 0;
        }
        double t_min = obs_times[0];
        if (time <= t_min) {
            return obs_yields[0];  // earlier than lowest obs.
        }
        double t_max = obs_times[no_obs - 1];
        if (time >= t_max) {
            return obs_yields[no_obs - 1]; // later than latest obs
        }
        int t = 1;  // find which two observations we are between
        while ((t < no_obs) && (time > obs_times[t])) {
            ++t;
        }
        ;
        double lambda = (obs_times[t] - time) / (obs_times[t] - obs_times[t - 1]);
        // by ordering assumption, time is  between t-1,t
        double r = obs_yields[t - 1] * lambda + obs_yields[t] * (1.0 - lambda);
        return r;
    }

    public static double term_structure_yield_nelson_siegel(double t,
                                                            double beta0, double beta1, double beta2,
                                                            double lambda) {
        if (t == 0.0) {
            return beta0;
        }
        double tl = t / lambda;
        double r = beta0 + (beta1 + beta2) * ((1 - Math.exp(-tl)) / tl) + beta2 * Math.exp(-tl);
        return r;
    }

    public double mv_calculate_mean(Matrix e, Matrix w) {
        Matrix tmp = e.transpose().times(w);
        return tmp.get(0, 0);
    }

    public double mv_calculate_variance(Matrix V, Matrix w) {
        Matrix tmp = w.transpose().times(V.times(w));
        return tmp.get(0, 0);
    }

    public double mv_calculate_st_dev(Matrix V, Matrix w) {
        double var = mv_calculate_variance(V, w);
        return Math.sqrt(var);
    }

    public Matrix mv_calculate_portfolio_given_mean_unconstrained(Matrix e,
                                                                  Matrix V, double r) {
        int noassets = e.getRowDimension();
        Matrix ones = new Matrix(noassets, 1);
        for (int i = 0; i < noassets; ++i) {
            ones.set(i, 0, 1);
        }
        Matrix Vinv = V.inverse();  // inverse of V
        Matrix A = ones.transpose().times(Vinv.times(e));
        double a = A.get(0, 0);
        Matrix B = e.transpose().times(Vinv.times(e));
        double b = B.get(0, 0);
        Matrix C = ones.transpose().times(Vinv.times(ones));
        double c = C.get(0, 0);
        Matrix D = B.times(C).minus(A.times(A));
        double d = D.get(0, 0);
        Matrix Vinv1 = Vinv.times(ones);
        Matrix Vinve = Vinv.times(e);
        Matrix g = (Vinv1.times(b).minus(Vinve.times(a))).times(1 / d);
        Matrix h = (Vinve.times(c).minus(Vinv1.times(a))).times(1 / d);
        return g.plus(h.times(r));
    }
}

