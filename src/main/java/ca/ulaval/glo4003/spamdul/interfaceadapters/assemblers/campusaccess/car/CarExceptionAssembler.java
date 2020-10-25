package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car;

import ca.ulaval.glo4003.spamdul.entity.car.InvalidCarYearException;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.exceptions.InvalidCarArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.exceptions.InvalidCarTypeArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.exceptions.InvalidCarYearArgumentException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CarExceptionAssembler implements ExceptionMapper<InvalidCarArgumentException> {

  @Override
  public Response toResponse(InvalidCarArgumentException e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.description = e.getMessage();

    if (e instanceof InvalidCarTypeArgumentException) {
      exceptionResponse.error = "INVALID_CAR_TYPE";

    } else if (e instanceof InvalidCarYearArgumentException || e instanceof InvalidCarYearException) {
      exceptionResponse.error = "INVALID_CAR_YEAR";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
