package ca.ulaval.glo4003.spamdul.charging.entities.rechargul.exceptions;

public class InvalidRechargULCardDebitingException extends RechargULException {

  @Override public String getError() {
    return "INVALID_RECHARGUL_DEBITING";
  }

  @Override public String getDescription() {
    return "Invalid debiting amount for rechargUL card";
  }
}
