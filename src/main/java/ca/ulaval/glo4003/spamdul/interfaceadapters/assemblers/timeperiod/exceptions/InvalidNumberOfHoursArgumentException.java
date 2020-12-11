package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions;

public class InvalidNumberOfHoursArgumentException extends InvalidTimePeriodException{

  public String getError() {
    return "INVALID_NUMBER_OF_HOURS_FORMAT";
  }

  public String getDescription() {
    return "Number of hours must be between 1 and 23";
  }
}
