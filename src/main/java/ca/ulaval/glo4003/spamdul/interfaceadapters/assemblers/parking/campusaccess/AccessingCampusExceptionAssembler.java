package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions.PassNotFoundException;
import ca.ulaval.glo4003.spamdul.ui.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.exceptions.InvalidAccessingCampusArgumentException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class AccessingCampusExceptionAssembler implements ExceptionMapper<InvalidAccessingCampusArgumentException> {

  @Override public Response toResponse(InvalidAccessingCampusArgumentException e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.description = e.getMessage();

    if (e instanceof PassNotFoundException) {
      exceptionResponse.error = "PASS_NOT_FOUND";
    } else {
      exceptionResponse.error = "INVALID_ARGUMENT_TO_ACCESS_CAMPUS";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
