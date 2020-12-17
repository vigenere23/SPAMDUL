package ca.ulaval.glo4003.spamdul.authentication.entities;

import ca.ulaval.glo4003.spamdul.assemblers.SpamDULBaseException;
import javax.ws.rs.core.Response.Status;

public class UnauthorizedUserException extends SpamDULBaseException {

  @Override public String getError() {
    return "NOT_AUTHORIZED";
  }

  @Override public String getDescription() {
    return "Unauthorized user";
  }

  @Override public int getStatus() {
    return Status.FORBIDDEN.getStatusCode();
  }
}
