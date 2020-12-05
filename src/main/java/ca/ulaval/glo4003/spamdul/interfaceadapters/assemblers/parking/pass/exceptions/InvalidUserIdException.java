package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.pass.exceptions;

public class InvalidUserIdException extends InvalidPassArgumentException {

  public InvalidUserIdException() {
    super("Invalid user id format");
  }
}
