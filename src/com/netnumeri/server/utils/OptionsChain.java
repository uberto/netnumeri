package com.netnumeri.server.utils;

import com.netnumeri.shared.finance.finpojo.derivative.equity.Option;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionsChain implements Serializable {

    String ticker;

    public Map<String, List<Option>> calls = new HashMap<String, List<Option>>();
    public Map<String, List<Option>> puts = new HashMap<String, List<Option>>();


}
