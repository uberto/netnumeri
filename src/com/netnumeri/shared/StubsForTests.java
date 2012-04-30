package com.netnumeri.shared;

import com.netnumeri.shared.entity.OptionType;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Option;

import java.util.ArrayList;
import java.util.List;

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
        list.add(createDummyOption());
        list.add(createDummyOption2());

        return list;

    }
}
