package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.InvalidDayToAccessCampusException;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.exceptions.InvalidCampusAccessArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.exceptions.InvalidDayOfCampusAccessArgumentException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CampusAccessExceptionAssembler implements ExceptionMapper<InvalidCampusAccessArgumentException> {

  @Override
  public Response toResponse(InvalidCampusAccessArgumentException e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.description = e.getMessage();

    if (e instanceof InvalidDayOfCampusAccessArgumentException || e instanceof InvalidDayToAccessCampusException) {
      exceptionResponse.error = "INVALID_DAY_TO_ACCESS_CAMPUS";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
