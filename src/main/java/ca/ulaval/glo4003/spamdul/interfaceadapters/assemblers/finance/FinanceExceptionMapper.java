package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.finance;

import ca.ulaval.glo4003.spamdul.entity.finance.exceptions.FinanceException;
import ca.ulaval.glo4003.spamdul.entity.finance.exceptions.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.ui.ExceptionResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class FinanceExceptionMapper implements ExceptionMapper<FinanceException> {

  @Override
  public Response toResponse(FinanceException e) {
    ExceptionResponse response = new ExceptionResponse();
    response.description = e.getMessage();

    if (e instanceof InsufficientFundsException) {
      response.error = "INSUFFICIENT_FUNDS_EXCEPTION";
    } else {
      response.error = "FINANCE_EXCEPTION";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(response)
                   .build();
  }
}
