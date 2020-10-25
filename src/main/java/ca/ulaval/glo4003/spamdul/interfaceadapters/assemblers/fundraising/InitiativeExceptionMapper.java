package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising;

import ca.ulaval.glo4003.spamdul.entity.account.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeException;
import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeName;
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

    if (e instanceof InvalidInitiativeName) {
      response.error = "INVALID_INITIATIVE_NAME";

    } else if (e instanceof InvalidInitiativeAmount) {
      response.error = "INVALID_INITIATIVE_AMOUNT";

    } else if (e instanceof InsufficientFundsException) {
      response.error = "INSUFFICIENT_FUNDS";

    } else {
      response.error = "INVALID_INITIATIVE";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(response)
                   .build();
  }
}
