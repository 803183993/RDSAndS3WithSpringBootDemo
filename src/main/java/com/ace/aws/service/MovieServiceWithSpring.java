package com.ace.aws.service;

import com.ace.aws.db.MovieDataObject;
import com.ace.aws.db.MovieRepository;
import com.ace.aws.domain.Movie;
import com.ace.aws.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.ace.aws.common.StringUtilities.capitaliseWords;

@Component
public class MovieServiceWithSpring implements MovieService
{
    @Autowired
    @SuppressWarnings("unused")
    private MovieRepository movieRepository;

    @Override
    public void addMovie(Movie movie)
    {
        movieRepository.addMovie(movie);
    }

    @Override
    public void addReview(Review review)
    {
        movieRepository.addReview(review);
    }

    @Override
    public Movie getMovie(String title)
    {
        MovieDataObject movieDO = movieRepository.findMovieByTitle(capitaliseWords(title));
        return movieDO != null ? movieDO.getMovie() : null;
    }

    @Override
    public Movie getMovieWithReviews(String title)
    {
        MovieDataObject movieDO = movieRepository.getMovieWithReviews(capitaliseWords(title));
        return movieDO != null ? movieDO.getMovie() : null;
    }

    @Override
    public List<Movie> getMovieByFirstLetterOfTitle(char letter)
    {
        List<MovieDataObject> movieDataObjects = movieRepository.findMoviesByFirstLetterOfTitle(letter);
        return getMovies(movieDataObjects);
    }


    @Override
    public List<Movie> getMovieByRelease(int year)
    {
        List<MovieDataObject> movieDataObjects = movieRepository.findMoviesByRelease(year);
        return getMovies(movieDataObjects);
    }

    private List<Movie> getMovies(List<MovieDataObject> movieDataObjects)
    {
        if (movieDataObjects != null)
        {
            List<Movie> movies = new ArrayList<>();
            for (MovieDataObject movieDataObject : movieDataObjects)
            {
                movies.add(movieDataObject.getMovie());
            }
            return movies;
        } else
        {
            return null;
        }
    }
}
