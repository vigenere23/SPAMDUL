package ca.ulaval.glo4003.spamdul.shared.entities.amount;

import ca.ulaval.glo4003.spamdul.assemblers.SpamDULBaseException;
import javax.ws.rs.core.Response.Status;

public class InvalidAmountException extends SpamDULBaseException {

  @Override public String getError() {
    return "INVALID_AMOUNT";
  }

  @Override public String getDescription() {
    return "Invalid amount";
  }

  @Override public int getStatus() {
    return Status.BAD_REQUEST.getStatusCode();
  }
}
