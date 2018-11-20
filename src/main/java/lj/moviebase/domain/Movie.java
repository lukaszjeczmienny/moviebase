package lj.moviebase.domain;

import org.joda.time.Minutes;

import java.time.Year;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

public class Movie {
    private final String title;
    private final Minutes duration;
    private final Year releaseYear;
    private final Set<Actor> cast;

    public Movie(String title, Minutes duration, Year releaseYear, Set<Actor> cast) {
        this.title = title;
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.cast = cast;
    }

    public String getTitle() {
        return title;
    }

    public Minutes getDuration() {
        return duration;
    }

    public Year getReleaseYear() {
        return releaseYear;
    }

    public Set<Actor> getCast() {
        return unmodifiableSet(cast);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title) &&
                Objects.equals(duration, movie.duration) &&
                Objects.equals(releaseYear, movie.releaseYear) &&
                Objects.equals(cast, movie.cast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, duration, releaseYear, cast);
    }
}
