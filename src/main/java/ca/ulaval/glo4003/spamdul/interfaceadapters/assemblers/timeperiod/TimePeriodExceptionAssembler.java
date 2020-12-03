package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidDayOfWeekArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidNumberOfHoursArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidTimePeriodArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidSemesterArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions.InvalidTimePeriodException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class TimePeriodExceptionAssembler implements ExceptionMapper<InvalidTimePeriodException> {

  public Response toResponse(InvalidTimePeriodException e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.description = e.getMessage();

    if (e instanceof InvalidSemesterArgumentException) {
      exceptionResponse.error = "INVALID_SEMESTER_FORMAT";
    } else if (e instanceof InvalidTimePeriodArgumentException) {
      exceptionResponse.error = "INVALID_PERIOD_FORMAT";
    } else if (e instanceof InvalidDayOfWeekArgumentException) {
      exceptionResponse.error = "INVALID_DAY_OF_WEEK_FORMAT";
    }else if (e instanceof InvalidNumberOfHoursArgumentException) {
      exceptionResponse.error = "INVALID_NUMBER_OF_HOURS_FORMAT";
    }
    return Response.status(Response.Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
