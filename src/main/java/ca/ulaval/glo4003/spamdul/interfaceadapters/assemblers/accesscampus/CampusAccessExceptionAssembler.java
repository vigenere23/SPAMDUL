package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.accesscampus.dto.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.campusaccessexceptions.InvalidCampusAccessArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.campusaccessexceptions.InvalidDayOfCampusAccessArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.campusaccessexceptions.InvalidPeriodArgumentException;
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

    if (e instanceof InvalidDayOfCampusAccessArgumentException) {
      exceptionResponse.error = "INVALID_DAY_TO_ACCESS_CAMPUS";
    } else if (e instanceof InvalidPeriodArgumentException) {
      exceptionResponse.error = "INVALID_PERIOD";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
