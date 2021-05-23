package com.ace.aws.domain;

import java.util.Objects;

public class Movie
{
    private final String title;
    private final int release;
    private final String uri;

    public Movie(String title, int release, String uri)
    {
        this.title = title;
        this.release = release;
        this.uri = uri;
    }

    public String getTitle()
    {
        return title;
    }

    public int getRelease()
    {
        return release;
    }

    public String getUri()
    {
        return uri;
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
                && this.uri.equals(that.getUri());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(title, release, uri);
    }
}

