package lj.moviebase.resource;

import lj.moviebase.domain.Movie;
import lj.moviebase.repository.MovieRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Optional;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path(MovieResource.VERSION)
@Produces(APPLICATION_JSON)
public class MovieResource {

    static final String VERSION = "/v1";
    private static final String MOVIES_PATH = "/movies";
    private static final String MOVIES_TITLE_PATH = "/movies/{title}";
    private final MovieRepository movieRepository;

    public MovieResource(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GET
    @Path(MOVIES_TITLE_PATH)
    @Produces(APPLICATION_JSON)
    public Response getMovieByTitle(@PathParam("title") String title) throws UnsupportedEncodingException {
        Optional<Movie> movie = movieRepository.getByTitle(decoded(title));
        return movie.map(this::movieFound)
                .orElseGet(this::notFound);
    }

    @POST
    @Path(MOVIES_PATH)
    @Consumes(APPLICATION_JSON)
    public Response create(Movie movie) throws UnsupportedEncodingException {
        movieRepository.save(movie);
        return Response.created(createdMovieUri(movie))
                .build();
    }

    @DELETE
    @Path(MOVIES_TITLE_PATH)
    public Response removeMovie(@PathParam("title") String title) throws UnsupportedEncodingException {
        movieRepository.removeByTitle(decoded(title));
        return Response.ok().build();
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

    private URI createdMovieUri(Movie movie) throws UnsupportedEncodingException {
        return URI.create(VERSION + MOVIES_PATH + "/" + encode(movie.getTitle(), UTF_8.name()));
    }

}
