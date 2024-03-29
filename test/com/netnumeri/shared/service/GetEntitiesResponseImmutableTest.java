package com.netnumeri.shared.service;

import com.netnumeri.shared.entity.OptionType;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Option;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GetEntitiesResponseImmutableTest {
    @Test
    public void testToString() throws Exception {

        TDay d = new TDay("3/27/2007");

        ArrayList<Option> list = new ArrayList<Option>();
        Option option = new Option("1", "NasdaqGS", "name", "ticket", OptionType.PUT, 1.1, d);
        list.add(option);

        GetEntitiesResponseImmutable<Option> resp = new GetEntitiesResponseImmutable<Option>(list);
        assertThat(resp.toString(), is("GetEntitiesResponse{" + option +"}"));
    }
}
