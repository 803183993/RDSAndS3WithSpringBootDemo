package com.ace.aws.domain;

import org.junit.Test;

import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReviewTest
{
    @Test
    public void canCreateReview()
    {
        ZonedDateTime date1 = ZonedDateTime.now();
        ZonedDateTime date2 = ZonedDateTime.now().withDayOfMonth(13);
        Review review1 = new Review();
        review1.setTitle("Star Trek");
        review1.setReviewer("Jane Doe");
        review1.setComment("some comment");
        review1.setDate(date1);
        review1.setRating(3);
        assertThat(review1.getTitle(), is("Star Trek"));
        assertThat(review1.getReviewer(), is("Jane Doe"));
        assertThat(review1.getComment(), is("some comment"));
        assertThat(review1.getRating(), is(3));
        assertThat(review1.getDate(), is(date1));

        Review review2 = new Review();
        review2.setTitle("Star Wars");
        review2.setReviewer("John Doe");
        review2.setComment("some other comment");
        review2.setRating(5);
        review2.setDate(date2);
        assertThat(review2.getTitle(), is("Star Wars"));
        assertThat(review2.getReviewer(), is("John Doe"));
        assertThat(review2.getComment(), is("some other comment"));
        assertThat(review2.getRating(), is(5));
        assertThat(review2.getDate(), is(date2));
    }
}