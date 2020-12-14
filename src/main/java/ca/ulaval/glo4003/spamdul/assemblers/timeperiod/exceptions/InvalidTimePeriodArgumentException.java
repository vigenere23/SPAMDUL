package ca.ulaval.glo4003.spamdul.assemblers.timeperiod.exceptions;

public class InvalidTimePeriodArgumentException extends InvalidTimePeriodException {

  public String getError() {
    return "INVALID_PERIOD_FORMAT";
  }

  public String getDescription() {
    return "Invalid time period";
  }
}
