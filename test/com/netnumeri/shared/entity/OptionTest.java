package com.netnumeri.shared.entity;

import com.netnumeri.shared.field.EntityField;
import com.netnumeri.shared.field.StringEntityField;
import com.netnumeri.shared.finance.date.TDay;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Option;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class OptionTest {
    TDay d = new TDay("3/27/2007");


    @Test
    public void valuesInConstructorAreAllUsed() throws Exception {

        Option option = new Option("1", "NasdaqGs", "name", "ticket", OptionType.PUT, 1.1, d);

        assertThat(option.name.get(), is("name"));
        assertThat(option.bourse.get(), is("NasdaqGs"));
        assertThat(option.name.get(), is("name"));
        assertThat(option.expiry.get(), is(d));
        assertThat(option.strike.get(), is(1.1));
        assertThat(option.type.get(), is(OptionType.PUT));
    }

    @Test
    public void representCompletelyAsString() throws Exception {
        Option option = new Option("1", "NasdaqGs", "name", "ticket", OptionType.PUT, 1.1, d);

        assertThat(option.toString(), is("Option{{bourse:NasdaqGs}, {name:name}, {underlying:ticket}, {type:PUT}, {strike:1.1}, {expiry:2/21/2007}}"));
    }

    @Test
    public void entity() throws Exception {
        Entity entity = new Option("123", "NasdaqGs", "name", "ticket", OptionType.PUT, 1.1, d);

        assertThat(entity.getFields().size(), is(15));
        assertThat(entity.getId(), is(new EntityId( "123")));

        assertTrue(entity.getFields().contains(((Option) entity).name));
        assertTrue(entity.getFields().contains(((Option) entity).underlying));
        assertThat((StringEntityField) entity.getField(Option.Field.name), is(((Option) entity).name));
        EntityField<Double> entityField = (EntityField<Double>) entity.getField(Option.Field.strike);
        assertThat(entityField.get(), is(1.1));

    }

    @Test
    public void checkEquals() throws Exception {
        Option option = new Option("1", "NasdaqGs", "name", "ticket", OptionType.PUT, 1.1, d);
        Option optionDiff = new Option("1", "NasdaqGs", "name2", "ticket", OptionType.PUT, 1.1, d);
        Option optionEqual = new Option("1", "NasdaqGs", "name", "ticket", OptionType.PUT, 1.1, d);

        assertThat(option, is(optionEqual));
        assertThat(option, not(optionDiff));

    }




}
