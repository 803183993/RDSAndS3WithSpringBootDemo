package com.ace.aws.service;

import com.ace.aws.db.MovieDataObject;
import com.ace.aws.db.MovieRepository;
import com.ace.aws.domain.Movie;
import com.ace.aws.domain.MovieFixture;
import com.ace.aws.domain.Review;
import com.ace.aws.domain.ReviewFixture;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class MovieServiceWithSpringTest
{
    @Autowired
    @InjectMocks
    private MovieServiceWithSpring movieService;
    @Mock
    private MovieRepository movieRepository;
    private Movie movie1;
    List<MovieDataObject> movies;

    @Before
    public void init()
    {
        MockitoAnnotations.openMocks(this);
        movie1 = new MovieFixture().build();

        Review review1 = new ReviewFixture().withTitle(movie1.getTitle()).build();
        Review review2 = new ReviewFixture().withTitle(movie1.getTitle()).build();

        movie1.setReviews(new ArrayList<>()
        {
            {
                add(review1);
                add(review2);
            }
        });

        Movie movie2 = new MovieFixture().withTitle("Deadpool").build();
        movies = MovieFixture.toDOList(movie1, movie2);
    }

    @Test
    public void shouldReturnMoviesWithReviews()
    {
        when(movieRepository.getMovieWithReviews(movie1.getTitle())).thenReturn(MovieDataObject.create(movie1));
        Movie actualMovie = movieService.getMovieWithReviews(movie1.getTitle());
        assertThat(actualMovie.getTitle(), is(movie1.getTitle()));
        assertThat(actualMovie.getReviews().size(), is(2));
    }

    @Test
    public void shouldReturnNullIfMovieWithReviewsNotFound()
    {
        when(movieRepository.getMovieWithReviews("someMovie")).thenReturn(null);
        Movie actualMovie = movieService.getMovieWithReviews("someMovie");
        assertThat(actualMovie, nullValue());
    }

    @Test
    public void shouldReturnMoviesByFirstLetter()
    {
        when(movieRepository.findMoviesByFirstLetterOfTitle('D')).thenReturn(movies);
        List<Movie> actualMovies = movieService.getMovieByFirstLetterOfTitle('D');
        assertThat(actualMovies.size(), is(2));
    }

    @Test
    public void shouldReturnNullIfMoviesNotFoundForSpecifiedLetter()
    {
        when(movieRepository.findMoviesByFirstLetterOfTitle('D')).thenReturn(null);
        List<Movie> actualMovies = movieService.getMovieByFirstLetterOfTitle('D');
        assertThat(actualMovies, is(nullValue()));
    }

    @Test
    public void shouldReturnMoviesByRelease()
    {
        when(movieRepository.findMoviesByRelease(1977)).thenReturn(movies);
        List<Movie> actualMovies = movieService.getMovieByRelease(1977);
        assertThat(actualMovies.size(), is(2));
    }

    @Test
    public void shouldReturnNullIfMoviesForSpecifiedYearNotFound()
    {
        when(movieRepository.findMoviesByRelease(1977)).thenReturn(null);
        List<Movie> actualMovies = movieService.getMovieByRelease(1977);
        assertThat(actualMovies, is(nullValue()));
    }

    @Test
    public void canAddMovie()
    {
        doNothing().when(movieRepository).addMovie(isA(Movie.class));
        movieService.addMovie(movie1);
    }

    @Test
    public void canAddReview()
    {
        doNothing().when(movieRepository).addReview(isA(Review.class));
        movieService.addReview(new ReviewFixture().build());
    }

    @Test
    public void canRetrieveMovie()
    {
        when(movieRepository.findMovieByTitle(movie1.getTitle())).thenReturn(MovieDataObject.create(movie1));
        assertThat(movieService.getMovie(movie1.getTitle()).getTitle(), is(movie1.getTitle()));
    }

    @Test
    public void canRetrieveMovieWithUnformattedTitle()
    {
        when(movieRepository.findMovieByTitle(movie1.getTitle())).thenReturn(MovieDataObject.create(movie1));
        assertThat(movieService.getMovie(movie1.getTitle().toLowerCase()).getTitle(), is(movie1.getTitle()));
    }

    @Test
    public void shouldReturnNullIfMovieNotFound()
    {
        when(movieRepository.findMovieByTitle(movie1.getTitle())).thenReturn(null);
        assertThat(movieService.getMovie(movie1.getTitle()), nullValue());
    }
}
