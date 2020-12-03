package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions;

public class InvalidSemesterException extends InvalidTimePeriodException {

  public InvalidSemesterException() {
    super("The semester must be in format {A|H|E}XXXX");
  }
}
