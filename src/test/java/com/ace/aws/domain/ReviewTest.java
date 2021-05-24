package com.ace.aws.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReviewTest
{
    @Test
    public void canCreateReview()
    {
        Review review1 = new Review();
        review1.setTitle("Star Trek");
        review1.setName("Jane Doe");
        review1.setComment("some comment");
        review1.setRating(3);
        assertThat(review1.getTitle(), is("Star Trek"));
        assertThat(review1.getName(), is("Jane Doe"));
        assertThat(review1.getComment(), is("some comment"));
        assertThat(review1.getRating(), is(3));

        Review review2 = new Review();
        review2.setTitle("Star Wars");
        review2.setName("John Doe");
        review2.setComment("some other comment");
        review2.setRating(5);
        assertThat(review2.getTitle(), is("Star Wars"));
        assertThat(review2.getName(), is("John Doe"));
        assertThat(review2.getComment(), is("some other comment"));
        assertThat(review2.getRating(), is(5));
    }
}