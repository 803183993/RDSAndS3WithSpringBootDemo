package com.ace.aws.domain;

import java.util.Objects;

public class Movie
{
    private final String title;
    private final int release;
    private final String posterImageFilename;
    private final String director;

    public Movie(String title, int release, String director, String posterImageFilename)
    {
        this.title = title;
        this.director = director;
        this.release = release;
        this.posterImageFilename = posterImageFilename;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDirector()
    {
        return director;
    }

    public int getRelease()
    {
        return release;
    }

    public String getPosterImageFilename()
    {
        return posterImageFilename;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || !getClass().equals(obj.getClass()))
        {
            return false;
        }

        Movie that = (Movie) obj;
        return this.title.equals(that.getTitle())
                && this.release == that.getRelease()
                && this.director.equals(that.getDirector())
                && this.posterImageFilename.equals(that.getPosterImageFilename());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(title, release, director, posterImageFilename);
    }
}

