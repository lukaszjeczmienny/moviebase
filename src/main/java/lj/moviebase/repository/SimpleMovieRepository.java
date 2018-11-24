package lj.moviebase.repository;

import lj.moviebase.domain.Movie;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.google.common.io.Resources.getResource;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.emptySet;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static lj.moviebase.resource.JsonUtils.jsonMapper;

public class SimpleMovieRepository implements MovieRepository {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleMovieRepository.class);
    private final Map<String, Movie> movies;

    public SimpleMovieRepository(Set<Movie> movies) {
        this.movies = movies.stream()
                .collect(toMap(Movie::getTitle, identity()));
    }

    @Override
    public Optional<Movie> getByTitle(String title) {
        return Optional.ofNullable(movies.get(title));
    }

    @Override
    public Optional<Movie> save(Movie movie) {
        return Optional.ofNullable(movies.putIfAbsent(movie.getTitle(), movie));
    }

    @Override
    public Optional<Movie> update(Movie movieToSet) {
        return Optional.ofNullable(
                movies.computeIfPresent(movieToSet.getTitle(), (key, existingMovie) -> movieToSet));
    }

    @Override
    public Movie removeByTitle(String title) {
        return movies.remove(title);
    }

    public static MovieRepository initiallyPopulatedMovieRepository(String initialJsonDataSetPath) {
        return new SimpleMovieRepository(loadMovies(initialJsonDataSetPath));
    }

    private static Set<Movie> loadMovies(String pathToFile) {
        try {
            String content = IOUtils.toString(getResource(pathToFile), UTF_8.name());
            return jsonMapper().readValue(content, jsonMapper().getTypeFactory().constructCollectionType(Set.class, Movie.class));
        } catch (IOException e) {
            LOG.warn("Couldn't load initial data set from path:" + pathToFile);
            return emptySet();
        }
    }
}