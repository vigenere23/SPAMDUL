package ca.ulaval.glo4003.spamdul.authentication.infrastructure.persistence.registereduser.exception;

import ca.ulaval.glo4003.spamdul.assemblers.SpamDULBaseException;
import javax.ws.rs.core.Response.Status;

public class InvalidCredentialsException extends SpamDULBaseException {

  @Override public String getError() {
    return "INVALID_CREDENTIALS";
  }

  @Override public String getDescription() {
    return "Invalid credentials provided";
  }

  @Override public int getStatus() {
    return Status.UNAUTHORIZED.getStatusCode();
  }
}
