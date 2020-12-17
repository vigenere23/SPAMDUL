package ca.ulaval.glo4003.spamdul.assemblers;

import ca.ulaval.glo4003.spamdul.api.ExceptionResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class SpamDULExceptionAssembler implements ExceptionMapper<SpamDULBaseException> {

  public Response toResponse(SpamDULBaseException e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.error = e.getError();
    exceptionResponse.description = e.getDescription();

    return Response.status(e.getStatus())
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
