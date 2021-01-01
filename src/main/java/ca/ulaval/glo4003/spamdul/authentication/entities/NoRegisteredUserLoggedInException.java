package ca.ulaval.glo4003.spamdul.authentication.entities;

import ca.ulaval.glo4003.spamdul.assemblers.SpamDULBaseException;
import javax.ws.rs.core.Response.Status;

public class NoRegisteredUserLoggedInException extends SpamDULBaseException {

  @Override public String getError() {
    return "INVALID_USER";
  }

  @Override public String getDescription() {
    return "No registered user logged in with this token";
  }

  @Override public int getStatus() {
    return Status.UNAUTHORIZED.getStatusCode();
  }
}
