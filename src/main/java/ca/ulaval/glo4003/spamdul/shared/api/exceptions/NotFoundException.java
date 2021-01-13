package ca.ulaval.glo4003.spamdul.shared.api.exceptions;

import ca.ulaval.glo4003.spamdul.shared.usecases.exceptions.UseCaseException;
import javax.ws.rs.core.Response.Status;

public class NotFoundException extends ApiException {

  public NotFoundException(Exception exception) {
    super(exception);
  }

  public NotFoundException(UseCaseException exception) {
    super(exception);
  }

  public NotFoundException(String type, String name, String description) {
    super(type, name, description);
  }

  @Override
  public int getStatus() {
    return Status.NOT_FOUND.getStatusCode();
  }
}
