package com.ace.aws.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReviewTest
{
    private Review review1;
    private Review review2;
    ZonedDateTime date1;
    ZonedDateTime date2;

    @Before
    public void setup()
    {
        date1 = ZonedDateTime.now().withDayOfMonth(12);
        date2 = ZonedDateTime.now().withDayOfMonth(13);
        review1 = new Review();
        review1.setTitle("Star Trek");
        review1.setReviewer("Jane Doe");
        review1.setComment("some comment");
        review1.setIpAddress("someIP");
        review1.setDate(date1);
        review1.setRating(3);

        review2 = new Review();
        review2.setTitle("Star Wars");
        review2.setReviewer("John Doe");
        review2.setComment("some other comment");
        review2.setRating(5);
        review2.setIpAddress("someOtherIP");
        review2.setDate(date2);
    }

    @Test
    public void canCreateReview()
    {
        assertThat(review1.getTitle(), is("Star Trek"));
        assertThat(review1.getReviewer(), is("Jane Doe"));
        assertThat(review1.getComment(), is("some comment"));
        assertThat(review1.getRating(), is(3));
        assertThat(review1.getIpAddress(), is("someIP"));
        assertThat(review1.getDate(), is(date1));

        assertThat(review2.getTitle(), is("Star Wars"));
        assertThat(review2.getReviewer(), is("John Doe"));
        assertThat(review2.getComment(), is("some other comment"));
        assertThat(review2.getRating(), is(5));
        assertThat(review2.getIpAddress(), is("someOtherIP"));
        assertThat(review2.getDate(), is(date2));
    }

    @Test
    public void shouldSortReviewsInDescendingOrder()
    {
        ZonedDateTime date3 = ZonedDateTime.now().withDayOfMonth(14);
        Review review3 = new Review();
        review3.setTitle("Star Trek");
        review3.setReviewer("Jane Doe");
        review3.setComment("some comment");
        review3.setDate(date3);
        review3.setRating(4);

        List<Review> reviews = new ArrayList<>()
        {
            {
                add(review1);
                add(review3);
                add(review2);
            }
        };

        Collections.sort(reviews);

        assertThat(reviews.get(0), is(review3));
        assertThat(reviews.get(1), is(review2));
        assertThat(reviews.get(2), is(review1));
    }
}