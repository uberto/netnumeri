package com.netnumeri.shared.entity;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class OptionTest {

    @Test
    public void createOption() throws Exception {
        Option option = new Option("name", "ticket", OptionType.PUT, 1.1, new Date(123));

        assertThat(option.getOptionName(), is("name"));
        assertThat(option.getStockTicket(), is("ticket"));
        assertThat(option.getDateDue(), is(new Date(123)));
        assertThat(option.getStrike(), is(1.1));
        assertThat(option.getType(), is(OptionType.PUT));
    }

    @Test
    public void entity() throws Exception {
        Entity entity = new Option("name", "ticket", OptionType.PUT, 1.1, new Date(123));

        assertThat(entity.getFields().size(), is(6));
//        assertThat(entity.getId(), is(new EntityId( "123")));

        assertTrue(entity.getFields().contains(((Option) entity).NAME));
//        assertTrue(entity.getFields().contains(entity.UNDERLYING));
//        assertThat((String) entity.get(Option.NAME).getValue(), is("name"));
//        assertThat((String) entity.get(Option.UNDERLYING).getValue(), is("ticket"));

    }

    @Test
    public void checkEquals() throws Exception {
        Option option = new Option("name", "ticket", OptionType.PUT, 1.1, new Date(123));
        Option optionDiff = new Option("name2", "ticket", OptionType.PUT, 1.1, new Date(123));
        Option optionEqual = new Option("name", "ticket", OptionType.PUT, 1.1, new Date(123));

        assertThat(option, is(optionEqual));
        assertThat(option, not(optionDiff));

    }




}
