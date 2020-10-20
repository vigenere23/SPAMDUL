package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidPeriodArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidSemesterException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidTimePeriodException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class TimePeriodExceptionAssembler implements ExceptionMapper<InvalidTimePeriodException> {

  public Response toResponse(InvalidTimePeriodException e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.description = e.getMessage();

    if (e instanceof InvalidSemesterException) {
      exceptionResponse.error = "INVALID_SEMESTER_FORMAT";
    } else if (e instanceof InvalidPeriodArgumentException) {
      exceptionResponse.error = "INVALID_PERIOD_FORMAT";
    }

    return Response.status(Response.Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
