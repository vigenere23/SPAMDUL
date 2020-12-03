package ca.ulaval.glo4003.spamdul.entity.user.exceptions;

public class InvalidUserIdFormatException extends RuntimeException {

  public InvalidUserIdFormatException() {
    super("Invalid user id format");
  }
}
