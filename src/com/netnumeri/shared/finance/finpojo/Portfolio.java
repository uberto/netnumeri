package com.netnumeri.shared.finance.finpojo;

// Portfolio
// A portfolio holds a list of portfolio entries (see PortfolioItem class).
// Portfolio can be seen as a
//   - collection of financial instruments (inherited from Instrument).
//   - result of a sequence of trade transaction
// Note that since Portfolio itself is derived from Instrument, a portfolio
// can hold another portfolio, etc.
// Portfolio provides a number of methods for risk calculations and management,
// such as greeks, risk matrix, P&L (Profit and Loss) and VaR (Value at Risk).
// Modern portfolio theory (Markovitz) and CAPM (Capital Asset Pricing Model)
// are currently implemented in this class for portolio optimization.
// A webuser-defined objective function can also be used for portfolio
// optimization.
// SimulatedAnnealing optimizer is designed to solve combinatorial optimization
// tasks. Thus you can select an optimal sub-portfolio of N stock out of M
// asset portfolio.


import com.netnumeri.shared.field.FieldMap;
import com.netnumeri.shared.field.FieldName;
import com.netnumeri.shared.field.StringEntityField;
import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.data.TransactionSeries;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.asset.Asset;
import com.netnumeri.shared.finance.matrix.Matrix;
import com.netnumeri.shared.finance.math.PortfolioMath;
import com.netnumeri.shared.finance.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class Portfolio extends Asset implements FinConstants {

    enum Field implements FieldName {
        name
    }
    private FieldMap fieldMap = new FieldMap();
    private StringEntityField name = new StringEntityField(fieldMap,Field.name, 5);


    public Portfolio() {
        init("Unnamed");
    }

    public List<PortfolioItem> items;
    public TransactionSeries transactions = null;

    public int assetsToHold = 0;
    public double wealth = 0;
    public double[] tempWeights;

    public Matrix covarianceMatrix;
    public Matrix correlationMatrix;

    private double targetReturn;
    private double tradeoff;
    private int objectiveOption;
    private int nPoints;

    public TDay firstDailyDate;
    public TDay lastDailyDate;

    private Parameters parm = new Parameters();

    public double modelPrice(int model) {
        return 0;
    }

    @Override
    public String getName() {
        return name.get();
    }

    @Override
    public void setName(String name) {
        this.name.setValue(name);
    }

    public Portfolio(String name) {
        init(name);
    }

    public Portfolio(String name, double wealth) {
        init(name);
        this.wealth = wealth;
    }

    public TDay getFirstDay() {
        if (firstDailyDate != null) {
            return firstDailyDate;
        }
        Instrument instrument = null;
        for (int i = 0; i < items.size(); i++) {
            instrument = PortfolioMath.getInstrument(this,i);
            if (instrument instanceof Asset) {
                if (instrument.getFirstDay() != null) {
                    firstDailyDate = new TDay();
                    firstDailyDate = DateUtils.max(firstDailyDate, instrument.getFirstDay());
                }
            }
        }
        return firstDailyDate;
    }

    public TDay getLastDay() {
        Instrument instrument;
        if (lastDailyDate != null) {
            return lastDailyDate;
        }
        for (int i = 0; i < items.size(); i++) {
            instrument = PortfolioMath.getInstrument(this,i);
            if (instrument instanceof Asset) {
                if (instrument.getLastDay() != null) {
                    if (lastDailyDate == null) {
                        lastDailyDate = new TDay();
                        lastDailyDate = instrument.getLastDay();
                    } else {
                        lastDailyDate = DateUtils.min(lastDailyDate, instrument.getLastDay());
                    }
                }
            }
        }
        return lastDailyDate;
    }

    private void init(String name) {
        setName(name);
        assetsToHold = 0;
        targetReturn = 0;
        objectiveOption = RETURN;
        nPoints = 25;
        tempWeights = null;
        firstDailyDate = null;
        lastDailyDate = null;
//        MersenneTwister engine = new MersenneTwister();
//        dist = new Uniform(engine);
        //    items = new ArrayList<PortfolioItem>();
        transactions = new TransactionSeries(name);
        items = new ArrayList<PortfolioItem>();
    }

    public Parameters getParm() {
        return parm;
    }

    public void setParm(Parameters parm) {
        this.parm = parm;
    }

    public PortfolioItem getPortfolioItemByName(String name) {
        PortfolioItem item = null;
        if (items != null)
            for (int i = 0; i < items.size(); i++) {
                PortfolioItem portfolioItem = items.get(i);
                if (portfolioItem.name.get().equalsIgnoreCase(name)) ;
                item = portfolioItem;
            }
        return item;
    }

    public Portfolio clone() {
        Portfolio p = new Portfolio(getName());
        for (int i = 0; i < items.size(); i++) {
            PortfolioItem portfolioItem = items.get(i);
            assert p != null;
            PortfolioMath.add(p, portfolioItem);
        }

        p.transactions = (TransactionSeries) transactions.clone();
        p.assetsToHold = assetsToHold;
        p.wealth = wealth;
        p.tempWeights = tempWeights;
        if (covarianceMatrix != null) p.covarianceMatrix = (Matrix) covarianceMatrix.clone();
        if (correlationMatrix != null) p.correlationMatrix = (Matrix) correlationMatrix.clone();
        p.targetReturn = targetReturn;
        p.tradeoff = tradeoff;
        p.objectiveOption = objectiveOption;
        p.nPoints = nPoints;
        p.firstDailyDate = (TDay) getFirstDay().clone();
        p.lastDailyDate = (TDay) getLastDay().clone();
//        if (dist != null) p.dist = (AbstractDistribution) dist.clone();
        return p;
    }


}
