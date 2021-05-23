package com.ace.aws.db;

import com.ace.aws.domain.Movie;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movie")
public class MovieDataObject
{
    @Id
    @Column(name = "title")
    private String title;

    @Column(name = "release")
    private int release;

    @Column(name = "uri")
    private String uri;

    public MovieDataObject()
    {
    }

    public MovieDataObject(String title, int release, String uri)
    {
        this.title = title;
        this.release = release;
        this.uri = uri;
    }

    public static MovieDataObject create(Movie movie)
    {
        return new MovieDataObject(movie.getTitle(), movie.getRelease(), movie.getUri());
    }

    public String getTitle()
    {
        return title;
    }

    public Movie getMovie()
    {
        return new Movie(title, release, uri);
    }
}
