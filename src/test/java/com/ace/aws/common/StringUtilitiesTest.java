package com.ace.aws.common;

import org.junit.Test;

import java.time.ZonedDateTime;

import static com.ace.aws.common.StringUtilities.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StringUtilitiesTest
{
    @Test
    public void shouldConvertNumberToWord()
    {
        assertThat(numberToWord(0), is("Zero"));
        assertThat(numberToWord(1), is("One"));
        assertThat(numberToWord(2), is("Two"));
        assertThat(numberToWord(3), is("Three"));
        assertThat(numberToWord(4), is("Four"));
        assertThat(numberToWord(5), is("Five"));
        assertThat(numberToWord(6), is("Six"));
        assertThat(numberToWord(7), is("Seven"));
        assertThat(numberToWord(8), is("Eight"));
        assertThat(numberToWord(9), is("Nine"));
        assertThat(numberToWord(10), is("Ten"));
        assertThat(numberToWord(11), is("11"));
    }

    @Test
    public void shouldCapitaliseWordsAndStripSpaces()
    {
        assertThat(capitaliseWords(" star trek"), is("Star Trek"));
        assertThat(capitaliseWords(" star trEk"), is("Star Trek"));
        assertThat(capitaliseWords("stAr   trek"), is("Star Trek"));
        assertThat(capitaliseWords("Star trek"), is("Star Trek"));
        assertThat(capitaliseWords("star Trek"), is("Star Trek"));
        assertThat(capitaliseWords("  Star    Trek"), is("Star Trek"));
        assertThat(capitaliseWords("Return of the Jedi"), is("Return Of The Jedi"));
    }

    @Test
    public void shouldConvertDateToDDMMYY()
    {
        ZonedDateTime date1 = ZonedDateTime.now().withDayOfMonth(3).withMonth(6).withYear(2020);
        ZonedDateTime date2 = ZonedDateTime.now().withDayOfMonth(31).withMonth(12).withYear(2021);

        assertThat(toDayMonthYear(date1), is("03 June 2020"));
        assertThat(toDayMonthYear(date2), is("31 December 2021"));
    }
}