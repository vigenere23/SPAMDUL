package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.exceptions;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.SpamDULBaseException;
import javax.ws.rs.core.Response.Status;

public class InvalidCampusAccessArgumentException extends SpamDULBaseException {

  public InvalidCampusAccessArgumentException() {
    super();
  }

  public String getError() {
    return "INVALID_CAMPUS_ACCESS";
  }

  public String getDescription() {
    return "A campus access code or a license plate must be provided";
  }

  public int getStatus() {
    return Status.BAD_REQUEST.getStatusCode();
  }
}
