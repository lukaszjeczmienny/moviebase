package lj.moviebase.repository;

import lj.moviebase.domain.Movie;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.HashSet;
import java.util.Optional;

import static com.google.common.collect.Sets.newHashSet;
import static java.time.Year.parse;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleMovieRepositoryTest {

    private static final String SOME_TITLE = "someTitle";
    private static final String SOME_OTHER_TITLE = "someOtherTitle";
    public static final String SOME_YEAR = "2018";

    private SimpleMovieRepository simpleMovieRepository;

    @Test
    public void shouldFindMovieByTitleIfItExistsInMovieBase() {
        givenMovieWithTitle(SOME_TITLE);

        Optional<Movie> movie = simpleMovieRepository.getByTitle(SOME_TITLE);

        assertThat(movie).hasValueSatisfying(movieWithTitle(SOME_TITLE));
    }

    @Test
    public void shouldNotFindMovieByTitleIfItDoesNotExistsInMovieBase() {
        givenMovieWithTitle(SOME_OTHER_TITLE);

        Optional<Movie> movie = simpleMovieRepository.getByTitle(SOME_TITLE);

        assertThat(movie).isNotPresent();
    }

    @Test
    public void shouldRemoveMovieBasedOnTitlePredicateIfSuchExistsAndReturnRemovedEntry() {
        Movie movie = givenMovieWithTitle(SOME_TITLE);

        Movie result = simpleMovieRepository.removeByTitle(SOME_TITLE);

        assertThat(result).isEqualTo(movie);
    }

    @Test
    public void shouldReturnNullIfMovieWithGivenTitleDoesNotExists() {
        givenMovieWithTitle(SOME_TITLE);

        Movie result = simpleMovieRepository.removeByTitle(SOME_OTHER_TITLE);

        assertThat(result).isNull();
    }

    @Test
    public void shouldSaveMovieIfDoesNotExistsAndReturnEmptyOptional() {
        simpleMovieRepository = new SimpleMovieRepository(new HashSet<>());

        Optional<Movie> result = simpleMovieRepository.save(movieWith(SOME_TITLE, SOME_YEAR));

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldNotSaveMovieIfDoesExistsAndReturnExistingOne() {
        Movie movie = givenMovieWithTitle(SOME_TITLE);
        Movie movieWithSameTitle = movieWith(SOME_TITLE, "2019");

        Optional<Movie> result = simpleMovieRepository.save(movieWithSameTitle);

        assertThat(result).hasValue(movie);
    }

    private Condition<Movie> movieWithTitle(String title) {
        return new Condition<>(m -> m.getTitle().equals(title), "same as %s title", title);
    }

    private Movie givenMovieWithTitle(String title) {
        Movie movie = movieWith(title, "2018");
        simpleMovieRepository = new SimpleMovieRepository(newHashSet(movie));
        return movie;
    }

    private Movie movieWith(String title, String year) {
        return new Movie(title, 10, parse(year), emptySet());
    }

}