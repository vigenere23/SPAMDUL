package ca.ulaval.glo4003.projet.base.ws.interfaceadapters.assemblers.user;

import ca.ulaval.glo4003.projet.base.ws.entity.user.InvalidDayToAccessCampusException;
import ca.ulaval.glo4003.projet.base.ws.infrastructure.ui.user.dto.UserExceptionResponse;
import ca.ulaval.glo4003.projet.base.ws.interfaceadapters.assemblers.user.exceptions.InvalidBirthDateArgumentException;
import ca.ulaval.glo4003.projet.base.ws.interfaceadapters.assemblers.user.exceptions.InvalidDayOfCampusAccessArgumentException;
import ca.ulaval.glo4003.projet.base.ws.interfaceadapters.assemblers.user.exceptions.InvalidGenderArgumentException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserExceptionAssembler implements ExceptionMapper<RuntimeException> {

  @Override
  public Response toResponse(RuntimeException e) {
    UserExceptionResponse userExceptionResponse = new UserExceptionResponse();
    userExceptionResponse.description = e.getMessage();

    if (e instanceof InvalidGenderArgumentException) {
      userExceptionResponse.error = "INVALID_GENDER";
    } else if (e instanceof InvalidBirthDateArgumentException) {
      userExceptionResponse.error = "INVALID_BIRTHDAY_DATE";
    } else if (e instanceof InvalidDayOfCampusAccessArgumentException
        || e instanceof InvalidDayToAccessCampusException) {
      userExceptionResponse.error = "INVALID_DAY_OF_CAMPUS_ACCESS";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(userExceptionResponse)
                   .build();
  }
}
