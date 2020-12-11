package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions;

public class InvalidSemesterArgumentException extends InvalidTimePeriodException {

  public InvalidSemesterArgumentException() {
    super("The semester must be in format {A|H|E}XXXX");
  }
}
