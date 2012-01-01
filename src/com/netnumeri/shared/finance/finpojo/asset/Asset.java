package com.netnumeri.shared.finance.finpojo.asset;


import com.netnumeri.shared.finance.finpojo.Instrument;

import java.io.Serializable;

public abstract class Asset extends Instrument implements Serializable {

    public Asset() {
    }

    public Asset(String name) {
        super(name);
    }

}

