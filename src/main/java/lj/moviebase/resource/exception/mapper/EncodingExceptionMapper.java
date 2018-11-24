package lj.moviebase.resource.exception.mapper;

import lj.moviebase.resource.exception.EncodingException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN_TYPE;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public class EncodingExceptionMapper implements ExceptionMapper<EncodingException> {
    @Override
    public Response toResponse(EncodingException exception) {
        return Response
                .status(BAD_REQUEST)
                .entity(exception.getMessage())
                .type(TEXT_PLAIN_TYPE)
                .build();
    }
}
