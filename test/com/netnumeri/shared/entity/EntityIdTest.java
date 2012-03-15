package com.netnumeri.shared.entity;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EntityIdTest {

    @Test
    public void equalIdShouldBeEqual() throws Exception {
        EntityId id1 = new EntityId("abc");
        EntityId id2 = new EntityId("abc");

        assertTrue(id1.equals(id2));
        assertThat(id1, is(id2));
    }

    @Test
    public void toStringShouldReturnTheOriginalString() throws Exception {
        EntityId id1 = new EntityId("abc");

        assertThat(id1.toString(), is("abc"));
    }

}
