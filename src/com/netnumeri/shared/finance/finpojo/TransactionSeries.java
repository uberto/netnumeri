package com.netnumeri.shared.finance.finpojo;

import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.data.Transaction;
import com.netnumeri.shared.finance.date.TDay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class TransactionSeries implements FinConstants, Serializable {
    protected ArrayList<Transaction> transactionArray;

    public TransactionSeries() {
        transactionArray = new ArrayList<Transaction>();
    }

    public void add(Transaction transaction) {
        transactionArray.add(transaction);
    }

    public int getN() {
        return getNTransactions();
    }

    public int getNTransactions() {
        return transactionArray.size();
    }

    public Transaction getTransaction(int i) {
        return transactionArray.get(i);
    }

    public Instrument getInstrument(int i) {
        return getTransaction(i).getInstrument();
    }

    public int getAction(int i) {
        return getTransaction(i).getAction();
    }

    public double getAmount(int i) {
        return getTransaction(i).getAmount();
    }

    public double getPrice(int i) {
        return getTransaction(i).getPrice();
    }

    public TDay getDateTime(int i) {
        return getTransaction(i).getDate();
    }

    public TDay getDate(int i) {
        return getTransaction(i).getDate();
    }

    public void clear() {
        transactionArray.clear();
    }

    public void delete() {
        transactionArray.clear();
    }

    // Return pointer to first transaction in series
    // Return zero if series is empty
    public Transaction getFirstTransaction() {
        int NTransactions = getNTransactions();
        if (NTransactions != 0) return getTransaction(0);
        else return null;
    }

    // Return pointer to last transaction in series
    // Return zero if series is empty
    public Transaction getLastTransaction() {
        int NTransactions = getNTransactions();
        if (NTransactions != 0) return getTransaction(NTransactions - 1);
        else return null;
    }

    // Return Date of first transaction
    // Return zero date if series is empty
    public TDay getFirstDate() {
        Transaction transaction = getFirstTransaction();
        if (transaction != null) return transaction.getDate();
        else return null;
    }

    // Return Date of last transaction
    // Return zero date if series is empty
    public TDay getLastDate() {
        Transaction transaction = getLastTransaction();
        if (transaction != null) return transaction.getDate();
        else return null;
    }

    // Return Date stamp of first transaction
    // Return zero datetime if series is empty
    public TDay getFirstDateTime() {
        Transaction transaction = getFirstTransaction();
        if (transaction != null) return transaction.getDate();
        else return null;
    }

    // Return Date stamp of last transaction
    // Return zero datetime if series is empty
    public TDay getLastDateTime() {
        Transaction transaction = getLastTransaction();
        if (transaction != null) return transaction.getDate();
        else return null;
    }

    // Return a "pair" of i-th transaction
    // Return next sell transaction for the same Instrument of buy transaction
    // Return previous buy transaction for the same Instrument of sell transaction
    // Return null if there is no pair transaction
    public Transaction getPair(int i) {
        Instrument instrument = getInstrument(i);
        int act = getAction(i);
        switch (act) {
            case FinConstants.BUY: {
                for (int count = i + 1; count < getNTransactions(); count++) {
                    if (getInstrument(count).equals(instrument) && getAction(count) == SELL) {
                        return getTransaction(count);
                    }
                }
            }
            break;
            case FinConstants.SELL: {
                for (int count = i - 1; count >= 0; count--) {
                    if (getInstrument(count).equals(instrument) && getAction(count) == BUY) {
                        return getTransaction(count);
                    }
                }
            }
            break;
            case FinConstants.SELLSHORT: {
                for (int count = i + 1; count < getNTransactions(); count++) {
                    if (getInstrument(count).equals(instrument) && getAction(count) == BUYSHORT) {
                        return getTransaction(count);
                    }
                }
            }
            break;
            case FinConstants.BUYSHORT: {
                for (int count = i - 1; count >= 0; count--) {
                    if (getInstrument(count).equals(instrument) && getAction(count) == SELLSHORT) {
                        return getTransaction(count);
                    }
                }
            }
            break;
        }
        return null;
    }

    // Find and return first index corresponding
    // to transaction with Date
    // Return -1 if there is no transaction with Date
    public int getFirstIndex(TDay date) {
        int NTransactions = getNTransactions();
        if (NTransactions == 0) return -1;
        if (date.before(getFirstDate()) || date.after(getLastDate())) return -1;
        for (int i = 0; i < NTransactions; i++) if (getDate(i).equals(date)) return i;
        return -1;
    }

    // Find and return first index corresponding
    // to transaction with Date
    // Return -1 if there is no transaction with Date
    public int getLastIndex(TDay date) {
        int NTransactions = getNTransactions();
        if (NTransactions == 0) return -1;
        if (date.before(getFirstDate()) || date.after(getLastDate())) return -1;
        for (int i = NTransactions - 1; i >= 0; i--) if (getDate(i).equals(date)) return i;
        return -1;
    }

    // Return series of transactions for Instrument
    public TransactionSeries getSeries(Instrument instrument) {
        TransactionSeries series = new TransactionSeries();
        for (int i = 0; i < getNTransactions(); i++)
            if (getInstrument(i).equals(instrument)) series.add(getTransaction(i));
        return series;
    }

    public TransactionSeries getSeries(Date date) {
        TransactionSeries series = new TransactionSeries();
        for (int i = 0; i < getNTransactions(); i++)
            if (getDate(i).equals(date)) series.add(getTransaction(i));
        return series;
    }

    public void transactionAdded() {
        System.out.println("transaction added  ");
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < getNTransactions(); i++) sb.append(getTransaction(i).toString());
        return sb.toString();
    }

    public Object clone() {
        TransactionSeries ts = new TransactionSeries();
        transactionArray.iterator();
        for (Iterator<Transaction> iterator = transactionArray.iterator(); iterator.hasNext(); ) {
            Transaction transaction = iterator.next();
            ts.transactionArray.add(transaction);
        }
        return ts;
    }

}