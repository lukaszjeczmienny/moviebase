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
    private final Set<Character> characters;

    public Movie(String title, Minutes duration, Year releaseYear,
                 Set<Character> characters) {
        this.title = title;
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.characters = characters;
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

    public Set<Character> getCharacters() {
        return unmodifiableSet(characters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title) &&
                Objects.equals(duration, movie.duration) &&
                Objects.equals(releaseYear, movie.releaseYear) &&
                Objects.equals(characters, movie.characters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, duration, releaseYear, characters);
    }
}
