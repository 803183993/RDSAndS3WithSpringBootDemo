package com.ace.aws.db;

import com.ace.aws.domain.Movie;

import java.util.List;

public interface MovieRepository
{
    MovieDataObject findMovieByTitle(String title);

    List<MovieDataObject> findMoviesByFirstLetterOfTitle(char letter);

    List<MovieDataObject> findMoviesByRelease(int year);

    void addMovie(Movie movie);

    List<AuditDataObject> listAllAuditEvents();

    List<AuditDataObject> getAuditEntriesByIdentifier(String entityNumber);
}
