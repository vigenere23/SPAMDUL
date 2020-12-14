package ca.ulaval.glo4003.spamdul.shared.amount;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.SpamDULBaseException;
import javax.ws.rs.core.Response.Status;

public class InvalidAmountException extends SpamDULBaseException {

  public String getError() {
    return "INVALID_AMOUNT";
  }

  public String getDescription() {
    return "Invalid amount";
  }

  public int getStatus() {
    return Status.BAD_REQUEST.getStatusCode();
  }
}
