package com.netnumeri.server.utils;


import com.netnumeri.shared.entity.OptionType;
import com.netnumeri.shared.finance.data.MaximumPainBean;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Vanilla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaximumPainCalculator {

    public static Double calculate(String ticker) throws Exception {

        OptionsDocuments optionsDocuments = YahooOptions.getOptionsDocuments(ticker);

        double lastPrice = YahooOptions.getLastPrice(ticker);

        List<Vanilla> callsOptions = YahooOptions.getChain(optionsDocuments, OptionType.CALL);
        List<Vanilla> putsOptions = YahooOptions.getChain(optionsDocuments, OptionType.PUT);

        List<MaximumPainBean> callBeans = new ArrayList<MaximumPainBean>();
        for (int i = 0; i < callsOptions.size(); i++) {
            Vanilla vanilla = callsOptions.get(i);
            MaximumPainBean bean = new MaximumPainBean();
            double cumul = computeCumulativeValue(vanilla.strike(), callsOptions);
            bean.setStrike(vanilla.strike());
            bean.setCumulative(cumul);
            callBeans.add(bean);
        }

        Map map = new HashMap();
        for (int i = 0; i < callBeans.size(); i++) {
            MaximumPainBean maximumPainBean = callBeans.get(i);
            map.put(maximumPainBean.getStrike(), maximumPainBean.getCumulative());
        }

        List<MaximumPainBean> putsBeans = new ArrayList<MaximumPainBean>();
        for (int i = 0; i < putsOptions.size(); i++) {
            Vanilla vanilla = putsOptions.get(i);
            MaximumPainBean bean = new MaximumPainBean();
            double cumul = computeCumulativeValue(vanilla.strike(), putsOptions);
            bean.setStrike(vanilla.strike());
            bean.setCumulative(cumul);
            putsBeans.add(bean);
        }

        Double maxPainCumulative = Double.MAX_VALUE;
        Double maxPainStrike = Double.MAX_VALUE;
        for (int i = 0; i < putsBeans.size(); i++) {
            MaximumPainBean maximumPainBean = putsBeans.get(i);

            if (map.get(maximumPainBean.getStrike()) != null) {
                Double d = (Double) map.get(maximumPainBean.getStrike());
                Double d2 = d + maximumPainBean.getCumulative();

                if (d2 < maxPainCumulative) {
                    maxPainCumulative = d2;
                    maxPainStrike = maximumPainBean.getStrike();
                }
            }
        }
        System.out.println("last price for " + ticker + " : " + lastPrice);
        System.out.println("maxPainStrike for " + ticker + " : " + maxPainStrike);
        return maxPainStrike;
    }

    private static double computeCumulativeValue(double underSpotPrice, List<Vanilla> options) {
        double cumulativeValue = 0;
        if (options != null)
            for (int i = 0; i < options.size(); i++) {
                Vanilla vanilla = options.get(i);
                int openInt = vanilla.openInterest();
                if (vanilla.type.get().equals(OptionType.CALL)) {
                    if (underSpotPrice > vanilla.strike()) {
                        double value = underSpotPrice - vanilla.strike();
                        cumulativeValue = cumulativeValue + (value * openInt);
                    }
                } else if (vanilla.type.get().equals(OptionType.PUT)) {
                    if (underSpotPrice < vanilla.strike()) {
                        double value = vanilla.strike() - underSpotPrice;
                        cumulativeValue = cumulativeValue + (value * openInt);
                    }
                }
            }
        return cumulativeValue;
    }

}
