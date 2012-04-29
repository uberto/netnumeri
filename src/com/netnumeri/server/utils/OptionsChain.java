package com.netnumeri.server.utils;

import com.netnumeri.shared.finance.finpojo.derivative.equity.Vanilla;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionsChain{

    String ticker;

    Map<String, List<Vanilla>> calls = new HashMap<String, List<Vanilla>>();
    Map<String, List<Vanilla>> puts = new HashMap<String, List<Vanilla>>();



}
