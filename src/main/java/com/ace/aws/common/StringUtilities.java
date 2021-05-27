package com.ace.aws.common;

import org.thymeleaf.util.StringUtils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StringUtilities
{
    private static final List<String> NUMBERS_IN_WORDS = new ArrayList<>()
    {
        {
            add("Zero");
            add("One");
            add("Two");
            add("Three");
            add("Four");
            add("Five");
            add("Six");
            add("Seven");
            add("Eight");
            add("Nine");
            add("Ten");
        }
    };

    // Very simple for now - should use an opensource lib
    public static String numberToWord(Integer number)
    {
        if (number == null)
        {
            return "Zero";
        } else
        {
            return number > 10 ? Integer.toString(number) : NUMBERS_IN_WORDS.get(number);
        }
    }

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
