package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.exceptions.InvalidParkingZoneException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.exceptions.InvalidPassArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.exceptions.InvalidPassTypeException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.exceptions.InvalidUserIdException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PassExceptionAssembler implements ExceptionMapper<InvalidPassArgumentException> {

  @Override
  public Response toResponse(InvalidPassArgumentException e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.description = e.getMessage();

    if (e instanceof InvalidParkingZoneException) {
      exceptionResponse.error = "INVALID_PARKING_ZONE";
    } else if (e instanceof InvalidPassTypeException) {
      exceptionResponse.error = "INVALID_PASS_TYPE";
    } else if (e instanceof InvalidUserIdException) {
      exceptionResponse.error = "INVALID_USER_ID";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
