package ca.ulaval.glo4003.spamdul.usecases.infraction;

public class UnauthorizedUserException extends RuntimeException {

  public UnauthorizedUserException() {
    super("Unauthorized user");
  }

}
