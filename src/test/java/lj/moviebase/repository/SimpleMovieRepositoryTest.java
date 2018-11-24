package lj.moviebase.repository;

import lj.moviebase.domain.Movie;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.time.Year;
import java.util.Optional;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleMovieRepositoryTest {

    private static final String SOME_TITLE = "someTitle";
    private static final String SOME_OTHER_TITLE = "someOtherTitle";

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

    private Condition<Movie> movieWithTitle(String title) {
        return new Condition<>(m -> m.getTitle().equals(title), "same as %s title", title);
    }

    private Movie givenMovieWithTitle(String title) {
        Movie movie = new Movie(title, 10, Year.now(), emptySet());
        simpleMovieRepository = new SimpleMovieRepository(newHashSet(movie));
        return movie;
    }

}