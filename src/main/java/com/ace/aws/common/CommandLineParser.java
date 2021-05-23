package com.ace.aws.common;

import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

public class CommandLineParser
{
    private final Map<String, Boolean> optionsMap;
    private final String[] args;

    public CommandLineParser(String[] args)
    {
        this.args = args;
        this.optionsMap = new HashMap<>();
    }

    public CommandLineParser add(String opt, Boolean... required)
    {
        optionsMap.put(opt, required.length > 0 ? required[0] : false);
        return this;
    }

    public CommandLine parse() throws ParseException
    {
        Options options = new Options();
        for (String key : optionsMap.keySet())
        {
            options.addOption(Option.builder().longOpt(key).hasArg().required(optionsMap.get(key)).valueSeparator(' ').build());
        }

        DefaultParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try
        {
            return parser.parse(options, args);
        } catch (Exception e)
        {
            formatter.printHelp("myJar", options);
            throw e;
        }
    }
}
