package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions;

public class InvalidDateArgumentException extends InvalidUsageReportArgumentException {

  public InvalidDateArgumentException() {
    super("The date provided must be yyyy-MM-dd");
  }
}
