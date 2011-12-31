package com.netnumeri.shared.finance.finpojo.derivative.equity;


import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.Instrument;
import com.netnumeri.shared.finance.finpojo.derivative.Derivative;
import com.netnumeri.shared.finance.math.FinRecipes;
import com.netnumeri.shared.finance.ta.FinMath;
import com.netnumeri.shared.pojoc.SearchablePojo;

public class Vanilla extends Derivative {
    protected int direction;
    protected int optionType;
    private double dividend;

    @Override
    public double alpha(int model) {
        return 0;
    }

    @Override
    public double beta(int model) {
        return 0;
    }

    @Override
    public double theta(int model) {
        return 0;
    }

    @Override
    public double rho(int model) {
        return 0;
    }

    @Override
    public double vega(int model) {
        return 0;
    }

    public Vanilla() {
    }

    public Vanilla(String Name) {
        super(Name);
    }

    public Vanilla(String name,
                   Instrument underlying,
                   TDay expirationDate,
                   double strikePrice,
                   double interestRate,
                   int direction,
                   int type) {
        super(name, underlying, expirationDate, strikePrice, interestRate);
        this.direction = direction;
        this.optionType = type;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getOptionType() {
        return optionType;
    }

    public void setOptionType(int optionType) {
        this.optionType = optionType;
    }

    public double getDividend() {
        return dividend;
    }

    public double d() {
        return getDividend() / 100D;
    }

    public void setDividend(double dividend) {
        this.dividend = dividend;
    }

    private double getPrice(String optionType,
                            double S,
                            double X,
                            double r,
                            double d,
                            double s,
                            double t) {
        double OptionPrice = 0;
        if (optionType.equalsIgnoreCase("European Call") || optionType.equalsIgnoreCase("European Put")) {
            OptionPrice = FinMath.BlackPrice(optionType, S, X, r, d, s, t);
        } else if (optionType.equalsIgnoreCase("American Call (BS)")) {
            OptionPrice = BSCallAmericanApprox(S, X, r, d, s, t);
        } else if (optionType.equalsIgnoreCase("American Put (BS)")) {
            OptionPrice = BSPutAmericanApprox(S, X, r, d, s, t);
        }
        return OptionPrice;
    }

    private double BSCallAmericanApprox(double d, double d1, double d2, double d3, double d4, double d5) {
        double d6 = 0;
        d6 = FinMath.BSAmericanCallApprox(d, d1, d5, d2, d2 - d3, d4);
        return d6;
    }

    private double BSPutAmericanApprox(double d, double d1, double d2, double d3, double d4, double d5) {
        double d6 = 0;
        d6 = FinMath.BSAmericanPutApprox(d, d1, d5, d2, d2 - d3, d4);
        return d6;
    }


    private double modelPrice() {
        if (direction == FinConstants.kCall) {
            if (optionType == FinConstants.kAmerican) {
                return getPrice("American Call (BS)", S(), X(), r(), d(), s(), t());
            }
            if (debug) {
                System.out.println("FinRecipes.option_price_call_black_scholes() = " +
                        FinRecipes.optionBlackScholesCall(S(), X(), r(), s(), t()));
            }
            return getPrice("European Call", S(), X(), r(), d(), s(), t());
        }
        if (optionType == FinConstants.kAmerican) {
            return getPrice("American Put (BS)", S(), X(), r(), d(), s(), t());
        } else {
            return getPrice("European Put", S(), X(), r(), d(), s(), t());
        }
    }

    public double getPayoff(double InstrumentPrice) {
        return getPayoff(InstrumentPrice, false);
    }

    public double getPayoff(double instrumentPrice, boolean withPremium) {
        double payoff = 0;
        if (direction == FinConstants.kCall) {
            payoff = Math.max(0, instrumentPrice - strike);
        } else {
            payoff = Math.max(0, strike - instrumentPrice);
        }
        if (withPremium) {
            payoff -= premium;
        }
        return payoff;
    }


    public double getDelta() {
        if (direction == FinConstants.kCall) {
            if (optionType == FinConstants.kAmerican) {
                return blackDelta("American Call (BS)", S(), X(), r(), d(), s(), t());
            } else {
                return blackDelta("European Call", S(), X(), r(), d(), s(), t());
            }
        } else if (optionType == FinConstants.kAmerican) {
            return blackDelta("American Put (BS)", S(), X(), r(), d(), s(), t());
        } else {
            return blackDelta("European Put", S(), X(), r(), d(), s(), t());
        }
    }


    public double gamma() {
        double d3 = 0.01D * S();
        double dv = 0.01D * s();
        if (direction == FinConstants.kCall) {
            if (optionType == FinConstants.kAmerican) {
                double d4 = (1 / (d3 * d3)) * ((getPrice("American Call (BS)", S() + d3, X(), r(), d(), s(), t()) - 2D * getPrice("American Call (BS)", S(), X(), r(), d(), s(), t())) + getPrice("American Call (BS)", S() - d3, X(), r(), d(), s(), t()));
                return d4;
            } else {
                double price = (1 / (d3 * d3)) * ((getPrice("European Call", S() + d3, X(), r(), d(), s(), t()) - 2D * getPrice("European Call", S(), X(), r(), d(), s(), t())) + getPrice("European Call", S() - d3, X(), r(), d(), s(), t()));
                return price;
            }
        } else if (optionType == FinConstants.kCall) {
            double d4 = (1 / (d3 * d3)) * ((getPrice("American Put (BS)", S() + d3, X(), r(), d(), s(), t()) - 2D * getPrice("American Put (BS)", S(), X(), r(), d(), s(), t())) + getPrice("American Put (BS)", S() - d3, X(), r(), d(), s(), t()));
            return d4;
        } else {
            double d4 = (1 / (d3 * d3)) * ((getPrice("European Put", S() + d3, X(), r(), d(), s(), t()) - 2D * getPrice("European Put", S(), X(), r(), d(), s(), t())) + getPrice("European Put", S() - d3, X(), r(), d(), s(), t()));
            return d4;
        }
    }


    public double theta() {
        if (direction == FinConstants.kCall) {
            return FinMath.Theta(S(), X(), t(), s(), r(), 92);
        } else {
            return FinMath.Theta(S(), X(), t(), s(), r(), 93);
        }
    }


    public double rho() {
        if (direction == FinConstants.kCall) {
            return FinMath.Rho(S(), X(), t(), s(), r(), 92);
        } else {
            return FinMath.Rho(S(), X(), t(), s(), r(), 93);
        }
    }


    public double vega() {
        System.out.println("Vega:" + FinMath.Vega(S(), X(), t(), s(), r()));
        double dv = 0.01D * s();
        if (direction == FinConstants.kCall) {
            if (optionType == FinConstants.kAmerican) {
                return (1 / dv) * (getPrice("American Call (BS)", S(), X(), r(), d(), s() + dv, t()) - getPrice("American Call (BS)", S(), X(), r(), d(), s(), t()));
            } else {
                return (1 / dv) * (getPrice("European Call", S(), X(), r(), d(), s() + dv, t()) - getPrice("European Call", S(), X(), r(), d(), s(), t()));
            }
        }
        if (optionType == FinConstants.kAmerican) {
            return (1 / dv) * (getPrice("American Put (BS)", S(), X(), r(), d(), s() + dv, t()) - getPrice("American Put (BS)", S(), X(), r(), d(), s(), t()));
        } else {
            return (1 / dv) * (getPrice("European Put", S(), X(), r(), d(), s() + dv, t()) - getPrice("European Put", S(), X(), r(), d(), s(), t()));
        }
    }


    public double blackDelta(String what,
                             double spot,
                             double strike,
                             double rate,
                             double dividend,
                             double volatility,
                             double time) {
        double delta = 0;
        double d6 = (Math.log(spot / strike) + ((rate - dividend) +
                (volatility * volatility) / 2D) * time) / (volatility * Math.sqrt(time));
        if (what.equalsIgnoreCase("European Call")) {
            delta = Math.exp(-dividend * time) * FinMath.CND(d6);
        } else if (what.equalsIgnoreCase("European Put")) {
            delta = Math.exp(-dividend * time) * (FinMath.CND(d6) - 1);
        } else if (what.equalsIgnoreCase("American Call (BS)")) {
            delta = BSCallAmericanApprox(spot + 1, strike, rate, dividend, volatility, time) - BSCallAmericanApprox(spot, strike, rate, dividend, volatility, time);
        } else if (what.equalsIgnoreCase("American Put (BS)")) {
            delta = BSPutAmericanApprox(spot + 1, strike, rate, dividend, volatility, time) - BSPutAmericanApprox(spot, strike, rate, dividend, volatility, time);
        }
        return delta;
    }


//    public MonteCarlo monteCarlo() {
//        if (monteCarlo != null) {
//            monteCarlo = new MonteCarlo(underlying, (int) expirationDays(), 10000);
//        }
//        return monteCarlo;
//    }
//

    public double binomialPrice() {
        return binomialPrice(50);
    }

    public double binomialPrice(int n) {
        if (direction == FinConstants.kCall) {
            return FinMath.BM(S(), X(), t(), s(), r(), 92, n);
        } else {
            return FinMath.BM(S(), X(), t(), s(), r(), 93, n);
        }
    }


    public double monteCarloPrice() {
        return monteCarloPrice(10000);
    }

    public double monteCarloPrice(int n) {
        if (direction == FinConstants.kCall) {
            return FinMath.MC(S(), X(), t(), s(), r(), 92, n);
        } else {
            return FinMath.MC(S(), X(), t(), s(), r(), 93, n);
        }
    }


    public double blackScholesPrice() {
        if (direction == FinConstants.kCall) {
            return FinMath.BS(S(), X(), t(), s(), r(), 92);
        } else {
            return FinMath.BS(S(), X(), t(), s(), r(), 93);
        }
    }

    @Override
    public double modelPrice(int model) {
        if (model == FinConstants.BlackScholes) {
            return blackScholesPrice();
        }
        if (model == FinConstants.Binomial) {
            return binomialPrice();
        }
        if (model == FinConstants.Montecarlo1) {
            return monteCarloPrice();
        }
//        double OptionPrice = 0;
//        if (model == FinConstants.Montecarlo2) {
//            monteCarlo();
//            double Price = 0;
//            double instrumentPrice = 0;
//            for (int i = 0; i < monteCarlo.getNTries(); i++) {
//                Price = underlying.premium() * monteCarlo.getSimulatedTargetPremium();
//                instrumentPrice += Price;
//                OptionPrice += getPayoff(Price);
//            }
//            instrumentPrice /= monteCarlo.getNTries();
//            OptionPrice /= monteCarlo.getNTries();
//            OptionPrice = FinMath.PV4(OptionPrice, r(), t());
//        }
//        return OptionPrice;
        return -1;
    }

    public double modelPrice(int model,
                             double price,
                             double days) {
        if (model == FinConstants.BlackScholes) {
            return blackScholesPrice(price, days);
        }
        if (model == FinConstants.Binomial) {
            return binomialPrice(price, days);
        }
        if (model == FinConstants.Montecarlo1) {
            return monteCarloPrice(price, days);
        }
        double OptionPrice = 0;
//        if (model == FinConstants.Montecarlo2) {
//            monteCarlo();
//            double Price = 0;
//            double instrumentPrice = 0;
//            for (int i = 0; i < monteCarlo.getNTries(); i++) {
//                Price = price * monteCarlo.getSimulatedTargetPremium();
//                instrumentPrice += Price;
//                OptionPrice += getPayoff(Price);
//            }
//
//            instrumentPrice /= monteCarlo.getNTries();
//            OptionPrice /= monteCarlo.getNTries();
//            OptionPrice = FinMath.PV4(OptionPrice, r(), days);
//        }
        return OptionPrice;
    }

    private double monteCarloPrice(double price, double days) {
        if (direction == FinConstants.kCall) {
            return FinMath.MC(price, X(), days, s(), r(), 92, 10000);
        } else {
            return FinMath.MC(price, X(), days, s(), r(), 93, 10000);
        }
    }

    private double binomialPrice(double price, double days) {
        if (direction == FinConstants.kCall) {
            return FinMath.BM(price, X(), days, s(), r(), 92, 500);
        } else {
            return FinMath.BM(price, X(), days, s(), r(), 93, 500);
        }
    }

    private double blackScholesPrice(double spot, double days) {
        if (direction == FinConstants.kCall) {
            return FinMath.BS(spot, X(), days, s(), r(), 92);
        } else {
            return FinMath.BS(spot, X(), days, s(), r(), 93);
        }
    }

    public double modelPrice(double price, double days) {
        return modelPrice(FinConstants.BlackScholes, price, days);
    }

    @Override
    public String getKind() {
        return null;
    }

    @Override
    public SearchablePojo clone() {
        return null;
    }
}