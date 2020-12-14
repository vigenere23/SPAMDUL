package ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions;

import javax.ws.rs.core.Response.Status;

public class RechargULCardNotFoundException extends RechargULException {

  public RechargULCardNotFoundException() {
    super();
  }

  public String getError() {
    return "RECHARGUL_CARD_NOT_FOUND";
  }

  public String getDescription() {
    return "This rechargUL card does not exist";
  }

  public int getStatus() {
    return Status.NOT_FOUND.getStatusCode();
  }
}
