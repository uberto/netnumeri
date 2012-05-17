package com.netnumeri.shared.finance.ta;

import com.netnumeri.shared.finance.finpojo.Instrument;
import com.netnumeri.shared.finance.finpojo.PortfolioItem;
import com.netnumeri.shared.finance.finpojo.asset.Asset;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Option;

import java.util.ArrayList;

public class OptionStrategy {
    private String name;
    private ArrayList fOption;
    private PortfolioItem item;
    private int option;

    public OptionStrategy() {
    }

    // Option strategy normal constructor
    public OptionStrategy(String name) {
        this.name = name;
        item = null;
        option = 0;
        fOption = new ArrayList();
    }

    // Set tile and its amount
    // amount < 0 means taking short position in tile
    public void setAsset(Asset asset, int amount) {
        this.item = new PortfolioItem(asset, amount);
    }

    // Add an option with amount
    // amount < 0 means taking short position (writing an option)
    public void addOption(Option option, int amount) {
        PortfolioItem entry = new PortfolioItem(option, amount);
        fOption.add(entry);
        this.option++;
    }

    // Return pointer to underlying tile
    public Instrument getItem() {
        return item.getInstrument();
    }

    // Return tile weight
    public double getAssetAmount() {
        return item.getAmount();
    }

    // Return tile trade position
    public int getAssetPosition() {
        return item.getPosition();
    }

    // Return pointer to option i
    public Option getOption(int i) {
        return (Option) ((PortfolioItem) fOption.get(i)).getInstrument();
    }

    // Return amount of i-th option
    public double getOptionAmount(int i) {
        return ((PortfolioItem) fOption.get(i)).getAmount();
    }

    // Return trade position of i-th option
    public int getOptionPosition(int i) {
        return ((PortfolioItem) fOption.get(i)).getPosition();
    }

    // Evaluate option startegy. Return profit value for underlying tile premium
    // If WithPremium is true, option premiums subtracted from profit value
    public double eval(double Price, boolean WithPremium) {
        double Payoff = 0;
        if (item != null) {
            Payoff += getAssetPosition() * getAssetAmount() * (Price - item.getInstrument().premium());
        }
        for (int i = 0; i < option; i++) {
            Payoff += getOptionPosition(i) * getOptionAmount(i) * getOption(i).getPayoff(Price, WithPremium);
        }
        return Payoff;
    }


}
