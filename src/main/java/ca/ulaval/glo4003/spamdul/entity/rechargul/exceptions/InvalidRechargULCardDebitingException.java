package ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions;

public class InvalidRechargULCardDebitingException extends RechargULException {

  public InvalidRechargULCardDebitingException() {
    super("Invalid debiting amount for rechargUL card");
  }
}
