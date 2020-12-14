package ca.ulaval.glo4003.spamdul.infrastructure.db.authentication.exception;

import ca.ulaval.glo4003.spamdul.assemblers.SpamDULBaseException;
import javax.ws.rs.core.Response.Status;

public class InvalidCredentialsException extends SpamDULBaseException {

  public String getError() {
    return "INVALID_CREDENTIALS";
  }

  public String getDescription() {
    return "Invalid credentials provided";
  }

  public int getStatus() {
    return Status.UNAUTHORIZED.getStatusCode();
  }
}
