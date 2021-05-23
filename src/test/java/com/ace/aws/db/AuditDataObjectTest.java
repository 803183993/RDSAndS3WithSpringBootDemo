package com.ace.aws.db;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class AuditDataObjectTest
{
    @Test
    public void shouldCreateAuditObject()
    {
        AuditDataObject ado = new AuditDataObject("SomeEntityType", "SomeEntityId", "SomeEvent", "SomeHostName");

        assertThat(ado.getCreateDate(), is(nullValue()));
        assertThat(ado.getId(), is(nullValue()));
        assertThat(ado.getEntityType(), is("SomeEntityType"));
        assertThat(ado.getEntityId(), is("SomeEntityId"));
        assertThat(ado.getEvent(), is("SomeEvent"));
        assertThat(ado.getHostName(), is("SomeHostName"));
    }
}