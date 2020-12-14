package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions;

public class InvalidBirthDateException extends InvalidUserException {

  public String getError() {
    return "INVALID_BIRTHDAY_DATE";
  }

  public String getDescription() {
    return "The birthday date provided must be yyyy-MM-dd";
  }
}
