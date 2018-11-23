package lj.moviebase.repository;

import lj.moviebase.domain.Movie;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.time.Year;
import java.util.Optional;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.joda.time.Minutes.minutes;

public class SimpleMovieRepositoryTest {

    private static final String SOME_TITLE = "someTitle";

    private SimpleMovieRepository simpleMovieRepository;

    @Test
    public void shouldFindMovieByTitleIfItExistsInMovieBase() {
        givenMovieWithTitle(SOME_TITLE);

        Optional<Movie> movie = simpleMovieRepository.getByTitle(SOME_TITLE);

        assertThat(movie).hasValueSatisfying(movieWithTitle(SOME_TITLE));
    }

    @Test
    public void shouldNotFindMovieByTitleIfItDoesNotExistsInMovieBase() {
        givenMovieWithTitle("someOtherTitle");

        Optional<Movie> movie = simpleMovieRepository.getByTitle(SOME_TITLE);

        assertThat(movie).isNotPresent();
    }

    private Condition<Movie> movieWithTitle(String title) {
        return new Condition<>(m -> m.getTitle().equals(title), "same as %s title", title);
    }

    private void givenMovieWithTitle(String title) {
        Set<Movie> movies = newHashSet(new Movie(title, minutes(10), Year.now(), emptySet()));
        simpleMovieRepository = new SimpleMovieRepository(movies);
    }
}