package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising;

import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeAmountException;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeCodeException;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeException;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeNameException;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class InitiativeExceptionMapper implements ExceptionMapper<InvalidInitiativeException> {

  @Override
  public Response toResponse(InvalidInitiativeException e) {
    ExceptionResponse response = new ExceptionResponse();
    response.description = e.getMessage();

    if (e instanceof InvalidInitiativeNameException) {
      response.error = "INVALID_INITIATIVE_NAME";

    } else if (e instanceof InvalidInitiativeAmountException) {
      response.error = "INVALID_INITIATIVE_AMOUNT";

    } else if (e instanceof InvalidInitiativeCodeException) {
      response.error = "INVALID_INITIATIVE_CODE";

    } else {
      response.error = "INVALID_INITIATIVE";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(response)
                   .build();
  }
}
