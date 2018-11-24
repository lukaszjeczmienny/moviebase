package lj.moviebase.repository;

import lj.moviebase.domain.Movie;

import java.util.Optional;

public interface MovieRepository {
    Optional<Movie> getByTitle(String id);

    Optional<Movie> save(Movie movie);

    Optional<Movie> update(Movie movieToSet);

    Movie removeByTitle(String title);
}
