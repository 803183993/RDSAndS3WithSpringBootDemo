package com.ace.aws.db;

import com.ace.aws.domain.Movie;
import com.ace.aws.domain.Review;

import java.util.List;

public interface MovieRepository
{
    MovieDataObject findMovieByTitle(String title);

    List<MovieDataObject> findMoviesByFirstLetterOfTitle(char letter);

    List<MovieDataObject> findMoviesByRelease(int year);

    void addMovie(Movie movie);

    void addReview(Review review);

    MovieDataObject getMovieWithReviews(String title);

    List<AuditDataObject> listAllAuditEvents();

    List<AuditDataObject> getAuditEntriesByIdentifier(String entityNumber);
}
