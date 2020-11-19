package ca.ulaval.glo4003.spamdul.entity.timeperiod;

public class InvalidPeriodTypeException extends RuntimeException {

  public InvalidPeriodTypeException() {
    super("The provided period is not valid");
  }
}
