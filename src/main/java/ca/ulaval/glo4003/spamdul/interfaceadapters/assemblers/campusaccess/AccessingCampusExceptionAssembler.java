package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.ExceptionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.exceptions.InvalidAccessingCampusArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.exceptions.InvalidAccessingCampusDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.exceptions.InvalidCampusAccessCodeArgumentException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class AccessingCampusExceptionAssembler implements ExceptionMapper<InvalidAccessingCampusArgumentException> {

  public Response toResponse(InvalidAccessingCampusArgumentException e) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.description = e.getMessage();

    if (e instanceof InvalidAccessingCampusDateArgumentException) {
      exceptionResponse.error = "INVALID_ACCESSING_CAMPUS_DATE_FORMAT";
    } else if (e instanceof InvalidCampusAccessCodeArgumentException) {
      exceptionResponse.error = "INVALID_CAMPUS_ACCESS_CODE_FORMAT";
    }

    return Response.status(Status.BAD_REQUEST)
                   .type(MediaType.APPLICATION_JSON)
                   .entity(exceptionResponse)
                   .build();
  }
}
