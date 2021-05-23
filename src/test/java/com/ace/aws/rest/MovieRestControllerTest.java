package com.ace.aws.rest;

import com.ace.aws.domain.Movie;
import com.ace.aws.domain.MovieFixture;
import com.ace.aws.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(MovieRestController.class)
public class MovieRestControllerTest
{
    @Autowired
    @SuppressWarnings({"UnusedDeclaration"})
    private MockMvc mockMvc;

    @MockBean
    @SuppressWarnings({"UnusedDeclaration"})
    private MovieService movieService;

    private Movie movie1;
    private Movie movie2;
    List<Movie> movies;

    @BeforeEach
    public void setup()
    {
        movie1 = new MovieFixture().build();
        movie2 = new MovieFixture().withTitle("Deadpool").build();
        movies = new ArrayList<>()
        {
            {
                add(movie1);
                add(movie2);
            }
        };
    }

    @Test
    public void shouldReturnMoviesByFirstLetter() throws Exception
    {
        when(movieService.getMovieByFirstLetterOfTitle('D')).thenReturn(movies);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/rest/movies/byLetter/D"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title", is(movie1.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].title", is(movie2.getTitle())));
    }

    @Test
    public void shouldReturnNullIfMoviesForSpecifiedLetterNotFound() throws Exception
    {
        when(movieService.getMovieByFirstLetterOfTitle('X')).thenReturn(null);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/rest/movies/byLetter/X"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(204))
                .andExpect(content().string(""));
    }

    @Test
    public void shouldReturnMoviesByYear() throws Exception
    {
        when(movieService.getMovieByRelease(1977)).thenReturn(movies);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/rest/movies/byYear/1977"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title", is(movie1.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].title", is(movie2.getTitle())));
    }

    @Test
    public void shouldReturnNullIfMoviesForSpecifiedYearNotFound() throws Exception
    {
        when(movieService.getMovieByRelease(1999)).thenReturn(null);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/rest/movies/byYear/1999"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(204))
                .andExpect(content().string(""));
    }

    @Test
    public void shouldThrowInternalServerErrorForUnexpectedExceptions() throws Exception
    {
        doThrow(new PersistenceException()).when(movieService).addMovie(any());

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/rest/movies/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(movie1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Unknown"));
    }

    @Test
    public void shouldThrowDuplicateKeyExceptionIfMovieAlreadyExists() throws Exception
    {
        doThrow(new DataIntegrityViolationException("")).when(movieService).addMovie(any());

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/rest/movies/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(movie1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(409))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("DuplicateKeyException"));
    }

    @Test
    public void shouldAddNewMovie() throws Exception
    {
        doNothing().when(movieService).addMovie(isA(Movie.class));

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/rest/movies/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(movie1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(content().contentType("text/plain;charset=UTF-8"));
    }

    @Test
    public void shouldReturnHttpOkIfSpecifiedMovieIsFound() throws Exception
    {
        when(movieService.getMovie(any())).thenReturn(movie1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/rest/movies/Star Wars"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is(movie1.getTitle())));
    }

    @Test
    public void shouldReturnErrorIfSpecifiedMovieNotFound() throws Exception
    {
        when(movieService.getMovie(any())).thenReturn(null);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/rest/movies/Star Wars"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(204))
                .andExpect(content().string(""));
    }
}
