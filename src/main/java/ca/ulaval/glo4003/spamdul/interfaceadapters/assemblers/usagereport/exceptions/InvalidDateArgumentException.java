package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions;

public class InvalidDateArgumentException extends InvalidUsageReportArgumentException {

  public String getError() {
    return "INVALID_DATE";
  }

  public String getDescription() {
    return "The date provided must be yyyy-MM-dd";
  }
}
