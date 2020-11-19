package ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions;

public class RechargULCardAlreadyExistsException extends RechargULException {

  public RechargULCardAlreadyExistsException() {
    super("This rechargUL card already exists");
  }
}
