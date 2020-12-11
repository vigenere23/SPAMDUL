package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.SpamDULBaseException;
import javax.ws.rs.core.Response.Status;

public abstract class InvalidInfractionException extends SpamDULBaseException {

  public int getStatus() {
    return Status.BAD_REQUEST.getStatusCode();
  }
}
