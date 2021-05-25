package com.ace.aws.service;

import com.ace.aws.domain.Movie;
import com.ace.aws.domain.Review;

import java.util.List;

public interface MovieService
{
    void addMovie(Movie movie);

    void addReview(Review review);

    Movie getMovie(String title);

    List<Movie> getMovieByFirstLetterOfTitle(char letter);

    List<Movie> getMovieByRelease(int year);

}
