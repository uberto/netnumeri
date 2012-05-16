package com.netnumeri.shared.finance.utils;

import com.netnumeri.shared.finance.finpojo.asset.Stock;

public class TestUtils {

    public static Stock buildStock() throws Exception {

            return StockUtils.getStock("./test/resources/SSRI.csv");

    }
}
