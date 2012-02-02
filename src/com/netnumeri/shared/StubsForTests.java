package com.netnumeri.shared;

import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.entity.OptionType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StubsForTests {

    public static Option createDummyOption() {
        return new Option("option123", "GOOG", OptionType.CALL, 180.0, new Date(0));
    }

    public static Option createDummyOption2() {
        return new Option("option234", "GOOG", OptionType.PUT, 130.0, new Date(0));
    }

    public static List<Option> createDummyOptionList() {
        List<Option> list = new ArrayList<Option>();
        list.add(createDummyOption());
        list.add(createDummyOption2());

        return list;

    }
}
