package com.ace.aws.service;

import com.ace.aws.domain.Movie;

import java.util.List;

public interface MovieService
{
    void addMovie(Movie movie);

    Movie getMovie(String title);

    List<Movie> getMovieByFirstLetterOfTitle(char letter);

    List<Movie> getMovieByRelease(int year);

}
