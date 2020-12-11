package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.authentication;

import ca.ulaval.glo4003.spamdul.entity.authentication.NoRegisteredUserLoggedInException;
import ca.ulaval.glo4003.spamdul.infrastructure.db.authentication.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.entity.authentication.UnauthorizedUserException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class AuthenticationExceptionAssembler implements ExceptionMapper<AuthenticationException> {

  public Response toResponse(AuthenticationException e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.description = e.getMessage();
    Status status;

    if (e instanceof NoRegisteredUserLoggedInException) {
      exceptionResponse.error = "INVALID_USER";
      status = Status.UNAUTHORIZED;
    } else if (e instanceof UnauthorizedUserException) {
      exceptionResponse.error = "NOT_AUTHORIZED";
      status = Status.FORBIDDEN;
    } else if (e instanceof InvalidCredentialsException) {
      exceptionResponse.error = "INVALID_CREDENTIALS";
      status = Status.UNAUTHORIZED;
    } else {
      status = Status.UNAUTHORIZED;
    }

    return Response.status(status)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
