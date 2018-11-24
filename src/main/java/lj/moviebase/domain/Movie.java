package lj.moviebase.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.time.Year;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

public class Movie {
    private final String title;
    private final Integer duration;
    private final Year releaseYear;
    private final Set<Character> characters;

    @JsonCreator
    public Movie(String title, Integer duration, Year releaseYear,
                 Set<Character> characters) {
        this.title = title;
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.characters = characters;
    }

    public String getTitle() {
        return title;
    }

    public Integer getDuration() {
        return duration;
    }

    @JsonSerialize(using = ToStringSerializer.class)
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
