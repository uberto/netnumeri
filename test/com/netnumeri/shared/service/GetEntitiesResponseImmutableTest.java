package com.netnumeri.shared.service;

import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.entity.OptionType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GetEntitiesResponseImmutableTest {
    @Test
    public void testToString() throws Exception {
        ArrayList<Option> list = new ArrayList<Option>();
        list.add(new Option("name", "ticket", OptionType.PUT, 1.1, new Date(123)));

        GetEntitiesResponseImmutable<Option> resp = new GetEntitiesResponseImmutable<Option>(list);
        assertThat(resp.toString(), is("GetEntitiesResponse{Option{NAME={name:name}, UNDERLYING={underlying:ticket}, TYPE={type:PUT}, STRIKE={strike:1.1}, EXPIRY={expiry:4/01/70}, BOURSE={bourse:NasdaqGS}}}"));
    }
}
