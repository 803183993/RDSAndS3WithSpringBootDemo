package com.ace.aws.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotEquals;

public class MovieTest
{
    @Test
    public void canCreateMovie()
    {
        Movie movie1 = new MovieFixture().build();
        assertThat(movie1.getTitle(), is("Death Star"));
        assertThat(movie1.getDirector(), is("Joe Bloggs"));
        assertThat(movie1.getRelease(), is(1977));
        assertThat(movie1.getPosterImageFilename(), is("http://s3/MovieBucket/DeathStar.jpg"));

        Movie movie2 = new Movie("Star Trek", 1974, "Gene Roddenberry", null);
        assertThat(movie2.getTitle(), is("Star Trek"));
        assertThat(movie2.getDirector(), is("Gene Roddenberry"));
        assertThat(movie2.getRelease(), is(1974));
        assertThat(movie2.getPosterImageFilename(), nullValue());
    }

    @Test
    public void shouldReturnTrueIfSameMovies()
    {
        Movie movie1 = new MovieFixture().build();
        Movie movie2 = new MovieFixture().build();

        assertThat(movie1, is(movie1));
        assertThat(movie1, is(movie2));
    }

    @Test
    public void shouldReturnFalseIfDifferentMovies()
    {
        Movie movie1 = new MovieFixture().withRelease(1977).build();
        Movie movie2 = new MovieFixture().withRelease(1978).build();
        Movie movie3 = new MovieFixture().withTitle("Star Trek").build();
        Movie movie4 = new Movie("Star Wars", 1977, "George Lucas", "");
        Movie movie5 = new MovieFixture().withDirector("George Lucas").build();

        assertNotEquals(null, movie1);
        assertNotEquals(movie1, new Object());

        assertThat(movie1, not(movie2));
        assertThat(movie1, not(movie3));
        assertThat(movie1, not(movie4));
        assertThat(movie1, not(movie5));
    }

    @Test
    public void shouldReturnSameHashCode()
    {
        Movie movie1 = new MovieFixture().build();
        Movie movie2 = new MovieFixture().build();
        Movie movie3 = new MovieFixture().withTitle("Star Trek").build();

        assertThat(movie1.hashCode(), is(21718261));
        assertThat(movie1.hashCode(), is(movie2.hashCode()));
        assertThat(movie1.hashCode(), not(movie3.hashCode()));
    }
}
