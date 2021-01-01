package ca.ulaval.glo4003.spamdul.charging.entities.rechargul.exceptions;

import javax.ws.rs.core.Response.Status;

public class RechargULCardNotFoundException extends RechargULException {

  public RechargULCardNotFoundException() {
    super();
  }

  @Override public String getError() {
    return "RECHARGUL_CARD_NOT_FOUND";
  }

  @Override public String getDescription() {
    return "This rechargUL card does not exist";
  }

  @Override public int getStatus() {
    return Status.NOT_FOUND.getStatusCode();
  }
}
