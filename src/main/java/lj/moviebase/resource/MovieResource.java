package lj.moviebase.resource;

import lj.moviebase.domain.Movie;
import lj.moviebase.repository.MovieRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/v1")
@Produces(APPLICATION_JSON)
public class MovieResource {

    private final MovieRepository movieRepository;

    public MovieResource(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GET
    @Path("/movies/{title}")
    @Produces(APPLICATION_JSON)
    public Response getMovieByTitle(@PathParam("title") String title) throws UnsupportedEncodingException {
        Optional<Movie> movie = movieRepository.getByTitle(decoded(title));
        return movie.map(this::movieFound)
                .orElseGet(this::notFound);
    }

    private String decoded(String title) throws UnsupportedEncodingException {
        return URLDecoder.decode(title, UTF_8.name());
    }

    private Response notFound() {
        return Response.status(NOT_FOUND)
                .build();
    }

    private Response movieFound(Movie movie) {
        return Response.ok()
                .entity(movie)
                .type(APPLICATION_JSON)
                .build();
    }
}
