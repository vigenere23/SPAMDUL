package ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.exceptions;

public abstract class TimePeriodException extends RuntimeException {

  protected TimePeriodException(String message) {
    super(message);
  }
}
