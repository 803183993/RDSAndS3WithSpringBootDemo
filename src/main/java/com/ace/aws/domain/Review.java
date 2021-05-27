package com.ace.aws.domain;

import java.time.ZonedDateTime;

public class Review implements Comparable<Review>
{
    private String title;
    private String reviewer;
    private int rating;
    private String comment;
    private ZonedDateTime date;

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDate(ZonedDateTime date)
    {
        this.date = date;
    }

    public void setReviewer(String reviewer)
    {
        this.reviewer = reviewer;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getReviewer()
    {
        return reviewer;
    }

    public String getTitle()
    {
        return title;
    }

    public int getRating()
    {
        return rating;
    }

    public ZonedDateTime getDate()
    {
        return date;
    }

    public String getComment()
    {
        return comment;
    }

    @Override
    public int compareTo(Review that)
    {
        return that.date.compareTo(this.date);
    }
}
