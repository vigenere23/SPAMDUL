package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport;

import ca.ulaval.glo4003.spamdul.ui.usagereport.dto.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidParkingZoneArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidUsageReportArgumentException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UsageReportExceptionAssembler implements ExceptionMapper<InvalidUsageReportArgumentException> {

  @Override
  public Response toResponse(InvalidUsageReportArgumentException e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.description = e.getMessage();

    if (e instanceof InvalidDateArgumentException) {
      exceptionResponse.error = "INVALID_DATE";
    } else if (e instanceof InvalidParkingZoneArgumentException) {
      exceptionResponse.error = "INVALID_PARKING_ZONE";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
