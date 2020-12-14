package ca.ulaval.glo4003.spamdul.entity.authentication;

import ca.ulaval.glo4003.spamdul.assemblers.SpamDULBaseException;
import javax.ws.rs.core.Response.Status;

public class NoRegisteredUserLoggedInException extends SpamDULBaseException {

  public String getError() {
    return "INVALID_USER";
  }

  public String getDescription() {
    return "No registered user logged in with this token";
  }

  public int getStatus() {
    return Status.UNAUTHORIZED.getStatusCode();
  }
}
