package ca.ulaval.glo4003.spamdul.infrastructure.db.infractions;

public class CantFindInfractionException extends RuntimeException {

  public CantFindInfractionException(String message) {
    super(message);
  }
}
