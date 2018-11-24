package lj.moviebase.resource;

import io.dropwizard.testing.junit.ResourceTestRule;
import lj.moviebase.domain.Actor;
import lj.moviebase.domain.Character;
import lj.moviebase.domain.Movie;
import lj.moviebase.repository.MovieRepository;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static com.google.common.collect.Sets.newHashSet;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.Year.parse;
import static javax.ws.rs.client.Entity.json;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static lj.moviebase.resource.JsonUtils.jsonWriterFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;

public class MovieResourceTest {
    private static final String SOME_TITLE = "someFancyTitle";
    private static final String VERSIONED_MOVIES_PATH = "/v1/movies/";

    private final MovieRepository movieRepository = mock(MovieRepository.class);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new MovieResource(movieRepository))
            .build();

    @Test
    public void shouldGetMovieByTitleIfExistsAndResponseWithStatusOkAndWithMovieJsonContent() throws IOException {
        Movie expectedMovie = givenMovieObject(SOME_TITLE);
        given(movieRepository.getByTitle(SOME_TITLE)).willReturn(Optional.of(expectedMovie));

        Response response = resources.client()
                .target(VERSIONED_MOVIES_PATH + SOME_TITLE)
                .request()
                .buildGet()
                .invoke();

        assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
        assertThat(entityFrom(response)).isEqualTo(serialized(expectedMovie));
    }

    @Test
    public void shouldResponseWithStatusNotFoundIfMovieWithGivenTitleDoesNotExists() {
        given(movieRepository.getByTitle(SOME_TITLE)).willReturn(Optional.empty());

        Response response = resources.client()
                .target(VERSIONED_MOVIES_PATH + SOME_TITLE)
                .request()
                .buildGet()
                .invoke();

        assertThat(response.getStatusInfo()).isEqualTo(Status.NOT_FOUND);
    }

    @Test
    public void shouldCreateMoviePostedAsJson() throws IOException {
        Movie movie = givenMovieObject(SOME_TITLE);

        Response response = resources.client()
                .target(VERSIONED_MOVIES_PATH)
                .request()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .buildPost(json(serialized(movie)))
                .invoke();

        then(movieRepository).should(only()).save(movie);
        assertThat(response.getStatusInfo()).isEqualTo(Status.CREATED);
        assertThat(response.getLocation().getPath()).isEqualTo(VERSIONED_MOVIES_PATH + SOME_TITLE);
    }

    private String serialized(Movie movie) throws IOException {
        return jsonWriterFor(Movie.class).writeValueAsString(movie);
    }

    private Movie givenMovieObject(String title) {
        Character character = new Character(new Actor("first", "last"), "name");
        return new Movie(title, 100, parse("2018"), newHashSet(character));
    }

    private String entityFrom(Response movieJson) throws IOException {
        return IOUtils.toString((ByteArrayInputStream) movieJson.getEntity(), UTF_8);
    }
}