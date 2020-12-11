package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions;

public class InvalidTimePeriodArgumentException extends InvalidTimePeriodException {

  public InvalidTimePeriodArgumentException(String valid_period) {
    super(String.format("Invalid time period, make a choice between: %s", valid_period));
  }

  public InvalidTimePeriodArgumentException() {
    super("Invalid time period");
  }
}
