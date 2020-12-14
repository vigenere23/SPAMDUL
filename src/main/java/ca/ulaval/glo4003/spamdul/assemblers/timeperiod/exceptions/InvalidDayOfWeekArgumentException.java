package ca.ulaval.glo4003.spamdul.assemblers.timeperiod.exceptions;

public class InvalidDayOfWeekArgumentException extends InvalidTimePeriodException{

  public String getError() {
    return "INVALID_DAY_OF_WEEK_FORMAT";
  }

  public String getDescription() {
    return "Day of the week must be from monday to friday";
  }
}
