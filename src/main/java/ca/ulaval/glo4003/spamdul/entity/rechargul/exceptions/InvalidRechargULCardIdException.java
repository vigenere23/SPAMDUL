package ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions;

public class InvalidRechargULCardIdException extends RechargULException {

  public InvalidRechargULCardIdException() {
    super("This rechargUL card id is invalid");
  }
}
