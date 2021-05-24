package com.ace.aws.common;

import org.thymeleaf.util.StringUtils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtilities
{
    public static String capitaliseWords(String words)
    {
        StringBuilder capitaliseWords = new StringBuilder();

        for (String word : words.split(" "))
        {
            if (!StringUtils.isEmptyOrWhitespace(word))
            {
                capitaliseWords.append(StringUtils.capitalize(word.trim().toLowerCase()));
                capitaliseWords.append(" ");
            }
        }

        return capitaliseWords.toString().trim();
    }

    public static String toDayMonthYear(ZonedDateTime date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return formatter.format(date);
    }
}
