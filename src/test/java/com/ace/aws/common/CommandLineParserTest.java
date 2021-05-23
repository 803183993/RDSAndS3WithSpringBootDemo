package com.ace.aws.common;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class CommandLineParserTest
{
    @Test
    public void shouldParseArguments() throws ParseException
    {
        String[] args = new String[]{"--jdbcUrl", "jdbc:postgresql://localhost:5432/moviedb", "--username", "admin"};
        CommandLineParser parser = new CommandLineParser(args);
        parser.add("jdbcUrl", true)
                .add("username")
                .add("password");

        CommandLine cmd = parser.parse();

        assertThat(cmd.getOptionValue("jdbcUrl"), is("jdbc:postgresql://localhost:5432/moviedb"));
        assertThat(cmd.getOptionValue("username"), is("admin"));
        assertThat(cmd.getOptionValue("password"), nullValue());
    }

    @Test(expected = MissingOptionException.class)
    public void shouldThrowMissingOptionExceptionIfMandatoryParameterIsMissing() throws ParseException
    {
        String[] args = new String[]{"--hostname", "localhost"};
        CommandLineParser parser = new CommandLineParser(args);
        parser.add("hostname", true)
                .add("jdbcUrl", true)
                .parse();
    }

    @Test(expected = UnrecognizedOptionException.class)
    public void shouldThrowUnrecognizedOptionExceptionIfUnexpectedArgumentIsPassed() throws ParseException
    {
        String[] args = new String[]{"--hostname", "localhost"};
        CommandLineParser parser = new CommandLineParser(args);
        parser.add("jdbcUrl").parse();
    }
}