package ca.ulaval.glo4003.spamdul.entity.infractions.exceptions;

public class InvalidInfractionIdException extends RuntimeException {

  public InvalidInfractionIdException() {
    super("invalid infraction id format");
  }
}
