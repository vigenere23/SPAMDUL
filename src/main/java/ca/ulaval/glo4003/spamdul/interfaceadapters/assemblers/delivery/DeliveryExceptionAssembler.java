package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions.InvalidDeliveryArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions.InvalidDeliveryModeException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions.InvalidEmailAddressException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.exceptions.InvalidPostalAddressException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class DeliveryExceptionAssembler implements ExceptionMapper<InvalidDeliveryArgumentException> {

  public Response toResponse(InvalidDeliveryArgumentException e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.description = e.getMessage();

    if (e instanceof InvalidDeliveryModeException) {
      exceptionResponse.error = "INVALID_DELIVERY_MODE";
    } else if (e instanceof InvalidEmailAddressException) {
      exceptionResponse.error = "INVALID_EMAIL_ADDRESS";
    } else if (e instanceof InvalidPostalAddressException) {
      exceptionResponse.error = "INVALID_POSTAL_ADDRESS";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
