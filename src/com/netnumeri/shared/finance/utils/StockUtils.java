package com.netnumeri.shared.finance.utils;

import com.netnumeri.shared.finance.beans.FinConstants;
import com.netnumeri.shared.finance.finpojo.asset.Stock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class StockUtils {

    public static Stock getStock(String filePath) throws Exception {
        Stock instrument = new Stock();

        Stack lines = new Stack();
//        String filePath = ("./src/test/resources/SSRI.csv");

        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {

            inputLine = inputLine.replaceAll("\"", "");
            if (inputLine.startsWith("Date")) continue;
            System.out.println("inputLine = " + inputLine);
            lines.push(inputLine);

        }
        reader.close();

        String date = null;
        double open = 0;
        double high = 0;
        double low = 0;
        double close = 0;
        double volume = 0;
        int i = 0;

        StringBuffer sb = new StringBuffer();
        while (!lines.isEmpty()) {
            String s3 = (String) lines.pop();
            sb.append(s3 + "\n");

            StringTokenizer stringtokenizer = new StringTokenizer(s3, ",");
            String token = stringtokenizer.nextToken();
            date = token;
            float d = 0;
            float d1 = 0;
            for (int j = 0; j < 4; j++) {
                token = stringtokenizer.nextToken();
                double dumm = Double.parseDouble(token);
                switch (j) {
                    case 0:
                        open = dumm;
                        break;
                    case 1:
                        high = dumm;
                        break;
                    case 2:
                        low = dumm;
                        break;
                    case 3:
                        close = dumm;
                        break;
                }
                d += dumm;
            }
            d1 = d / 4;
            double vol = Double.parseDouble(stringtokenizer.nextToken());
            volume = vol;
            instrument.addDaily(i++, DateUtils.toYahoo(date), high, low, open, close, (int) volume, 0, FinConstants.VALID);
        }
        return instrument;
    }

}
