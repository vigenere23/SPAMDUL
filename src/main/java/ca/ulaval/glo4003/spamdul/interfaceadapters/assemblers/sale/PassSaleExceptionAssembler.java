package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale;

import ca.ulaval.glo4003.spamdul.entity.pass.PassNotAcceptedByAccessException;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PassSaleExceptionAssembler implements ExceptionMapper<InvalidPassArgumentException> {

  @Override
  public Response toResponse(InvalidPassArgumentException e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.description = e.getMessage();

    if (e instanceof InvalidParkingZoneException) {
      exceptionResponse.error = "INVALID_PARKING_ZONE";
    } else if (e instanceof InvalidPassTypeException) {
      exceptionResponse.error = "INVALID_PASS_TYPE";
    } else if (e instanceof InvalidCampusAccessCodeException) {
      exceptionResponse.error = "INVALID_CAMPUS_ACCESS_CODE";
    } else if (e instanceof PassNotAcceptedByAccessException) {
      exceptionResponse.error = "NO_ACCESS";
    } else if (e instanceof InvalidPassDayOfWeekException) {
      exceptionResponse.error = "INVALID_DAY_OF_WEEK";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
