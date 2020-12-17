package ca.ulaval.glo4003.spamdul.time.entities.timeperiod.exception;

public class InvalidSeasonException extends RuntimeException {

  public InvalidSeasonException() {
    super("Invalid season, must be: AUTUMN, WINTER or SUMMER");
  }
}
