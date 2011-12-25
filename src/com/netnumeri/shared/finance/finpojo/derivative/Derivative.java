package com.netnumeri.shared.finance.finpojo.derivative;


import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.Instrument;
import com.netnumeri.shared.finance.utils.DateUtils;

public abstract class Derivative extends Instrument {
    public static final boolean debug = true;

    protected Instrument underlying;
    protected double interestRate;
    protected TDay expiration = null;
    protected double strike;
    protected double premium;
    protected int contractSize;

    protected int openInterest;

    protected double alpha = 0;
    protected double beta = 0;
    protected double theta = 0;
    protected double rho = 0;
    protected double vega = 0;

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public void setRho(double rho) {
        this.rho = rho;
    }

    public void setVega(double vega) {
        this.vega = vega;
    }


    public abstract double alpha(int model);


    public abstract double beta(int model);


    public abstract double theta(int model);


    public abstract double rho(int model);


    public abstract double vega(int model);

    protected Derivative() {
    }

    public Derivative(String name) {
        super(name);
        strike = 0;
        premium = 0;
        interestRate = 0;
    }

    public Derivative(String name,
                      Instrument underlying,
                      TDay expirationDate,
                      double exercisePrice,
                      double premium,
                      double interestRate) {
        super(name);
        this.underlying = underlying;
        this.expiration = expirationDate;
        this.strike = exercisePrice;
        this.premium = premium;
        this.interestRate = interestRate;
    }

    public Derivative(String s,
                      Instrument underlying,
                      TDay expirationDate,
                      double strikePrice,
                      double interestRate) {
        super(s);
        this.underlying = underlying;
        this.expiration = expirationDate;
        this.strike = strikePrice;
        this.interestRate = interestRate;
    }

    public Instrument getUnderlying() {
        return underlying;
    }

    public double getInterestRate() {
        return interestRate / 100;
    }

    public TDay getExpiration() {
        return expiration;
    }

    public double getStrike() {
        return strike;
    }

    public double premium() {
        return premium;
    }

    public int getContractSize() {
        return contractSize;
    }

    public void setUnderlying(Instrument Underlying) {
        underlying = Underlying;
    }

    public void setUnderlying(double p) {
        setSpot(p);
    }

    public void setInterestRate(double InterestRate) {
        interestRate = InterestRate;
    }

    public void setExpiration(TDay expirationDate) {
        expiration = expirationDate;
    }

    public void setExercisePrice(double ExercisePrice) {
        strike = ExercisePrice;
    }

    public void setStrike(double Strike) {
        strike = Strike;
    }

    public void setPremium(double Price) {
        premium = Price;
    }

    public void setContractSize(int ContractSize) {
        contractSize = ContractSize;
    }

    /**
     * underlying instrument spot premium
     */
    public double spot() {
        if (isSpotFixed()) {
            return super.spot();
        }
        if (underlying != null) {
            return underlying.spot();
        } else {
            System.out.println("getSpot. No underlyer");
            return 0;
        }
    }

    public double volatility() {
        if (isVolatilityFixed()) {
            return super.volatility() / 100;
        }
        if (underlying != null) {
            return underlying.volatility() / 100;
        } else {
            System.out.println("getVolatility. No underlyer");
            return 0;
        }
    }

    /**
     * Underlying instrument spot premium
     */
    public double S() {
        return spot();
    }

    /**
     * Exercise (strike) premium
     */
    public double X() {
        return strike;
    }

    /**
     * Derivative premium (premium)
     */
    public double P() {
        return premium;
    }

    /**
     * Time till expiration (maturity)
     */
    public double t() {
        return (double) (expirationDays()) / 365;
    }


    public double s() {
        return volatility();
    }

    public int getOpenInterest() {
        return openInterest;
    }

    public void setOpenInterest(int openInterest) {
        this.openInterest = openInterest;
    }

    /**
     * Continuous risk-free interest rate
     */
    public double r() {
        return interestRate / 100;
    }


    public long expirationDays() {
        return expirationDays(null);
    }

    /**
     * Returns number of days between Date and expiration date If Date == 0, return number of days betwen current
     * date and expiration date
     */
    public long expirationDays(TDay date) {
        if (date == null)
            date = new TDay();
        return DateUtils.diffDays(date, expiration);
    }

    public double getPremium() {
        return premium;
    }

}