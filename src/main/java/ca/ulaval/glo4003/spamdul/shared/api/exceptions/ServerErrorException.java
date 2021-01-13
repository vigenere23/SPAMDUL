package ca.ulaval.glo4003.spamdul.shared.api.exceptions;

import ca.ulaval.glo4003.spamdul.shared.usecases.exceptions.UseCaseException;
import javax.ws.rs.core.Response.Status;

public class ServerErrorException extends ApiException {

  public ServerErrorException(String type, String name, String description) {
    super(type, name, description);
  }

  public ServerErrorException(UseCaseException exception) {
    super(exception);
  }

  public ServerErrorException(Exception exception) {
    super(exception);
  }

  @Override
  public int getStatus() {
    return Status.INTERNAL_SERVER_ERROR.getStatusCode();
  }
}
