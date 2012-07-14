package com.netnumeri.shared.finance.data;


import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.Daily;
import com.netnumeri.shared.finance.finpojo.Instrument;
import com.netnumeri.shared.finance.finpojo.InstrumentMath;
import com.netnumeri.shared.finance.utils.FormatUtils;

public class Transaction {


    private String name;
    private int action;
    private Instrument instrument;
    private int amount = 0;
    private double price = 0;
    private TransactionCost cost;
    private TDay date;

    public Transaction() {
    }

    public Transaction(Instrument instrument,
                       int action,
                       int amount,
                       double price,
                       TDay date) {
        this.name = instrument.getName();
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        if (date == null) throw new IllegalArgumentException("date cannot be null");
        this.date = date;
        this.instrument = instrument;
        this.action = action;
        this.amount = amount;
        this.price = price;
        this.cost = null;
    }

    public Transaction(Instrument instrument,
                       int action,
                       int amount,
                       double price,
                       TDay date,
                       TransactionCost cost) {
        this.name = instrument.getName();
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        if (date == null) throw new IllegalArgumentException("date cannot be null");
        this.instrument = instrument;
        this.date = date;
        this.action = action;
        this.amount = amount;
        this.price = price;
        this.cost = cost;
    }

    public Transaction(Instrument instrument,
                       int action,
                       int amount,
                       double price,
                       TDay d,
                       int t,
                       TransactionCost cost) {
        this.name = instrument.getName();
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        if (d == null) throw new IllegalArgumentException("d cannot be null");
        if (cost == null) throw new IllegalArgumentException("cost cannot be null");
        this.date = d;
        this.instrument = instrument;
        this.action = action;
        this.amount = amount;
        this.price = price;
        this.cost = cost;
    }

    public Transaction(Instrument instrument,
                       int action,
                       int amount,
                       TDay date) {
        this.name = instrument.getName();
        if (date == null) throw new IllegalArgumentException("date cannot be null");

        this.instrument = instrument;
        Daily daily = new InstrumentMath(instrument).getDaily(date);
        if (daily.valid()) {
            this.price = daily.get(FinConstants.CLOSE);
        } else {
            System.out.println("Transaction. No valid daily data available for " + instrument.getName() + " " + date.toString());
            this.price = 0;
        }
        this.date = date;
        this.action = action;
        this.amount = amount;
        this.price = 0;
        this.cost = null;
    }

    public Transaction(Instrument instrument,
                       int Action,
                       int Amount,
                       TDay date,
                       TransactionCost cost,
                       int Option) {
        this.name = instrument.getName();
        if (instrument == null) throw new IllegalArgumentException("instrument cannot be null");
        if (date == null) throw new IllegalArgumentException("date cannot be null");
        if (cost == null) throw new IllegalArgumentException("cost cannot be null");
        this.instrument = instrument;
        Daily daily = new InstrumentMath(instrument).getDaily(date);
        if (daily.valid()) {
            this.price = daily.get(Option);
        } else {
            System.out.println("Transaction. No valid daily data available for " + instrument.getName() + " " + date.toString());
            this.price = 0;
        }
        this.date = date;
        this.action = Action;
        this.amount = Amount;
        this.cost = cost;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TDay getDate() {
        return date;
    }

    public void setDate(TDay date) {
        this.date = date;
    }

    public TransactionCost getTransactionCost() {
        TransactionCost tc = new TransactionCost();
        tc = cost;
        return tc;
    }

    public void setTransactionCost(TransactionCost cost) {
        this.cost = cost;
    }

    public double getCost() {
        if (cost != null) {
            return cost.getCost(amount * price);
        } else {
            return 0;
        }
    }

    public void set(Instrument instrument, int action, int amount, double price, TDay date) {
        this.instrument = instrument;
        this.action = action;
        this.amount = amount;
        this.price = price;
    }

    public void set(Instrument instrument,
                    int Action,
                    int amount, double price) {
        this.instrument = instrument;
        this.action = Action;
        this.amount = amount;
        this.price = price;
    }

    public void set(Transaction t) {
        date = t.getDate();
        action = t.getAction();
        instrument = t.getInstrument();
        amount = t.getAmount();
        price = t.getPrice();
        cost = t.cost;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final Transaction that = (Transaction) o;

        if (action != that.action) return false;
        if (amount != that.amount) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (date != null ? !date.isEqual(that.date) : that.date != null) return false;
        if (instrument != null ? !instrument.equals(that.instrument) : that.instrument != null) return false;
        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 29 * result + action;
        result = 29 * result + (instrument != null ? instrument.hashCode() : 0);
        result = 29 * result + amount;
        temp = price != +0.0d ? Double.doubleToLongBits(price) : 0L;
        result = 29 * result + (int) (temp ^ (temp >>> 32));
        result = 29 * result + (cost != null ? cost.hashCode() : 0);
        result = 29 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    public int compareTo(Object obj) {
        if (this.equals(obj)) return 0;
        return date.compareTo(((Transaction) obj).getDate());
    }

    public Object getKey() {
        return date;
    }

    public String print() {
        StringBuffer sb = new StringBuffer();
        sb.append(getDate().toString() + ": " + FormatUtils.actionToSting(action));
        sb.append(" # " + amount);
        sb.append(" of " + instrument.getName());
        sb.append(" @ " + price);
        return sb.toString();
    }

    public double getValue() {
        return amount * price;
    }
}
