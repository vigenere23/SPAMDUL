package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions;

public class InvalidGenderException extends InvalidUserException {

  public InvalidGenderException() {
    super("The gender provided must be of type male, female or other");
  }
}
