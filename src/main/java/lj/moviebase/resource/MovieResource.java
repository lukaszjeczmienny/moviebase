package lj.moviebase.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lj.moviebase.domain.Actor;
import lj.moviebase.domain.Movie;
import lj.moviebase.repository.MovieRepository;
import lj.moviebase.resource.exception.EncodingException;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.time.Year;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static lj.moviebase.query.filter.MovieFilteringCriteria.filterBasedOn;

@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "WeakerAccess"})
@Path(MovieResource.VERSION)
@Produces(APPLICATION_JSON)
@Api(value = "Movie base versioned API: " + MovieResource.VERSION, produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
public class MovieResource {

    static final String VERSION = "/v1";

    private final static Logger LOG = LoggerFactory.getLogger(MovieResource.class);
    private static final String MOVIES_PATH = "/movies";
    private static final String MOVIES_TITLE_PATH = "/movies/{title}";

    private final MovieRepository movieRepository;

    public MovieResource(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GET
    @Path(MOVIES_TITLE_PATH)
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Get movie by title")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Movie not found ith title parameter"),
            @ApiResponse(code = 200, message = "Movie found with title parameter")})
    public Response getMovieByTitle(@PathParam("title") @NotBlank String title) {
        Optional<Movie> movie = movieRepository.getByTitle(decoded(title));
        return movie.map(this::movieFound)
                .orElseGet(this::notFound);
    }

    @GET
    @Path(MOVIES_PATH)
    @Produces(APPLICATION_JSON)
    public Response findAllMoviesBy(@QueryParam("releaseYear") Optional<Year> releaseYear,
                                    @QueryParam("duration") Optional<Integer> duration,
                                    @QueryParam("actor") Optional<Actor> actor) {
        Set<Movie> findResult = movieRepository.findAllBy(filterBasedOn(duration, releaseYear, actor));
        return findResult.isEmpty()
                ? notFound()
                : movieFound(findResult);
    }

    @POST
    @Path(MOVIES_PATH)
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Add new movie to base")
    @ApiResponses(value = {@ApiResponse(code = 409, message = "Movie already exist"),
            @ApiResponse(code = 200, message = "Movie created")})
    public Response create(@Valid Movie movie) {
        return movieRepository.save(movie)
                .map(this::movieAlreadyExists)
                .orElse(movieCreated(movie));
    }

    @DELETE
    @ApiOperation(value = "Remove movie by title")
    @Path(MOVIES_TITLE_PATH)
    public Response removeMovie(@PathParam("title") @NotBlank String title) {
        movieRepository.removeByTitle(decoded(title));
        return Response.ok().build();
    }

    @PUT
    @Path(MOVIES_PATH)
    @Consumes(APPLICATION_JSON)
    @ApiOperation(value = "Update movie by passing new definition")
    public Response updateMovie(@Valid Movie movie) {
        movieRepository.update(movie);
        return Response.ok().build();
    }

    private String decoded(String title) {
        try {
            return URLDecoder.decode(title, UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw logAndWrapIntoEncodingException(title, e);
        }
    }

    private Response notFound() {
        return Response.status(NOT_FOUND)
                .build();
    }

    private <M> Response movieFound(M movie) {
        return Response.ok()
                .entity(movie)
                .type(APPLICATION_JSON)
                .build();
    }

    private Response movieAlreadyExists(Movie movie) {
        return Response.status(CONFLICT).entity(movie).build();
    }

    private Response movieCreated(Movie movie) {
        return Response.created(createdMovieUri(movie)).build();
    }

    private URI createdMovieUri(Movie movie) {
        try {
            return URI.create(VERSION + MOVIES_PATH + "/" + encode(movie.getTitle(), UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            throw logAndWrapIntoEncodingException(movie.getTitle(), e);
        }
    }

    private EncodingException logAndWrapIntoEncodingException(String title, Exception e) {
        String msg = format("Error during encoding of movie title %s", title);
        LOG.error(msg, e);
        return new EncodingException(msg, e);
    }
}
