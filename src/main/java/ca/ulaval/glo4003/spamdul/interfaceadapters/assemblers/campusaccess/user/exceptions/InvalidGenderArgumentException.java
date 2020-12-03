package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user.exceptions;

public class InvalidGenderArgumentException extends InvalidUserArgumentException {

  public InvalidGenderArgumentException() {
    super("The gender provided must be of type male, female or other");
  }
}
