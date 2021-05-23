package com.ace.aws.domain;

import com.ace.aws.db.MovieDataObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class MovieFixture
{
    private static final String S3_URL = "http://s3/MovieBucket";
    private String title = "Death Star";
    private int release = 1977;

    public MovieFixture withTitle(String title)
    {
        this.title = title;
        return this;
    }

    public MovieFixture withRelease(int release)
    {
        this.release = release;
        return this;
    }

    public Movie build()
    {
        return new Movie(title, release, format("%s/%s.jpg", S3_URL, title.replaceAll(" ", "")));
    }

    public static List<MovieDataObject> toDOList(Movie... movies)
    {
        List<MovieDataObject> moviesDO = new ArrayList<>();
        for (Movie movie : movies)
        {
            moviesDO.add(MovieDataObject.create(movie));
        }
        return moviesDO;
    }
}
