package ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions;

public class InvalidRechargULCardDebitingException extends RechargULException {

  public String getError() {
    return "INVALID_RECHARGUL_DEBITING";
  }

  public String getDescription() {
    return "Invalid debiting amount for rechargUL card";
  }
}
