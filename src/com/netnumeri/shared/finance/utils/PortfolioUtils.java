package com.netnumeri.shared.finance.utils;

import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.beans.TimeSeries;
import com.netnumeri.shared.finance.data.Transaction;
import com.netnumeri.shared.finance.data.TransactionSeries;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.*;
import com.netnumeri.shared.finance.finpojo.asset.Asset;
import com.netnumeri.shared.finance.finpojo.derivative.Derivative;
import com.netnumeri.shared.finance.matrix.Matrix;
import com.netnumeri.shared.finance.utils.DateUtils;

import java.util.List;

public class PortfolioUtils {


    public static void clear(Portfolio portfolio) {
        portfolio.items.clear();
        portfolio.transactions.clear();
    }

    boolean isEmpty(Portfolio portfolio) {
        if (portfolio.items.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static TDay getFirstDay(Portfolio portfolio) {
        if (portfolio.firstDailyDate != null) {
            return portfolio.firstDailyDate;
        }
        Instrument instrument = null;
        for (int i = 0; i < portfolio.items.size(); i++) {
            instrument = getInstrument(portfolio,i);
            if (instrument instanceof Asset) {
                if (instrument.getFirstDay() != null) {
                    portfolio.firstDailyDate = new TDay();
                    portfolio.firstDailyDate =
                            DateUtils.max(portfolio.firstDailyDate, instrument.getFirstDay());
                }
            }
        }
        return portfolio.firstDailyDate;
    }

    public static TDay getLastDay(Portfolio portfolio) {
        Instrument instrument;
        if (portfolio.lastDailyDate != null) {
            return portfolio.lastDailyDate;
        }
        for (int i = 0; i < portfolio.items.size(); i++) {
            instrument = getInstrument(portfolio,i);
            if (instrument instanceof Asset) {
                if (instrument.getLastDay() != null) {
                    if (portfolio.lastDailyDate == null) {
                        portfolio.lastDailyDate = new TDay();
                        portfolio.lastDailyDate = instrument.getLastDay();
                    } else {
                        portfolio.lastDailyDate = DateUtils.min(portfolio.lastDailyDate,
                                instrument.getLastDay());
                    }
                }
            }
        }
        return portfolio.lastDailyDate;
    }

    public static void add(Portfolio portfolio, PortfolioItem entry) {
        Instrument instrument = entry.getInstrument();
        if (getEntry(portfolio,instrument) != null) {
            System.out.println("addEntry. Instrument: " + instrument.getName() + " already exists in portfolio " + portfolio.getName());
            return;
        }

        if (instrument instanceof Asset) {
            if (instrument.getLowerBoundDate() != null && instrument.getUpperBoundDate() != null) {
                instrument.setRangeBounds(instrument.getLowerBoundDate(), instrument.getUpperBoundDate());
            }
            portfolio.setWindow(instrument.getLowerBoundDate(), instrument.getUpperBoundDate());
            portfolio.setRangeBounds(instrument.getLowerBoundDate(), instrument.getUpperBoundDate());
        }
        portfolio.items.add(entry);
    }

/*
    private void changed()
    {
        emit("portfolio changed");
    }

    public static static void entryAdded()
    {
        emit("Entryadded() in portfolio");
    }

    public static static void entryRemoved()
    {
        emit("Entry removed()");
    }
*/

    public static void add(Portfolio portfolio,Instrument instrument) {
        if (instrument == null) {
            throw new IllegalArgumentException("instrument cannot be null");
        }
        add(portfolio,instrument, 0);
    }

    public  static void add(Portfolio portfolio,Instrument instrument, int Amount) {
        add(portfolio, new PortfolioItem(portfolio, instrument, Amount));
    }

    public  static void add(Portfolio portfolio,Transaction transaction) {
        if (transaction == null) throw new IllegalArgumentException("transaction cannot be null");

        Instrument instrument = transaction.getInstrument();
        PortfolioItem entry = getEntry(portfolio,instrument);

        if (entry == null) {
            entry = new PortfolioItem(portfolio,instrument);
            if (transaction.getAction() == FinConstants.BUY) {
                entry.setAmount(transaction.getAmount());
            } else if (transaction.getAction() == FinConstants.SELL) {
                System.out.println("addTransaction. No long position on sell for " + transaction.getInstrument().getName() + " in " + portfolio.getName());
                return;
            } else if (transaction.getAction() == FinConstants.SELLSHORT) {
                entry.setAmount(-transaction.getAmount());
            } else if (transaction.getAction() == FinConstants.BUYSHORT) {
                System.out.println("addTransaction. No short position on buy short for " + transaction.getInstrument().getName() + " in " + portfolio.getName());
                return;
            }
            portfolio.transactions.add(transaction);
            add(portfolio, entry);
            return;
        } else {
            int amount = 0;
            if (transaction.getAction() == FinConstants.BUY) {
                if (entry.getAmount() < 0) {
                    System.out.println("addTransaction. Short position on buy for " + transaction.getInstrument().getName() + " in " + portfolio.getName());
                    return;
                }
                amount = entry.getAmount() + transaction.getAmount();
            } else if (transaction.getAction() == FinConstants.SELL) {
                amount = entry.getAmount() - transaction.getAmount();
                if (amount < 0) {
                    System.out.println("addTransaction. Sell amount larger than long position for" + transaction.getInstrument().getName() + " in " + portfolio.getName());
                    return;
                }
            } else if (transaction.getAction() == FinConstants.SELLSHORT) {
                if (entry.getAmount() > 0) {
                    System.out.println("addTransaction. Long position in instrument on sell short: " + portfolio.getName());
                    return;
                }
                amount = entry.getAmount() - transaction.getAmount();
            } else if (transaction.getAction() == FinConstants.BUYSHORT) {
                if (entry.getAmount() > 0) {
                    System.out.println("addTransaction. Long position on buy short for " + transaction.getInstrument().getName() + " in " + portfolio.getName());
                    return;
                }
                amount = entry.getAmount() + transaction.getAmount();
                if (amount > 0) {
                    System.out.println("addTransaction. Buy short amount larger than short position: " + portfolio.getName());
                    return;
                }
            }
            portfolio.transactions.add(transaction);

            if (amount == 0) {
                remove(portfolio,instrument);
            } else {
                entry.setAmount(amount);
            }
        }
    }

    // add series of trade transactions

    public static void add(Portfolio portfolio,TransactionSeries series) {
        if (series == null) {
            throw new IllegalArgumentException("series cannot be null");
        }
        int N = series.getN();
        for (int i = 0; i < N; i++) {
            add(portfolio,series.getTransaction(i));
        }
    }

    // Return pointer to portfolio entry holding instrument
    // Return null if there is no such entry in portfolio
    public static PortfolioItem getEntry(Portfolio portfolio,Instrument instrument) {
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        PortfolioItem entry;
        if (portfolio.items != null)
            for (int i = 0; i < portfolio.items.size(); i++) {
                entry = item(portfolio,i);
                if (entry.getInstrument().equals(instrument)) {
                    return entry;
                }
            }
        return null;
    }

    public static PortfolioItem getEntry(Portfolio portfolio,String Name) {
        PortfolioItem entry;
        for (int i = 0; i < portfolio.items.size(); i++) {
            entry = item(portfolio,i);
            if (entry.getInstrument().getName().compareToIgnoreCase(Name) >= 0) {
                return entry;
            }
        }
        return null;
    }

    public static void invest(Portfolio portfolio, double wealth) {
        invest(portfolio, wealth, null);
    }

    // Invest wealth into portfolio according to current portfolio weights
    public static void invest(Portfolio portfolio,double wealth, TDay date) {

        if (portfolio.items == null || portfolio.items.isEmpty()) throw new PortfolioException("no instruments to invest money into");
        for (int i = 0; i < portfolio.items.size(); i++) {
            Asset asset = (Asset) getInstrument(portfolio, i);
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
            setAmount(portfolio, i, (int) (getItemAmount(portfolio, i) + wealth * getWeight(portfolio, i) / price));
        }
    }

    public static Transaction buy(Portfolio portfolio,Instrument instrument, int amount) {
        return buy(portfolio, instrument, amount, null);
    }

    public static Transaction buy(Portfolio portfolio, Instrument instrument, int amount, TDay date) {
        if (date == null) date = new TDay();
        Transaction transaction = new Transaction(instrument, FinConstants.BUY, amount, instrument.getPrice(date), date);
        add(portfolio, transaction);
        return transaction;
    }

    public static Transaction sell(Portfolio portfolio,Instrument instrument, int amount) {
        return sell(portfolio,instrument, amount, new TDay());
    }

    public static Transaction sell(Portfolio portfolio,Instrument instrument, int amount, TDay date) {
        if (date == null) date = new TDay();
        Transaction transaction = new Transaction(instrument, FinConstants.SELL, amount, instrument.getPrice(date), date);
        add(portfolio,transaction);
        return transaction;
    }

    public static Transaction sellShort(Portfolio portfolio,Instrument instrument, int amount) {
        return sellShort(portfolio,instrument, amount, null, 0);
    }

    public static Transaction sellShort(Portfolio portfolio,Instrument instrument, int Amount, TDay date, int Time) {
        if (date == null) date = new TDay();
        Transaction transaction = new Transaction(instrument, FinConstants.SELLSHORT, Amount, instrument.getPrice(date), date);
        add(portfolio, transaction);
        return transaction;
    }

    public static Transaction buyShort(Portfolio portfolio,Instrument instrument, int Amount) {
        return buyShort(portfolio, instrument, Amount, new TDay());
    }

    // Buy short

    public static Transaction buyShort(Portfolio portfolio,Instrument instrument, int Amount, TDay date) {
        if (date == null) date = new TDay();
        Transaction transaction = new Transaction(instrument, FinConstants.BUYSHORT, Amount, instrument.getPrice(date), date);
        add(portfolio,transaction);
        return transaction;
    }

    public static Transaction sell(Portfolio portfolio,Instrument instrument) {
        return sell(portfolio, instrument, new TDay());
    }

    // Sell everything
    public static Transaction sell(Portfolio portfolio,Instrument instrument, TDay date) {
        int amount;
        if (getEntry(portfolio, instrument) != null) {
            amount = getAmount(portfolio, instrument);
        } else {
            return null;
        }
        if (date == null) date = new TDay();
        Transaction transaction = new Transaction(instrument, FinConstants.SELL, amount, instrument.getPrice(date), date);
        add(portfolio, transaction);
        return transaction;
    }

    // delete instrument from portfolio
    public static void remove(Portfolio portfolio,Instrument instrument) {
        for (int i = 0; i < portfolio.items.size(); i++) {
            PortfolioItem entry = portfolio.items.get(i);
            if (entry.getInstrument().getName().equalsIgnoreCase(instrument.getName())) {
                portfolio.items.remove(entry);
                normalizeWeights(portfolio);
                break;
            }
        }
    }

    // Return weight of this instrument in the portfolio
    // Return 0 if instrument is not in the portfolio

    public static double getWeight(Portfolio portfolio,Instrument instrument) {
        PortfolioItem entry = getEntry(portfolio, instrument);
        if (entry != null) {
            return entry.getWeight();
        } else {
            return 0;
        }
    }

    // Return position of this instrument in the portfolio
    // Return 0 if instrument is not in the portfolio

    public static int getPosition(Portfolio portfolio,Instrument instrument) {
        PortfolioItem entry = getEntry(portfolio, instrument);
        if (entry != null) {
            return entry.getPosition();
        } else {
            return 0;
        }
    }

    // Return amount of this instrument in the portfolio
    // Return 0 if instrument is not in the portfolio

    public static int getAmount(Portfolio portfolio,Instrument instrument) {
        PortfolioItem entry = getEntry(portfolio, instrument);
        if (entry != null) {
            return entry.getAmount();
        } else {
            return 0;
        }
    }


    public static double getAmount(Portfolio portfolio) {
        // Return amount of all items in portfolio
        int Amount = 0;
        for (int i = 0; i < portfolio.items.size(); i++) {
            Amount += getItemAmount(portfolio, i);
        }
        return Amount;
    }

    public static double getWealth(Portfolio portfolio,int i) {
        return getWealth(portfolio, i, null);
    }

    // Return wealth for i-th asset in portfolio
    public static double getWealth(Portfolio portfolio,int i, TDay date) {
        Instrument asset = getInstrument(portfolio, i);
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
        return price * getItemAmount(portfolio, i);
    }


    public static double getWealth(Portfolio portfolio) {
        return getWealth(portfolio,null);
    }

    // Return wealth of portfolio

    public static double getWealth(Portfolio portfolio,TDay date) {
        double Wealth = 0;
        for (int i = 0; i < portfolio.items.size(); i++) {
            Wealth += getWealth(portfolio,i, date);
        }
        return Wealth;
    }

    // Store weights in temp array

    public static void storeWeights(Portfolio portfolio) {
        portfolio.tempWeights = new double[portfolio.items.size()];
        for (int i = 0; i < portfolio.items.size(); i++) {
            portfolio.tempWeights[i] = getWeight(portfolio,i);
        }
    }

    // Restore weights from temp array

    public static void restoreWeights(Portfolio portfolio) {
        if (portfolio.tempWeights == null) {
            System.out.println("restoreWeights. weight array is null");
            return;
        }
        for (int i = 0; i < portfolio.items.size(); i++) {
            setWeight(portfolio, i, portfolio.tempWeights[i]);
        }
    }

    // randomize portfolio weights
//    public static void randomizeWeights() {
//        for (int i = 0; i < items.size(); i++) {
//            setWeight(i, dist.nextDouble());
//        }
//        normalizeWeights();
//    }

    // Normalize weigts of all stock in portfolio to keep
    // weight sum equal to unity and satisfy boundary conditions
    // Note that we exclude stock with zero weights from the portfolio,
    // meaning that such stock will have zero weight after normalization
    public static void normalizeWeights(Portfolio portfolio) {
        double WeightSum = 0;
        int i = 0;
        for (i = 0; i < portfolio.items.size(); i++) {
            WeightSum += getWeight(portfolio,i);
        }
        for (i = 0; i < portfolio.items.size(); i++) {
            setWeight(portfolio, i, getWeight(portfolio,i) / WeightSum);
        }
        WeightSum = 1;
        boolean InBounds = true;
        for (i = 0; i < portfolio.items.size(); i++) {
            if (getWeight(portfolio,i) != 0) {
                if (getWeight(portfolio,i) < lowerBound(portfolio,i)) {
                    InBounds = false;
                    break;
                }
            }
        }
        if (!InBounds) {
            double LowerBoundSum = 0;
            for (i = 0; i < portfolio.items.size(); i++) {
                if (getWeight(portfolio,i) != 0) {
                    LowerBoundSum += lowerBound(portfolio,i);
                }
            }
            for (i = 0; i < portfolio.items.size(); i++) {
                if (getWeight(portfolio,i) != 0) {
                    setWeight(portfolio, i, lowerBound(portfolio,i) + getWeight(portfolio,i) * (1 - LowerBoundSum) / WeightSum);
                }
            }
        }
    }

    public static void normalize(Portfolio portfolio) {
        normalize(portfolio, FinConstants.kFixedWeight);
    }

    public static void normalize(Portfolio portfolio,int Option) {
        if (Option == FinConstants.kFixedWeight) {
            normalizeWeights(portfolio);
        } else {
            double Wealth = getWealth(portfolio);
            for (int i = 0; i < portfolio.items.size(); i++) {
                setWeight(portfolio,i, getWealth(portfolio,i) / Wealth);
            }
        }
    }

    // Check boundary conditions. Return true if feasible
    public static boolean checkBounds(Portfolio portfolio) {
        double lowsum = 0;
        double upsum = 0;
        for (int i = 0; i < portfolio.items.size(); i++) {
            lowsum += lowerBound(portfolio,i);
            upsum += upperBound(portfolio,i);
            if (lowerBound(portfolio,i) >= upperBound(portfolio, i)) {
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

    public static double getModelPrice(Portfolio portfolio,String Model, String printMode) {
        double price = 0;
        for (int i = 0; i < portfolio.items.size(); i++) {
            price += getInstrument(portfolio,i).premium() * getItemAmount(portfolio,i) * getPosition(portfolio,i);
        }
        return price;
    }

    // Return marked to market portfolio premium
    // If we consider a portfolio as one
    // financial instrument, its premium is
    // equal to its value

    public static double getPrice(Portfolio portfolio,int Entry) {
        return getValue(portfolio,Entry);
    }

    // Return marked to market portfolio premium If we consider a portfolio as one
    // financial instrument, its premium is equal to its value

    public static double getPrice(Portfolio portfolio,TDay date) {
        return getValue(portfolio,date);
    }

    // Return portfolio premium If we consider a portfolio as one
    // financial instrument, its premium is equal to its value


    public static double premium(Portfolio portfolio) {
        return getValue(portfolio);
    }

    // Return marked to market portfolio value

    public static double getValue(Portfolio portfolio,int index) {
        Instrument instrument;
        Daily daily;
        int amount;
        double value = 0;
        for (int i = 0; i < portfolio.items.size(); i++) {
            amount = getItemAmount(portfolio,i);
            instrument = getInstrument(portfolio,i);
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
    public static double getValue(Portfolio portfolio,TDay date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        Instrument instrument;
        Daily daily;
        int amount;
        double value = 0;
        for (int i = 0; i < portfolio.items.size(); i++) {
            instrument = getInstrument(portfolio,i);
            amount = getItemAmount(portfolio,i);
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


    public static double getValue(Portfolio portfolio) {
        // Return portfolio value
        double Value = 0;
        for (int i = 0; i < portfolio.items.size(); i++) {
            Value += item(portfolio,i).getValue();
        }
        return Value;
    }

    public static double getReturn(Portfolio portfolio,TDay date) {
        // getAndRemove marked to market daily return
        double price = getPrice(portfolio,date);
        double previousPrice;
        if (price == 0) {
            return 1;
        }
        TDay previousDate = getInstrument(portfolio,0).getPrevDate(date);
        if (previousDate == null) {
            return 1;
        } else {
            previousPrice = getPrice(portfolio,previousDate);
            if (previousPrice == 0) {
                return 1;
            } else {
                return price / previousPrice;
            }
        }
    }

    public static double getLogReturn(Portfolio portfolio,TDay date) {
        return Math.log(getReturn(portfolio,date));
    }

    public static TimeSeries logReturnSeries(Portfolio portfolio) {
        TimeSeries logReturnSeries = new TimeSeries();
        logReturnSeries.setOption(FinConstants.LOGRETURN);
        TDay firstDate = getInstrument(portfolio,0).firstDate();
        TDay lastDate = getInstrument(portfolio,0).lastDate();
        for (TDay date = firstDate;
             date.isLessEqual(lastDate);
             date = date.nextDay()) {
            logReturnSeries.add(date, getLogReturn(portfolio,date));
        }
        return logReturnSeries;
    }

    public static double minReturn(Portfolio portfolio) {
        // Return min return
        double MinReturn = getInstrument(portfolio,0).expectedReturn();
        for (int i = 0; i < portfolio.items.size(); i++) {
            if (getInstrument(portfolio,i).expectedReturn() < MinReturn) {
                MinReturn = getInstrument(portfolio,i).expectedReturn();
            }
        }

        return MinReturn;
    }

    public static double maxReturn(Portfolio portfolio) {
        // Return max return

        double MaxReturn = getInstrument(portfolio,0).expectedReturn();
        for (int i = 0; i < portfolio.items.size(); i++) {
            if (getInstrument(portfolio,i).expectedReturn() > MaxReturn) {
                MaxReturn = getInstrument(portfolio,i).expectedReturn();
            }
        }

        return MaxReturn;
    }

    public static double minVariance(Portfolio portfolio) {
        // Return min variance
        double MinVariance = getInstrument(portfolio,0).getVariance(FinConstants.LOGRETURN);
        for (int i = 0; i < portfolio.items.size(); i++) {
            if (getInstrument(portfolio,i).getVariance(FinConstants.LOGRETURN) < MinVariance) {
                MinVariance = getInstrument(portfolio,i).getVariance(FinConstants.LOGRETURN);
            }
        }
        return MinVariance;
    }

    public static double maxVariance(Portfolio portfolio) {
        // Return max variance
        double MaxVariance = getInstrument(portfolio,0).getVariance(FinConstants.LOGRETURN);
        for (int i = 0; i < portfolio.items.size(); i++) {
            if (getInstrument(portfolio,i).getVariance(FinConstants.LOGRETURN) > MaxVariance) {
                MaxVariance = getInstrument(portfolio,i).getVariance(FinConstants.LOGRETURN);
            }
        }
        return MaxVariance;
    }

    public static double expectedReturn(Portfolio portfolio) {
        // Calculate portfolio expected return
        double ExpectedReturn = 0;
        double Weight = 0;
        for (int i = 0; i < portfolio.items.size(); i++) {
            Weight = getWeight(portfolio,i);
            if (Weight != 0) {
                ExpectedReturn += getInstrument(portfolio,i).expectedReturn() * Weight;
            }
        }
        return ExpectedReturn;
    }

    public static double lowerBound(Portfolio portfolio,int i) {
        return item(portfolio,i).getAssetWeightLowerBound();
    }

    public static double upperBound(Portfolio portfolio,int i) {
        return item(portfolio,i).getAssetWeightUpperBound();
    }

    public static void setNHoldAsset(Portfolio portfolio,int NHoldAsset) {
        portfolio.assetsToHold = NHoldAsset;
    }

    public static void setInstrument(Portfolio portfolio,int i, Instrument instrument) {
        item(portfolio,i).setInstrument(instrument);
    }

    public static void setAmount(Portfolio portfolio,int i, int amount) {
        item(portfolio,i).setAmount(amount);
    }

    public static void setWeight(Portfolio portfolio,int i, double Weight) {
        item(portfolio,i).setWeight(Weight);
    }

    public static void setModelPrice(Portfolio portfolio,int i, double Price) {
        item(portfolio,i).setModelPrice(Price);
    }

    public static void setModelDate(Portfolio portfolio,int i, TDay date) {
        item(portfolio,i).setModelDate(date);
    }

    public static void setLowerBound(Portfolio portfolio,int i, double LowerBound) {
        item(portfolio,i).setAssetWeightLowerBound(LowerBound);
    }

    public static void setUpperBound(Portfolio portfolio,int i, double UpperBound) {
        item(portfolio,i).setAssetWeightLowerBound(UpperBound);
    }

    public static void setWealth(Portfolio portfolio,double Wealth) {
        portfolio.wealth = Wealth;
    }

//    public static TreeGridNode getTreeNode() {
//        return treeNode;
//    }

//    public static void setTreeNode(TreeGridNode treeNode) {
//        this.treeNode = treeNode;
//    }

    public static double variance(Portfolio portfolio) {
        // Calculate portfolio variance
        Matrix matrix = covarianceMatrix(portfolio);
        double Variance = 0;
        double Weight1 = 0;
        double Weight2 = 0;
        for (int i1 = 0; i1 < portfolio.items.size(); i1++) {
            Weight1 = getWeight(portfolio,i1);
            if (Weight1 != 0) {
                for (int i2 = 0; i2 < portfolio.items.size(); i2++) {
                    Weight2 = getWeight(portfolio,i2);
                    if (Weight2 != 0)
                    //      Variance += covarianceMatrix[i1][i2]*Weight1*Weight2;
                    {
                        Variance += matrix.get(i1, i2) * Weight1 * Weight2;
                    }
                }
            }
        }
        return Variance;
    }

    // Calculate portfolio standard deviation

    public static double standardDeviation(Portfolio portfolio) {
        return Math.sqrt(variance(portfolio));
    }

    // Return annual expected return

    public static double annualExpectedReturn(Portfolio portfolio) {
        return (Math.pow(expectedReturn(portfolio), 365) - 1);
    }

    public static double annualVariance(Portfolio portfolio) {
        // Calculate annual variance
        return variance(portfolio) * 365.0;
    }

    public static double annualStandardDeviation(Portfolio portfolio) {
        return standardDeviation(portfolio) * Math.sqrt(365.0);
    }

    // Calculate CAPM beta with Benchmark representing market portfolio
    public static double getBeta(Portfolio portfolio,Portfolio index) {
        double Beta = 0;
        for (int i = 0; i < portfolio.items.size(); i++) {
            Portfolio p =  (Portfolio)getInstrument(portfolio, i);
            double beta = getBeta(portfolio, index);
            Beta += beta * getWeight(portfolio, i);
        }
        return Beta;
    }

    // Claculate CAPM expected return excess with Benchmark representing market portfolio
    public static double excess(Portfolio portfolio,Portfolio index, double InterestRate) {
        return InterestRate + getBeta(portfolio, index) * (index.annualExpectedReturn() - InterestRate);
    }

    public static double getSharpeIndex(Portfolio portfolio,double rate) {
        return (annualExpectedReturn(portfolio) - rate) / annualStandardDeviation(portfolio);
    }

    public static double getTreynorIndex(Portfolio portfolio,Portfolio index, double rate) {
        return (annualExpectedReturn(portfolio) - rate) / getBeta(portfolio, index);
    }


    public static double getDelta(Portfolio portfolio) {
        for (int i = 0; i < portfolio.items.size(); i++) {
            portfolio.delta += getInstrument(portfolio, i).getDelta() * getItemAmount(portfolio,i);
        }
        return portfolio.delta;
    }


    /**
     * Calculate CAPM beta with Benchmark representing market portfolio
     */
//    public static double getBeta(Portfolio portfolio,Asset index) {
//        return getCovariance(portfolio,index) / index.variance();
//    }

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
//    public static double getTreynorIndex(Portfolio portfolio, Portfolio index, double interestRate) {
//        return ((Math.pow(expectedReturn(portfolio), 365) - 1) * 100 - interestRate) / getBeta(portfolio, index);
//    }

//    public static double VaR(int NDays) {
//        return VaR(NDays, 0.95);
//    }

//    public static double VaR(int NDays, double Confidence) {
//        return VaR(NDays, 1000, 0.95);
//    }

//    public static double VaR(int NDays, int NTries, double Confidence) {
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

    private double modelPrice(Portfolio portfolio) {
        return 0;
    }

//    public static Histogram histogram() {
//        return monteCarlo.getHistogram();
//    }

    public static Matrix covarianceMatrix(Portfolio portfolio) {
        portfolio.covarianceMatrix = new Matrix(portfolio.items.size(), portfolio.items.size());
        for (int i1 = 0; i1 < portfolio.items.size(); i1++) {
            for (int i2 = i1; i2 < portfolio.items.size(); i2++) {
                portfolio.covarianceMatrix.set(i1, i2, getInstrument(portfolio, i1).getCovariance(getInstrument(portfolio,i2), FinConstants.LOGRETURN));
                portfolio.covarianceMatrix.set(i2, i1, portfolio.covarianceMatrix.get(i1, i2));
            }
        }
        return portfolio.covarianceMatrix;
    }

    // Build correlation matrix

    public static Matrix correlationMatrix(Portfolio portfolio) {
        portfolio.correlationMatrix = new Matrix(portfolio.items.size(), portfolio.items.size());
        portfolio.covarianceMatrix = covarianceMatrix(portfolio);
        for (int i1 = 0; i1 < portfolio.items.size(); i1++) {
            for (int i2 = i1; i2 < portfolio.items.size(); i2++) {
                portfolio.correlationMatrix.set(i1, i2, portfolio.covarianceMatrix.get(i1, i2) /
                        (getInstrument(portfolio,i1).getStandardDeviation(FinConstants.LOGRETURN) *
                                getInstrument(portfolio,i2).getStandardDeviation(FinConstants.LOGRETURN)));
                portfolio.correlationMatrix.set(i2, i1, portfolio.correlationMatrix.get(i1, i2));
            }
        }
        return portfolio.correlationMatrix;
    }

//    public static void initWeights() {
//        // initialize portfolio weights
//        for (int i = 0; i < items.size(); i++) {
//            setWeight(i, 1.0 / (double) (items.size()));
//        }
//        normalizeWeights();
//    }
//
//    public static void setTargetReturn(double TargetReturn) {
//        // set target return for portfolio optimizacion
//        // Annual, qualitativo, i.e. 20 for 20% per year
//        targetReturn = Math.pow(TargetReturn / 100.0 + 1, 1.0 / 365.0);
//    }
//
//    public static double objective() {
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
//    public static void update() {
//        update(-1);
//    }
//
//    public static void update(int Param) {
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
//    public static Optimizer optimizer() {
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
//    public static void runOptimize() {
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
//    public static void optimize() {
//        optimize(RETURN);
//    }
//
//    public static void optimize(int ObjectiveOption) {
//        objectiveOption = ObjectiveOption;
//        runOptimize();
//    }
//
//    public static void efficientFrontier() {
//        efficientFrontier(false);
//    }
//
//    public static void efficientFrontier(boolean InThread) {
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
//    public static void cleanUp(double MinWeight) {
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
//    public static void runEfficientFrontier() {
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

    public static void onNewTrade(Portfolio portfolio) {
        System.out.println("onNewTrade");
        System.out.println("Price: " + premium(portfolio));
    }

    /*   public static Parameters getParamSet(int i) {
        PortfolioItem entry = tile(i);
        if (entry != null) {
            return entry.getParamset();
        } else {
            System.out.println("getParamSet. No entry with number " + i + " in " + getName());
            return null;
        }
    }*/

    /* public static Parameters getParamSet(Instrument instrument) {
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

        public static Parameters getParamset(String Name) {
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

        public static Parameters getParamSet(PortfolioItem entry) {
            // Return parameter set for entry
            return entry.getParamset();
        }
    */
    /*
      public static void print(String Option) {
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

    public static void onCircle() {
    }

    public static void onStep() {
    }

    public static int nTransactions(Portfolio portfolio) {
        return portfolio.transactions.getNTransactions();
    }

    public static TransactionSeries transactionSeries(Portfolio portfolio) {
        return portfolio.transactions;
    }

    public static Transaction getTransaction(Portfolio portfolio,int i) {
        return portfolio.transactions.getTransaction(i);
    }

/*

    public static Link<PortfolioItem> getEntryList()
    {
        return items;
    }

*/

    public static PortfolioItem item(Portfolio portfolio,int i) {
        return portfolio.items.get(i);
    }

    public static int nentries(Portfolio portfolio) {
        return portfolio.items.size();
    }

    public static int nHoldAsset(Portfolio portfolio) {
        return portfolio.assetsToHold;
    }

    public static Instrument getInstrument(Portfolio portfolio,int i) {
        return item(portfolio,i).getInstrument();
    }

    public static int getPosition(Portfolio portfolio,int i) {
        return item(portfolio,i).getPosition();
    }

    public static int getItemAmount(Portfolio portfolio,int i) {
        return item(portfolio,i).getAmount();
    }

    public static double getWeight(Portfolio portfolio,int i) {
        return item(portfolio,i).getWeight();
    }

    public static double getModelPrice(Portfolio portfolio,int i) {
        return item(portfolio,i).getModelPrice();
    }

    public static TDay getModelDate(Portfolio portfolio,int i) {
        return item(portfolio,i).getModelDate();
    }

//    public static void randomize() {
//        randomizeWeights();
//    }

    public static double getCovariance(Portfolio portfolio,int Row, int Col) {
        return portfolio.covarianceMatrix.get(Row, Col);
    }

    public static double getCorrelation(Portfolio portfolio,int Row, int Col) {
        return portfolio.correlationMatrix.get(Row, Col);
    }

    public static List<PortfolioItem> getInstruments(Portfolio portfolio) {
        return portfolio.items;
    }

    public static void setItems(Portfolio portfolio,List<PortfolioItem> items) {
        portfolio.items = items;
    }

    public static Matrix toMatrixLogReturns(Portfolio portfolio) {
        int dimension = getInstruments(portfolio).size();
        Matrix ret = null;
        List entries = getInstruments(portfolio);
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

    public static Matrix toMatrixReturns(Portfolio portfolio) {
        int dimension = getInstruments(portfolio).size();
        Matrix ret = null;
        List entries = getInstruments(portfolio);
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
