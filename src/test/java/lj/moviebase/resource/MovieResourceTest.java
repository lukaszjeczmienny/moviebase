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
import static lj.moviebase.resource.JsonUtils.jsonWriterFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MovieResourceTest {
    private static final String SOME_TITLE = "someFancyTitle";
    private static final String MOVIES_PATH = "/v1/movies/";

    private final MovieRepository movieRepository = mock(MovieRepository.class);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new MovieResource(movieRepository))
            .build();

    @Test
    public void shouldGetMovieByTitleIfExistsAndResponseWithStatusOkAndWithMovieJsonContent() throws IOException {
        Movie expectedMovie = givenMovieObject();
        given(movieRepository.getByTitle(SOME_TITLE)).willReturn(Optional.of(expectedMovie));

        Response response = resources.client()
                .target(MOVIES_PATH + SOME_TITLE)
                .request()
                .buildGet()
                .invoke();

        assertThat(response.getStatusInfo()).isEqualTo(Status.OK);
        assertThat(entityFrom(response)).isEqualTo(serialized(expectedMovie));
    }

    @Test
    public void shouldResponseWithStatusNotFoundIfMovieWithGivenTitleDoesNotExists() throws IOException {
        given(movieRepository.getByTitle(SOME_TITLE)).willReturn(Optional.empty());

        Response response = resources.client()
                .target(MOVIES_PATH + SOME_TITLE)
                .request()
                .buildGet()
                .invoke();

        assertThat(response.getStatusInfo()).isEqualTo(Status.NOT_FOUND);
    }

    private String serialized(Movie movie) throws IOException {
        return jsonWriterFor(Movie.class).writeValueAsString(movie);
    }

    private Movie givenMovieObject() {
        Character character = new Character(new Actor("first", "last"), "name");
        return new Movie(SOME_TITLE, 100, parse("2018"), newHashSet(character));
    }

    private String entityFrom(Response movieJson) throws IOException {
        return IOUtils.toString((ByteArrayInputStream) movieJson.getEntity(), UTF_8);
    }
}