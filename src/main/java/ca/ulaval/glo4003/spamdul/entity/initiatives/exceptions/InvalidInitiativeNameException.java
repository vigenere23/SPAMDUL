package ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions;

public class InvalidInitiativeNameException extends InvalidInitiativeException {

  public String getError() {
    return "INVALID_INITIATIVE_NAME";
  }

  public String getDescription() {
    return "Invalid initiative name";
  }
}
