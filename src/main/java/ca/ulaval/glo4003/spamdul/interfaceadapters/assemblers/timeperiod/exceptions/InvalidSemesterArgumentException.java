package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions;

public class InvalidSemesterArgumentException extends InvalidTimePeriodException {

  public String getError() {
    return "INVALID_SEMESTER_FORMAT";
  }

  public String getDescription() {
    return "The semester must be in format {A|H|E}XXXX";
  }
}
