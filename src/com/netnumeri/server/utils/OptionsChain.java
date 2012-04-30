package com.netnumeri.server.utils;

import com.netnumeri.shared.finance.finpojo.derivative.equity.Option;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionsChain{

    String ticker;

    Map<String, List<Option>> calls = new HashMap<String, List<Option>>();
    Map<String, List<Option>> puts = new HashMap<String, List<Option>>();


}
