package com.netnumeri.shared.finance.math;


public abstract class TermStructure {
    TermStructure() {
    }

    public abstract String name();

    public int no_parameters() {
        return 0;
    }

    public double forward_rate(double t1, double T) {
        double d1 = discount_factor(t1);
        double dT = discount_factor(T);
        return FinRecipes.term_structure_forward_rate_from_disc_facts(d1, dT, T - t1);
    }

    public double yield(double t) {
        return FinRecipes.term_structure_yield_from_discount_factor(discount_factor(t), t);
    }

    public double discount_factor(double t) {
        return FinRecipes.term_structure_discount_factor_from_yield(yield(t), t);
    }

    public void set_parameters(double[] x) {
    }

    void get_parameters(double[] y) {
    }

    double[] parameter_lower_bounds() {
        return null;
    }

    double[] parameter_upper_bounds() {
        return null;
    }
}

