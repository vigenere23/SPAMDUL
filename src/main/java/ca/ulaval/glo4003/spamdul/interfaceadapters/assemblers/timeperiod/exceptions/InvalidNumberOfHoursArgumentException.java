package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions;

public class InvalidNumberOfHoursArgumentException extends InvalidTimePeriodException{

  public InvalidNumberOfHoursArgumentException() {
    super("Number of hours must be between 1 and 23");
  }
}
