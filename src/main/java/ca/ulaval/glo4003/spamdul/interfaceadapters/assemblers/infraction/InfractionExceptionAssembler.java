package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionParkingZoneException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionPassCodeFormatException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionTimeOfTheDayException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class InfractionExceptionAssembler implements ExceptionMapper<InvalidInfractionException> {

  public Response toResponse(InvalidInfractionException e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.description = e.getMessage();

    if (e instanceof InvalidInfractionParkingZoneException) {
      exceptionResponse.error = "INVALID_PARKING_ZONE";
    } else if (e instanceof InvalidInfractionPassCodeFormatException) {
      exceptionResponse.error = "INVALID_PASS_CODE_FORMAT";
    } else if (e instanceof InvalidInfractionTimeOfTheDayException) {
      exceptionResponse.error = "INVALID_TIME_OF_DAY";
    }
    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
