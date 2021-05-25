package com.ace.aws.db;

import com.ace.aws.domain.Review;
import com.ace.aws.domain.ReviewFixture;
import org.junit.Test;

import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class ReviewDataObjectTest
{
    @Test
    public void shouldCreateReviews()
    {
        ZonedDateTime date1 = ZonedDateTime.now();
        ZonedDateTime date2 = ZonedDateTime.now().withDayOfMonth(13);

        Review review1 = new ReviewFixture().withTitle("Star Wars").withDate(date1).build();
        Review review2 = new ReviewFixture().withTitle("Star Trek").withReviewer("joe bloggs").withDate(date2).build();

        ReviewDataObject reviewDataObject1 = ReviewDataObject.create(review1);
        ReviewDataObject reviewDataObject2 = ReviewDataObject.create(review2);

        assertThat(reviewDataObject1.getReviewId(), is(0));
        assertThat(reviewDataObject1.getReview().getTitle(), is(review1.getTitle()));
        assertThat(reviewDataObject1.getReview().getReviewer(), is(review1.getReviewer()));
        assertThat(reviewDataObject1.getReview().getDate(), is(nullValue()));
        assertThat(reviewDataObject1.getReview().getComment(), is(review1.getComment()));
        assertThat(reviewDataObject1.getReview().getRating(), is(review1.getRating()));

        assertThat(reviewDataObject2.getReviewId(), is(0));
        assertThat(reviewDataObject2.getReview().getTitle(), is(review2.getTitle()));
        assertThat(reviewDataObject2.getReview().getReviewer(), is("Joe Bloggs"));
        assertThat(reviewDataObject2.getReview().getDate(), is(nullValue()));
        assertThat(reviewDataObject2.getReview().getComment(), is(review2.getComment()));
        assertThat(reviewDataObject2.getReview().getRating(), is(review2.getRating()));
    }
}