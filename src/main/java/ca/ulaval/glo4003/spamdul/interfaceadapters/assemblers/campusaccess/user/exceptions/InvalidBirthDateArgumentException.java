package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user.exceptions;

public class InvalidBirthDateArgumentException extends InvalidUserArgumentException {

  public InvalidBirthDateArgumentException() {
    super("The birthday date provided must be yyyy-MM-dd");
  }
}
