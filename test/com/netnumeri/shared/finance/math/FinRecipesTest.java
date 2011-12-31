package com.netnumeri.shared.finance.math;

import junit.framework.TestCase;
import org.junit.Test;

public class FinRecipesTest extends TestCase {

    @Test
    public void test_option_price_call_black_scholes() {
        double S = 50;
        double K = 50;
        double r = 0.10;
        double sigma = 0.30;
        double time = 0.50;

        System.out.println(" Black Scholes call price = ");
        FinRecipes.optionBlackScholesCall(S, K, r, sigma, time);
    }


//    @Test
//    public void test_black_scholes_partials_call() {
//        cout << " Black Scholes call partial derivatives " << endl;
//        double S = 50;
//        double K = 50;
//        double r = 0.10;
//        double sigma = 0.30;
//        double time = 0.50;
//        double Delta, Gamma, Theta, Vega, Rho;
//        option_price_partials_call_black_scholes(S, K, r, sigma, time,
//                Delta, Gamma, Theta, Vega, Rho);
//        System.out.println
//        "  Delta = " << Delta << endl;
//        System.out.println
//        "  Gamma = " << Gamma << endl;
//        System.out.println
//        "  Theta = " << Theta << endl;
//        System.out.println
//        "  Vega  = " << Vega << endl;
//        System.out.println
//        "  Rho   = " << Rho << endl;
//    }
//
//    ;
//
//    @Test
//    public void test_black_scholes_implied_volatility() {
//        double S = 50;
//        double K = 50;
//        double r = 0.10;
//        double time = 0.50;
//        double C = 2.5;
//        System.out.println
//        " Black Scholes implied volatility using Newton search = ";
//
//    System.out.println option_price_implied_volatility_call_black_scholes_newton(S, K, r, time, C)
//
//    <<endl;
//    System.out.println" Black Scholes implied volatility using bisections = ";
//
//    System.out.println option_price_implied_volatility_call_black_scholes_bisections(S, K, r, time, C)
//
//    <<endl;
//};
//
//@Test
//public
//void black_scholes_examples(){
//        System.out.println"----------------------------------------"<<endl;
//System.out.println"Examples in Black Scholes chapter "<<endl;
//System.out.println"----------------------------------------"<<endl;
//test_option_price_call_black_scholes();
//test_black_scholes_partials_call();
//test_black_scholes_implied_volatility();
//};
//

}
