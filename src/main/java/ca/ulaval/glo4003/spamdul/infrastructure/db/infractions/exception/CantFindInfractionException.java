package ca.ulaval.glo4003.spamdul.infrastructure.db.infractions.exception;

public class CantFindInfractionException extends RuntimeException {

  public CantFindInfractionException() {
    super("The provided infraction code does not correspond to any infraction");
  }
}
