package com.netnumeri.shared.pojoc.field;

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class DateTimeFieldTest {

    @Test
    public void fromToString() {
        DateTimeField field = new DateTimeField("last_update");
        assertThat(field.getFieldName(), is("last_update"));

        assertThat(field.getValueAsString(), is(nullValue()));

        field.setValue(new Date(0));
        assertThat(field.getValueAsString(), is("01-01-1970 01:00:00"));
    }

}
