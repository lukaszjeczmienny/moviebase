package lj.moviebase.repository;

import lj.moviebase.domain.Movie;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static com.google.common.io.Resources.getResource;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.emptySet;
import static lj.moviebase.resource.JsonUtils.jsonMapper;

public class SimpleMovieRepository implements MovieRepository {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleMovieRepository.class);
    private final Set<Movie> movies;

    public SimpleMovieRepository(Set<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public Optional<Movie> getByTitle(String title) {
        return movies.stream()
                .filter(movie -> movie.getTitle().equals(title))
                .findFirst();
    }

    @Override
    public void save(Movie movie) {
        movies.add(movie);
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