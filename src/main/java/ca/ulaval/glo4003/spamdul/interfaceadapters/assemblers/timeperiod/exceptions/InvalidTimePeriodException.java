package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions;

public abstract class InvalidTimePeriodException extends RuntimeException {

  protected InvalidTimePeriodException(String message) {
    super(message);
  }
}
