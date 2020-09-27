package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidDeliveryModeException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidSaleArgumentException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class SaleExceptionAssembler implements ExceptionMapper<InvalidSaleArgumentException> {

  public Response toResponse(InvalidSaleArgumentException e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.description = e.getMessage();

    if (e instanceof InvalidDeliveryModeException) {
      exceptionResponse.error = "INVALID_DELIVERY_MODE";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
