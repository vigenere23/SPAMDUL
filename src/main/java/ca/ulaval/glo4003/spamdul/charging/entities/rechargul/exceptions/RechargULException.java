package ca.ulaval.glo4003.spamdul.charging.entities.rechargul.exceptions;

import ca.ulaval.glo4003.spamdul.assemblers.SpamDULBaseException;
import javax.ws.rs.core.Response.Status;

public abstract class RechargULException extends SpamDULBaseException {

  @Override public int getStatus() {
    return Status.BAD_REQUEST.getStatusCode();
  }
}
