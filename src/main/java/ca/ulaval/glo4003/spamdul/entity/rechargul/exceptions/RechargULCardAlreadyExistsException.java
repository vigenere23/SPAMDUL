package ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions;

public class RechargULCardAlreadyExistsException extends RechargULException {

  public String getError() {
    return "RECHARGUL_CARD_ALREADY_EXISTS";
  }

  public String getDescription() {
    return "This rechargUL card already exists";
  }
}
