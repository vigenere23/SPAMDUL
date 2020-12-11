package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.ui.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.exceptions.InvalidCampusAccessArgumentException;
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

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
