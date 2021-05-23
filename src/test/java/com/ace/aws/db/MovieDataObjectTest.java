package com.ace.aws.db;

import com.ace.aws.domain.Movie;
import com.ace.aws.domain.MovieFixture;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class MovieDataObjectTest
{
    @Rule
    public JUnitRuleMockery mockery = new JUnitRuleMockery()
    {{
        setImposteriser(ClassImposteriser.INSTANCE);
        setThreadingPolicy(new Synchroniser());
    }};

    private Movie movie;

    @Before
    public void setup()
    {
        movie = new MovieFixture().withTitle("Remains Of The Day").withRelease(1993).build();
    }

    @Test
    public void shouldCreateMovieDataObjectFromMovie()
    {
        MovieDataObject movieDataObject = MovieDataObject.create(movie);
        assertThat(movieDataObject.getTitle(), is(movie.getTitle()));
        assertThat(movieDataObject.getMovie(), is(movie));

        assertThat(new MovieDataObject().getTitle(), nullValue());
    }
}
