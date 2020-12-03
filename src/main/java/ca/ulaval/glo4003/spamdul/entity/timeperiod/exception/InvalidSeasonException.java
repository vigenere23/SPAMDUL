package ca.ulaval.glo4003.spamdul.entity.timeperiod.exception;

public class InvalidSeasonException extends RuntimeException{

  public InvalidSeasonException() {
    super("Invalid season, must be: AUTUMN, WINTER or SUMMER");
  }
}
