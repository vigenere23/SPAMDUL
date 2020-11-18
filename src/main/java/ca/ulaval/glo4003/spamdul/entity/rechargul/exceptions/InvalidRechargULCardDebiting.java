package ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions;

public class InvalidRechargULCardDebiting extends RechargULException {

  public InvalidRechargULCardDebiting() {
    super("Invalid debiting amount for rechargUL card");
  }
}
