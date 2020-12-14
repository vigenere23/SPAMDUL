package ca.ulaval.glo4003.spamdul.assemblers.user.exceptions;

public class InvalidGenderException extends InvalidUserException {

  public String getError() {
    return "INVALID_GENDER";
  }

  public String getDescription() {
    return "The gender provided must be of type male, female or other";
  }
}
