package lj.moviebase.repository;

import lj.moviebase.domain.Movie;
import lj.moviebase.query.filter.FilteringCriteria;

import java.util.Optional;
import java.util.Set;

public interface MovieRepository {
    Optional<Movie> getByTitle(String id);

    Optional<Movie> save(Movie movie);

    Optional<Movie> update(Movie movieToSet);

    Movie removeByTitle(String title);

    Set<Movie> findAllBy(FilteringCriteria<Movie> filteringCriteria);
}
