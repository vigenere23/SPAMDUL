package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions;

public class InvalidDayOfWeekArgumentException extends InvalidTimePeriodException{

  public InvalidDayOfWeekArgumentException() {
    super("Day of the week must be from monday to friday");
  }
}
