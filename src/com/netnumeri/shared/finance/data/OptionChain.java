package com.netnumeri.shared.finance.data;


import com.netnumeri.shared.finance.finpojo.asset.Stock;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Vanilla;

import java.util.Vector;

public class OptionChain {
    private Vector options;
    private Stock stock;

    public OptionChain(Stock stock) {
        this.stock = stock;
    }

    public Vector getOptions() {
        return options;
    }

    public void setOptions(Vector options) {
        this.options = options;
    }

    public void addOption(Vanilla option) {
        options.add(option);
    }

}
