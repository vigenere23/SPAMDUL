package ca.ulaval.glo4003.spamdul.entity.infractions.exceptions;

public class PassRepositoryNotSetException extends RuntimeException {

  public PassRepositoryNotSetException() {
    super("This validator requires pass repository to be set before usage");
  }
}
