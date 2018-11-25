package lj.moviebase.query.filter;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import lj.moviebase.domain.Actor;
import lj.moviebase.domain.Character;
import lj.moviebase.domain.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static lj.moviebase.query.filter.MovieFilteringCriteria.filterBasedOn;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalGetWithoutIsPresent"})
@RunWith(JUnitParamsRunner.class)
public class MovieFilteringCriteriaTest {

    private static final Optional<Integer> SOME_DURATION = Optional.of(1);
    private static final Optional<Integer> OTHER_DURATION = Optional.of(2);
    private static final Optional<Year> SOME_RELEASE_YEAR = Optional.of(Year.of(2018));
    private static final Optional<Year> OTHER_RELEASE_YEAR = Optional.of(Year.of(2017));
    private static final Optional<Actor> SOME_ACTOR = Optional.of(new Actor("Adam", "B"));
    private static final Optional<Actor> OTHER_ACTOR = Optional.of(new Actor("Jan", "C"));

    private final Movie movie = mock(Movie.class);
    private final Character character = mock(Character.class);

    @Test
    @Parameters(method = "casesEvaluatedToTrue")
    public void shouldEvaluateToTrueBasedOnSuppliedParametersCombinations(Optional<Integer> duration, Optional<Year> releaseYear, Optional<Actor> actor) {
        givenMovie();

        Predicate<Movie> moviePredicate = filterBasedOn(duration, releaseYear, actor).asPredicate();

        assertThat(moviePredicate.test(movie)).isTrue();
    }

    @Test
    @Parameters(method = "casesEvaluatedToFalse")
    public void shouldEvaluateToFalseBasedOnSuppliedParametersCombinations(Optional<Integer> duration, Optional<Year> releaseYear, Optional<Actor> actor) {
        givenMovie();

        Predicate<Movie> moviePredicate = filterBasedOn(duration, releaseYear, actor).asPredicate();

        assertThat(moviePredicate.test(movie)).isFalse();
    }

    public List<List<Optional>> casesEvaluatedToTrue() {
        return asList(asList(SOME_DURATION, SOME_RELEASE_YEAR, SOME_ACTOR),
                asList(SOME_DURATION, SOME_RELEASE_YEAR, empty()),
                asList(SOME_DURATION, empty(), SOME_ACTOR),
                asList(empty(), SOME_RELEASE_YEAR, SOME_ACTOR),
                asList(SOME_DURATION, empty(), empty()),
                asList(empty(), empty(), SOME_ACTOR),
                asList(empty(), SOME_RELEASE_YEAR, empty()),
                asList(empty(), empty(), empty()));
    }

    public Collection<List<Optional>> casesEvaluatedToFalse() {
        ArrayList<List<Optional>> combinations = new ArrayList<>();
        for (Optional arg1 : asList(OTHER_DURATION, SOME_DURATION, empty())) {
            for (Optional arg2 : asList(OTHER_RELEASE_YEAR, SOME_RELEASE_YEAR, empty())) {
                for (Optional arg3 : asList(OTHER_ACTOR, SOME_ACTOR, empty())) {
                    combinations.add(asList(arg1, arg2, arg3));
                }
            }
        }
        combinations.removeAll(casesEvaluatedToTrue());
        return combinations;
    }

    private void givenMovie() {
        given(movie.getDuration()).willReturn(SOME_DURATION.get());
        given(movie.getReleaseYear()).willReturn(SOME_RELEASE_YEAR.get());
        given(movie.getCharacters()).willReturn(newHashSet(character));
        given(character.getActor()).willReturn(SOME_ACTOR.get());
    }
}
