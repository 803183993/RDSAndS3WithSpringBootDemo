package com.ace.aws.domain;

public class Review
{
    private String title;
    private String name;
    private int rating;
    private String comment;

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getName()
    {
        return name;
    }

    public String getTitle()
    {
        return title;
    }

    public int getRating()
    {
        return rating;
    }

    public String getComment()
    {
        return comment;
    }
}
