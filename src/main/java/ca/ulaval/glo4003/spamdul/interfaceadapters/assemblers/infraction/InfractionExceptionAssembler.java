package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction;

import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.AlreadyPaidInfractionException;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionIdFormatException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionParkingZoneException;
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

    } else if (e instanceof InvalidInfractionIdFormatException) {
      exceptionResponse.error = "INVALID_INFRACTION_ID_FORMAT";

    } else if (e instanceof AlreadyPaidInfractionException) {
      exceptionResponse.error = "ALREADY_PAID_INFRACTION";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
