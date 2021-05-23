package com.ace.aws.common;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static java.lang.System.getProperty;
import static java.lang.System.setProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SystemPropertyBuilderTest
{
    @Test
    public void shouldSetSystemsPropertiesFromCommandLineArgs() throws ParseException
    {
        String[] args = new String[]{"--hostname", "localhost", "--jdbcUrl", "jdbc:postgresql://localhost:5432/moviedb", "--dbUsername", "app1", "--dbPassword", "password"};

        setProperty("DO_NOT_SET_THIS_PROPERTY", "someValue");

        new SystemPropertyBuilder(args)
                .withProperty("hostname", "SYSTEM_HOST_NAME")
                .withProperty("jdbcUrl", "JDBC_URL")
                .withProperty("dbUsername", "DB_USER")
                .withProperty("dbPassword", "DB_PASS")
                .withProperty("doNotSetThisProperty", "DO_NOT_SET_THIS_PROPERTY")
                .build();

        assertThat(getProperty("SYSTEM_HOST_NAME"), is("localhost"));
        assertThat(getProperty("JDBC_URL"), is("jdbc:postgresql://localhost:5432/moviedb"));
        assertThat(getProperty("DB_USER"), is("app1"));
        assertThat(getProperty("DB_PASS"), is("password"));
        assertThat(getProperty("DO_NOT_SET_THIS_PROPERTY"), is("someValue"));
    }
}