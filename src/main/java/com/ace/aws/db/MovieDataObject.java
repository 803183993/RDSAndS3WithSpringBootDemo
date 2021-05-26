package com.ace.aws.db;

import com.ace.aws.domain.Movie;
import com.ace.aws.domain.Review;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "director")
    private String director;

    @OneToMany(mappedBy = "movie", targetEntity = ReviewDataObject.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection, FieldMayBeFinal"})
    private List<ReviewDataObject> reviews = new ArrayList<>();

    public MovieDataObject()
    {
    }

    public MovieDataObject(String title, int release, String director, String uri)
    {
        this.title = title;
        this.director = director;
        this.release = release;
        this.uri = uri;
    }

    public static MovieDataObject create(Movie movie)
    {
        return new MovieDataObject(movie.getTitle(), movie.getRelease(), movie.getDirector(), movie.getPosterImageFilename());
    }

    public String getTitle()
    {
        return title;
    }

    public Movie getMovie()
    {
        Movie movie = new Movie(title, release, director, uri);
        List<Review> retrievedReviews = new ArrayList<>();
        for (ReviewDataObject review : reviews)
        {
            retrievedReviews.add(review.getReview());
        }
        movie.setReviews(retrievedReviews);
        return movie;
    }
}
