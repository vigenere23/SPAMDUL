package ca.ulaval.glo4003.spamdul.shared.api.exceptions;

import ca.ulaval.glo4003.spamdul.shared.usecases.exceptions.UseCaseException;
import javax.ws.rs.core.Response.Status;

public class UserErrorException extends ApiException {

  public UserErrorException(String type, String name, String description) {
    super(type, name, description);
  }

  public UserErrorException(UseCaseException exception) {
    super(exception);
  }

  public UserErrorException(Exception exception) {
    super(exception);
  }

  @Override
  public int getStatus() {
    return Status.BAD_REQUEST.getStatusCode();
  }
}
