package ca.ulaval.glo4003.spamdul.shared.api.exceptions;

import ca.ulaval.glo4003.spamdul.shared.usecases.exceptions.UseCaseException;
import javax.ws.rs.core.Response.Status;

public class UnauthorizedException extends ApiException {

  public UnauthorizedException(String type, String name, String description) {
    super(type, name, description);
  }

  public UnauthorizedException(UseCaseException exception) {
    super(exception);
  }

  public UnauthorizedException(Exception exception) {
    super(exception);
  }

  @Override
  public int getStatus() {
    return Status.UNAUTHORIZED.getStatusCode();
  }
}
