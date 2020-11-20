package ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions;

public class RechargULCardNotFoundException extends RechargULException {

  public RechargULCardNotFoundException() {
    super("This rechargUL card does not exist");
  }
}
