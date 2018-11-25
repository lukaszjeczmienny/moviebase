package lj.moviebase.query.filter;

import lj.moviebase.domain.Actor;
import lj.moviebase.domain.Character;
import lj.moviebase.domain.Movie;

import java.time.Year;
import java.util.Optional;
import java.util.function.Predicate;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class MovieFilteringCriteria implements FilteringCriteria<Movie> {

    private static final Predicate<Movie> ALWAYS_TRUE_PREDICATE = m -> true;
    private final Optional<Integer> duration;
    private final Optional<Year> releaseYear;
    private final Optional<Actor> actor;

    private MovieFilteringCriteria(Optional<Integer> duration, Optional<Year> releaseYear, Optional<Actor> actor) {
        this.duration = duration;
        this.releaseYear = releaseYear;
        this.actor = actor;
    }

    @Override
    public Predicate<Movie> asPredicate() {
        return optionallyHasReleaseYear(releaseYear)
                .and(optionallyHasDuration(duration))
                .and(optionallyHasActor(actor));
    }

    private Predicate<Movie> optionallyHasReleaseYear(Optional<Year> optionalYear) {
        return optionalYear
                .map(this::hasReleaseYear)
                .orElse(ALWAYS_TRUE_PREDICATE);
    }

    private Predicate<Movie> optionallyHasDuration(Optional<Integer> optionalDuration) {
        return optionalDuration
                .map(this::hasDuration)
                .orElse(ALWAYS_TRUE_PREDICATE);
    }

    private Predicate<Movie> optionallyHasActor(Optional<Actor> optionalActor) {
        return optionalActor
                .map(this::hasActor)
                .orElse(ALWAYS_TRUE_PREDICATE);
    }

    private Predicate<Movie> hasReleaseYear(Year givenYear) {
        return movie -> movie.getReleaseYear().equals(givenYear);
    }

    private Predicate<Movie> hasDuration(Integer givenDuration) {
        return movie -> movie.getDuration().equals(givenDuration);
    }

    private Predicate<Movie> hasActor(Actor givenActor) {
        return movie -> movie.getCharacters()
                .stream()
                .anyMatch(playedRoleBy(givenActor));
    }

    private Predicate<Character> playedRoleBy(Actor value) {
        return character -> character.getActor().equals(value);
    }

    public static MovieFilteringCriteria filterBasedOn(Optional<Integer> duration, Optional<Year> releaseYear, Optional<Actor> actor) {
        return new MovieFilteringCriteria(duration, releaseYear, actor);
    }
}
