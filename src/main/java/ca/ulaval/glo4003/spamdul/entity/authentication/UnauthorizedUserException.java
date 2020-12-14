package ca.ulaval.glo4003.spamdul.entity.authentication;

import ca.ulaval.glo4003.spamdul.assemblers.SpamDULBaseException;
import javax.ws.rs.core.Response.Status;

public class UnauthorizedUserException extends SpamDULBaseException {

  public String getError() {
    return "NOT_AUTHORIZED"; }

  public String getDescription() {
    return "Unauthorized user";
  }

  public int getStatus() {
    return Status.FORBIDDEN.getStatusCode();
  }
}
