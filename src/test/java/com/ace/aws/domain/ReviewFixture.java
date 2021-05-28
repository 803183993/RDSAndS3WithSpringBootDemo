package com.ace.aws.domain;

import java.time.ZonedDateTime;

public class ReviewFixture
{
    private String title;
    private String reviewer = "John Doe";
    private ZonedDateTime date;

    public ReviewFixture withTitle(String title)
    {
        this.title = title;
        return this;
    }

    public ReviewFixture withReviewer(String reviewer)
    {
        this.reviewer = reviewer;
        return this;
    }

    public ReviewFixture withDate(ZonedDateTime date)
    {
        this.date = date;
        return this;
    }

    public Review build()
    {
        Review review = new Review();
        review.setTitle(title);
        review.setReviewer(reviewer);
        review.setComment("some review");
        review.setDate(date);
        review.setRating(2);
        review.setIpAddress("someIP");
        return review;
    }
}
