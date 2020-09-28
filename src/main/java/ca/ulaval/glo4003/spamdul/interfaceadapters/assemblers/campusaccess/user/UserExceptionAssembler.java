package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user.exceptions.InvalidBirthDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user.exceptions.InvalidGenderArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user.exceptions.InvalidUserArgumentException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserExceptionAssembler implements ExceptionMapper<InvalidUserArgumentException> {

  @Override
  public Response toResponse(InvalidUserArgumentException e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.description = e.getMessage();

    if (e instanceof InvalidGenderArgumentException) {
      exceptionResponse.error = "INVALID_GENDER";
    } else if (e instanceof InvalidBirthDateArgumentException) {
      exceptionResponse.error = "INVALID_BIRTHDAY_DATE";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
