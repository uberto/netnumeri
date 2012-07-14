package com.netnumeri.shared;

import com.netnumeri.server.utils.OptionsChain;
import com.netnumeri.server.utils.YahooOptions;
import com.netnumeri.shared.entity.OptionType;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Option;
import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class StubsForTests {

    static TDay d = new TDay("3/27/2007");

    public static Option createDummyOption() {
        return new Option("1", "NasdaqGS", "option123", "GOOG", OptionType.CALL, 180.0, d);
    }

    public static Option createDummyOption2() {
        return new Option("2", "NasdaqGS", "option234", "GOOG", OptionType.PUT, 130.0, d);
    }

    public static List<Option> createDummyOptionList() {


        List<Option> list = new ArrayList<Option>();

        try {
            OptionsChain chain = YahooOptions.loadOptionChain("GOOG");

            Set<Map.Entry<String,List<Option>>> entries = chain.calls.entrySet();

            for (Iterator<Map.Entry<String, List<Option>>> iterator = entries.iterator(); iterator.hasNext(); ) {
                Map.Entry<String, List<Option>> listEntry = (Map.Entry<String, List<Option>>) iterator.next();

                List<Option> lOptions = listEntry.getValue();
                for (int i = 0; i < lOptions.size(); i++) {
                    Option option = lOptions.get(i);
                    list.add(option);
                }

            }

        } catch (Throwable e) {
            e.printStackTrace();
        }


//        list.add(createDummyOption());
//        list.add(createDummyOption2());

        return list;

    }
}
