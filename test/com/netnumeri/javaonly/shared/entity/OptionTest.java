package com.netnumeri.javaonly.shared.entity;

import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.entity.OptionType;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OptionTest {

    @Test
    public void createOption() throws Exception {
        Option option = new Option("name", "ticket", OptionType.PUT, 1.1, new Date(123));

        assertThat(option.getOptionName(), is("name"));
        assertThat(option.getStockTicket(), is("ticket"));
    }
}
