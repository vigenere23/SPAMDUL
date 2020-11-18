package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging;

import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.InvalidRechargULCardCredits;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.InvalidRechargULCardIdException;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.NotEnoughCreditsException;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.RechargULCardAlreadyExistsException;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.RechargULCardNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.RechargULException;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class RechargULExceptionMapper implements ExceptionMapper<RechargULException> {

  @Override public Response toResponse(RechargULException e) {
    ExceptionResponse response = new ExceptionResponse();
    response.description = e.getMessage();
    Status status = Status.BAD_REQUEST;

    if (e instanceof InvalidRechargULCardIdException) {
      response.error = "INVALID_RECHARGUL_CARD_ID";
    } else if (e instanceof NotEnoughCreditsException) {
      response.error = "NOT_ENOUGH_CREDITS";
    } else if (e instanceof RechargULCardAlreadyExistsException) {
      response.error = "RECHARGUL_CARD_ALREADY_EXISTS";
    } else if (e instanceof RechargULCardNotFoundException) {
      response.error = "RECHARGUL_CARD_NOT_FOUND";
      status = Status.NOT_FOUND;
    } else if (e instanceof InvalidRechargULCardCredits) {
      response.error = "INVALID_RECHARGUL_CREDITS";
    } else {
      response.error = "INVALID_RECHARGUL_OPERATION";
    }

    return Response.status(status).type(MediaType.APPLICATION_JSON).entity(response).build();
  }
}
