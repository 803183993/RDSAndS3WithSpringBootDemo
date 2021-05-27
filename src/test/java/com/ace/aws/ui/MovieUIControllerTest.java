package com.ace.aws.ui;

import com.ace.aws.domain.Movie;
import com.ace.aws.domain.MovieFixture;
import com.ace.aws.service.MovieService;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieUIController.class)
public class MovieUIControllerTest
{
    @Autowired
    @SuppressWarnings({"UnusedDeclaration"})
    private MockMvc mockMvc;

    @MockBean
    @SuppressWarnings({"UnusedDeclaration"})
    private MovieService movieService;

    private Movie movie;

    @BeforeEach
    public void setup()
    {
        movie = new MovieFixture().build();
    }

    @Test
    public void shouldReturnMessageFromService() throws Exception
    {
        when(movieService.getMovieWithReviews(movie.getTitle())).thenReturn(movie);
        this.mockMvc.perform(get("/ui/movies/?title=Death Star"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(movie.getTitle())));
    }

    @Test
    @Ignore
    public void shouldAddReview() throws Exception
    {
        when(movieService.getMovie(movie.getTitle())).thenReturn(movie);
        this.mockMvc.perform(get("/ui/movies/addReview/"))
                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString(movie.getTitle())))
        ;
    }
}