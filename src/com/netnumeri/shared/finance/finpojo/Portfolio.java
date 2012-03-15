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


import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.beans.TimeSeries;
import com.netnumeri.shared.finance.data.Transaction;
import com.netnumeri.shared.finance.data.TransactionSeries;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.asset.Asset;
import com.netnumeri.shared.finance.finpojo.derivative.Derivative;
import com.netnumeri.shared.finance.matrix.Matrix;
import com.netnumeri.shared.finance.utils.DateUtils;
import com.netnumeri.shared.pojoc.field.IdField;

import java.util.ArrayList;
import java.util.List;

public class Portfolio extends Asset implements FinConstants {

    public IdField userId = idField("userId");

    public Portfolio() {
        init("Unnamed");
    }

//    public Optimizer optimizer;

    private List<PortfolioItem> items;

    private TransactionSeries transactions = null;

    private int assetsToHold = 0;
    private double wealth = 0;
    private double[] tempWeights;


    private Matrix covarianceMatrix;
    private Matrix correlationMatrix;

    private double targetReturn;
    private double tradeoff;
    private int objectiveOption;
    private int nPoints;

//    private MonteCarlo monteCarlo;
    private TDay firstDailyDate;
    private TDay lastDailyDate;

//    private AbstractDistribution dist = null;

    private Parameters parm = new Parameters();

    public double modelPrice(int model) {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    public Portfolio(String name) {
        init(name);
    }

    public Portfolio(String name, double wealth) {
        init(name);
        this.wealth = wealth;
    }

    private void init(String name) {
        fName.setValue(name);
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

//    public Optimizer getOptimizer() {
//        return optimizer;
//    }
//
//    public void setOptimizer(Optimizer optimizer) {
//        this.optimizer = optimizer;
//    }

    public PortfolioItem getPortfolioItemByName(String name) {
        PortfolioItem item = null;
        List<PortfolioItem> list = getInstruments();
        if (list != null)
            for (int i = 0; i < list.size(); i++) {
                PortfolioItem portfolioItem = list.get(i);
                if (portfolioItem.getName().equalsIgnoreCase(name)) ;
                item = portfolioItem;
            }
        return item;
    }


//    public Optimizer setOptimizer(int OptimizerType) {
//        if (optimizer == null) {
//            if (OptimizerType == kCoordinateDescent) {
//                throw new IllegalArgumentException("impossible switch");
//            } else if (OptimizerType == kGeneticAlgorithm) {
//                throw new IllegalArgumentException("impossible switch");
//            } else if (OptimizerType == kSimulatedAnnealing) {
//                optimizer = new SimulatedAnnealing(this);
//            }
//        }
//        optimizer.setNParam(parm.getNParam());
//        for (int i = 0; i < parm.getNParam(); i++) {
//            if (parm.getParamType(i) == kInt) {
//                optimizer.setParam(i, (int) (parm.getParam(i)));
//            } else {
//                optimizer.setParam(i, parm.getParam(i));
//            }
//            optimizer.setLowerBound(i, parm.getLowerBound(i));
//            optimizer.setUpperBound(i, parm.getUpperBound(i));
//            optimizer.fixParam(i, parm.isParamFixed(i));
//        }
//        return optimizer;
//    }

    public Portfolio clone() {
        Portfolio p = new Portfolio(getName());
        for (int i = 0; i < items.size(); i++) {
            PortfolioItem portfolioItem = items.get(i);
            assert p != null;
            p.add(portfolioItem);
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

    public void clear() {
        items.clear();
        transactions.clear();
    }


    boolean isEmpty() {
        if (items.size() == 0) {
            return true;
        } else {
            return false;
        }
    }


    public TDay getFirstDay() {
        if (firstDailyDate != null) {
            return firstDailyDate;
        }
        Instrument instrument = null;
        for (int i = 0; i < items.size(); i++) {
            instrument = getInstrument(i);
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
            instrument = getInstrument(i);
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

    public void add(PortfolioItem entry) {
        Instrument instrument = entry.getInstrument();
        if (getEntry(instrument) != null) {
            System.out.println("addEntry. Instrument: " + instrument.getName() + " already exists in portfolio " + getName());
            return;
        }

        if (instrument instanceof Asset) {
            if (instrument.getLowerBoundDate() != null && instrument.getUpperBoundTDay() != null) {
                instrument.setRangeBounds(instrument.getLowerBoundDate(), instrument.getUpperBoundTDay());
            }
            this.setWindow(instrument.getLowerBoundDate(), instrument.getUpperBoundTDay());
            this.setRangeBounds(instrument.getLowerBoundDate(), instrument.getUpperBoundTDay());
        }
        items.add(entry);
    }

/*
    private void changed()
    {
        emit("portfolio changed");
    }

    public void entryAdded()
    {
        emit("Entryadded() in portfolio");
    }

    public void entryRemoved()
    {
        emit("Entry removed()");
    }
*/

    public void add(Instrument instrument) {
        if (instrument == null) {
            throw new IllegalArgumentException("instrument cannot be null");
        }
        add(instrument, 0);
    }

    public void add(Instrument instrument, int Amount) {
        add(new PortfolioItem(instrument, Amount));
    }

    public void add(Transaction transaction) {
        if (transaction == null) throw new IllegalArgumentException("transaction cannot be null");

        Instrument instrument = transaction.getInstrument();
        PortfolioItem entry = getEntry(instrument);

        if (entry == null) {
            entry = new PortfolioItem(instrument);
            if (transaction.getAction() == BUY) {
                entry.setAmount(transaction.getAmount());
            } else if (transaction.getAction() == SELL) {
                System.out.println("addTransaction. No long position on sell for " + transaction.getInstrument().getName() + " in " + getName());
                return;
            } else if (transaction.getAction() == SELLSHORT) {
                entry.setAmount(-transaction.getAmount());
            } else if (transaction.getAction() == BUYSHORT) {
                System.out.println("addTransaction. No short position on buy short for " + transaction.getInstrument().getName() + " in " + getName());
                return;
            }
            transactions.add(transaction);
            add(entry);
            return;
        } else {
            int amount = 0;
            if (transaction.getAction() == BUY) {
                if (entry.getAmount() < 0) {
                    System.out.println("addTransaction. Short position on buy for " + transaction.getInstrument().getName() + " in " + getName());
                    return;
                }
                amount = entry.getAmount() + transaction.getAmount();
            } else if (transaction.getAction() == SELL) {
                amount = entry.getAmount() - transaction.getAmount();
                if (amount < 0) {
                    System.out.println("addTransaction. Sell amount larger than long position for" + transaction.getInstrument().getName() + " in " + getName());
                    return;
                }
            } else if (transaction.getAction() == SELLSHORT) {
                if (entry.getAmount() > 0) {
                    System.out.println("addTransaction. Long position in instrument on sell short: " + getName());
                    return;
                }
                amount = entry.getAmount() - transaction.getAmount();
            } else if (transaction.getAction() == BUYSHORT) {
                if (entry.getAmount() > 0) {
                    System.out.println("addTransaction. Long position on buy short for " + transaction.getInstrument().getName() + " in " + getName());
                    return;
                }
                amount = entry.getAmount() + transaction.getAmount();
                if (amount > 0) {
                    System.out.println("addTransaction. Buy short amount larger than short position: " + getName());
                    return;
                }
            }
            transactions.add(transaction);

            if (amount == 0) {
                remove(instrument);
            } else {
                entry.setAmount(amount);
            }
        }
    }

    // add series of trade transactions

    public void add(TransactionSeries series) {
        if (series == null) {
            throw new IllegalArgumentException("series cannot be null");
        }
        int N = series.getN();
        for (int i = 0; i < N; i++) {
            add(series.getTransaction(i));
        }
    }

    // Return pointer to portfolio entry holding instrument
    // Return null if there is no such entry in portfolio
    public PortfolioItem getEntry(Instrument instrument) {
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        PortfolioItem entry;
        if (items != null)
        for (int i = 0; i < items.size(); i++) {
            entry = item(i);
            if (entry.getInstrument().equals(instrument)) {
                return entry;
            }
        }
        return null;
    }

    public PortfolioItem getEntry(String Name) {
        PortfolioItem entry;
        for (int i = 0; i < items.size(); i++) {
            entry = item(i);
            if (entry.getInstrument().getName().compareToIgnoreCase(Name) >= 0) {
                return entry;
            }
        }
        return null;
    }

    public void invest(double wealth) {
        invest(wealth, null);
    }

    // Invest wealth into portfolio according to current portfolio weights
    public void invest(double wealth, TDay date) {

        if (items == null || items.isEmpty()) throw new PortfolioException("no instruments to invest money into");
        for (int i = 0; i < items.size(); i++) {
            Asset asset = (Asset) getInstrument(i);
            double price = 0;
            if (asset.isDataAvailable(date)) {
                price = asset.premium();
            } else {
                int PrevIndex = asset.getPrevIndex(date);
                if (PrevIndex == -1) {
                    System.out.println("Invest. No data for " + date.toString());
                    return;
                } else {
                    price = asset.getPrice(PrevIndex);
                }
            }
            setAmount(i, (int) (getItemAmount(i) + wealth * getWeight(i) / price));
        }
    }

    public Transaction buy(Instrument instrument, int amount) {
        return buy(instrument, amount, null);
    }

    public Transaction buy(Instrument instrument, int amount, TDay date) {
        if (date == null) date = new TDay();
        Transaction transaction = new Transaction(instrument, BUY, amount, instrument.getPrice(date), date);
        add(transaction);
        return transaction;
    }

    public Transaction sell(Instrument instrument, int amount) {
        return sell(instrument, amount, null);
    }

    public Transaction sell(Instrument instrument, int amount, TDay date) {
        if (date == null) date = new TDay();
        Transaction transaction = new Transaction(instrument, SELL, amount, instrument.getPrice(date), date);
        add(transaction);
        return transaction;
    }

    public Transaction sellShort(Instrument instrument, int amount) {
        return sellShort(instrument, amount, null, 0);
    }

    public Transaction sellShort(Instrument instrument, int Amount, TDay date, int Time) {
        if (date == null) date = new TDay();
        Transaction transaction = new Transaction(instrument, SELLSHORT, Amount, instrument.getPrice(date), date);
        add(transaction);
        return transaction;
    }

    public Transaction buyShort(Instrument instrument, int Amount) {
        return buyShort(instrument, Amount, null);
    }

    // Buy short

    public Transaction buyShort(Instrument instrument, int Amount, TDay date) {
        if (date == null) date = new TDay();
        Transaction transaction = new Transaction(instrument, BUYSHORT, Amount, instrument.getPrice(date), date);
        add(transaction);
        return transaction;
    }

    public Transaction sell(Instrument instrument) {
        return sell(instrument, null);
    }

    // Sell everything

    public Transaction sell(Instrument instrument, TDay date) {
        int amount;
        if (getEntry(instrument) != null) {
            amount = getAmount(instrument);
        } else {
            return null;
        }
        if (date == null) date = new TDay();
        Transaction transaction = new Transaction(instrument, SELL, amount, instrument.getPrice(date), date);
        add(transaction);
        return transaction;
    }

    // delete instrument from portfolio

    public void remove(Instrument instrument) {
        for (int i = 0; i < items.size(); i++) {
            PortfolioItem entry = items.get(i);
            if (entry.getInstrument().getName().equalsIgnoreCase(instrument.getName())) {
                items.remove(entry);
                normalizeWeights();
                break;
            }
        }
    }

    // Return weight of this instrument in the portfolio
    // Return 0 if instrument is not in the portfolio

    public double getWeight(Instrument instrument) {
        PortfolioItem entry = getEntry(instrument);
        if (entry != null) {
            return entry.getWeight();
        } else {
            return 0;
        }
    }

    // Return position of this instrument in the portfolio
    // Return 0 if instrument is not in the portfolio

    public int getPosition(Instrument instrument) {
        PortfolioItem entry = getEntry(instrument);
        if (entry != null) {
            return entry.getPosition();
        } else {
            return 0;
        }
    }

    // Return amount of this instrument in the portfolio
    // Return 0 if instrument is not in the portfolio

    public int getAmount(Instrument instrument) {
        PortfolioItem entry = getEntry(instrument);
        if (entry != null) {
            return entry.getAmount();
        } else {
            return 0;
        }
    }


    public double getAmount() {
        // Return amount of all items in portfolio
        int Amount = 0;
        for (int i = 0; i < items.size(); i++) {
            Amount += getItemAmount(i);
        }
        return Amount;
    }

    public double getWealth(int i) {
        return getWealth(i, null);
    }

    // Return wealth for i-th asset in portfolio

    public double getWealth(int i, TDay date) {
        Instrument asset = getInstrument(i);
        double price = 0;
        if (asset.isDataAvailable(date)) {
            price = asset.premium();
        } else {
            int PrevIndex = asset.getPrevIndex(date);
            if (PrevIndex == -1) {
                System.out.println("getWealth. No data for " + date.toString());
            } else {
                price = asset.getPrice(PrevIndex);
            }
        }
        return price * getItemAmount(i);
    }


    public double getWealth() {
        return getWealth(null);
    }

    // Return wealth of portfolio

    public double getWealth(TDay date) {
        double Wealth = 0;
        for (int i = 0; i < items.size(); i++) {
            Wealth += getWealth(i, date);
        }
        return Wealth;
    }

    // Store weights in temp array

    public void storeWeights() {
        tempWeights = new double[items.size()];
        for (int i = 0; i < items.size(); i++) {
            tempWeights[i] = getWeight(i);
        }
    }

    // Restore weights from temp array

    public void restoreWeights() {
        if (tempWeights == null) {
            System.out.println("restoreWeights. weight array is null");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            setWeight(i, tempWeights[i]);
        }
    }

    // randomize portfolio weights
//    public void randomizeWeights() {
//        for (int i = 0; i < items.size(); i++) {
//            setWeight(i, dist.nextDouble());
//        }
//        normalizeWeights();
//    }

    // Normalize weigts of all stock in portfolio to keep
    // weight sum equal to unity and satisfy boundary conditions
    // Note that we exclude stock with zero weights from the portfolio,
    // meaning that such stock will have zero weight after normalization
    public void normalizeWeights() {
        double WeightSum = 0;
        int i = 0;
        for (i = 0; i < items.size(); i++) {
            WeightSum += getWeight(i);
        }
        for (i = 0; i < items.size(); i++) {
            setWeight(i, getWeight(i) / WeightSum);
        }
        WeightSum = 1;
        boolean InBounds = true;
        for (i = 0; i < items.size(); i++) {
            if (getWeight(i) != 0) {
                if (getWeight(i) < lowerBound(i)) {
                    InBounds = false;
                    break;
                }
            }
        }
        if (!InBounds) {
            double LowerBoundSum = 0;
            for (i = 0; i < items.size(); i++) {
                if (getWeight(i) != 0) {
                    LowerBoundSum += lowerBound(i);
                }
            }
            for (i = 0; i < items.size(); i++) {
                if (getWeight(i) != 0) {
                    setWeight(i, lowerBound(i) + getWeight(i) * (1 - LowerBoundSum) / WeightSum);
                }
            }
        }
    }

    public void normalize() {
        normalize(kFixedWeight);
    }

    public void normalize(int Option) {
        if (Option == kFixedWeight) {
            normalizeWeights();
        } else {
            double Wealth = getWealth();
            for (int i = 0; i < items.size(); i++) {
                setWeight(i, getWealth(i) / Wealth);
            }
        }
    }

    // Check boundary conditions. Return true if feasible

    public boolean checkBounds() {
        double lowsum = 0;
        double upsum = 0;
        for (int i = 0; i < items.size(); i++) {
            lowsum += lowerBound(i);
            upsum += upperBound(i);
            if (lowerBound(i) >= upperBound(i)) {
                System.out.println("CheckBounds LowerBound >= UpperBound for parameter " + i);
                return false;
            }
        }
        if (lowsum >= 1) {
            System.out.println("CheckBounds LowerBoundSum >= 1");
            return false;
        }
        if (upsum <= 1) {
            System.out.println("CheckBounds UpperBoundSum <= 1");
            return false;
        }
        return true;
    }

    // Calculate models premium of portfolio

    public double getModelPrice(String Model, String printMode) {
        double price = 0;
        for (int i = 0; i < items.size(); i++) {
            price += getInstrument(i).premium() * getItemAmount(i) * getPosition(i);
        }
        return price;
    }

    // Return marked to market portfolio premium
    // If we consider a portfolio as one
    // financial instrument, its premium is
    // equal to its value

    public double getPrice(int Entry) {
        return getValue(Entry);
    }

    // Return marked to market portfolio premium If we consider a portfolio as one
    // financial instrument, its premium is equal to its value

    public double getPrice(TDay date) {
        return getValue(date);
    }

    // Return portfolio premium If we consider a portfolio as one
    // financial instrument, its premium is equal to its value


    public double premium() {
        return getValue();
    }

    // Return marked to market portfolio value

    public double getValue(int index) {
        Instrument instrument;
        Daily daily;
        int amount;
        double value = 0;
        for (int i = 0; i < items.size(); i++) {
            amount = getItemAmount(i);
            instrument = getInstrument(i);
            daily = instrument.getDaily(index);
            if (!daily.valid()) {
                daily = instrument.getPrevDaily(index);
            }
            if (daily != null) {
                value += daily.getCloseprice() * amount;
            } else {
                System.out.println("getName. Out of data range :" + index);
                return 0;
            }
        }
        return value;
    }

    /**
     * Mark 2 Market portfolio value
     *
     * @param date
     */
    public double getValue(TDay date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        Instrument instrument;
        Daily daily;
        int amount;
        double value = 0;
        for (int i = 0; i < items.size(); i++) {
            instrument = getInstrument(i);
            amount = getItemAmount(i);
            if (instrument instanceof Asset) {
                daily = instrument.getDaily(date);
                if (!daily.valid()) {
                    daily = instrument.getPrevDaily(date);
                    throw new IllegalArgumentException("date:" + date.toString() + "is not valid");
                }
                if (daily != null) {
                    value += daily.getCloseprice() * amount;
                } else {
                    System.out.println("getValue. Out of data range");
                    return 0;
                }
            }
            if (instrument instanceof Derivative) {
                value += instrument.premium() * amount;
            }
        }
        return value;
    }


    public double getValue() {
        // Return portfolio value
        double Value = 0;
        for (int i = 0; i < items.size(); i++) {
            Value += item(i).getValue();
        }
        return Value;
    }

    public double getReturn(TDay date) {
        // getAndRemove marked to market daily return
        double price = getPrice(date);
        double previousPrice;
        if (price == 0) {
            return 1;
        }
        TDay previousDate = getInstrument(0).getPrevDate(date);
        if (previousDate == null) {
            return 1;
        } else {
            previousPrice = getPrice(previousDate);
            if (previousPrice == 0) {
                return 1;
            } else {
                return price / previousPrice;
            }
        }
    }

    public double getLogReturn(TDay date) {
        return Math.log(getReturn(date));
    }

    public TimeSeries logReturnSeries() {
        TimeSeries logReturnSeries = new TimeSeries();
        logReturnSeries.setOption(LOGRETURN);
        TDay firstDate = getInstrument(0).firstDate();
        TDay lastDate = getInstrument(0).lastDate();
        for (TDay date = firstDate;
             date.isLessEqual(lastDate);
             date = date.nextDay()) {
            logReturnSeries.add(date, getLogReturn(date));
        }
        return logReturnSeries;
    }

    public double minReturn() {
        // Return min return
        double MinReturn = getInstrument(0).expectedReturn();
        for (int i = 0; i < items.size(); i++) {
            if (getInstrument(i).expectedReturn() < MinReturn) {
                MinReturn = getInstrument(i).expectedReturn();
            }
        }

        return MinReturn;
    }

    public double maxReturn() {
        // Return max return

        double MaxReturn = getInstrument(0).expectedReturn();
        for (int i = 0; i < items.size(); i++) {
            if (getInstrument(i).expectedReturn() > MaxReturn) {
                MaxReturn = getInstrument(i).expectedReturn();
            }
        }

        return MaxReturn;
    }

    public double minVariance() {
        // Return min variance
        double MinVariance = getInstrument(0).getVariance(LOGRETURN);
        for (int i = 0; i < items.size(); i++) {
            if (getInstrument(i).getVariance(LOGRETURN) < MinVariance) {
                MinVariance = getInstrument(i).getVariance(LOGRETURN);
            }
        }
        return MinVariance;
    }

    public double maxVariance() {
        // Return max variance
        double MaxVariance = getInstrument(0).getVariance(LOGRETURN);
        for (int i = 0; i < items.size(); i++) {
            if (getInstrument(i).getVariance(LOGRETURN) > MaxVariance) {
                MaxVariance = getInstrument(i).getVariance(LOGRETURN);
            }
        }
        return MaxVariance;
    }

    public double expectedReturn() {
        // Calculate portfolio expected return
        double ExpectedReturn = 0;
        double Weight = 0;
        for (int i = 0; i < items.size(); i++) {
            Weight = getWeight(i);
            if (Weight != 0) {
                ExpectedReturn += getInstrument(i).expectedReturn() * Weight;
            }
        }
        return ExpectedReturn;
    }

    public double lowerBound(int i) {
        return item(i).getAssetWeightLowerBound();
    }

    public double upperBound(int i) {
        return item(i).getAssetWeightUpperBound();
    }

    public void setNHoldAsset(int NHoldAsset) {
        assetsToHold = NHoldAsset;
    }

    public void setInstrument(int i, Instrument instrument) {
        item(i).setInstrument(instrument);
    }

    public void setAmount(int i, int amount) {
        item(i).setAmount(amount);
    }

    public void setWeight(int i, double Weight) {
        item(i).setWeight(Weight);
    }

    public void setModelPrice(int i, double Price) {
        item(i).setModelPrice(Price);
    }

    public void setModelDate(int i, TDay date) {
        item(i).setModelDate(date);
    }

    public void setLowerBound(int i, double LowerBound) {
        item(i).setAssetWeightLowerBound(LowerBound);
    }

    public void setUpperBound(int i, double UpperBound) {
        item(i).setAssetWeightLowerBound(UpperBound);
    }

    public void setWealth(double Wealth) {
        wealth = Wealth;
    }

//    public TreeGridNode getTreeNode() {
//        return treeNode;
//    }

//    public void setTreeNode(TreeGridNode treeNode) {
//        this.treeNode = treeNode;
//    }

    public double variance() {
        // Calculate portfolio variance
        covarianceMatrix();
        double Variance = 0;
        double Weight1 = 0;
        double Weight2 = 0;
        for (int i1 = 0; i1 < items.size(); i1++) {
            Weight1 = getWeight(i1);
            if (Weight1 != 0) {
                for (int i2 = 0; i2 < items.size(); i2++) {
                    Weight2 = getWeight(i2);
                    if (Weight2 != 0)
                    //      Variance += covarianceMatrix[i1][i2]*Weight1*Weight2;
                    {
                        Variance += covarianceMatrix.get(i1, i2) * Weight1 * Weight2;
                    }
                }
            }
        }
        return Variance;
    }

    // Calculate portfolio standard deviation

    public double standardDeviation() {
        return Math.sqrt(variance());
    }

    // Return annual expected return

    public double annualExpectedReturn() {
        return (Math.pow(expectedReturn(), 365) - 1);
    }

    public double annualVariance() {
        // Calculate annual variance
        return variance() * 365.0;
    }

    public double annualStandardDeviation() {
        return standardDeviation() * Math.sqrt(365.0);
    }

    // Calculate CAPM beta with Benchmark representing market portfolio
    public double getBeta(Portfolio index) {
        double Beta = 0;
        for (int i = 0; i < items.size(); i++) {
            Beta += ((Portfolio) getInstrument(i)).getBeta(index) * getWeight(i);
        }
        return Beta;
    }

    // Claculate CAPM expected return excess with Benchmark representing market portfolio
    public double excess(Portfolio index, double InterestRate) {
        return InterestRate + getBeta(index) * (index.annualExpectedReturn() - InterestRate);
    }

    public double getSharpeIndex(double rate) {
        return (annualExpectedReturn() - rate) / annualStandardDeviation();
    }

    public double getTreynorIndex(Portfolio index, double rate) {
        return (annualExpectedReturn() - rate) / getBeta(index);
    }


    public double getDelta() {
        for (int i = 0; i < items.size(); i++) {
            delta += getInstrument(i).getDelta() * getItemAmount(i);
        }
        return delta;
    }


    /**
     * Calculate CAPM beta with Benchmark representing market portfolio
     */
    public double getBeta(Asset index) {
        return getCovariance(index) / index.variance();
    }

    /**
     * Calculate Traynor index
     * The Treynor index is a risk adjusted measure of portfolio performance, not unlike the Sharpe ratio. The Treynor index only adjusts for non-diversifiable risk, by dividing the excess return by the portfolio beta:
     * (rp - rf)/β
     *
     *  Where rp is the return on the portfolio,rf is the risk free return, and,β is the beta of the portfolio.
     * By excluding only adjusting for non-diversifiable risk, we assume that the portfolio it self is an investment
     * that we expect to be part of a wider diversified portfolio.
     * As this is typically true for funds this means that it is a suitable measure for assessing the performance of
     * fund managers.
     */
    public double getTreynorIndex(Asset index, double interestRate) {
        return ((Math.pow(expectedReturn(), 365) - 1) * 100 - interestRate) / getBeta(index);
    }

//    public double VaR(int NDays) {
//        return VaR(NDays, 0.95);
//    }

//    public double VaR(int NDays, double Confidence) {
//        return VaR(NDays, 1000, 0.95);
//    }

//    public double VaR(int NDays, int NTries, double Confidence) {
        // Calculate portfolio VaR (Value at Risk)
        // VaR measures the worst expected loss over a given time interval under
        // normal market conditions at a given confidence level
        // Historical Monte-Carlo bootstrap calculations for equities and path-dependent
        // derivatives. Model evaluation at the end of VaR interval for path-independent
        // derivatives and fixed income sequrities
        //
        // - use monteCarlo() and methods of TMonteCarlo class to set number of
        //   simulated paths and other related parameters
        //
        // - use setDataRange() to set range of historical data from which asset
        //   paths are calculated

//        double VaR = 0;
//        if (monteCarlo == null) {
//            monteCarlo = new MonteCarlo(this, NDays, NTries, false);
//            monteCarlo.setTargetOption(RETURN);
//        }
//
//        monteCarlo.getTargetArray();
//        DynamicBin1D fVaRBin = monteCarlo.getTarget1DDistribution();
//        VaR = fVaRBin.quantile(1 - Confidence);
//        VaR -= modelPrice();
//        return VaR;
//    }

    private double modelPrice() {
        return 0;
    }

//    public Histogram histogram() {
//        return monteCarlo.getHistogram();
//    }

    public Matrix covarianceMatrix() {
        covarianceMatrix = new Matrix(items.size(), items.size());
        for (int i1 = 0; i1 < items.size(); i1++) {
            for (int i2 = i1; i2 < items.size(); i2++) {
                covarianceMatrix.set(i1, i2, getInstrument(i1).getCovariance(getInstrument(i2), LOGRETURN));
                covarianceMatrix.set(i2, i1, covarianceMatrix.get(i1, i2));
            }
        }
        return covarianceMatrix;
    }

    // Build correlation matrix

    public Matrix correlationMatrix() {
        correlationMatrix = new Matrix(items.size(), items.size());
        covarianceMatrix();
        for (int i1 = 0; i1 < items.size(); i1++) {
            for (int i2 = i1; i2 < items.size(); i2++) {
                correlationMatrix.set(i1, i2, covarianceMatrix.get(i1, i2) /
                        (getInstrument(i1).getStandardDeviation(LOGRETURN) *
                                getInstrument(i2).getStandardDeviation(LOGRETURN)));
                correlationMatrix.set(i2, i1, correlationMatrix.get(i1, i2));
            }
        }
        return correlationMatrix;
    }

//    public void initWeights() {
//        // initialize portfolio weights
//        for (int i = 0; i < items.size(); i++) {
//            setWeight(i, 1.0 / (double) (items.size()));
//        }
//        normalizeWeights();
//    }
//
//    public void setTargetReturn(double TargetReturn) {
//        // set target return for portfolio optimizacion
//        // Annual, qualitativo, i.e. 20 for 20% per year
//        targetReturn = Math.pow(TargetReturn / 100.0 + 1, 1.0 / 365.0);
//    }
//
//    public double objective() {
//        double objective;
//        if (objectiveOption == RETURN) {
//            objective = Math.abs(expectedReturn() - targetReturn) + variance();
//        } else {
//            if (tradeoff >= 0) {
//                objective = tradeoff * variance() - (1 - tradeoff) * expectedReturn();
//            } else {
//                objective = -tradeoff * variance() + (1 + tradeoff) * expectedReturn();
//            }
//        }
//        return objective;
//    }
//
//    public void update() {
//        update(-1);
//    }
//
//    public void update(int Param) {
//        // optimizer update function, see Toptimizer class
//        int i = 0;
//        for (i = 0; i < items.size(); i++) {
//            setWeight(i, optimizer.getParam(i));
//        }
//        normalizeWeights();
//        for (i = 0; i < items.size(); i++) {
//            optimizer.setParam(i, getWeight(i));
//        }
//    }
//
//    public Optimizer optimizer() {
//        if (optimizer == null) {
//            optimizer = new SimulatedAnnealing(this);
//            optimizer.setNParam(items.size());
//            optimizer.setNParamSubset(assetsToHold);
//            for (int i = 0; i < items.size(); i++) {
//                optimizer.setParam(i, getWeight(i));
//                optimizer.setLowerBound(i, lowerBound(i));
//                optimizer.setUpperBound(i, upperBound(i));
//            }
//        }
//        return optimizer;
//    }
//
//    public void runOptimize() {
//        optimizer();
//        if (objectiveOption == RETURN) {
//            optimizer.setAttScheme(kPortfolio);
//        } else {
//            optimizer.setAttScheme(kEfficientFrontier);
//        }
//
//        if (items.size() == 0 || items.size() == 1) {
//            System.out.println("optimize. Number of stock in porfolio is" + items.size() + ": nothing to optimize");
//            return;
//        }
//
//        if (!checkBounds()) {
//            System.out.println("optimize. Boundary conditions are not feasible");
//            return;
//        }
//
//        initWeights();
//        if (optimizer == null) {
//            optimizer = optimizer();
//        } else {
//            optimizer.setObject(this);
//        }
//        optimizer.setNParam(items.size());
//        optimizer.setNParamSubset(assetsToHold);
//
//        int i = 0;
//        for (i = 0; i < items.size(); i++) {
//            optimizer.setParam(i, getWeight(i));
//            optimizer.setLowerBound(i, lowerBound(i));
//            optimizer.setUpperBound(i, upperBound(i));
//        }
//
//        initWeights();
//        optimizer.optimize();
//        //  print();
//        int NEntries = 0;
//        for (i = 0; i < items.size(); i++) {
//            if (getWeight(i) > 0) {
//                NEntries++;
//            }
//        }
//
//        System.out.println("NEntries = " + NEntries);
//    }
//
//    public void optimize() {
//        optimize(RETURN);
//    }
//
//    public void optimize(int ObjectiveOption) {
//        objectiveOption = ObjectiveOption;
//        runOptimize();
//    }
//
//    public void efficientFrontier() {
//        efficientFrontier(false);
//    }
//
//    public void efficientFrontier(boolean InThread) {
//        /*
//         #ifdef _Thread
//        if (InThread) {
//            if (fThread)
//                delete fThread;
//            fThread = new TThread(PortfolioefficientFrontierStartFunction, (void*)this);
//            fThread.Run();
//        }
//        else
//            #endif
//         */
//        runEfficientFrontier();
//    }
//
//    public void cleanUp(double MinWeight) {
//        // delete stock those weights in portfolio less than MinWeight
//        for (int i = 0; i < items.size(); i++) {
//            if (getWeight(i) < MinWeight) {
//                remove(getInstrument(i));
//            }
//        }
//    }
//
//    // Build efficient frontier graph (MPT, Modern Portfolio Theory, Markovitz)
//
//    public void runEfficientFrontier() {
//        storeWeights();
//        initWeights();
//        objectiveOption = RETURN;
//        //  objectiveOption = -1;
//        targetReturn = 0;
//        tradeoff = 0;
//        int PointCount = 0;
//        double StepSize = 0;
//        if (objectiveOption != RETURN) {
//            StepSize = 2.0 / nPoints;
//            // upper part
//            for (tradeoff = 0; tradeoff <= 1; tradeoff += StepSize) {
//                System.out.println("Tradeoff = " + tradeoff);
//                optimize(-1);
//                System.out.println("PointCount = " + PointCount);
//                System.out.println("annualStandardDeviation() * 100 " + annualStandardDeviation() * 100);
//                System.out.println("annualExpectedReturn() * 100 " + annualExpectedReturn() * 100);
////                fEfficientFrontier.setPoint(PointCount, annualStandardDeviation() * 100, annualExpectedReturn() * 100);
//                PointCount++;
//            }
//            // lower part
//            for (tradeoff = -1; tradeoff < 0; tradeoff += StepSize) {
//                System.out.println("Tradeoff = " + tradeoff);
//                optimize(-1);
////                fEfficientFrontier.setPoint(PointCount, annualStandardDeviation() * 100, annualExpectedReturn() * 100);
//                System.out.println("PointCount = " + PointCount);
//                System.out.println("annualStandardDeviation() * 100 " + annualStandardDeviation() * 100);
//                System.out.println("annualExpectedReturn() * 100 " + annualExpectedReturn() * 100);
//                PointCount++;
//            }
//        } else {
//            double MinReturn = (Math.pow(minReturn(), 365) - 1) * 100;
//            double MaxReturn = (Math.pow(maxReturn(), 365) - 1) * 100;
//            System.out.println("Min return = " + MinReturn);
//            System.out.println("Max return = " + MaxReturn);
//            StepSize = (MaxReturn - MinReturn) / nPoints;
//            for (double TargetReturn = MinReturn; TargetReturn <= MaxReturn; TargetReturn += StepSize) {
//                System.out.println("Return = " + TargetReturn);
//                setTargetReturn(TargetReturn);
//                randomize();
//                optimize();
////                fEfficientFrontier.setPoint(PointCount, annualStandardDeviation() * 100, annualExpectedReturn() * 100);
//                System.out.println("PointCount = " + PointCount);
//                System.out.println("annualStandardDeviation() * 100 " + annualStandardDeviation() * 100);
//                System.out.println("annualExpectedReturn() * 100 " + annualExpectedReturn() * 100);
//                PointCount++;
//            }
//        }
//        restoreWeights();
//    }

    public void onNewTrade() {
        System.out.println("onNewTrade");
        System.out.println("Price: " + premium());
    }

    /*   public Parameters getParamSet(int i) {
        PortfolioItem entry = tile(i);
        if (entry != null) {
            return entry.getParamset();
        } else {
            System.out.println("getParamSet. No entry with number " + i + " in " + getName());
            return null;
        }
    }*/

    /* public Parameters getParamSet(Instrument instrument) {
            // Return parameter set for entry holding Instrument
            // Return zero pointer if there is no
            // such entry in portfolio
            PortfolioItem entry = getEntry(instrument);
            if (entry != null) {
                return entry.getParamset();
            } else {
                System.out.println("getParamSet. No entry for instrument " + instrument.getName() + " in " + getName());
                return null;
            }
        }

        public Parameters getParamset(String Name) {
            // Return parameter set for entry holding
            // instrument with Name
            // Return zero pointer if there is no
            // such entry in portfolio
            PortfolioItem entry = getEntry(Name);
            if (entry != null) {
                return entry.getParamset();
            } else {
                System.out.println("getParamSet. No entry for instrument with name " + Name + " in " + getName());
                return null;
            }
        }

        public Parameters getParamSet(PortfolioItem entry) {
            // Return parameter set for entry
            return entry.getParamset();
        }
    */
    /*
      public void print(String Option) {
          // print portfolio
          //
          // Option
          //
          // "I" list of intruments
          // "T" list of transactions
          String SOption=Option;
          SOption.toLowerCase();
          System.out.println("Name: "+ getName() + " Title: "+ getTitle());
          if (SOption.indexOf("i")>=0)
              for(int i=0;i<items.size();i++)
                  instrument(i).print();
          if (SOption.indexOf("a")>=0) {
              double  Amount;
              for(int i=0;i<items.size();i++) {
                  Amount  = getItemAmount(i);
                  System.out.println("Name: " +instrument(i).getName()+"Amount: "+Amount);
              }
          }
          if (SOption.indexOf("w")>=0) {
              double  Weight;
              double  WeightSum = 0;
              for(int i=0;i<items.size();i++) {
                  Weight  = getWeight(i);
                  WeightSum += Weight;
                  if (Weight!=0)
                      System.out.println("Name: " +tile(i).getName() +"w: "+ Weight +"low: "+ getAssetWeightLowerBound(i)+"up :"+ getAssetWeightUpperBound(i));
              }
              System.out.println("Weight sum: " +WeightSum);
              System.out.println();
              System.out.println("Return : "+  annualExpectedReturn()   *100 +  "%/year");
              System.out.println("StdDev : " + annualStandardDeviation()*100 + "%");
          }
          if (SOption.indexOf("t")>=0)
              System.out.println(transactions.toString());
      }
    */

    public void onCircle() {
    }

    public void onStep() {
    }

    public int nTransactions() {
        return transactions.getNTransactions();
    }

    public TransactionSeries transactionSeries() {
        return transactions;
    }

    public Transaction getTransaction(int i) {
        return transactions.getTransaction(i);
    }

/*

    public Link<PortfolioItem> getEntryList()
    {
        return items;
    }

*/

    public PortfolioItem item(int i) {
        return items.get(i);
    }

    public int nentries() {
        return items.size();
    }

    public int nHoldAsset() {
        return assetsToHold;
    }

    public Instrument getInstrument(int i) {
        return item(i).getInstrument();
    }

    public int getPosition(int i) {
        return item(i).getPosition();
    }

    public int getItemAmount(int i) {
        return item(i).getAmount();
    }

    public double getWeight(int i) {
        return item(i).getWeight();
    }

    public double getModelPrice(int i) {
        return item(i).getModelPrice();
    }

    public TDay getModelDate(int i) {
        return item(i).getModelDate();
    }

//    public void randomize() {
//        randomizeWeights();
//    }

    public double getCovariance(int Row, int Col) {
        return covarianceMatrix.get(Row, Col);
    }

    public double getCorrelation(int Row, int Col) {
        return correlationMatrix.get(Row, Col);
    }

    public int objectiveOption() {
        return objectiveOption;
    }

    public int nPoints() {
        return nPoints;
    }

    public double tradeoff() {
        return tradeoff;
    }

    public double targetReturn() {
        return targetReturn;
    }

    public void setObjectiveOption(int objectiveOption) {
        this.objectiveOption = objectiveOption;
    }

    public void setNPoints(int NPoints) {
        nPoints = NPoints;
    }

    public void setTradeoff(double tradeoff) {
        this.tradeoff = tradeoff;
    }

    public int nparam() {
        return items.size();
    }

    public double getParam(int i) {
        return getWeight(i);
    }


    public Object getKey() {
        return getName();
    }

    public List<PortfolioItem> getInstruments() {
        return items;
    }

    public void setItems(List<PortfolioItem> items) {
        this.items = items;
    }

    public Matrix toMatrixLogReturns() {
        int dimension = getInstruments().size();
        Matrix ret = null;
        List entries = getInstruments();
        for (int i = 0; i < entries.size(); i++) {
            PortfolioItem item = (PortfolioItem) entries.get(i);
            Instrument instrument = item.getInstrument();
            double[] serie = instrument.logReturnSeries().toArray();
            if (ret == null) {
                ret = new Matrix(dimension, serie.length);
            }
            for (int x = 0; x < serie.length; x++) {
                double v = serie[x];
                ret.set(i, x, v);
            }
        }
        return ret;
    }

    public Matrix toMatrixReturns() {
        int dimension = getInstruments().size();
        Matrix ret = null;
        List entries = getInstruments();
        for (int i = 0; i < entries.size(); i++) {
            PortfolioItem item = (PortfolioItem) entries.get(i);
            Instrument instrument = item.getInstrument();
            double[] serie = instrument.returnSeries().toArray();
            if (ret == null) {
                ret = new Matrix(dimension, serie.length);
            }
            for (int x = 0; x < serie.length; x++) {
                double v = serie[x];
                ret.set(i, x, v);
            }
        }
        return ret;
    }


}
