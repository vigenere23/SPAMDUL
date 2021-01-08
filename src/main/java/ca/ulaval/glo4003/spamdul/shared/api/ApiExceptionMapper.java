package ca.ulaval.glo4003.spamdul.shared.api;

import ca.ulaval.glo4003.spamdul.shared.api.exceptions.ApiException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ApiExceptionMapper implements ExceptionMapper<ApiException> {

  @Override
  public Response toResponse(ApiException e) {
    ErrorResponse response = new ErrorResponse();
    response.name = e.getName();
    response.code = e.getCode();
    response.description = e.getMessage();

    int status = e.getStatus();

    return Response.status(status).entity(response).build();
  }
}
