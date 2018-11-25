package lj.moviebase.resource.exception.mapper;

import lj.moviebase.resource.exception.EncodingException;
import org.junit.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.io.UnsupportedEncodingException;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;


public class EncodingExceptionMapperTest {

    private static final String SOME_EXCEPTION_MESSAGE = "someMessage";

    private final ExceptionMapper<EncodingException> exceptionMapper = new EncodingExceptionMapper();

    @Test
    public void shouldReturnResponseWithStatusBadRequestAndWithMessageFromException() {
        Response response = exceptionMapper.toResponse(new EncodingException(SOME_EXCEPTION_MESSAGE, new UnsupportedEncodingException()));

        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.getStatusCode());
        assertThat(response.getEntity()).isEqualTo(SOME_EXCEPTION_MESSAGE);
    }
}
