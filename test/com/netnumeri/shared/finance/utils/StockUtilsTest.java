package com.netnumeri.shared.finance.utils;

import com.netnumeri.shared.finance.finpojo.asset.Stock;
import org.junit.Test;

import java.io.File;

public class StockUtilsTest {

    @Test
    public void testGetStock() throws Exception {

        File file = new File(".");
        System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());

        Stock ssri = StockUtils.getStock("./test/resources/SSRI.csv");

    }
}
