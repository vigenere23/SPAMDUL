package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user;

import ca.ulaval.glo4003.spamdul.entity.user.InvalidDayToAccessCampusException;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidBirthDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidGenderArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidDayOfCampusAccessArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidUserArgumentException;
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
    } else if (e instanceof InvalidDayOfCampusAccessArgumentException
        || e instanceof InvalidDayToAccessCampusException) {
      exceptionResponse.error = "INVALID_DAY_OF_CAMPUS_ACCESS";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
