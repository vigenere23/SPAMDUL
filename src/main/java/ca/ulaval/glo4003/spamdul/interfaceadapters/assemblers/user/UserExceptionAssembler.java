package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user;

import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidBirthDateException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidGenderException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidUserException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserExceptionAssembler implements ExceptionMapper<InvalidUserException> {

  @Override
  public Response toResponse(InvalidUserException e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.description = e.getMessage();

    if (e instanceof InvalidGenderException) {
      exceptionResponse.error = "INVALID_GENDER";
    } else if (e instanceof InvalidBirthDateException) {
      exceptionResponse.error = "INVALID_BIRTHDAY_DATE";
    } else if (e instanceof UserNotFoundException) {
      exceptionResponse.error = "INVALID_USER_ID";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
