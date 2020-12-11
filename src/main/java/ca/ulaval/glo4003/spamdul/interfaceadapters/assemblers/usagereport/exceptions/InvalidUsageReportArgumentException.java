package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions;

public abstract class InvalidUsageReportArgumentException extends RuntimeException {

  protected InvalidUsageReportArgumentException(String message) {
    super(message);
  }
}
