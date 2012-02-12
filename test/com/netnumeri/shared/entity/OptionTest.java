package com.netnumeri.shared.entity;

import com.netnumeri.shared.field.EntityField;
import com.netnumeri.shared.field.StringEntityField;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class OptionTest {

    @Test
    public void createOption() throws Exception {
        Option option = new Option("1", "NasdaqGs", "name", "ticket", OptionType.PUT, 1.1, new Date(123));

        assertThat(option.getOptionName(), is("name"));
        assertThat(option.getStockTicket(), is("ticket"));
        assertThat(option.getDateDue(), is(new Date(123)));
        assertThat(option.getStrike(), is(1.1));
        assertThat(option.getType(), is(OptionType.PUT));
    }

    @Test
    public void entity() throws Exception {
        Entity entity = new Option("123", "NasdaqGs", "name", "ticket", OptionType.PUT, 1.1, new Date(123));

        assertThat(entity.getFields().size(), is(7));
        assertThat(entity.getId(), is(new EntityId( "123")));

        assertTrue(entity.getFields().contains(((Option) entity).name));
        assertTrue(entity.getFields().contains(((Option) entity).underlying));
        assertThat((StringEntityField) entity.getField(Option.Field.name), is(((Option) entity).name));
        EntityField<Double> entityField = (EntityField<Double>) entity.getField(Option.Field.strike);
        assertThat(entityField.get(), is(1.1));

    }

    @Test
    public void checkEquals() throws Exception {
        Option option = new Option("1", "NasdaqGs", "name", "ticket", OptionType.PUT, 1.1, new Date(123));
        Option optionDiff = new Option("1", "NasdaqGs", "name2", "ticket", OptionType.PUT, 1.1, new Date(123));
        Option optionEqual = new Option("1", "NasdaqGs", "name", "ticket", OptionType.PUT, 1.1, new Date(123));

        assertThat(option, is(optionEqual));
        assertThat(option, not(optionDiff));

    }




}
