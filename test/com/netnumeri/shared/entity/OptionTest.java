package com.netnumeri.shared.entity;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OptionTest {

    @Test
    public void createOption() throws Exception {
        Option option = new Option("name", "ticket", OptionType.PUT, 1.1, new Date(123));

        assertThat(option.getOptionName(), is("name"));
        assertThat(option.getStockTicket(), is("ticket"));
    }

    @Test
    public void entity() throws Exception {
        Entity entity = new Option("name", "ticket", OptionType.PUT, 1.1, new Date(123));

        assertThat(entity.getFields().size(), is(2));
//        assertThat(entity.getId(), is(new EntityId( "123")));
        assertTrue(entity.getFields().contains(Option.NAME));
        assertTrue(entity.getFields().contains(Option.NAME));
//        assertThat((String) entity.get(Option.NAME).getValue(), is("name"));

    }



}
