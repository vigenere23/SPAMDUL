package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions;

public class InvalidBirthDateException extends InvalidUserException {

  public InvalidBirthDateException() {
    super("The birthday date provided must be yyyy-MM-dd");
  }
}
