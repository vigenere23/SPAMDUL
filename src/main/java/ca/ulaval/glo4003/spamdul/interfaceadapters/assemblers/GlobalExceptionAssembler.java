package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;

import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class GlobalExceptionAssembler implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.description = e.getMessage();

        if (e instanceof NotSupportedException) {
            exceptionResponse.error = "NOT_IMPLEMENTED";
        } else {
            exceptionResponse.description = e.toString();
            exceptionResponse.error = e.getClass().toString();
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(exceptionResponse)
                .build();
    }
}
