package ca.ulaval.glo4003.projet.base.ws.api.user;

import ca.ulaval.glo4003.projet.base.ws.api.user.dto.UserExceptionDto;
import ca.ulaval.glo4003.projet.base.ws.domain.user.InvalidArgumentException;
import ca.ulaval.glo4003.projet.base.ws.domain.user.InvalidBirthdayDateArgumentException;
import ca.ulaval.glo4003.projet.base.ws.domain.user.InvalidDayOfCampusAccessArgumentException;
import ca.ulaval.glo4003.projet.base.ws.domain.user.InvalidGenderArgumentException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserExceptionAssembler implements ExceptionMapper<InvalidArgumentException> {

  @Override
  public Response toResponse(InvalidArgumentException e) {
    UserExceptionDto userExceptionDto = new UserExceptionDto();
    userExceptionDto.description = e.getMessage();

    if (e instanceof InvalidGenderArgumentException) {
      userExceptionDto.error = "INVALID_GENDER";
    } else if (e instanceof InvalidBirthdayDateArgumentException) {
      userExceptionDto.error = "INVALID_BIRTHDAY_DATE";
    } else if (e instanceof InvalidDayOfCampusAccessArgumentException) {
      userExceptionDto.error = "INVALID_DAY_OF_CAMPUS_ACCESS";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(userExceptionDto)
                   .build();
  }
}
