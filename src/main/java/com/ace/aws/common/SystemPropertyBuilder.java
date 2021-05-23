package com.ace.aws.common;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import java.util.HashMap;
import java.util.Map;

public class SystemPropertyBuilder
{
    private final Map<String, String> properties;
    private final String[] args;

    public SystemPropertyBuilder(String[] args)
    {
        this.args = args;
        this.properties = new HashMap<>();
    }

    public SystemPropertyBuilder withProperty(String commandLinePropertyName, String systemPropertyName)
    {
        properties.put(commandLinePropertyName, systemPropertyName);
        return this;
    }

    public void build() throws ParseException
    {
        CommandLineParser parser = new CommandLineParser(args);
        for (String key : properties.keySet())
        {
            parser.add(key);
        }

        CommandLine cmd = parser.parse();

        for (String key : properties.keySet())
        {
            if (cmd.hasOption(key))
            {
                System.setProperty(properties.get(key), cmd.getOptionValue(key));
            }
        }
    }
}
